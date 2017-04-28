package eisbw.actions;

import bwapi.Mirror;
import bwapi.Position;
import bwapi.Unit;
import eis.iilang.Action;
import eis.iilang.Numeral;
import eis.iilang.Parameter;

import java.util.LinkedList;

/**
 * @author Danny & Harm - Makes the unit move to the specified location.
 *
 */
public class Move extends StarcraftMovableAction {

  /**
   * The Move constructor.
   * 
   * @param api
   *          The BWAPI
   */
  public Move(Mirror api) {
    super(api);
  }

  @Override
  public void execute(Unit unit, Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    int xpos = ((Numeral) parameters.get(0)).getValue().intValue();
    int ypos = ((Numeral) parameters.get(1)).getValue().intValue();

    Position pos = new Position(xpos, ypos);
    unit.move(pos, false);
  }

  @Override
  public String toString() {
    return "move(unitId, x, y)";
  }
}
