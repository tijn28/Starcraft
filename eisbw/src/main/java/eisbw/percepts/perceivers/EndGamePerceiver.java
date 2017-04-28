package eisbw.percepts.perceivers;

import eis.eis2java.translation.Filter;
import eis.iilang.Percept;
import eisbw.percepts.Percepts;
import eisbw.percepts.WinnerPercept;
import bwapi.Mirror;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Danny & Harm - The perceiver which handles the events.
 *
 */
public class EndGamePerceiver extends Perceiver {

	/**
	 * The BufferPerceiver constructor.
	 * 
	 * @param api
	 *            The BWAPI
	 */
	public EndGamePerceiver(Mirror api) {
		super(api);
	}

	@Override
	public Map<PerceptFilter, Set<Percept>> perceive(Map<PerceptFilter, Set<Percept>> toReturn) {
		Set<Percept> percepts = new HashSet<>();
		percepts.add(new WinnerPercept());
		toReturn.put(new PerceptFilter(Percepts.WINNER, Filter.Type.ALWAYS), percepts);
		return toReturn;
	}

}
