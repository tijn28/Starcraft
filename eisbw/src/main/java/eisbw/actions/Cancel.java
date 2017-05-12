package eisbw.actions;

import bwapi.Mirror;
import bwapi.Race;
import bwapi.TechType;
import bwapi.Unit;
import eis.iilang.Action;
import eis.iilang.Parameter;

import java.util.LinkedList;

/**
 * @author Danny & Harm - Cancels the training of a specified unit.
 *
 */
public class Cancel extends StarcraftAction {

	/**
	 * The Train constructor.
	 * 
	 * @param api
	 *            The BWAPI
	 */
	public Cancel(Mirror api) {
		super(api);
	}

	@Override
	public boolean isValid(Action action) {
		LinkedList<Parameter> parameters = action.getParameters();
		return parameters.isEmpty();
	}

	@Override
	public boolean canExecute(Unit unit, Action action) {
		return unit.getType().isBuilding() || api.getGame().self().getRace().toString().equals(Race.Zerg.toString());
	}

	@Override
	public void execute(Unit unit, Action action) {
		if (unit.isMorphing()) {
			unit.cancelMorph();
		} else if (unit.isBeingConstructed()) {
			unit.cancelConstruction();
		} else if (unit.isUpgrading()) {
			unit.cancelUpgrade();
		} else if (unit.isTraining()) {
			unit.cancelTrain();
		} else if (unit.getTech() != null) {
			if (!unit.getTech().equals(TechType.None)) {
				unit.cancelResearch();
			}
		}
	}

	@Override
	public String toString() {
		return "cancel";
	}
}
