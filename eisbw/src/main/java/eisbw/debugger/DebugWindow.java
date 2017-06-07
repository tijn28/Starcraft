package eisbw.debugger;

import java.util.Iterator;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import eisbw.Game;
import jnibwapi.JNIBWAPI;

/**
 * @author Danny & Harm.
 */
public class DebugWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	private final SpeedSlider speedSlider;
	private final CheatButtons cheats;
	private final DrawButtons draw;

	/**
	 * Constructs a debug window for the game.
	 * 
	 * @param game
	 *            - the game data.
	 */
	public DebugWindow(Game game) {
		setTitle("StarCraft GOAL development tools");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 300);

		JPanel contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		speedSlider = new SpeedSlider();
		contentPane.add(speedSlider);

		cheats = new CheatButtons();
		contentPane.add(cheats);

		draw = new DrawButtons(game);
		contentPane.add(draw);

		setVisible(true);
	}

	private List<String> getActions() {
		List<String> result = cheats.getActions();
		cheats.clean();
		return result;
	}

	/**
	 * Gets current FPS.
	 * 
	 * @return current FPS value
	 */
	public int getFPS() {
		return speedSlider.getFPS();
	}

	/**
	 * Iterates over the debug options and executes.
	 * 
	 * @param bwapi
	 *            - the API.
	 */
	public void debug(JNIBWAPI bwapi) {
		Iterator<String> iter = getActions().iterator();
		while (iter.hasNext()) {
			bwapi.sendText(iter.next());
			iter.remove();
		}
		if (speedSlider.speedChanged()) {
			bwapi.setGameSpeed(speedSlider.getSpeed());
		}
		draw.draw(bwapi);
	}
}
