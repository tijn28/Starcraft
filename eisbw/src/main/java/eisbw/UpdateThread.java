package eisbw;

import java.util.LinkedList;
import java.util.List;

import eisbw.units.Units;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

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
		this.running = true;
	}

	/**
	 * Terminate the thread.
	 */
	public void terminate() {
		synchronized (this) {
			this.running = false;
			notifyAll();
		}
	}

	@Override
	public void run() {
		Thread.currentThread().setName("Update thread");
		synchronized (this) {
			while (this.running) {
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
		this.game.update(this.bwapi);
		if (this.units.getUninitializedUnits() != null) {
			List<Unit> toAdd = new LinkedList<>();
			Unit unit;
			while ((unit = this.units.getUninitializedUnits().poll()) != null) {
				String unitName = BwapiUtility.getUnitName(unit);
				if (unit.isCompleted() && this.game.isInitialized(unitName)) {
					this.game.getEnvironment().addToEnvironment(unitName, BwapiUtility.getEisUnitType(unit));
				} else {
					toAdd.add(unit);
				}
			}
			this.units.getUninitializedUnits().addAll(toAdd);
		}
	}
}
