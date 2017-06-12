package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

/**
 * @author Danny & Harm - The Unit Region percept.
 *
 * @TODO remove (add to the status percept)
 */
public class UnitRegionPercept extends Percept {
	private static final long serialVersionUID = 1L;

	/**
	 * The UnitRegion constructor.
	 *
	 * @param unitId
	 *            The id of the unit
	 * @param region
	 *            The id of the region the unit is in.
	 */
	public UnitRegionPercept(int unitId, int region) {
		super(Percepts.UNITREGION, new Numeral(unitId), new Numeral(region));
	}
}
