package eisbw;

import eis.iilang.Action;
import jnibwapi.Unit;

public class BwapiAction {
	private final Unit unit;
	private final Action action;
	
	public BwapiAction(Unit unit, Action action) {
		this.unit = unit;
		this.action = action;
	}
	
	public Unit getUnit() {
		return this.unit;
	}
	
	public Action getAction() {
		return this.action;
	}

	@Override
	public int hashCode() {
		return unit.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof BwapiAction)) {
			return false;
		}
		BwapiAction other = (BwapiAction) obj;
		if (unit == null) {
			if (other.unit != null) {
				return false;
			}
		} else if (!unit.equals(other.unit)) {
			return false;
		}
		return true;
	}
}
