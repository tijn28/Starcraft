package eisbw;

import bwapi.TechType;
import bwapi.Unit;
import bwapi.UnitType;
import bwapi.UpgradeType;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Danny & Harm - The Utility class of the BWAPI.
 *
 */
public class BwapiUtility {

  private static final HashMap<String, UnitType> unitTypeMap = new HashMap<>();
  private static final HashMap<String, TechType> techTypeMap = new HashMap<>();
  private static final HashMap<String, UpgradeType> upgradeTypeMap = new HashMap<>();

  private BwapiUtility() {
    // Private constructor for static class.
  }

  /**
   * Get the name of a unit.
   * 
   * @param unit
   *          - the unit that has to be named.
   * @return the name of the unit.
   */
  public static String getUnitName(Unit unit) {
    String name = (unit.getType().toString() + unit.getID()).replace("_", "").replace(" ", "");
    name = name.substring(0, 1).toLowerCase() + name.substring(1);
    return name;
  }

  /**
   * Get the EIS unittype of a unit.
   * 
   * @param unit
   *          - the unit that you want yhe Type from.
   * @return the type of a unit.
   */
  public static String getEisUnitType(Unit unit) {
    String type = unit.getType().toString().replace(" ", "");
    type = type.substring(0, 1).toLowerCase() + type.substring(1);
    if ("terranSiegeTankTankMode".equals(type) || "terranSiegeTankSiegeMode".equals(type)) {
      return "terranSiegeTank";
    }
    return type;
  }

  /**
   * Convert EIS type to unit.
   * 
   * @param type
   *          - the type to be converted.
   * @return the unit.
   */
  public static UnitType getUnitType(String type) {
    if (unitTypeMap.isEmpty()) {
      Field[] declaredFields = String.class.getDeclaredFields();
      List<UnitType> staticFields = new LinkedList<UnitType>();
      for (Field field : declaredFields) {
        if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
          try {
            staticFields.add((UnitType) field.get(field));
          } catch (IllegalArgumentException | IllegalAccessException e) {
            Logger.getLogger("UtilLogger").log(Level.SEVERE,
                "Failed to " + "convert the fields to unittypes.");
            e.printStackTrace();
          }
        }
      }
      for (UnitType ut : staticFields) {
        unitTypeMap.put(ut.toString(), ut);
      }
    }

    return unitTypeMap.get(type);
  }

  /**
   * Convert type string to a techtype.
   * 
   * @param type
   *          - the string to be converted.
   * @return a techtype.
   */
  public static TechType getTechType(String type) {
    if (techTypeMap.isEmpty()) {
      Field[] declaredFields = String.class.getDeclaredFields();
      List<TechType> staticFields = new LinkedList<TechType>();
      for (Field field : declaredFields) {
        if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
          try {
            staticFields.add((TechType) field.get(field));
          } catch (IllegalArgumentException | IllegalAccessException e) {
            Logger.getLogger("UtilLogger").log(Level.SEVERE,
                "Failed to " + "convert the fields to techtypes.");
            e.printStackTrace();
          }
        }
      }
      for (TechType tt : staticFields) {
        techTypeMap.put(tt.toString(), tt);
      }
    }

    return techTypeMap.get(type);
  }

  /**
   * Convert a string to a upgradetype.
   * 
   * @param type
   *          - the string to be converted.
   * @return a upgradetype.
   */
  public static UpgradeType getUpgradeType(String type) {
    if (upgradeTypeMap.isEmpty()) {
      Field[] declaredFields = String.class.getDeclaredFields();
      List<UpgradeType> staticFields = new LinkedList<UpgradeType>();
      for (Field field : declaredFields) {
        if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
          try {
            staticFields.add((UpgradeType) field.get(field));
          } catch (IllegalArgumentException | IllegalAccessException e) {
            Logger.getLogger("UtilLogger").log(Level.SEVERE,
                "Failed to " + "convert the fields to techtypes.");
            e.printStackTrace();
          }
        }
      }
      for (UpgradeType tt : staticFields) {
        upgradeTypeMap.put(tt.toString(), tt);
      }
    }

    return upgradeTypeMap.get(type);
  }

}
