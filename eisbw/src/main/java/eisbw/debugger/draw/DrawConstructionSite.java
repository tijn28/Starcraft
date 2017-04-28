package eisbw.debugger.draw;

import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Translator;
import eis.iilang.Parameter;
import eis.iilang.Percept;
import eisbw.Game;
import bwapi.Mirror;
import bwapi.Position;
import bwapi.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Danny & Harm - The class which handles the drawing of the
 *         construction sites of the dev. tool.
 *
 */
public class DrawConstructionSite extends IDraw {

  /**
   * The DrawConstructionSite constructor.
   * 
   * @param game
   *          The current game.
   */
  public DrawConstructionSite(Game game) {
    super(game);
  }

  @Override
  protected void drawOnMap(Mirror api) throws TranslationException {
    Translator translator = Translator.getInstance();
    List<Percept> percepts = game.getConstructionSites();
    for (Percept percept : percepts) {
      LinkedList<Parameter> params = percept.getParameters();
      int xpos = translator.translate2Java(params.get(0), Integer.class);
      int ypos = translator.translate2Java(params.get(1), Integer.class);
      api.getGame().drawBoxMap(new Position(xpos, ypos),
          new Position(xpos + 3, ypos + 3), Color.Blue, false);
    }
  }

}
