package eisbw.actions;

import bwapi.Mirror;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Danny & Harm - The ActionProvider.
 *
 */
public class ActionProvider {

  private final Map<String, StarcraftAction> actions;

  /**
   * The ActionProvider constructor.
   */
  public ActionProvider() {
    this.actions = new HashMap<>();
  }

  /**
   * @param actionName
   *          The name of the action.
   * @return The action object with the specified action name.
   */
  public StarcraftAction getAction(String actionName) {
    return actions.get(actionName);
  }

  /**
   * Load all actions to the EIS environment.
   * 
   * @param api
   *          the API to pass into the actions.
   */
  public void loadActions(Mirror api) {
    actions.put("attack/1", new Attack(api));
    actions.put("attack/2", new AttackMove(api));
    actions.put("upgrade/1", new Upgrade(api));
    actions.put("build/3", new Build(api));
    actions.put("gather/1", new Gather(api));
    actions.put("move/2", new Move(api));
    actions.put("train/1", new Train(api));
    actions.put("stop/0", new Stop(api));
    actions.put("ability/1", new Use(api));
    actions.put("ability/2", new UseOnTarget(api));
    actions.put("ability/3", new UseOnPosition(api));
    actions.put("research/1", new Research(api));
    actions.put("setRallyPoint/2", new SetRallyPoint(api));
    actions.put("setRallyPoint/1", new SetRallyUnit(api));
    actions.put("lift/0", new Lift(api));
    actions.put("land/2", new Land(api));
    actions.put("siege/0", new Siege(api));
    actions.put("unsiege/0", new UnSiege(api));
    actions.put("buildAddon/1", new BuildAddon(api));
    actions.put("follow/1", new Follow(api));
    actions.put("load/1", new Load(api));
    actions.put("rightClick/2", new RightClickPosition(api));
    actions.put("rightClick/1", new RightClickUnit(api));
    // Documentation goes untill this point
    actions.put("unload/1", new UnloadUnit(api));
    actions.put("unloadAll/0", new UnloadAll(api));
    actions.put("morph/1", new Morph(api));
    actions.put("patrol/1", new Patrol(api));
    actions.put("cancel/0", new Cancel(api));
  }
}
