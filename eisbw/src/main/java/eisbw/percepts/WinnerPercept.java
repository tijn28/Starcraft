package eisbw.percepts;

import eis.iilang.Percept;

/**
 * @author Danny & Harm - The Winner percept.
 *
 */
public class WinnerPercept extends Percept {

  private static final long serialVersionUID = 1L;

  /**
   * The WorkerActivityPercept constructor.
   * 
   * @param hasWon
   *          Indicates whether the player has won or not.
   */
  public WinnerPercept() {
    super(Percepts.WINNER);
  }
}
