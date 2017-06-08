package eisbw.debugger;

/**
 * @author Danny & Harm.
 */
public enum Draw {
	CHOKEPOINTS("choke"), CONSTRUCTION_SITES("construct"), BASE_LOCATIONS("base"), BUILDING_DETAILS("buildings");

	private final String parameter;

	private Draw(String name) {
		this.parameter = name;
	}

	public String getName() {
		return this.parameter;
	}
}
