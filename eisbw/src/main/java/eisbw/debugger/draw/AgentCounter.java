package eisbw.debugger.draw;

import bwapi.Mirror;
import bwapi.Position;
import eis.eis2java.exception.TranslationException;
import eisbw.Game;

/**
 * @author Danny & Harm.
 */
public class AgentCounter extends IDraw {

  /**
   * initializes the agentcounter.
   * @param game - the game object
   */
  public AgentCounter(Game game) {
    super(game);
    toggle();
  }

  @Override
  protected void drawOnMap(Mirror api) throws TranslationException {
    api.getGame().drawTextScreen(new Position(10, 10), "Agentcount: " + game.getAgentCount());
  }

  @Override
  public void toggle() {
    toggle = true;
  }
}
