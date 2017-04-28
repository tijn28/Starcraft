package eisbw.actions;

import bwapi.Mirror;
import bwapi.Unit;
import eis.iilang.Action;
import eis.iilang.Numeral;

/**
 * @author Danny & Harm - Loads a unit into a specified other unit.
 *
 */
public class Load extends StarcraftLoadingAction {

  /**
   * The Load constructor.
   * 
   * @param api
   *          The BWAPI
   */
  public Load(Mirror api) {
    super(api);
  }

  @Override
  public void execute(Unit unit, Action action) {
    Unit target = api.getGame().getUnit(((Numeral) action.getParameters().get(0)).getValue().intValue());
    unit.load(target, false);
  }

  @Override
  public String toString() {
    return "load(targetID)";
  }
}
