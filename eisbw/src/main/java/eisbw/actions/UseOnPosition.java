package eisbw.actions;

import bwapi.Mirror;
import bwapi.Position;
import bwapi.TechType;
import bwapi.Unit;
import eis.iilang.Action;
import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;

import java.util.LinkedList;

/**
 * @author Danny & Harm - Ability which can be used on a specified location.
 *
 */
public class UseOnPosition extends StarcraftAction {

  /**
   * The UseOnPosition constructor.
   * 
   * @param api
   *          The BWAPI.
   */
  public UseOnPosition(Mirror api) {
    super(api);
  }

  @Override
  public boolean isValid(Action action) {
    LinkedList<Parameter> parameters = action.getParameters();

    if (parameters.size() == 3) { // type, x, y
      boolean isTechType = parameters.get(0) instanceof Identifier
          && getTechType(((Identifier) parameters.get(0)).getValue()) != null;

      return isTechType && parameters.get(1) instanceof Numeral
          && parameters.get(2) instanceof Numeral;
    }

    return false;
  }

  @Override
  public boolean canExecute(Unit unit, Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    TechType techType = getTechType(((Identifier) parameters.get(0)).getValue());
    return techType.targetsPosition();
  }

  @Override
  public void execute(Unit unit, Action action) {
    LinkedList<Parameter> parameters = action.getParameters();

    TechType techType = getTechType(((Identifier) parameters.get(0)).getValue());

    int xpos = ((Numeral) parameters.get(1)).getValue().intValue();
    int ypos = ((Numeral) parameters.get(2)).getValue().intValue();
    unit.useTech(techType, new Position(xpos, ypos));
  }

  @Override
  public String toString() {
    return "abilityOnPosition(TechType, X, Y)";
  }
}
