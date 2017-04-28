package eisbw.percepts.perceivers;

import eis.eis2java.translation.Filter;
import eis.iilang.Percept;
import eisbw.percepts.BasePercept;
import eisbw.percepts.ChokepointPercept;
import eisbw.percepts.MapPercept;
import eisbw.percepts.Percepts;
import bwta.BWTA;
import bwta.BaseLocation;
import bwta.Chokepoint;
import bwapi.Mirror;

import java.util.HashSet;
import java.util.Map;
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
   *          The BWAPI
   */
  public MapPerceiver(Mirror api) {
    super(api);
  }

  @Override
  public Map<PerceptFilter, Set<Percept>> perceive(Map<PerceptFilter, Set<Percept>> toReturn) {
    Set<Percept> percepts = new HashSet<>();

    Percept mapPercept = new MapPercept(api.getGame().mapWidth(), api.getGame().mapHeight());
    percepts.add(mapPercept);

    for (BaseLocation location : BWTA.getBaseLocations()) {
      Percept basePercept = new BasePercept(location.getTilePosition().getX(),
          location.getTilePosition().getY(), location.isStartLocation(), location.getRegion().toString());
      percepts.add(basePercept);
    }

    for (Chokepoint cp : BWTA.getChokepoints()) {
      Percept chokePercept = new ChokepointPercept(cp.getCenter().getX(), cp.getCenter().getY());
      percepts.add(chokePercept);
    }
    toReturn.put(new PerceptFilter(Percepts.MAP, Filter.Type.ONCE), percepts);
    return toReturn;
  }
}
