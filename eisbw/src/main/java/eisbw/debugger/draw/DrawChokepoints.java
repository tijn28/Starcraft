package eisbw.debugger.draw;

import bwapi.Color;
import bwapi.Mirror;
import bwta.BWTA;
import bwta.Chokepoint;
import eis.eis2java.exception.TranslationException;
import eisbw.Game;

/**
 * @author Danny & Harm.
 */
public class DrawChokepoints extends IDraw {

  /**
   * Draw the chokepoints on the map.
   * @param game - the game object
   */
  public DrawChokepoints(Game game) {
    super(game);
  }

  @Override
  protected void drawOnMap(Mirror bwapi) throws TranslationException {
    for (Chokepoint cp : BWTA.getChokepoints()) {
      bwapi.getGame().drawLineMap(cp.getSides().first, cp.getSides().second, Color.Yellow);
      bwapi.getGame().drawCircleMap(cp.getCenter(), (int) cp.getWidth()/2, Color.Red, false);
      bwapi.getGame().drawTextMap(cp.getCenter(),
          "(" + cp.getCenter().getX() + "," + cp.getCenter().getY() + ")");
    }
  }

}
