package eisbw.debugger.draw;

import eis.eis2java.exception.TranslationException;
import eisbw.Game;
import bwta.BWTA;
import bwta.BaseLocation;
import bwapi.Color;
import bwapi.Mirror;

/**
 * @author Harm & Danny.
 *
 */
public class DrawBases extends IDraw {

  /**
   * Draw the baselocations on the map.
   * @param game - the game object
   */
  public DrawBases(Game game) {
    super(game);
  }

  @Override
  protected void drawOnMap(Mirror api) throws TranslationException {
    for (BaseLocation base : BWTA.getBaseLocations()) {
      api.getGame().drawCircleMap(base.getPosition(), 75, Color.Purple, false);
      if (base.isStartLocation()) {
        api.getGame().drawTextMap(base.getPosition(), "Starting Location");
      }
    }
  }

}
