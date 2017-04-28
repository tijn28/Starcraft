package eisbw.percepts.perceivers;

import eis.eis2java.translation.Filter;
import eis.iilang.Percept;
import eisbw.percepts.DefensiveMatrixPercept;
import eisbw.percepts.Percepts;
import eisbw.percepts.ResourcesPercept;
import eisbw.percepts.SelfPercept;
import eisbw.percepts.StatusPercept;
import eisbw.units.ConditionHandler;
import bwapi.Mirror;
import bwapi.Unit;
import bwapi.UnitType;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Danny & Harm - The perceiver which handles all the generic percepts.
 *
 */
public class GenericUnitPerceiver extends UnitPerceiver {

  /**
   * @param api
   *          The BWAPI.
   * @param unit
   *          The perceiving unit.
   */
  public GenericUnitPerceiver(Mirror api, Unit unit) {
    super(api, unit);
  }

  @Override
  public Map<PerceptFilter, Set<Percept>> perceive(Map<PerceptFilter, Set<Percept>> toReturn) {

    defenceMatrixPercept(toReturn);
    resourcesPercept(toReturn);
    selfPercept(toReturn);
    statusPercept(toReturn);

    return toReturn;
  }

  /**
   * @param toReturn
   *          The percept and reference of which kind of percept it is.
   */
  private void statusPercept(Map<PerceptFilter, Set<Percept>> toReturn) {
    Set<Percept> percepts = new HashSet<>();
    ConditionHandler conditionHandler = new ConditionHandler(api, unit);

    percepts.add(new StatusPercept(unit.getHitPoints(), unit.getShields(), unit.getEnergy(),
        conditionHandler.getConditions(), unit.getPosition().getX(), unit.getPosition().getY()));

    toReturn.put(new PerceptFilter(Percepts.STATUS, Filter.Type.ON_CHANGE), percepts);
  }

  /**
   * @param toReturn
   *          The percept and reference of which kind of percept it is.
   */
  private void selfPercept(Map<PerceptFilter, Set<Percept>> toReturn) {
    Set<Percept> percepts = new HashSet<>();
    UnitType type = unit.getType();

    percepts.add(new SelfPercept(unit.getID(), type.toString(), type.maxHitPoints(),
        type.maxShields(), type.maxEnergy()));

    toReturn.put(new PerceptFilter(Percepts.SELF, Filter.Type.ONCE), percepts);
  }

  /**
   * @param toReturn
   *          The percept and reference of which kind of percept it is.
   */
  private void resourcesPercept(Map<PerceptFilter, Set<Percept>> toReturn) {
    Set<Percept> percepts = new HashSet<>();

    percepts.add(new ResourcesPercept(api.getGame().self().minerals(), api.getGame().self().gas(),
        api.getGame().self().supplyUsed(), api.getGame().self().supplyTotal()));

    toReturn.put(new PerceptFilter(Percepts.RESOURCES, Filter.Type.ON_CHANGE), percepts);
  }

  /**
   * @param toReturn
   *          The percept and reference of which kind of percept it is.
   */
  private void defenceMatrixPercept(Map<PerceptFilter, Set<Percept>> toReturn) {
    Set<Percept> percepts = new HashSet<>();

    if (unit.isDefenseMatrixed()) {
      percepts.add(new DefensiveMatrixPercept(unit.getDefenseMatrixPoints()));
    }

    toReturn.put(new PerceptFilter(Percepts.DEFENSIVEMATRIX, Filter.Type.ALWAYS), percepts);
  }

}
