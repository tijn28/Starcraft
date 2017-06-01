package eisbw;

import jnibwapi.types.UnitType;
import jnibwapi.types.UnitType.UnitTypes;

/**
 * @author Danny & Harm - Utility class for type checks.
 *
 */
public class UnitTypesEx {
	private UnitTypesEx() {
		// Private constructor as this class does not need to be instantiated.
	}

	/**
	 * Check if a unit is a resource.
	 * 
	 * @param unitType
	 *            - the type of the unit.
	 * @return true when the unit is a resource, else false.
	 */
	public static boolean isResourceType(UnitType unitType) {
		return isMineralField(unitType) || unitType.isRefinery() || isVespeneGeyser(unitType);
	}

	/**
	 * Check if a unit is a vespene geyser.
	 * 
	 * @param unitType
	 *            - the type of the unit.
	 * @return true iff the unit is a vespene geyser.
	 */
	public static boolean isVespeneGeyser(UnitType unitType) {
		return (unitType.getID() == UnitTypes.Resource_Vespene_Geyser.getID());
	}

	/**
	 * Check if a unit is a mineral field.
	 * 
	 * @param unitType
	 *            - the type of the unit.
	 * @return true iff the unit is a vespene geyser.
	 */
	public static boolean isMineralField(UnitType unitType) {
		return (unitType.getID() == UnitTypes.Resource_Mineral_Field.getID())
				|| (unitType.getID() == UnitTypes.Resource_Mineral_Field_Type_2.getID())
				|| (unitType.getID() == UnitTypes.Resource_Mineral_Field_Type_3.getID());
	}
}
