package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

/**
 * @author Danny & Harm - The Frame percept.
 *
 */
public class FramePercept extends Percept {
	private static final long serialVersionUID = 1L;

	/**
	 * The FramePercept constructor.
	 *
	 * @param frame
	 *            The frame number
	 */
	public FramePercept(int frame) {
		super(Percepts.FRAME, new Numeral(frame));
	}
}
