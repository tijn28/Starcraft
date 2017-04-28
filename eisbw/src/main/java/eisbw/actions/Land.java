package eisbw.actions;

import bwapi.Mirror;
import bwapi.TilePosition;
import bwapi.Unit;
import eis.iilang.Action;
import eis.iilang.Numeral;
import eis.iilang.Parameter;

import java.util.LinkedList;

/**
 * @author Danny & Harm - Lands the flying unit on the specified location.
 *
 */
public class Land extends StarcraftMovableAction {

  /**
   * The Land constructor.
   * 
   * @param api
   *          The BWAPI
   */
  public Land(Mirror api) {
    super(api);
  }

  @Override
  public boolean canExecute(Unit unit, Action action) {
    return unit.isLifted();
  }

  @Override
  public void execute(Unit unit, Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    int xpos = ((Numeral) parameters.get(0)).getValue().intValue();
    int ypos = ((Numeral) parameters.get(1)).getValue().intValue();
    unit.land(new TilePosition(xpos, ypos));
  }

  @Override
  public String toString() {
    return "land(x,y)";
  }
}
