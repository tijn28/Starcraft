package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

/**
 * @author Danny & Harm - The Region Unit percept.
 *
 * @TODO remove (add to the status percept)
 */
public class RegionUnitPercept extends Percept {
	private static final long serialVersionUID = 1L;

	/**
	 * The RegionUnit constructor.
	 * 
	 * @param queueSize
	 *            The region the unit is in.
	 */
	public RegionUnitPercept(int region) {
		super(Percepts.REGION, new Numeral(region));
	}
}
