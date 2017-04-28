package eisbw.debugger.draw;

import eis.eis2java.exception.TranslationException;
import eisbw.Game;
import bwapi.Color;
import bwapi.Mirror;
import bwapi.Position;
import bwapi.Unit;

/**
 * @author Danny & Harm - The class which handles the drawing of the buildings
 *         of the dev. tool.
 *
 */
public class DrawBuildingDetails extends IDraw {

  /**
   * The DrawBuildingDetails constructor.
   * 
   * @param game
   *          The current game.
   */
  public DrawBuildingDetails(Game game) {
    super(game);
  }

  @Override
  protected void drawOnMap(Mirror api) throws TranslationException {
    // Draw "is working" box
    int barHeight = 18;

    for (Unit unit : api.getGame().self().getUnits()) {
      int total = 0;
      int done = 0;
      String txt = "";
      Color color = Color.Blue;
      if (unit.getRemainingResearchTime() > 0) {
        total = unit.getTech().researchTime();
        done = total - unit.getRemainingResearchTime();
        txt = unit.getTech().toString();
      }
      if (unit.isUpgrading()) {
        total = unit.getUpgrade().upgradeTime();
        done = total - unit.getRemainingUpgradeTime();
        txt = unit.getUpgrade().toString();
      }

      if (total > 0) {
        int width = unit.getType().tileWidth() * 32;

        Position start = new Position(unit.getPosition().getX() - width / 2,
            unit.getPosition().getY() - 30);
        Position end = new Position(start.getX() + width, start.getY() + barHeight);

        int progress = (int) ((double) done / (double) total * width);
        Position progressbar = new Position(start.getX() + progress, start.getY() + barHeight);
        api.getGame().drawBoxMap(start, end, color, false);
        api.getGame().drawBoxMap(start, progressbar, color, true);

        api.getGame().drawTextMap(new Position(start.getX() + 5, start.getY() + 2), txt);
      }

    }
  }

  @Override
  public void draw(Mirror api) {
//    api.getGame().drawHealth(toggle);
//    api.getGame().drawTargets(toggle);
//    api.getGame().drawIDs(toggle);
    super.draw(api);
  }
}
