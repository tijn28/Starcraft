package eisbw;

import java.util.HashMap;
import java.util.Map;

import jnibwapi.Unit;
import jnibwapi.types.TechType;
import jnibwapi.types.TechType.TechTypes;
import jnibwapi.types.UnitType;
import jnibwapi.types.UnitType.UnitTypes;
import jnibwapi.types.UpgradeType;
import jnibwapi.types.UpgradeType.UpgradeTypes;

/**
 * @author Danny & Harm - The Utility class of the BWAPI.
 *
 */
public class BwapiUtility {
	private static final Map<String, UnitType> unitTypeMap = new HashMap<>();
	private static final Map<String, TechType> techTypeMap = new HashMap<>();
	private static final Map<String, UpgradeType> upgradeTypeMap = new HashMap<>();

	private BwapiUtility() {
		// Private constructor for static class.
	}

	/**
	 * Get the name of a unit.
	 *
	 * @param unit
	 *            - the unit that has to be named.
	 * @return the name of the unit.
	 */
	public static String getUnitName(Unit unit) {
		String name = (unit.getType().getName() + unit.getID()).replace("_", "").replace(" ", "");
		return name.substring(0, 1).toLowerCase() + name.substring(1);
	}

	public static String getUnitType(Unit unit) {
		String type = unit.getType().getName();
		if (type.length() > 17 && "Terran Siege Tank".equals(type.substring(0, 17))) {
			return "Terran Siege Tank";
		} else {
			return type;
		}
	}

	/**
	 * Get the EIS unittype of a unit.
	 *
	 * @param unit
	 *            - the unit that you want yhe Type from.
	 * @return the type of a unit.
	 */
	public static String getEisUnitType(Unit unit) {
		String type = unit.getType().getName().replace(" ", "");
		type = type.substring(0, 1).toLowerCase() + type.substring(1);
		if ("terranSiegeTankTankMode".equals(type) || "terranSiegeTankSiegeMode".equals(type)) {
			return "terranSiegeTank";
		} else {
			return type;
		}
	}

	/**
	 * Convert EIS type to unit.
	 *
	 * @param type
	 *            - the type to be converted.
	 * @return the unit.
	 */
	public static UnitType getUnitType(String type) {
		if (unitTypeMap.isEmpty()) {
			for (UnitType ut : UnitTypes.getAllUnitTypes()) {
				unitTypeMap.put(ut.getName(), ut);
			}
		}
		return unitTypeMap.get(type);
	}

	/**
	 * Convert type string to a techtype.
	 *
	 * @param type
	 *            - the string to be converted.
	 * @return a techtype.
	 */
	public static TechType getTechType(String type) {
		if (techTypeMap.isEmpty()) {
			for (TechType tt : TechTypes.getAllTechTypes()) {
				techTypeMap.put(tt.getName(), tt);
			}
		}
		return techTypeMap.get(type);
	}

	/**
	 * Convert a string to a upgradetype.
	 *
	 * @param type
	 *            - the string to be converted.
	 * @return a upgradetype.
	 */
	public static UpgradeType getUpgradeType(String type) {
		if (upgradeTypeMap.isEmpty()) {
			for (UpgradeType tt : UpgradeTypes.getAllUpgradeTypes()) {
				upgradeTypeMap.put(tt.getName(), tt);
			}
		}
		return upgradeTypeMap.get(type);
	}
}
