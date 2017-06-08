package eisbw;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import eis.EIDefaultImpl;
import eis.eis2java.translation.Translator;
import eis.exceptions.ActException;
import eis.exceptions.AgentException;
import eis.exceptions.EntityException;
import eis.exceptions.ManagementException;
import eis.exceptions.NoEnvironmentException;
import eis.exceptions.RelationException;
import eis.iilang.Action;
import eis.iilang.EnvironmentState;
import eis.iilang.Parameter;
import eis.iilang.Percept;
import eisbw.configuration.Configuration;
import eisbw.translators.BooleanStringTranslator;
import eisbw.translators.ParamEnumTranslator;
import eisbw.translators.RaceStringTranslator;

/**
 * @author Danny & Harm - The starcraft environment class which handles most
 *         environment logics.
 *
 */
public class StarcraftEnvironmentImpl extends EIDefaultImpl {
	private static final long serialVersionUID = 1L;
	private final Logger logger = Logger.getLogger("StarCraft Logger");

	protected BwapiListener bwapiListener;
	private Configuration configuration;
	private final Game game;
	private final Set<String> registeredEntities;
	private volatile boolean mapAgent = false;

	/**
	 * Constructor of the environment.
	 */
	public StarcraftEnvironmentImpl() {
		super();
		installTranslators();
		this.registeredEntities = new HashSet<>();
		this.game = new Game(this);
	}

	private void installTranslators() {
		Translator translatorfactory = Translator.getInstance();
		translatorfactory.registerParameter2JavaTranslator(new ParamEnumTranslator());
		translatorfactory.registerParameter2JavaTranslator(new BooleanStringTranslator());
		translatorfactory.registerParameter2JavaTranslator(new RaceStringTranslator());
	}

	@Override
	public void init(Map<String, Parameter> parameters) throws ManagementException {
		super.init(parameters);
		setState(EnvironmentState.PAUSED);
		Thread.currentThread().setPriority(3);
		try {
			this.configuration = new Configuration(parameters);
			if (!"test".equals(this.configuration.getOwnRace())) {
				this.bwapiListener = new BwapiListener(this.game, this.configuration.getScDir(),
						this.configuration.getDebugMode(), this.configuration.getInvulnerable(),
						this.configuration.getSpeed());
				if (!"OFF".equals(this.configuration.getAutoMenu())
						&& !WindowsTools.isProcessRunning("Chaoslauncher.exe")) {
					WindowsTools.startChaoslauncher(this.configuration.getOwnRace(), this.configuration.getMap(),
							this.configuration.getScDir(), this.configuration.getAutoMenu(),
							this.configuration.getEnemyRace());
				}
			}
			this.mapAgent = this.configuration.getMapAgent();
			setState(EnvironmentState.RUNNING);
		} catch (Exception ex) {
			Logger.getLogger(StarcraftEnvironmentImpl.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public boolean mapAgent() {
		return this.mapAgent;
	}

	@Override
	protected LinkedList<Percept> getAllPerceptsFromEntity(String entity) throws NoEnvironmentException {
		return this.game.getPercepts(entity);
	}

	@Override
	protected boolean isSupportedByEnvironment(Action action) {
		return this.bwapiListener.getAction(action) != null;
	}

	@Override
	protected boolean isSupportedByType(Action action, String type) {
		return isSupportedByEnvironment(action);
	}

	@Override
	protected boolean isSupportedByEntity(Action action, String entity) {
		return this.bwapiListener.isSupportedByEntity(action, entity);
	}

	@Override
	protected Percept performEntityAction(String entity, Action action) throws ActException {
		this.bwapiListener.performEntityAction(entity, action);
		return null;
	}

	/**
	 * Adds a unit to the environment.
	 *
	 * @param unitName
	 *            - the name of the unit
	 * @param eisUnitType
	 *            - the type of the unit
	 */
	public void addToEnvironment(String unitName, String eisUnitType) {
		try {
			if (!this.registeredEntities.contains(unitName)) {
				this.registeredEntities.add(unitName);
				addEntity(unitName, eisUnitType);
			}
		} catch (EntityException exception) {
			this.logger.log(Level.WARNING, "Could not add " + unitName + " to the environment", exception);
		}
	}

	/**
	 * Deletes a unit from the environment.
	 *
	 * @param unitName
	 *            - the name of the unit
	 */
	public void deleteFromEnvironment(String unitName) {
		if (!this.registeredEntities.contains(unitName)) {
			return;
		}
		try {
			Set<String> agents = getAssociatedAgents(unitName);
			deleteEntity(unitName);
			for (String agent : agents) {
				unregisterAgent(agent);
			}
			this.registeredEntities.remove(unitName);
		} catch (EntityException exception) {
			this.logger.log(Level.WARNING, "Could not delete " + unitName + " from the environment", exception);
		} catch (RelationException exception) {
			this.logger.log(Level.WARNING, "Exception when deleting entity from the environment", exception);
		} catch (AgentException exception) {
			this.logger.log(Level.WARNING, "Exception when deleting agent from the environment", exception);
		}
	}

	public int getFPS() {
		return this.bwapiListener.getFPS();
	}
}
