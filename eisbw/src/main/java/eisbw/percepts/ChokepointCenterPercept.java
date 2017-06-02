package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

/**
 * @author Danny & Harm - The Chokepoint/3 percept.
 *
 */
public class ChokepointCenterPercept extends Percept {
	private static final long serialVersionUID = 1L;

	/**
	 * @param xpos
	 *            The x coordinate of the center of the chokepoint.
	 * @param ypos
	 *            The y coordinate of the center of the chokepoint.
	 * @param width
	 *            The width of the chokepoint.
	 */
	public ChokepointCenterPercept(int xpos, int ypos, int width) {
		super(Percepts.CHOKEPOINT, new Numeral(xpos), new Numeral(ypos), new Numeral(width));
	}
}
