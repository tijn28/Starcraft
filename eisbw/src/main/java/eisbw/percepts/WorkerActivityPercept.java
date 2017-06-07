package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Percept;

/**
 * @author Danny & Harm - The Worker Activity percept.
 *
 * @TODO remove (can be derived from the friendly percept)
 */
public class WorkerActivityPercept extends Percept {
	private static final long serialVersionUID = 1L;

	/**
	 * The WorkerActivityPercept constructor.
	 * 
	 * @param activity
	 *            The current activity of the worker unit.
	 * 
	 * @FIXME can be removed now because of the conditions in the state percept?
	 */
	public WorkerActivityPercept(String activity) {
		super(Percepts.WORKERACTIVITY, new Identifier(activity));
	}
}
