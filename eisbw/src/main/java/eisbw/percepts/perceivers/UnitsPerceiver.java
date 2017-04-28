package eisbw.percepts.perceivers;

import eis.eis2java.translation.Filter;
//import eis.iilang.Identifier;
//import eis.iilang.Parameter;
import eis.iilang.Percept;
import eisbw.percepts.Attacking;
import eisbw.percepts.Percepts;
import eisbw.units.ConditionHandler;
import eisbw.percepts.FriendlyPercept;
import eisbw.percepts.EnemyPercept;
import bwapi.Mirror;
import bwapi.Player;
import bwapi.Unit;

import java.util.HashSet;
import java.util.LinkedList;
//import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Danny & Harm - The perceiver which handles all the unit percepts.
 *
 */
public class UnitsPerceiver extends Perceiver {

  /**
   * @param api
   *          The BWAPI.
   */
  public UnitsPerceiver(Mirror api) {
    super(api);
  }

  /**
   * Sets some of the generic Unit percepts.
   * 
   * @param units
   *          The perceived units
   * @param isFriendly
   *          indicates whether these units are friendly or not
   * @param attackingpercepts
   *          - list with unitPercepts
   * @param unitpercepts
   *          - list with attackingPercepts
   * @param percepts
   *          The list of percepts
   * @param toReturn
   *          - the map that will be returned
   */
  private void setUnitPercepts(List<Unit> units, boolean isFriendly, Set<Percept> unitpercepts,
      Set<Percept> attackingpercepts) {

    // Fix for the phantom marines bug
    for (Unit u : units) {
      if (u.isBeingConstructed() && u.isLoaded()) {
        continue;
      }

      // List<Parameter> conditions = new LinkedList<>();

      // setUnitConditions(u, conditions);
      ConditionHandler conditionHandler = new ConditionHandler(api, u);

      if (!isFriendly) {
        unitpercepts.add(new EnemyPercept(u.getType().toString(), u.getID(), u.getHitPoints(),
            u.getShields(), conditionHandler.getConditions(), u.getPosition().getX(),
            u.getPosition().getY()));

        if (u.getType().canAttack()) {
          Unit targetUnit = u.getOrderTarget();

          if (targetUnit != null && targetUnit.getType().canAttack()) {
            attackingpercepts.add(new Attacking(u.getID(), targetUnit.getID()));
          }
        }
      } else if (isFriendly) {
        // if (u.getType().getID() == 36)
        // unitpercepts.add(new FriendlyPercept(u.getBuildType().getName(),
        // u.getID(),
        // conditionHandler.getConditions()));
        // else
          unitpercepts.add(new FriendlyPercept(u.getType().toString(), u.getID(),
              conditionHandler.getConditions()));
      }
    }
  }

  // /**
  // * Sets the conditions of the unit.
  // *
  // * @param unit
  // * The evaluated unit
  // * @param conditions
  // * The list of conditions of the unit
  // */
  // public void setUnitConditions(Unit unit, List<Parameter> conditions) {
  // if (unit.getType().isFlyer()) {
  // conditions.add(new Identifier("flying"));
  // }
  // if (unit.isMorphing()) {
  // conditions.add(new Identifier("morphing"));
  // }
  // if (unit.isCloaked()) {
  // conditions.add(new Identifier("cloaked"));
  // }
  // if (unit.isBeingConstructed()) {
  // conditions.add(new Identifier("beingConstructed"));
  // }
  // }

  @Override
  public Map<PerceptFilter, Set<Percept>> perceive(Map<PerceptFilter, Set<Percept>> toReturn) {

    Set<Percept> friendlypercepts = new HashSet<>();
    Set<Percept> enemypercepts = new HashSet<>();
    Set<Percept> attackingpercepts = new HashSet<>();

    // perceive friendly units
    setUnitPercepts(api.getGame().self().getUnits(), true, friendlypercepts, attackingpercepts);

    // perceive enemy units
    List<Player> enemies = api.getGame().enemies();
    List<Unit> enemyUnits = new LinkedList<Unit>();
    for (Player p : enemies){
      enemyUnits.addAll(p.getUnits());
    }
    setUnitPercepts(enemyUnits, false, enemypercepts, attackingpercepts);

    toReturn.put(new PerceptFilter(Percepts.FRIENDLY, Filter.Type.ALWAYS), friendlypercepts);
    toReturn.put(new PerceptFilter(Percepts.ENEMY, Filter.Type.ALWAYS), enemypercepts);
    toReturn.put(new PerceptFilter(Percepts.ATTACKING, Filter.Type.ALWAYS), attackingpercepts);

    return toReturn;
  }

}
