package eisbw;

import eisbw.units.Units;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Danny & Harm - The thread which handles all the percept updates.
 *
 */
public class UpdateThread extends Thread {
	private final Game game;
	private final JNIBWAPI bwapi;
	private volatile boolean running;
	private final Units units;

	/**
	 * Constructs a thread.
	 * 
	 * @param game
	 *            - the game data class.
	 * @param bwapi
	 *            - the API.
	 */
	public UpdateThread(Game game, JNIBWAPI bwapi) {
		this.bwapi = bwapi;
		this.game = game;
		this.units = game.getUnits();
		running = true;
	}

	/**
	 * Terminate the thread.
	 */
	public void terminate() {
		synchronized (this) {
			running = false;
			notifyAll();
		}
	}

	@Override
	public void run() {
		Thread.currentThread().setName("Update thread");
		synchronized (this) {
			while (running) {
				update();
				try {
					wait();
				} catch (InterruptedException ex) {
					break;
				}
			}
		}
	}

	protected void update() {
		game.update(bwapi);
		if (units.getUninitializedUnits() != null) {
			List<Unit> toAdd = new LinkedList<>();
			Unit unit;
			while ((unit = units.getUninitializedUnits().poll()) != null) {
				String unitName = BwapiUtility.getUnitName(unit);
				if (unit.isCompleted() && game.isInitialized(unitName)) {
					game.getEnvironment().addToEnvironment(unitName, BwapiUtility.getEisUnitType(unit));
				} else {
					toAdd.add(unit);
				}
			}
			units.getUninitializedUnits().addAll(toAdd);
		}
	}
}
