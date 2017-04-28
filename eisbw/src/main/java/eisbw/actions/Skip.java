package eisbw.actions;

import bwapi.Mirror;
import bwapi.Unit;
import eis.iilang.Action;
import eis.iilang.Parameter;

import java.util.LinkedList;

/**
 * @author Danny & Harm - A action which does nothing the skip a frame.
 *
 */
public class Skip extends StarcraftAction {

  /**
   * The Skip constructor.
   * 
   * @param api
   *          The BWAPI
   */
  public Skip(Mirror api) {
    super(api);
  }

  @Override
  public boolean isValid(Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    return parameters.isEmpty();
  }

  @Override
  public boolean canExecute(Unit unit, Action action) {
    return true;
  }

  @Override
  public void execute(Unit unit, Action action) {
    // Skip action, thus executes nothing.
  }

  @Override
  public String toString() {
    return "skip";
  }
}
