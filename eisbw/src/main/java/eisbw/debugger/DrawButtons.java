package eisbw.debugger;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import eis.eis2java.exception.NoTranslatorException;
import eis.eis2java.exception.TranslationException;
import eisbw.Game;
import eisbw.debugger.draw.AgentCounter;
import eisbw.debugger.draw.DrawBases;
import eisbw.debugger.draw.DrawUnitDetails;
import eisbw.debugger.draw.DrawChokepoints;
import eisbw.debugger.draw.DrawConstructionSite;
import eisbw.debugger.draw.IDraw;
import jnibwapi.JNIBWAPI;

/**
 * @author Danny & Harm.
 */
public class DrawButtons extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private final Map<String, IDraw> draw;
	private final Color buttonBackground;

	/**
	 * Toggle switches to draw in the game.
	 *
	 * @param game
	 *            - the game data.
	 */
	public DrawButtons(Game game) {
		setLayout(new BorderLayout());

		JLabel label = new JLabel("Draw actions", SwingConstants.CENTER);
		add(label, BorderLayout.NORTH);

		this.draw = new HashMap<>(5);
		this.draw.put(Draw.CONSTRUCTION_SITES.getName(), new DrawConstructionSite(game));
		this.draw.put(Draw.CHOKEPOINTS.getName(), new DrawChokepoints(game));
		this.draw.put(Draw.BASE_LOCATIONS.getName(), new DrawBases(game));
		this.draw.put(Draw.BUILDING_DETAILS.getName(), new DrawUnitDetails(game));
		this.draw.put("AgentCount", new AgentCounter(game));

		JButton buildButton = new JButton("Build Locations");
		buildButton.setActionCommand(Draw.CONSTRUCTION_SITES.getName());
		buildButton.addActionListener(this);
		JButton chokeButton = new JButton("Chokepoints");
		chokeButton.setActionCommand(Draw.CHOKEPOINTS.getName());
		chokeButton.addActionListener(this);
		JButton baseButton = new JButton("Base Locations");
		baseButton.setActionCommand(Draw.BASE_LOCATIONS.getName());
		baseButton.addActionListener(this);
		JButton buildingButton = new JButton("Unit Details");
		buildingButton.setActionCommand(Draw.BUILDING_DETAILS.getName());
		buildingButton.addActionListener(this);

		this.buttonBackground = buildButton.getBackground();

		JPanel drawPanel = new JPanel();

		drawPanel.add(buildingButton);
		drawPanel.add(baseButton);
		drawPanel.add(chokeButton);
		drawPanel.add(buildButton);

		add(drawPanel);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		this.draw.get(event.getActionCommand()).toggle();
		JButton buttonPressed = (JButton) event.getSource();
		if (buttonPressed.getBackground().equals(Color.GRAY)) {
			buttonPressed.setBackground(this.buttonBackground);
		} else {
			buttonPressed.setBackground(Color.GRAY);
		}
	}

	/**
	 * Draw on screen.
	 *
	 * @param api
	 *            - the API.
	 * @throws NoTranslatorException
	 *             iff there is no translator.
	 * @throws TranslationException
	 *             iff translation fails.
	 */
	public void draw(JNIBWAPI api) {
		for (Entry<String, IDraw> drawable : this.draw.entrySet()) {
			drawable.getValue().draw(api);
		}
	}
}
