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
	 * @param id
	 *            The ID of the unit
	 * @param type
	 *            The unit type
	 * @param conditions
	 *            The current conditions of the unit
	 */
	public FriendlyPercept(int id, String type, List<Parameter> conditions) {
		super(Percepts.FRIENDLY, new Numeral(id), new Identifier(type), new ParameterList(conditions));
	}
}
