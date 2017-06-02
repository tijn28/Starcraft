package eisbw.percepts.perceivers;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import eis.eis2java.translation.Filter;
import eis.iilang.Percept;
import eisbw.percepts.NukePercept;
import eisbw.percepts.Percepts;
import jnibwapi.JNIBWAPI;
import jnibwapi.Position;

/**
 * @author Danny & Harm - The perceiver which handles the events.
 *
 */
public class NukePerceiver extends Perceiver {
	private final Position pos;

	/**
	 * The NukePerceiver constructor.
	 * 
	 * @param api
	 *            The BWAPI
	 */
	public NukePerceiver(JNIBWAPI api, Position pos) {
		super(api);
		this.pos = pos;
	}

	@Override
	public Map<PerceptFilter, Set<Percept>> perceive(Map<PerceptFilter, Set<Percept>> toReturn) {
		Set<Percept> percepts = new HashSet<>();
		percepts.add(new NukePercept(pos.getBX(), pos.getBY()));
		toReturn.put(new PerceptFilter(Percepts.NUKE, Filter.Type.ON_CHANGE), percepts);
		return toReturn;
	}

}
