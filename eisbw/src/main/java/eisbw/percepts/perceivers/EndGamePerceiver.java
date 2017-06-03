package eisbw.percepts.perceivers;

import eis.eis2java.translation.Filter;
import eis.iilang.Percept;
import eisbw.percepts.Percepts;
import eisbw.percepts.WinnerPercept;
import jnibwapi.JNIBWAPI;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Danny & Harm - The perceiver which handles the events.
 *
 */
public class EndGamePerceiver extends Perceiver {
	private final boolean winner;
	/**
	 * The EndGamePerceiver constructor.
	 * 
	 */
	public EndGamePerceiver(JNIBWAPI api, boolean winner) {
		super(api);
		this.winner = winner;
	}

	@Override
	public Map<PerceptFilter, Set<Percept>> perceive(Map<PerceptFilter, Set<Percept>> toReturn) {
		Set<Percept> percepts = new HashSet<>();
		percepts.add(new WinnerPercept(winner));
		toReturn.put(new PerceptFilter(Percepts.WINNER, Filter.Type.ALWAYS), percepts);
		return toReturn;
	}

}
