package eisbw.actions;

import bwapi.Mirror;
import bwapi.Unit;
import bwapi.UnitType;
import eis.iilang.Action;
import eis.iilang.Identifier;
import eis.iilang.Parameter;

import java.util.LinkedList;

/**
 * @author Danny & Harm - Trains a specified unit from a production facility.
 *
 */
public class Train extends StarcraftAction {

  /**
   * The Train constructor.
   * 
   * @param api
   *          The BWAPI
   */
  public Train(Mirror api) {
    super(api);
  }

  @Override
  public boolean isValid(Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    if (parameters.size() == 1) {
      return parameters.get(0) instanceof Identifier
          && getUnitType(((Identifier) parameters.get(0)).getValue()) != null;
    }
    return false;
  }

  @Override
  public boolean canExecute(Unit unit, Action action) {
    return unit.getType().canProduce();
  }

  @Override
  public void execute(Unit unit, Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    UnitType unitType = getUnitType(((Identifier) parameters.get(0)).getValue());
    unit.train(unitType);
  }

  @Override
  public String toString() {
    return "train(Type)";
  }
}
