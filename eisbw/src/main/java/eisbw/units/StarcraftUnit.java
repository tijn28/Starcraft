package eisbw.units;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import eis.iilang.Percept;
import eisbw.percepts.perceivers.IPerceiver;
import eisbw.percepts.perceivers.PerceptFilter;

/**
 * @author Danny & Harm - The class which gathers all the percepts of a
 *         starcraft unit.
 *
 */
public class StarcraftUnit {
	protected final List<IPerceiver> perceivers;
	private final boolean worker;

	/**
	 * A starcraft unit with perceivers.
	 *
	 * @param perceivers
	 *            - list with perceivers to percept from.
	 * @param worker
	 *            - true iff the unit is a worker
	 */
	public StarcraftUnit(List<IPerceiver> perceivers, boolean worker) {
		this.perceivers = perceivers;
		this.worker = worker;
	}

	/**
	 * Perceive this units' percepts.
	 *
	 * @return - a list of percepts.
	 */
	public Map<PerceptFilter, Set<Percept>> perceive() {
		Map<PerceptFilter, Set<Percept>> toReturn = new HashMap<>();
		for (IPerceiver perceiver : this.perceivers) {
			perceiver.perceive(toReturn);
		}
		return toReturn;
	}

	public boolean isWorker() {
		return this.worker;
	}
}
