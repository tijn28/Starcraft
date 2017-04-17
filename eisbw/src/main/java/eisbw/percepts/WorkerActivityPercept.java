package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Percept;

/**
 * @author Danny & Harm - The Worker Activity percept.
 *
 */
public class WorkerActivityPercept extends Percept {

  private static final long serialVersionUID = 1L;

  /**
   * The WorkerActivityPercept constructor.
   * 
   * @param activity
   *          The current activity of the worker unit.
   */
  public WorkerActivityPercept(String activity) {
    super(Percepts.WORKERACTIVITY, new Identifier(activity));
  }
}
