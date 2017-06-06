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
	 * The GameSpeedPercept constructor.
	 * 
	 * @param speed
	 *            The current game FPS
	 */
	public GameSpeedPercept(int speed) {
		super(Percepts.GAMESPEED, new Numeral(speed));
	}
}
