package eisbw.percepts.perceivers;

import eis.eis2java.translation.Filter;
import eis.iilang.Percept;
import eisbw.percepts.EnemyRacePercept;
import eisbw.percepts.Percepts;
import jnibwapi.JNIBWAPI;
import jnibwapi.Player;

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
  public BufferPerceiver(JNIBWAPI api) {
    super(api);
  }
  
  private void enemyRacePercept(Map<PerceptFilter, Set<Percept>> toReturn) {
    Set<Percept> percepts = new HashSet<>();
    for (Player p : api.getEnemies()) {
      percepts.add(new EnemyRacePercept(p.getRace().getName().toLowerCase()));
    }
    toReturn.put(new PerceptFilter(Percepts.ENEMYRACE, Filter.Type.ON_CHANGE), percepts);
  }

  @Override
  public Map<PerceptFilter, Set<Percept>> perceive(Map<PerceptFilter, Set<Percept>> toReturn) {
    enemyRacePercept(toReturn);

    return toReturn;
  }

}
