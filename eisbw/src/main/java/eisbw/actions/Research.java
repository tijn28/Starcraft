package eisbw.actions;

import java.util.List;

import eis.iilang.Action;
import eis.iilang.Identifier;
import eis.iilang.Parameter;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.TechType;

/**
 * @author Danny & Harm - Researches a specified Tech Type.
 *
 */
public class Research extends StarcraftTechAction {
	/**
	 * The Research constructor.
	 *
	 * @param api
	 *            The BWAPI
	 */
	public Research(JNIBWAPI api) {
		super(api);
	}

	@Override
	public boolean canExecute(Unit unit, Action action) {
		List<Parameter> parameters = action.getParameters();
		TechType techType = getTechType(((Identifier) parameters.get(0)).getValue());
		return techType != null;
	}

	@Override
	public void execute(Unit unit, Action action) {
		List<Parameter> parameters = action.getParameters();
		TechType techType = getTechType(((Identifier) parameters.get(0)).getValue());

		unit.research(techType);
	}

	@Override
	public String toString() {
		return "research(Type)";
	}
}
