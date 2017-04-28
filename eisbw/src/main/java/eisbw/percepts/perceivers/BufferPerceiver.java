package eisbw.percepts.perceivers;

import eis.eis2java.translation.Filter;
import eis.iilang.Percept;
import eisbw.percepts.EnemyRacePercept;
import eisbw.percepts.Percepts;
import bwapi.Mirror;
import bwapi.Player;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Danny & Harm - The perceiver which handles all the buffered percepts.
 *
 */
public class BufferPerceiver extends Perceiver {

  /**
   * The BufferPerceiver constructor.
   * 
   * @param api
   *          The BWAPI
   */
  public BufferPerceiver(Mirror api) {
    super(api);
  }
  
  private void enemyRacePercept(Map<PerceptFilter, Set<Percept>> toReturn) {
    Set<Percept> percepts = new HashSet<>();
    for (Player p : api.getGame().enemies()) {
      percepts.add(new EnemyRacePercept(p.getRace().toString().toLowerCase()));
    }
    toReturn.put(new PerceptFilter(Percepts.ENEMYRACE, Filter.Type.ON_CHANGE), percepts);
  }

  @Override
  public Map<PerceptFilter, Set<Percept>> perceive(Map<PerceptFilter, Set<Percept>> toReturn) {
    enemyRacePercept(toReturn);

    return toReturn;
  }

}
