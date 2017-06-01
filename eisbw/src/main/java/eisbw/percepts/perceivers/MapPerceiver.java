package eisbw.percepts.perceivers;

import eis.eis2java.translation.Filter;
import eis.iilang.Percept;
import eisbw.UnitTypesEx;
import eisbw.percepts.BasePercept;
import eisbw.percepts.ChokepointPercept;
import eisbw.percepts.EnemyRacePercept;
import eisbw.percepts.MapPercept;
import eisbw.percepts.Percepts;
import jnibwapi.BaseLocation;
import jnibwapi.ChokePoint;
import jnibwapi.JNIBWAPI;
import jnibwapi.Player;
import jnibwapi.Position;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author Danny & Harm - The perceiver which handles all the map percepts.
 *
 */
public class MapPerceiver extends Perceiver {
	/**
	 * The MapPerceiver constructor.
	 * 
	 * @param api
	 *            The BWAPI
	 */
	public MapPerceiver(JNIBWAPI api) {
		super(api);
	}

	@Override
	public Map<PerceptFilter, Set<Percept>> perceive(Map<PerceptFilter, Set<Percept>> toReturn) {
		Set<Percept> percepts = new HashSet<>();
		jnibwapi.Map map = api.getMap();

		Percept mapPercept = new MapPercept(map.getSize().getBX(), map.getSize().getBY());
		percepts.add(mapPercept);

		/** Distance calculation between resource groups and base location. **/
		HashMap<Integer, Position> distanceMatrix = new HashMap<>();
		for (Unit u : api.getNeutralUnits()) {
			UnitType unitType = u.getType();
			if (UnitTypesEx.isMineralField(unitType)) {
				if (!distanceMatrix.containsKey(u.getResourceGroup())) {
					distanceMatrix.put(u.getResourceGroup(), u.getPosition());
				}
			}
		}

		for (Player p : api.getEnemies()) {
			percepts.add(new EnemyRacePercept(p.getRace().getName().toLowerCase()));
		}

		for (BaseLocation location : map.getBaseLocations()) {
			int resourcegroup = -1;
			double distance = Integer.MAX_VALUE;
			for (Entry<Integer, Position> resource : distanceMatrix.entrySet()) {
				double newDist = resource.getValue().getBDistance(location.getPosition());
				if (newDist < distance) {
					resourcegroup = resource.getKey();
					distance = newDist;
				}
			}

			Percept basePercept = new BasePercept(location.getPosition().getBX(), location.getPosition().getBY(),
					location.isStartLocation(), resourcegroup);
			percepts.add(basePercept);
		}

		for (ChokePoint cp : map.getChokePoints()) {
			Percept chokePercept = new ChokepointPercept(cp.getFirstSide().getBX(), cp.getFirstSide().getBY(),
					cp.getSecondSide().getBX(), cp.getSecondSide().getBY());
			percepts.add(chokePercept);
		}
		toReturn.put(new PerceptFilter(Percepts.MAP, Filter.Type.ONCE), percepts);
		return toReturn;
	}
}
