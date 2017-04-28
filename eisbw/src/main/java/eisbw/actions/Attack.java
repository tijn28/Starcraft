package eisbw.actions;

import bwapi.Mirror;
import bwapi.Unit;
import bwapi.UnitType;
import eis.iilang.Action;
import eis.iilang.Numeral;
import eis.iilang.Parameter;

import java.util.LinkedList;

/**
 * @author Danny & Harm - Makes the unit attack the specified unit.
 *
 */
public class Attack extends StarcraftAction {

  /**
   * The Attack constructor.
   * 
   * @param api
   *          The BWAPI
   */
  public Attack(Mirror api) {
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
    return unitType.canAttack();
  }

  @Override
  public void execute(Unit unit, Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    int targetId = ((Numeral) parameters.get(0)).getValue().intValue();
    Unit target = api.getGame().getUnit(targetId);
    unit.attack(target, false);
  }

  @Override
  public String toString() {
    return "attack(targetId)";
  }
}
