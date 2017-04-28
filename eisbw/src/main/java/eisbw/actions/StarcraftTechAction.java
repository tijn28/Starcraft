package eisbw.actions;

import bwapi.Mirror;
import bwapi.TechType;
import bwapi.Unit;
import eis.iilang.Action;
import eis.iilang.Identifier;
import eis.iilang.Parameter;

import java.util.LinkedList;

/**
 * @author Danny & Harm - Abstract class for Tech actions.
 *
 */
public abstract class StarcraftTechAction extends StarcraftAction {

  /**
   * The StarcraftTechAction constructor.
   * 
   * @param api
   *          The BWAPI
   */
  public StarcraftTechAction(Mirror api) {
    super(api);
  }

  @Override
  public boolean isValid(Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    if (parameters.size() == 1) { // type
      return parameters.get(0) instanceof Identifier
          && getTechType(((Identifier) parameters.get(0)).getValue()) != null;
    }

    return false;
  }

  @Override
  public boolean canExecute(Unit unit, Action action) {
    if (!unit.isLoaded()) {
      LinkedList<Parameter> parameters = action.getParameters();
      TechType techType = getTechType(((Identifier) parameters.get(0)).getValue());

      return !techType.targetsPosition() && !techType.targetsUnit();
    }
    return false;
  }
}
