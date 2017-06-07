package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

/**
 * @author Danny & Harm - The Space Provided percept.
 *
 * @TODO remove (can be derived from the unitLoaded percept)
 */
public class SpaceProvidedPercept extends Percept {
	private static final long serialVersionUID = 1L;

	/**
	 * The SpaceProvidedPercept.
	 * 
	 * @param used
	 *            The already used amount of space.
	 * @param max
	 *            The maximum amount of space available.
	 */
	public SpaceProvidedPercept(int used, int max) {
		super(Percepts.SPACEPROVIDED, new Numeral(used), new Numeral(max));
	}
}
