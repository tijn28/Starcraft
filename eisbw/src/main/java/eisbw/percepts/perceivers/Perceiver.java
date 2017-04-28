package eisbw.percepts.perceivers;

import bwapi.Mirror;

/**
 * @author Danny & Harm - The abstract perceiver.
 *
 */
public abstract class Perceiver implements IPerceiver {

  protected final Mirror api;

  /**
   * @param api
   *          The BWAPI.
   */
  public Perceiver(Mirror api) {
    this.api = api;
  }
}
