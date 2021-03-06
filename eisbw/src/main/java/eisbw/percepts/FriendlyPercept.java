package eisbw.percepts;

import java.util.List;

import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import eis.iilang.ParameterList;
import eis.iilang.Percept;

/**
 * @author Danny & Harm - The Friendly percept which gives information about
 *         your own units.
 *
 */
public class FriendlyPercept extends Percept {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor FriendlyPercept.
	 *
	 * @param type
	 *            The unit type (TODO: maybe move type to newunit?)
	 * @param id
	 *            The ID of the unit
	 * @param conditions
	 *            The current conditions of the unit
	 */
	public FriendlyPercept(String type, int id, List<Parameter> conditions) {
		super(Percepts.FRIENDLY, new Identifier(type), new Numeral(id), new ParameterList(conditions));
	}
}
