package eisbw.percepts.perceivers;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import eis.eis2java.translation.Filter;
import eis.iilang.Percept;
import eisbw.percepts.MineralFieldPercept;
import eisbw.percepts.Percepts;
import eisbw.percepts.VespeneGeyserPercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Region;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;
import jnibwapi.types.UnitType.UnitTypes;

/**
 * @author Danny & Harm - The perceiver which handles all the worker percepts.
 */
public class WorkerPerceiver extends UnitPerceiver {
	/**
	 * @param api
	 *            The BWAPI.
	 * @param unit
	 *            The unit which is about the perceiving.
	 */
	public WorkerPerceiver(JNIBWAPI api, Unit unit) {
		super(api, unit);
	}

	@Override
	public Map<PerceptFilter, Set<Percept>> perceive(Map<PerceptFilter, Set<Percept>> toReturn) {
		resourcesPercepts(toReturn);
		return toReturn;
	}

	private void resourcesPercepts(Map<PerceptFilter, Set<Percept>> toReturn) {
		Set<Percept> minerals = new HashSet<>();
		Set<Percept> geysers = new HashSet<>();
		for (Unit u : this.api.getNeutralUnits()) {
			UnitType unitType = u.getType();
			if (unitType.isMineralField()) {
				Region region = this.api.getMap().getRegion(u.getPosition());
				MineralFieldPercept mineralfield = new MineralFieldPercept(u.getID(), u.getResources(),
						u.getPosition().getBX(), u.getPosition().getBY(), region.getID());
				minerals.add(mineralfield);
			} else if (unitType.getID() == UnitTypes.Resource_Vespene_Geyser.getID()) {
				Region region = this.api.getMap().getRegion(u.getPosition());
				VespeneGeyserPercept geyser = new VespeneGeyserPercept(u.getID(), u.getResources(),
						u.getPosition().getBX(), u.getPosition().getBY(), region.getID());
				geysers.add(geyser);
			}
		}
		for (Unit u : this.api.getMyUnits()) {
			UnitType unitType = u.getType();
			if (unitType.isRefinery()) {
				Region region = this.api.getMap().getRegion(u.getPosition());
				VespeneGeyserPercept geyser = new VespeneGeyserPercept(u.getID(), u.getResources(),
						u.getPosition().getBX(), u.getPosition().getBY(), region.getID());
				geysers.add(geyser);

			}
		}
		toReturn.put(new PerceptFilter(Percepts.MINERALFIELD, Filter.Type.ALWAYS), minerals);
		toReturn.put(new PerceptFilter(Percepts.VESPENEGEYSER, Filter.Type.ALWAYS), geysers);
	}
}
