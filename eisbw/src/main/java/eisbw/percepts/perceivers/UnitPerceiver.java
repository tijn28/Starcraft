package eisbw.percepts.perceivers;

import bwapi.Mirror;
import bwapi.Unit;

/**
 * @author Danny & Harm - Abstract class for Unit Perceivers.
 *
 */
public abstract class UnitPerceiver extends Perceiver {

  protected final Unit unit;

  /**
   * @param api
   *          The BWAPI.
   * @param unit
   *          The perceiving unit.
   */
  public UnitPerceiver(Mirror api, Unit unit) {
    super(api);
    this.unit = unit;
  }
}
