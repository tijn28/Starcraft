package eisbw.actions;

import eis.iilang.Action;
import eisbw.BwapiUtility;
import bwapi.Mirror;
import bwapi.Unit;
import bwapi.TechType;
import bwapi.UnitType;
import bwapi.UpgradeType;

/**
 * @author Danny & Harm - Abstract class for all the actions.
 *
 */
public abstract class StarcraftAction {

  protected Mirror api;

  /**
   * The StarcraftAction constructor.
   * 
   * @param api
   *          The BWAPI
   */
  public StarcraftAction(Mirror api) {
    this.api = api;
  }

  protected UpgradeType getUpgradeType(String type) {
    return BwapiUtility.getUpgradeType(type);
  }

  protected TechType getTechType(String type) {
    return BwapiUtility.getTechType(type);
  }

  protected UnitType getUnitType(String type) {
    return BwapiUtility.getUnitType(type);
  }

  /**
   * @param action
   *          The evaluated action.
   * @return A boolean which indicates whether the parameters of the action are
   *         valid.
   */
  public abstract boolean isValid(Action action);

  /**
   * @param unit
   *          The unit performing the action.
   * @param action
   *          The evaluated action.
   * @return A boolean which indicated wheter the specified unit can execute the
   *         action.
   */
  public abstract boolean canExecute(Unit unit, Action action);

  /**
   * @param unit
   *          The unit performing the action.
   * @param action
   *          The evaluated action.
   */
  public abstract void execute(Unit unit, Action action);

  @Override
  public abstract String toString();
}
