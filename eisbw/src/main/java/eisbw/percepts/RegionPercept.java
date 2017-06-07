package eisbw.percepts;

import java.util.List;

import eis.iilang.Numeral;
import eis.iilang.Parameter;
import eis.iilang.ParameterList;
import eis.iilang.Percept;

/**
 * @author Danny & Harm - The Chokepoint/4 percept.
 *
 */
public class RegionPercept extends Percept {
	private static final long serialVersionUID = 1L;

	/**
	 * @param id
	 *            The identifier of the region.
	 * @param height
	 *            The height of the region.
	 * @param polygon
	 *            A list of [x,y] combinations describing the region.
	 */
	public RegionPercept(int id, int height, List<Parameter> polygon) {
		super(Percepts.REGION, new Numeral(id), new Numeral(height), new ParameterList(polygon));
	}
}
