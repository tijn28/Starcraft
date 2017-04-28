package eisbw;

import eis.eis2java.translation.Filter;
import eis.iilang.Percept;
import eisbw.percepts.GameSpeedPercept;
import eisbw.percepts.Percepts;
import eisbw.percepts.perceivers.BufferPerceiver;
import eisbw.percepts.perceivers.ConstructionSitePerceiver;
import eisbw.percepts.perceivers.EndGamePerceiver;
import eisbw.percepts.perceivers.MapPerceiver;
import eisbw.percepts.perceivers.PerceptFilter;
import eisbw.percepts.perceivers.UnitsPerceiver;
import eisbw.units.StarcraftUnit;
import eisbw.units.Units;
import bwapi.Mirror;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author Danny & Harm - The game class where the percepts are updated.
 *
 */
public class Game {

  protected volatile Map<String, Map<PerceptFilter, Set<Percept>>> percepts;
  protected Units units;
  protected boolean endGame;
  protected volatile Map<PerceptFilter, Set<Percept>> constructionPercepts;
  protected volatile Map<PerceptFilter, Set<Percept>> endGamePercepts;
  protected StarcraftEnvironmentImpl env;

  private Map<PerceptFilter, Set<Percept>> mapPercepts;
  private Map<String, Map<String, Set<Percept>>> previous;

  /**
   * Constructor.
   * 
   * @param environment
   *          - the environment
   */
  public Game(StarcraftEnvironmentImpl environment) {
    units = new Units(environment);
    percepts = new HashMap<>();
    constructionPercepts = new HashMap<>();
    mapPercepts = new HashMap<>();
    previous = new HashMap<>();
    env = environment;
    endGame = false;
  }

  /**
   * Update the map.
   * 
   * @param api
   *          - the API.
   */
  public void updateMap(Mirror api) {
    Map<PerceptFilter, Set<Percept>> toReturn = new HashMap<>();
    new MapPerceiver(api).perceive(toReturn);
    mapPercepts = toReturn;
  }

  /**
   * Updates the percepts.
   * 
   * @param bwapi
   *          - the game bridge
   */
  public void update(Mirror bwapi) {
    Map<String, Map<PerceptFilter, Set<Percept>>> unitPerceptHolder = new HashMap<>();
    Map<PerceptFilter, Set<Percept>> perceptHolder = getPercepts(bwapi);

    Map<String, StarcraftUnit> unitList = units.getStarcraftUnits();
    for (Entry<String, StarcraftUnit> unit : unitList.entrySet()) {
      Map<PerceptFilter, Set<Percept>> thisUnitPercepts = new HashMap<>(perceptHolder);
      if (unit.getValue().isWorker()) {
        thisUnitPercepts.putAll(constructionPercepts);
      }
      if (endGame) {
        thisUnitPercepts.putAll(endGamePercepts);
      }
      thisUnitPercepts.putAll(mapPercepts);

      thisUnitPercepts.putAll(getGameSpeedPercept());

      thisUnitPercepts.putAll(unit.getValue().perceive());

      unitPerceptHolder.put(unit.getKey(), thisUnitPercepts);
    }

    percepts = unitPerceptHolder;
  }

  private List<Percept> translatePercepts(String unitName, Map<PerceptFilter, Set<Percept>> map) {
    List<Percept> percept = new LinkedList<>();
    if (!previous.containsKey(unitName)) {
      previous.put(unitName, new HashMap<>());
    }
    for (Entry<PerceptFilter, Set<Percept>> entry : map.entrySet()) {
      switch (entry.getKey().getType()) {
        case ALWAYS:
          percept.addAll(entry.getValue());
          break;
        case ONCE:
          if (!previous.get(unitName).containsKey(entry.getKey().getName())) {
            percept.addAll(entry.getValue());
            previous.get(unitName).put(entry.getKey().getName(), null);
          }
          break;
        case ON_CHANGE:
          handleOnChangePercept(entry, unitName, percept);
          break;
        case ON_CHANGE_NEG:
          Logger.getLogger("StarCraft logger").warning("Change with negation is not allowed.");
          break;
        default:
          break;
      }
    }
    return percept;
  }

  private void handleOnChangePercept(Entry<PerceptFilter, Set<Percept>> entry, String unitName,
      List<Percept> percept) {
    if (previous.get(unitName).containsKey(entry.getKey().getName())) {
      Set<Percept> checkList = new HashSet<>(entry.getValue());
      checkList.removeAll(previous.get(unitName).get(entry.getKey().getName()));
      if (!checkList.isEmpty()) {
        previous.get(unitName).put(entry.getKey().getName(), entry.getValue());
      }
      percept.addAll(checkList);
    } else {
      previous.get(unitName).put(entry.getKey().getName(), entry.getValue());
      percept.addAll(entry.getValue());
    }
  }

  /**
   * updates the constructionsites in the game.
   * 
   * @param bwapi
   *          - the bwapi
   */
  public void updateConstructionSites(Mirror bwapi) {
    Map<PerceptFilter, Set<Percept>> toReturn = new HashMap<>();
    new ConstructionSitePerceiver(bwapi).perceive(toReturn);
    constructionPercepts = toReturn;
  }

  /**
   * Updates the endGame percept.
   * 
   * @param bwapi
   *          - the bwapi
   */
  public void updateEndGamePerceiver(Mirror bwapi) {
    Map<PerceptFilter, Set<Percept>> toReturn = new HashMap<>();
    new EndGamePerceiver(bwapi).perceive(toReturn);
    endGamePercepts = toReturn;
  }

  public void setEndGame(boolean endGame) {
    this.endGame = endGame;
  }


  private Map<PerceptFilter, Set<Percept>> getPercepts(Mirror bwapi) {
    Map<PerceptFilter, Set<Percept>> toReturn = new HashMap<>();
    new BufferPerceiver(bwapi).perceive(toReturn);
    new UnitsPerceiver(bwapi).perceive(toReturn);
    return toReturn;
  }
  /**
   * Get the percepts of this unit.
   * 
   * @param entity
   *          - the name of the unit
   * @return the percepts
   */
  public List<Percept> getPercepts(String entity) {
    if (percepts.containsKey(entity)) {
      return translatePercepts(entity, percepts.get(entity));
    }
    return new LinkedList<>();
  }

  public Units getUnits() {
    return units;
  }

  /**
   * get the constructionSites.
   * 
   * @return the constructionsites.
   */
  public List<Percept> getConstructionSites() {
    List<Percept> perceptHolder = new LinkedList<>();
    for (Set<Percept> percept : constructionPercepts.values()) {
      perceptHolder.addAll(percept);
    }
    return perceptHolder;
  }

  /**
   * Clean the game data.
   */
  public void clean() {
    units.clean();
    percepts.clear();
    constructionPercepts.clear();
    mapPercepts.clear();
    previous.clear();
  }

  public int getAgentCount() {
    return env.getAgents().size();
  }

  public StarcraftEnvironmentImpl getEnvironment() {
    return env;
  }

  private Map<PerceptFilter, Set<Percept>> getGameSpeedPercept() {
    Map<PerceptFilter, Set<Percept>> toReturn = new HashMap<PerceptFilter, Set<Percept>>();
    Set<Percept> percepts = new HashSet<>();
    percepts.add(new GameSpeedPercept(env.getFPS()));
    toReturn.put(new PerceptFilter(Percepts.GAMESPEED, Filter.Type.ON_CHANGE), percepts);
    return toReturn;
  }

  /**
   * @param entity
   *          The evaluated entity
   * @return Boolean indicating whether the unit is initialized or not.
   */
  public boolean isInitialized(String entity) {
    return percepts.containsKey(entity);
  }
}
