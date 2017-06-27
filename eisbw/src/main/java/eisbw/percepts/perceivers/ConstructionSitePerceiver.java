package eisbw.percepts.perceivers;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import eis.eis2java.translation.Filter;
import eis.iilang.Percept;
import eisbw.percepts.ConstructionSitePercept;
import eisbw.percepts.Percepts;
import jnibwapi.JNIBWAPI;
import jnibwapi.Position;
import jnibwapi.Region;
import jnibwapi.types.RaceType.RaceTypes;
import jnibwapi.types.UnitType;
import jnibwapi.types.UnitType.UnitTypes;

/**
 * @author Danny & Harm - The perceiver which handles all the construction site
 *         percepts.
 *
 */
public class ConstructionSitePerceiver extends Perceiver {
	public final static int steps = 2;

	/**
	 * The ConstructionSitePerceiver constructor.
	 *
	 * @param api
	 *            The BWAPI.
	 */
	public ConstructionSitePerceiver(JNIBWAPI api) {
		super(api);
	}

	/**
	 * @param pos
	 *            The current evaluated position
	 * @param percepts
	 *            The list of perceived constructionsites
	 */
	private void perceiveTerran(Position pos, Set<Percept> percepts) {
		if (this.api.canBuildHere(pos, UnitType.UnitTypes.Terran_Missile_Turret, true)) {
			Region region = this.api.getMap().getRegion(pos);
			percepts.add(new ConstructionSitePercept(pos.getBX(), pos.getBY(), region.getID()));
		}
	}

	/**
	 * @param pos
	 *            The current evaluated position
	 * @param percepts
	 *            The list of perceived constructionsites
	 */
	private void perceiveProtosss(Position pos, Set<Percept> percepts) {
		boolean nearPylon = this.api.canBuildHere(pos, UnitType.UnitTypes.Protoss_Photon_Cannon, true);
		if (nearPylon || this.api.canBuildHere(pos, UnitType.UnitTypes.Protoss_Pylon, true)) {
			Region region = this.api.getMap().getRegion(pos);
			percepts.add(new ConstructionSitePercept(pos.getBX(), pos.getBY(), region.getID(), nearPylon));
		}
	}

	/**
	 * @param pos
	 *            The current evaluated position
	 * @param percepts
	 *            The list of perceived constructionsites
	 */
	private void perceiveZerg(Position pos, Set<Percept> percepts) {
		boolean onCreep = this.api.canBuildHere(pos, UnitTypes.Zerg_Creep_Colony, true);
		if (onCreep || this.api.canBuildHere(pos, UnitTypes.Terran_Missile_Turret, true)) {
			Region region = this.api.getMap().getRegion(pos);
			percepts.add(new ConstructionSitePercept(pos.getBX(), pos.getBY(), region.getID(), onCreep));
		}
	}

	@Override
	public Map<PerceptFilter, Set<Percept>> perceive(Map<PerceptFilter, Set<Percept>> toReturn) {
		Set<Percept> percepts = new HashSet<>();
		jnibwapi.Map map = this.api.getMap();
		int mapWidth = map.getSize().getBX();
		int mapHeight = map.getSize().getBY();

		for (int x = 0; x < mapWidth; x += steps) {
			for (int y = 0; y < mapHeight; y += steps) {
				Position pos = new Position(x, y, Position.PosType.BUILD);
				if (map.isBuildable(pos)) {
					if (this.api.getSelf().getRace().getID() == RaceTypes.Terran.getID()) {
						perceiveTerran(pos, percepts);
					} else if (this.api.getSelf().getRace().getID() == RaceTypes.Protoss.getID()) {
						perceiveProtosss(pos, percepts);
					} else if (this.api.getSelf().getRace().getID() == RaceTypes.Zerg.getID()) {
						perceiveZerg(pos, percepts);
					}
				}
			}
		}
		toReturn.put(new PerceptFilter(Percepts.CONSTRUCTIONSITE, Filter.Type.ALWAYS), percepts);
		return toReturn;
	}
}
