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
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

import java.util.HashSet;
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
	 *            The BWAPI.
	 */
	public UnitsPerceiver(JNIBWAPI api) {
		super(api);
	}

	/**
	 * Sets some of the generic Unit percepts.
	 * 
	 * @param units
	 *            The perceived units
	 * @param isFriendly
	 *            indicates whether these units are friendly or not
	 * @param attackingpercepts
	 *            - list with unitPercepts
	 * @param unitpercepts
	 *            - list with attackingPercepts
	 * @param percepts
	 *            The list of percepts
	 * @param toReturn
	 *            - the map that will be returned
	 */
	private void setUnitPercepts(List<Unit> units, boolean isFriendly, Set<Percept> unitpercepts,
			Set<Percept> attackingpercepts) {

		// Fix for the phantom marines bug
		for (Unit u : units) {
			if (u.isBeingConstructed() && u.isLoaded()) {
				continue;
			}

			ConditionHandler conditionHandler = new ConditionHandler(api, u);

			if (!isFriendly) {
				unitpercepts.add(new EnemyPercept(u.getType().getName(), u.getID(), u.getHitPoints(), u.getShields(),
						conditionHandler.getConditions(), u.getPosition().getBX(), u.getPosition().getBY()));

				if (u.getType().isAttackCapable()) {
					Unit targetUnit = u.getOrderTarget();

					if (targetUnit != null && targetUnit.getType().isAttackCapable()) {
						attackingpercepts.add(new Attacking(u.getID(), targetUnit.getID()));
					}
				}
			} else if (isFriendly) {
				if (u.getType().getID() == 36)
					unitpercepts.add(new FriendlyPercept(u.getBuildType().getName(), u.getID(),
							conditionHandler.getConditions()));
				else
					unitpercepts.add(
							new FriendlyPercept(u.getType().getName(), u.getID(), conditionHandler.getConditions()));
			}
		}
	}


	@Override
	public Map<PerceptFilter, Set<Percept>> perceive(Map<PerceptFilter, Set<Percept>> toReturn) {

		Set<Percept> friendlypercepts = new HashSet<>();
		Set<Percept> enemypercepts = new HashSet<>();
		Set<Percept> attackingpercepts = new HashSet<>();

		// perceive friendly units
		setUnitPercepts(api.getMyUnits(), true, friendlypercepts, attackingpercepts);

		// perceive enemy units
		setUnitPercepts(api.getEnemyUnits(), false, enemypercepts, attackingpercepts);

		toReturn.put(new PerceptFilter(Percepts.FRIENDLY, Filter.Type.ALWAYS), friendlypercepts);
		toReturn.put(new PerceptFilter(Percepts.ENEMY, Filter.Type.ALWAYS), enemypercepts);
		toReturn.put(new PerceptFilter(Percepts.ATTACKING, Filter.Type.ALWAYS), attackingpercepts);

		return toReturn;
	}

}
