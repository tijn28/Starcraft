package eisbw.percepts.perceivers;

import eis.eis2java.translation.Filter;
import eis.iilang.Percept;
import eisbw.UnitTypesEx;
import eisbw.percepts.ConstructionSitePercept;
import eisbw.percepts.Percepts;
import bwapi.Mirror;
import bwapi.Position;
import bwapi.Unit;
import bwapi.Race;
import bwapi.TilePosition;
import bwapi.UnitType;

import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Danny & Harm - The perceiver which handles all the construction site
 *         percepts.
 *
 */
public class ConstructionSitePerceiver extends Perceiver {

  /**
   * The ConstructionSitePerceiver constructor.
   * 
   * @param api
   *          The BWAPI.
   */
  public ConstructionSitePerceiver(Mirror api) {
    super(api);
  }

  /**
   * @param xpos
   *          The x-coordinate.
   * @param ypos
   *          The y-coordinate.
   * @param illegals
   *          A list of illegal build places.
   * @return Check whether the given ConstructionSite is legal or not.
   */
  private Boolean checkConstructionSite(int xpos, int ypos, List<Point> illegals) {
    Point possible = new Point(xpos, ypos);
    boolean add = true;
    for (Point illegal : illegals) {
      if (illegal.distance(possible) < 3) {
        add = false;
        break;
      }
    }

    return add;
  }

  /**
   * @param pos
   *          The current evaluated position
   * @param xpos
   *          The x-coordinate
   * @param ypos
   *          The y-coordinate
   * @param illegals
   *          A list of illegal build places.
   * @param percepts
   *          The list of perceived constructionsites
   */
  private void perceiveTerran(TilePosition pos, int xpos, int ypos, List<Point> illegals,
      Set<Percept> percepts) {

    // check if you can actually build here as terran
    if (api.getGame().canBuildHere(pos, UnitType.Terran_Command_Center)
        && checkConstructionSite(xpos, ypos, illegals)) {
      percepts.add(new ConstructionSitePercept(xpos, ypos));
    }
  }

  /**
   * @param pos
   *          The current evaluated position
   * @param xpos
   *          The x-coordinate
   * @param ypos
   *          The y-coordinate
   * @param illegals
   *          A list of illegal build places.
   * @param percepts
   *          The list of perceived constructionsites
   */
  private void perceiveZerg(TilePosition pos, int xpos, int ypos, List<Point> illegals,
      Set<Percept> percepts) {

    // check if you can actually build here as zerg
    if (api.getGame().canBuildHere(pos, UnitType.Zerg_Hatchery) && api.getGame().hasCreep(pos)
        && checkConstructionSite(xpos, ypos, illegals)) {
      percepts.add(new ConstructionSitePercept(xpos, ypos));
    }
  }

  /**
   * @param pos
   *          The current evaluated position
   * @param xpos
   *          The x-coordinate
   * @param ypos
   *          The y-coordinate
   * @param illegals
   *          A list of illegal build places.
   * @param percepts
   *          The list of perceived constructionsites
   */
  private void perceiveProtosss(TilePosition pos, int xpos, int ypos, List<Point> illegals,
      Set<Percept> percepts) {
    if (api.getGame().canBuildHere(pos, UnitType.Protoss_Nexus)) {

      // checks whether the ConstructionSite is not near an illegal build
      // place
      boolean legal = checkConstructionSite(xpos, ypos, illegals);
      boolean nearPylon = api.getGame().canBuildHere(pos, UnitType.Protoss_Gateway);
      if (legal && nearPylon) {
        percepts.add(new ConstructionSitePercept(xpos, ypos, true));
      } else if (legal) {
        percepts.add(new ConstructionSitePercept(xpos, ypos, false));
      }
    }
  }

  @Override
  public Map<PerceptFilter, Set<Percept>> perceive(Map<PerceptFilter, Set<Percept>> toReturn) {
    Set<Percept> percepts = new HashSet<>();

    int mapWidth = api.getGame().mapWidth();
    int mapHeight = api.getGame().mapHeight();

    List<Point> illegals = new LinkedList<>();
    for (Unit u : api.getGame().getNeutralUnits()) {
      if (UnitTypesEx.isResourceType(u.getType()) && u.exists()){
        illegals.add(new Point(u.getTilePosition().getX(), u.getTilePosition().getY()));
      }
    }

    for (int x = 0; x < mapWidth; x = x + 3) {
      for (int y = 0; y < mapHeight; y = y + 3) {
        TilePosition pos = new TilePosition(x, y);

        if (api.getGame().self().getRace().toString().equals(Race.Terran.toString())) {

          perceiveTerran(pos, x, y, illegals, percepts);

        } else if (api.getGame().self().getRace().toString().equals(Race.Zerg.toString())) {

          perceiveZerg(pos, x, y, illegals, percepts);

        } else if (api.getGame().self().getRace().toString().equals(Race.Protoss.toString())) {

          perceiveProtosss(pos, x, y, illegals, percepts);

        }
      }
    }
    toReturn.put(new PerceptFilter(Percepts.CONSTRUCTIONSITE, Filter.Type.ALWAYS), percepts);
    return toReturn;
  }

}
