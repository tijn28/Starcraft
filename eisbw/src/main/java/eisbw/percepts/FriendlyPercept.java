package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import eis.iilang.ParameterList;
import eis.iilang.Percept;

import java.util.List;

/**
 * @author Danny & Harm - The Unit percept which gives information about the
 *         other units.
 *
 */
public class FriendlyPercept extends Percept {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor FriendlyPercept.
	 * 
	 * @param isFriendly
	 *            Indicates whether the unit is friendly or not
	 * @param type
	 *            The unit type
	 * @param id
	 *            The ID of the unit
	 * @param health
	 *            The amount of health of the unit
	 * @param shields
	 *            The amount of shields of the unit
	 * @param conditions
	 *            The current conditions of the unit
	 */
	public FriendlyPercept(String type, int id, List<Parameter> conditions) {
		super(Percepts.FRIENDLY, new Identifier(type), new Numeral(id), new ParameterList(conditions));
	}

}
