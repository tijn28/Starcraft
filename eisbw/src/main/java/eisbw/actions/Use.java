package eisbw.actions;

import bwapi.Mirror;
import bwapi.TechType;
import bwapi.Unit;
import eis.iilang.Action;
import eis.iilang.Identifier;
import eis.iilang.Parameter;

import java.util.LinkedList;

/**
 * @author Danny & Harm - Use a researched TechType.
 *
 */
public class Use extends StarcraftTechAction {

  /**
   * The Use constructor.
   * 
   * @param api
   *          The BWAPI.
   */
  public Use(Mirror api) {
    super(api);
  }

  @Override
  public void execute(Unit unit, Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    TechType techType = getTechType(((Identifier) parameters.get(0)).getValue());
    unit.useTech(techType);
  }

  @Override
  public String toString() {
    return "ability(Type)";
  }
}
