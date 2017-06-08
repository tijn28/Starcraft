package eisbw.percepts;

import eis.iilang.Percept;
import eis.iilang.TruthValue;

/**
 * @author Danny & Harm - The Winner percept.
 *
 */
public class WinnerPercept extends Percept {
	private static final long serialVersionUID = 1L;

	/**
	 * The WinnerPercept constructor.
	 *
	 * @param boolean
	 *            true if won, false if lost.
	 */
	public WinnerPercept(boolean winner) {
		super(Percepts.WINNER, new TruthValue(winner));
	}
}
