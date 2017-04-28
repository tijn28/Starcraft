package eisbw.percepts.perceivers;

import eis.eis2java.translation.Filter;
import eis.iilang.Percept;
import eisbw.percepts.Percepts;
import eisbw.percepts.QueueSizePercept;
import eisbw.percepts.RallyPointPercept;
import eisbw.percepts.RallyUnitPercept;
import eisbw.percepts.ResearchingPercept;
import eisbw.percepts.SpaceProvidedPercept;
import eisbw.percepts.UnitLoadedPercept;
import eisbw.percepts.UpgradePercept;
import bwapi.Mirror;
import bwapi.Position;
import bwapi.Unit;
import bwapi.TechType;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Danny & Harm - The perceiver which handles all the building percepts.
 *
 */
public class BuildingPerceiver extends UnitPerceiver {

  /**
   * @param api
   *          The BWAPI
   * @param unit
   *          The perceiving unit.
   */
  public BuildingPerceiver(Mirror api, Unit unit) {
    super(api, unit);
  }

  @Override
  public Map<PerceptFilter, Set<Percept>> perceive(Map<PerceptFilter, Set<Percept>> toReturn) {

    rallyPointPercept(toReturn);
    rallyUnitPercept(toReturn);
    queueSizePercept(toReturn);
    upgradingPercept(toReturn);
    researchedPercept(toReturn);
    if (unit.getType().spaceProvided() > 0) {
      List<Unit> loadedUnits = unit.getLoadedUnits();
      spaceProvidedPercept(toReturn, loadedUnits);
      unitLoadedPercept(toReturn, loadedUnits);
    }

    return toReturn;
  }

  private void unitLoadedPercept(Map<PerceptFilter, Set<Percept>> toReturn,
      List<Unit> loadedUnits) {
    Set<Percept> percepts = new HashSet<>();
    for (Unit u : loadedUnits) {
      if (u != null) {
        percepts.add(new UnitLoadedPercept(u.getID(), u.getType().toString()));
      }
    }
    toReturn.put(new PerceptFilter(Percepts.UNITLOADED, Filter.Type.ALWAYS), percepts);
  }

  private void spaceProvidedPercept(Map<PerceptFilter, Set<Percept>> toReturn,
      List<Unit> loadedUnits) {
    Set<Percept> percepts = new HashSet<>();
    percepts.add(new SpaceProvidedPercept(loadedUnits.size(), unit.getType().spaceProvided()));
    toReturn.put(new PerceptFilter(Percepts.SPACEPROVIDED, Filter.Type.ON_CHANGE), percepts);
  }

  private void researchedPercept(Map<PerceptFilter, Set<Percept>> toReturn) {
    Set<Percept> percepts = new HashSet<>();
    if (unit.getTech() != null) {
      if (!unit.getTech().equals(TechType.None))
        percepts.add(new ResearchingPercept(unit.getTech().toString()));
    }
    toReturn.put(new PerceptFilter(Percepts.RESEARCHING, Filter.Type.ALWAYS), percepts);
  }

  private void rallyPointPercept(Map<PerceptFilter, Set<Percept>> toReturn) {
    Set<Percept> percepts = new HashSet<>();
    if (!unit.getRallyPosition().equals(Position.None)) {
      percepts.add(
          new RallyPointPercept(unit.getRallyPosition().getX(), unit.getRallyPosition().getY()));
      toReturn.put(new PerceptFilter(Percepts.RALLYPOINT, Filter.Type.ON_CHANGE), percepts);
    }
  }

  private void upgradingPercept(Map<PerceptFilter, Set<Percept>> toReturn) {
    Set<Percept> percepts = new HashSet<>();
    if (unit.isUpgrading()) {
      percepts.add(new UpgradePercept(unit.getUpgrade().toString()));
      toReturn.put(new PerceptFilter(Percepts.UPGRADING, Filter.Type.ALWAYS), percepts);
    }
  }

  private void queueSizePercept(Map<PerceptFilter, Set<Percept>> toReturn) {
    Set<Percept> percepts = new HashSet<>();
    percepts.add(new QueueSizePercept(unit.getTrainingQueue().size()));
    toReturn.put(new PerceptFilter(Percepts.QUEUESIZE, Filter.Type.ON_CHANGE), percepts);
  }

  private void rallyUnitPercept(Map<PerceptFilter, Set<Percept>> toReturn) {
    Set<Percept> percepts = new HashSet<>();
    if (unit.getRallyUnit() != null) {
      percepts.add(new RallyUnitPercept(unit.getRallyUnit().getID()));
      toReturn.put(new PerceptFilter(Percepts.RALLYUNIT, Filter.Type.ON_CHANGE), percepts);
    }
  }
}
