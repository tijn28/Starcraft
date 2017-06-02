package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

/**
 * @author Danny & Harm - The Unit percept which gives information about the
 *         other units.
 *
 */
public class NewUnitPercept extends Percept {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor NewUnitPercept.
	 * 
	 * 
	 * @param id
	 *            The ID of the unit
	 * @param x
	 *            The (initial) X coordinate of the location of the unit
	 * @param y
	 *            The (initial) Y coordinate of the location of the unit
	 */
	public NewUnitPercept(int id, int x, int y) {
		super(Percepts.NEWUNIT, new Numeral(id), new Numeral(x), new Numeral(y));
	}
}
