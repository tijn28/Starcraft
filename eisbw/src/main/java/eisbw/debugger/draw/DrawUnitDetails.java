package eisbw.debugger.draw;

import eis.eis2java.exception.TranslationException;
import eisbw.Game;
import jnibwapi.JNIBWAPI;
import jnibwapi.Position;
import jnibwapi.Unit;
import jnibwapi.util.BWColor;

/**
 * @author Danny & Harm - The class which handles the drawing of the buildings
 *         of the dev. tool.
 *
 */
public class DrawUnitDetails extends IDraw {
	private final static int barHeight = 18;

	/**
	 * The DrawBuildingDetails constructor.
	 *
	 * @param game
	 *            The current game.
	 */
	public DrawUnitDetails(Game game) {
		super(game);
	}

	@Override
	protected void drawOnMap(JNIBWAPI api) throws TranslationException {
		// Draw "is working" box
		for (Unit unit : api.getMyUnits()) {
			int total = 0;
			int done = 0;
			String txt = "";
			BWColor color = BWColor.Blue;
			if (unit.getRemainingResearchTime() > 0) {
				total = unit.getTech().getResearchTime();
				done = total - unit.getRemainingResearchTime();
				txt = unit.getTech().getName();
			}
			if (unit.isUpgrading()) {
				total = unit.getUpgrade().getUpgradeTimeBase();
				done = total - unit.getRemainingUpgradeTime();
				txt = unit.getUpgrade().getName();
			}
			if (total > 0) {
				int width = unit.getType().getTileWidth() * 32;
				Position start = new Position(unit.getPosition().getPX() - width / 2, unit.getPosition().getPY() - 30);
				api.drawBox(start, new Position(start.getPX() + width, start.getPY() + barHeight), color, false, false);
				int progress = (int) ((double) done / (double) total * width);
				api.drawBox(start, new Position(start.getPX() + progress, start.getPY() + barHeight), color, true,
						false);
				api.drawText(new Position(start.getPX() + 5, start.getPY() + 2), txt, false);
			}
		}
		if (this.toggle) {
			drawHealth(api);
			drawTargets(api);
			drawIDs(api);
		}
	}

	/**
	 * Draws health boxes for units (ported from JNIBWAPI native code). Added a
	 * max>0 check to prevent crashes on spell units (with health 255)
	 */
	private void drawHealth(JNIBWAPI api) {
		for (final Unit unit : api.getAllUnits()) {
			int health = unit.getHitPoints();
			int max = unit.getType().getMaxHitPoints();
			if (health > 0 && max > 0) {
				int x = unit.getPosition().getPX();
				int y = unit.getPosition().getPY();
				int l = unit.getType().getDimensionLeft();
				int t = unit.getType().getDimensionUp();
				int r = unit.getType().getDimensionRight();
				int b = unit.getType().getDimensionDown();
				int width = ((r + l) * health) / max;
				if (health * 3 < max) {
					api.drawBox(new Position(x - l, y - t - 5), new Position(x - l + width, y - t), BWColor.Red, true,
							false);
				} else if (health * 3 < 2 * max) {
					api.drawBox(new Position(x - l, y - t - 5), new Position(x - l + width, y - t), BWColor.Yellow,
							true, false);
				} else {
					api.drawBox(new Position(x - l, y - t - 5), new Position(x - l + width, y - t), BWColor.Green, true,
							false);
				}
				boolean self = (unit.getPlayer().getID() == api.getSelf().getID());
				api.drawBox(new Position(x - l, y - t - 5), new Position(x + r, y - t),
						self ? BWColor.White : BWColor.Red, false, false);
				api.drawBox(new Position(x - l, y - t), new Position(x + r, y + b), self ? BWColor.White : BWColor.Red,
						false, false);
				api.drawText(new Position(x - l, y - t), unit.getType().getName(), false);
			}
		}
	}

	/**
	 * Draws the targets of each unit. (ported from JNIBWAPI native code)
	 */
	private void drawTargets(JNIBWAPI api) {
		for (final Unit unit : api.getAllUnits()) {
			boolean self = (unit.getPlayer().getID() == api.getSelf().getID());
			Unit target = (unit.getTarget() == null) ? unit.getOrderTarget() : unit.getTarget();
			if (target != null) {
				api.drawLine(unit.getPosition(), target.getPosition(), self ? BWColor.Yellow : BWColor.Purple, false);
			}
			Position position = unit.getTargetPosition();
			if (position != null) {
				api.drawLine(unit.getPosition(), position, self ? BWColor.Yellow : BWColor.Purple, false);
			}
		}
	}

	/**
	 * Draws the IDs of each unit. (ported from JNIBWAPI native code)
	 */
	private void drawIDs(JNIBWAPI api) {
		for (final Unit unit : api.getAllUnits()) {
			api.drawText(unit.getPosition(), Integer.toString(unit.getID()), false);
		}
	}
}
