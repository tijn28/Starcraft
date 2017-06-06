package eisbw;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import eis.exceptions.ActException;
import eis.iilang.Action;
import eisbw.actions.ActionProvider;
import eisbw.actions.StarcraftAction;
import eisbw.debugger.DebugWindow;
import eisbw.units.StarcraftUnitFactory;
import jnibwapi.JNIBWAPI;
import jnibwapi.Position;
import jnibwapi.Unit;
import jnibwapi.types.RaceType;

/**
 * @author Danny & Harm - The Listener of the BWAPI Events.
 * 
 */
public class BwapiListener extends BwapiEvents {
	protected final Logger logger = Logger.getLogger("StarCraft Logger");
	protected JNIBWAPI bwapi; // overridden in test
	protected final Game game;
	protected final ActionProvider actionProvider;
	protected final Map<Unit, Action> pendingActions;
	protected final StarcraftUnitFactory factory;
	protected final boolean debugmode;
	protected final boolean invulnerable;
	protected final int speed;
	protected UpdateThread updateThread;
	protected int count = 0;
	protected int nuke = -1;
	protected DebugWindow debug;

	/**
	 * Event listener for BWAPI.
	 * 
	 * @param game
	 *            - the game data class
	 * @param debugmode
	 *            - true iff debugger should be attached
	 */
	public BwapiListener(Game game, String scDir, boolean debugmode, boolean invulnerable, int speed) {
		File bwta = new File(scDir + File.separator + "bwapi-data" + File.separator + "BWTA");
		bwapi = new JNIBWAPI(this, bwta);
		this.game = game;
		actionProvider = new ActionProvider();
		actionProvider.loadActions(bwapi);
		pendingActions = new ConcurrentHashMap<>();
		factory = new StarcraftUnitFactory(bwapi);
		this.debugmode = debugmode;
		this.invulnerable = invulnerable;
		this.speed = speed;

		new Thread() {
			@Override
			public void run() {
				Thread.currentThread().setPriority(MAX_PRIORITY);
				Thread.currentThread().setName("BWAPI thread");
				bwapi.start();
			}
		}.start();
	}

	@Override
	public void matchStart() {
		updateThread = new UpdateThread(game, bwapi);
		updateThread.start();
		game.updateConstructionSites(bwapi);
		game.updateMap(bwapi);

		// SET INIT SPEED (DEFAULT IS 50 FPS, WHICH IS 20 SPEED)
		if (speed > 0)
			bwapi.setGameSpeed(1000 / speed);
		else if (speed == 0)
			bwapi.setGameSpeed(speed);

		// SET INIT INVULNERABLE PARAMETER
		if (invulnerable)
			bwapi.sendText("power overwhelming");

		// START THE DEBUG TOOLS.
		if (debugmode) {
			debug = new DebugWindow(game);
			bwapi.drawTargets(true);
			bwapi.enableUserInput();
		}
	}

	@Override
	public void matchFrame() {
		for (final Unit unit : pendingActions.keySet()) {
			Action act = pendingActions.remove(unit);
			StarcraftAction action = getAction(act);
			action.execute(unit, act);
		}

		if (debug != null) {
			debug.debug(bwapi);
		}

		if (count++ == 50) {
			game.updateConstructionSites(bwapi);
			count = 0;
		}

		if (nuke > 0 && nuke++ == 50) {
			game.updateNukePerceiver(bwapi, null);
			nuke = -1;
		}

		signalUpdateThread();
	}

	private void signalUpdateThread() {
		if (this.updateThread != null) {
			synchronized (this.updateThread) {
				this.updateThread.notifyAll();
			}
		}
	}

	@Override
	public void unitComplete(int id) {
		Unit unit = bwapi.getUnit(id);
		if (bwapi.getMyUnits().contains(unit) && !game.getUnits().getUnitNames().containsKey(id)) {
			game.getUnits().addUnit(unit, factory);
			signalUpdateThread();
		}
	}

	@Override
	public void unitDestroy(int id) {
		String unitName = game.getUnits().getUnitNames().get(id);
		if (unitName != null) {
			Unit deleted = game.getUnits().deleteUnit(unitName, id);
			pendingActions.remove(deleted);
			signalUpdateThread();
		}
	}

	@Override
	public void unitMorph(int id) {
          Unit unit = bwapi.getUnit(id);
          if(unit.getType().getRaceID() == RaceType.RaceTypes.Terran.getID()) return;
	  
		unitDestroy(id);
		unitComplete(id);
	}

	@Override
	public void unitRenegade(int id) {
		unitDestroy(id);
	}

	@Override
	public void nukeDetect(Position pos) {
		game.updateNukePerceiver(bwapi, pos);
		nuke = 0;
	}

	@Override
	public void matchEnd(boolean winner) {
		game.updateEndGamePerceiver(bwapi, winner);
		signalUpdateThread();

		// have the winner percept perceived for 1 second before all agents
		// are removed
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException ignore) {
		}
		updateThread.terminate();
		try {
			updateThread.join();
		} catch (InterruptedException ignore) {
		}
		pendingActions.clear();
		if (debug != null) {
			debug.dispose();
		}
		bwapi.leaveGame();
		game.clean();
	}

	protected boolean isSupportedByEntity(Action act, String name) {
		Unit unit = game.getUnits().getUnits().get(name);
		StarcraftAction action = getAction(act);
		return action != null && action.isValid(act) && action.canExecute(unit, act);
	}

	/**
	 * @param action
	 *            The inserted requested action.
	 * @return The requested Starcraft Action.
	 */
	public StarcraftAction getAction(Action action) {
		return actionProvider.getAction(action.getName() + "/" + action.getParameters().size());
	}

	/**
	 * Returns the current FPS.
	 * 
	 * @return the current FPS.
	 */
	public int getFPS() {
		return (debug == null) ? speed : debug.getFPS();
	}

	/**
	 * Adds an action to the action queue, the action is then executed on the
	 * next frame.
	 * 
	 * @param name
	 *            - the name of the unit.
	 * @param act
	 *            - the action.
	 * @throws ActException
	 *             - mandatory from EIS
	 */
	public void performEntityAction(String name, Action act) throws ActException {
		Unit unit = game.getUnits().getUnits().get(name);
		if (isSupportedByEntity(act, name)) {
			pendingActions.put(unit, act);
		} else {
			logger.log(Level.WARNING, "The Entity: " + name + " is not able to perform the action: " + act.getName());
		}
	}
}
