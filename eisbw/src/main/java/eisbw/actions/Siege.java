package eisbw.actions;

import bwapi.Mirror;
import bwapi.Unit;
import eis.iilang.Action;
import eis.iilang.Parameter;

import java.util.LinkedList;

/**
 * @author Danny & Harm - Sieges a Siege Tank.
 *
 */
public class Siege extends StarcraftAction {

  /**
   * The Siege constructor.
   * 
   * @param api
   *          The BWAPI
   */
  public Siege(Mirror api) {
    super(api);
  }

  @Override
  public boolean isValid(Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    return parameters.isEmpty();
  }

  @Override
  public boolean canExecute(Unit unit, Action action) {
    return "Terran Siege Tank Tank Mode".equals(unit.getType().toString());
  }

  @Override
  public void execute(Unit unit, Action action) {
    unit.siege();
  }

  @Override
  public String toString() {
    return "siege()";
  }
}
