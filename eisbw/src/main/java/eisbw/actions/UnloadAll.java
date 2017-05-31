package eisbw.actions;

import java.util.List;

import eis.iilang.Action;
import eis.iilang.Parameter;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

/**
 * @author Danny & Harm - Unloads all units.
 *
 */
public class UnloadAll extends StarcraftLoadingAction {
	/**
	 * The UnloadAll constructor.
	 * 
	 * @param api
	 *            The BWAPI
	 */
	public UnloadAll(JNIBWAPI api) {
		super(api);
	}

	@Override
	public boolean isValid(Action action) {
		List<Parameter> parameters = action.getParameters();
		return parameters.isEmpty();
	}

	@Override
	public void execute(Unit unit, Action action) {
		unit.unloadAll(false);
	}

	@Override
	public String toString() {
		return "unloadAll";
	}
}
