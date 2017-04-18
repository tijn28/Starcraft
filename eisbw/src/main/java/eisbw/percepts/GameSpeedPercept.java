package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

/**
 * @author Danny & Harm - The GameSpeed percept.
 *
 */
public class GameSpeedPercept extends Percept {

	private static final long serialVersionUID = 1L;

	/**
	 * The MapPercept constructor.
	 * 
	 * @param width
	 *            The width of the map
	 * @param height
	 *            The height of the map
	 */
	public GameSpeedPercept(int speed) {
		super(Percepts.GAMESPEED, new Numeral(speed));
	}
}
