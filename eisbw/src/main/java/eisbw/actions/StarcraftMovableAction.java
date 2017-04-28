package eisbw.actions;

import bwapi.Mirror;
import bwapi.Unit;
import eis.iilang.Action;
import eis.iilang.Numeral;
import eis.iilang.Parameter;

import java.util.LinkedList;

/**
 * @author Danny & Harm - Abstract class for some of the Movable actions.
 *
 */
public abstract class StarcraftMovableAction extends StarcraftAction {

  /**
   * The Starcraft MovableAction constructor.
   * 
   * @param api
   *          The BWAPI.
   */
  public StarcraftMovableAction(Mirror api) {
    super(api);
  }

  @Override
  public boolean isValid(Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    return parameters.size() == 2 && parameters.get(0) instanceof Numeral
        && parameters.get(1) instanceof Numeral;
  }

  @Override
  public boolean canExecute(Unit unit, Action action) {
    return unit.getType().canMove();
  }
}
