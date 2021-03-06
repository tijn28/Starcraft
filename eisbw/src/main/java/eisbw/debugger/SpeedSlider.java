package eisbw.debugger;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Danny & Harm - Class which handles the Speedslider of the dev. tool.
 *
 */
public class SpeedSlider extends JPanel {
	private static final long serialVersionUID = 1L;

	private int initialSpeed = 20;
	private int slowest = 50;
	private int fastest = 0;

	private boolean changed = false;
	private int speed = this.initialSpeed;

	/**
	 * Slider to change gamespeed.
	 */
	public SpeedSlider() {
		setPreferredSize(new Dimension(500, 100));

		final JLabel showSpeed = new JLabel("Current FPS: " + getFPS());

		final JSlider slider = new JSlider(SwingConstants.HORIZONTAL, this.fastest, this.slowest, this.initialSpeed);
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent event) {
				SpeedSlider.this.changed = true;
				SpeedSlider.this.speed = slider.getValue();
				if (slider.getValue() == 0) {
					showSpeed.setText("Current FPS: MAX");
				} else {
					showSpeed.setText("Current FPS: " + getFPS());
				}
			}
		});

		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);

		Hashtable<Integer, JLabel> labels = new Hashtable<>();
		labels.put(0, new JLabel("MAX"));
		labels.put(10, new JLabel("100"));
		labels.put(20, new JLabel("50"));
		labels.put(30, new JLabel("33"));
		labels.put(40, new JLabel("25"));
		labels.put(50, new JLabel("20"));
		slider.setLabelTable(labels);
		slider.setInverted(true);

		slider.setPaintLabels(true);

		// JButton defaultSpeed = new JButton("Default speed");
		// defaultSpeed.addActionListener(new ActionListener() {
		// @Override
		// public void actionPerformed(ActionEvent event) {
		// slider.setValue(30);
		// }
		// });

		JButton tournamentSpeed = new JButton("Tournament Speed");
		tournamentSpeed.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				slider.setValue(20);
			}
		});

		JLabel label = new JLabel("Game Speed");
		add(label);
		add(slider);

		add(showSpeed);
		// add(defaultSpeed);
		add(tournamentSpeed);
	}

	/**
	 * The on change function.
	 *
	 * @return true iff changed.
	 */
	public boolean speedChanged() {
		if (this.changed) {
			this.changed = false;
			return true;
		}
		return false;
	}

	public int getSpeed() {
		return this.speed;
	}

	public int getFPS() {
		if (this.speed > 0) {
			return (1000 / this.speed);
		} else {
			// max fps
			return 1000;
		}
	}
}
