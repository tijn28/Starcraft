package eisbw.actions;

import bwapi.Mirror;
import bwapi.Unit;
import eis.iilang.Action;
import eis.iilang.Numeral;

/**
 * @author Danny & Harm - Unloads a specified unit.
 *
 */
public class UnloadUnit extends StarcraftLoadingAction {

  /**
   * The UnloadUnit constructor.
   * 
   * @param api
   *          The BWAPI
   */
  public UnloadUnit(Mirror api) {
    super(api);
  }

  @Override
  public void execute(Unit unit, Action action) {
    Unit target = api.getGame().getUnit(((Numeral) action.getParameters().get(0)).getValue().intValue());
    unit.unload(target);
  }

  @Override
  public String toString() {
    return "unload(targetId)";
  }
}
