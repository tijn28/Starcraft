package eisbw.debugger.draw;

import java.util.List;

import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Translator;
import eis.iilang.Parameter;
import eis.iilang.Percept;
import eisbw.Game;
import jnibwapi.JNIBWAPI;
import jnibwapi.Position;
import jnibwapi.Position.PosType;
import jnibwapi.types.RaceType.RaceTypes;
import jnibwapi.util.BWColor;

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
	 *            The current game.
	 */
	public DrawConstructionSite(Game game) {
		super(game);
	}

	@Override
	protected void drawOnMap(JNIBWAPI api) throws TranslationException {
		Translator translator = Translator.getInstance();
		List<Percept> percepts = this.game.getConstructionSites();
		for (Percept percept : percepts) {
			List<Parameter> params = percept.getParameters();
			int xpos = translator.translate2Java(params.get(0), Integer.class);
			int ypos = translator.translate2Java(params.get(1), Integer.class);
			if (api.getSelf().getRace().getID() == RaceTypes.Terran.getID()) {
				api.drawBox(new Position(xpos, ypos, PosType.BUILD), new Position(xpos + 3, ypos + 3, PosType.BUILD),
						BWColor.Blue, false, false);
			} else {
				boolean bool = translator.translate2Java(params.get(2), Boolean.class);
				if (bool) {
					api.drawBox(new Position(xpos, ypos, PosType.BUILD),
							new Position(xpos + 3, ypos + 3, PosType.BUILD), BWColor.Blue, false, false);
				} else {
					api.drawBox(new Position(xpos, ypos, PosType.BUILD),
							new Position(xpos + 3, ypos + 3, PosType.BUILD), BWColor.Red, false, false);
				}
			}
		}
	}
}
