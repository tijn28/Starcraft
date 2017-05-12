package eisbw;

import bwapi.Mirror;
import bwapi.Race;
import bwapi.Unit;
import bwapi.UnitType;
import bwta.BWTA;
import eis.exceptions.ActException;
import eis.iilang.Action;
import eisbw.actions.ActionProvider;
import eisbw.actions.StarcraftAction;
import eisbw.debugger.DebugWindow;
import eisbw.units.StarcraftUnitFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Danny & Harm - The Listener of the BWAPI Events.
 *
 */
public class BwapiListener extends BwapiEvents {

  protected Logger logger = Logger.getLogger("StarCraft Logger");
  protected Mirror bwapi;
  protected Game game;
  protected ActionProvider actionProvider;
  protected Map<Unit, Action> pendingActions;
  protected StarcraftUnitFactory factory;
  protected UpdateThread updateThread;
  protected boolean debugmode;
  protected boolean invulnerable;
  protected int speed;
  protected int count = 0;
  protected DebugWindow debug;

  // // temp
  // private int fps = 0;

  /**
   * Event listener for BWAPI.
   * 
   * @param game
   *          - the game data class
   * @param debugmode
   *          - true iff debugger should be attached
   */
  public BwapiListener(Game game, boolean debugmode, boolean invulnerable, int speed) {
    bwapi = new Mirror();
    bwapi.getModule().setEventListener(this);
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
        bwapi.startGame();
      }
    }.start();

  }

  @Override
  public void onStart() {

    BWTA.readMap();
    BWTA.analyze(); 
    
    updateThread = new UpdateThread(game, bwapi);
    updateThread.start();
    game.updateConstructionSites(bwapi);
    game.updateMap(bwapi);

    // SET INIT SPEED (DEFAULT IS 1000/20=50 FPS)
    if (speed > 0) {
      bwapi.getGame().setLocalSpeed(1000 / speed);
    } else {
      bwapi.getGame().setLocalSpeed(speed);
    }
    // SET INIT INVULNERABLE PARAMETER
    if (invulnerable) {
      bwapi.getGame().sendText("power overwhelming");
    }

    // START THE DEBUG TOOLS.
    if (debugmode) {
      debug = new DebugWindow(game);
     // bwapi.getGame().drawTargets(true);
     // bwapi.getGame().enableUserInput();
    }
  }

  @Override
  public void onFrame() {
    Iterator<Entry<Unit, Action>> it = pendingActions.entrySet().iterator();
    while (it.hasNext()) {
      Entry<Unit, Action> entry = it.next();
      Unit unit = entry.getKey();
      Action act = entry.getValue();

      StarcraftAction action = getAction(act);
      action.execute(unit, act);

      it.remove();
    }

    if (debug != null) {
      debug.debug(bwapi);
    }

    if (count == 200) {
      game.updateConstructionSites(bwapi);
      count = 0;
    }
    count++;

    // // Check FPS
    // if (fps == 1000) {
    // System.out.println("The Current FPS: " + fps / getGameSpeed());
    // fps = 0;
    // }
    //
    // fps++;
  }

  @Override
  public void onUnitDestroy(Unit unit) {
    int id = unit.getID();
    if (game.getUnits().getUnitNames().containsKey(id)) {
      String unitName = game.getUnits().getUnitNames().get(id);
      game.getUnits().deleteUnit(unitName, id);
    }
  }

  @Override
  public void onUnitComplete(Unit unit2) {
    System.out.println("Got Unit!!");
    int unitId = unit2.getID();
    Unit unit = bwapi.getGame().getUnit(unitId);
    if (containsUnit(bwapi.getGame().self().getUnits(),unit) && !game.getUnits().getUnitNames().containsKey(unitId)) {
      game.getUnits().addUnit(unit, factory);
    }
  }
  
  private boolean containsUnit(List<Unit> unitlist,Unit unit){
    for(Unit u : unitlist){
      if (u.getID() == unit.getID()){
        return true;
      }
    }
    return false;
  }

  @Override
  public void onUnitMorph(Unit unit) {
    int id = unit.getID();
    if (!bwapi.getGame().self().getRace().toString().equals(Race.Zerg.toString())) {
      return;
    }
    onUnitDestroy(unit);
    if (!bwapi.getGame().getUnit(id).getType().toString().equals(UnitType.Zerg_Zergling)) {
      onUnitComplete(unit);
    }
  }

  @Override
  public void onEnd(boolean winner) {
    if (winner) {
      game.updateEndGamePerceiver(bwapi);
      game.setEndGame(winner);
    }
    // have the winner percept perceived for 2 seconds before all agents
    // are removed
    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    updateThread.terminate();
    try {
      updateThread.join();
    } catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
    }
    pendingActions = new ConcurrentHashMap<>();
    if (debug != null) {
      debug.dispose();
    }
    game.clean();
  }

  protected boolean isSupportedByEntity(Action act, String name) {
    Unit unit = game.getUnits().getUnits().get(name);

    StarcraftAction action = getAction(act);
    return action != null && action.isValid(act) && action.canExecute(unit, act);
  }

  /**
   * @param action
   *          The inserted requested action.
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
    return debug.getFPS();
  }

  /**
   * Adds an action to the action queue, the action is then executed on the next
   * frame.
   * 
   * @param name
   *          - the name of the unit.
   * @param act
   *          - the action.
   * @throws ActException
   *           - mandatory from EIS
   */
  public void performEntityAction(String name, Action act) throws ActException {
    Unit unit = game.getUnits().getUnits().get(name);

    // cant act during construction
    // if (!unit.isBeingConstructed()) {
    StarcraftAction action = getAction(act);
    // Action might be invalid
    if (action.isValid(act) && isSupportedByEntity(act, name)) {
      pendingActions.put(unit, act);
    } else {
      logger.log(Level.WARNING,
          "The Entity: " + name + " is not able to perform the action: " + act.getName());
    }
    // }

  }

}
