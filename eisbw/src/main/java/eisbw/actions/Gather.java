package eisbw.actions;

import bwapi.Mirror;
import bwapi.Unit;
import bwapi.UnitType;
import eis.iilang.Action;
import eis.iilang.Numeral;
import eis.iilang.Parameter;

import java.util.LinkedList;

/**
 * @author Danny & Harm - Makes the unit gather from a specified resource.
 *
 */
public class Gather extends StarcraftAction {

  /**
   * The Gather constructor.
   * 
   * @param api
   *          The BWAPI
   */
  public Gather(Mirror api) {
    super(api);
  }

  @Override
  public boolean isValid(Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    return parameters.size() == 1 && parameters.get(0) instanceof Numeral;
  }

  @Override
  public boolean canExecute(Unit unit, Action action) {
    UnitType unitType = unit.getType();
    return unitType.isWorker();
  }

  @Override
  public void execute(Unit unit, Action action) {
    int targetId = ((Numeral) action.getParameters().get(0)).getValue().intValue();
    unit.gather(api.getGame().getUnit(targetId), false);
  }

  @Override
  public String toString() {
    return "gather(targetID)";
  }
}
