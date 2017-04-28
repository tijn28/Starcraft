package eisbw.actions;

import bwapi.Mirror;
import bwapi.Unit;
import eis.iilang.Action;
import eis.iilang.Numeral;
import eis.iilang.Parameter;

import java.util.LinkedList;

/**
 * @author Danny & Harm - Right clicks on a specified unit. (testing purposes)
 *
 */
public class RightClickUnit extends StarcraftAction {

  /**
   * The RightClickUnit constructor.
   * 
   * @param api
   *          The BWAPI.
   */
  public RightClickUnit(Mirror api) {
    super(api);
  }

  @Override
  public boolean isValid(Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    return parameters.size() == 1 && parameters.get(0) instanceof Numeral;
  }

  @Override
  public boolean canExecute(Unit unit, Action action) {
    return true;
  }

  @Override
  public void execute(Unit unit, Action action) {
    Unit target = api.getGame().getUnit(((Numeral) action.getParameters().get(0)).getValue().intValue());
    unit.rightClick(target, false);
  }

  @Override
  public String toString() {
    return "rightClick(targetId)";
  }
}
