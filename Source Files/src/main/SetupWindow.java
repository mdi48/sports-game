package main;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;

/**
 * GUI used to set starting conditions for the game. All of these have default
 * values, but may be changed by the {@link main.Player Player} The default
 * value for {@link main.Team Team} name is chosen randomly. The player may
 * enter a custom name, but it must have length between 3 to 16 inclusive and
 * contain only letters and spaces. The number of weeks is 10 by default, but
 * may be set between 5 and 15 inclusive. The team size is 10 by default, but
 * may be set be set between 4 and 15 inclusive. Difficulty is 'normal' by
 * default, but may be switched to 'easy' or 'hard'.
 * 
 * @author mdi48, bsm62
 * @version 1.04
 */

public class SetupWindow {
	private JFrame setupWindow;
	private static String teamName;
	private static double diffLevel = 1;
	private int setSize, setTime;
	private JTextField inputName;

	/**
	 * Constructor for the setup window.
	 * 
	 * @param x used to inherit the horizontal position of the previous window
	 * @param y used to inherit the vertical position of the previous window
	 */
	public SetupWindow(int x, int y) {
		setupWindow = new JFrame();
		setupWindow.getContentPane().setBackground(new Color(0, 0, 0));
		setupWindow.setBackground(new Color(0, 0, 0));
		setupWindow.setResizable(false);
		setupWindow.setBounds(x, y, 440, 360);
		setupWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setupWindow.getContentPane().setLayout(null);

		JLabel titleLabel = new JLabel("SETUP");
		titleLabel.setForeground(new Color(0, 255, 0));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		titleLabel.setBounds(150, 12, 140, 20);
		setupWindow.getContentPane().add(titleLabel);

		inputName = new JTextField(GenerateStats.generateTeamName());
		inputName.setBorder(new LineBorder(new Color(0, 255, 0)));
		inputName.setForeground(new Color(0, 255, 0));
		inputName.setBackground(new Color(0, 0, 0));
		inputName.setColumns(10);
		inputName.setBounds(250, 40, 150, 20);
		setupWindow.getContentPane().add(inputName);

		JLabel nameLabel = new JLabel("Please enter Team Name:");
		nameLabel.setForeground(new Color(0, 255, 0));
		nameLabel.setBounds(50, 42, 200, 15);
		setupWindow.getContentPane().add(nameLabel);

		JLabel timeLabel = new JLabel("Please select number of Weeks:");
		timeLabel.setForeground(new Color(0, 255, 0));
		timeLabel.setBounds(100, 68, 250, 15);
		setupWindow.getContentPane().add(timeLabel);

		JLabel teamSizeLabel = new JLabel("Please select Team Size:");
		teamSizeLabel.setForeground(new Color(0, 255, 0));
		teamSizeLabel.setBounds(125, 140, 200, 15);
		setupWindow.getContentPane().add(teamSizeLabel);

		JSlider weekSlider = new JSlider();
		weekSlider.setForeground(new Color(0, 255, 0));
		weekSlider.setBackground(new Color(0, 0, 0));
		weekSlider.setValue(10);
		weekSlider.setPaintLabels(true);
		weekSlider.setPaintTicks(true);
		weekSlider.setSnapToTicks(true);
		weekSlider.setMajorTickSpacing(1);
		weekSlider.setMinimum(5);
		weekSlider.setMaximum(15);
		weekSlider.setBounds(25, 85, 400, 45);
		setupWindow.getContentPane().add(weekSlider);

		JSlider sizeSlider = new JSlider();
		sizeSlider.setForeground(new Color(0, 255, 0));
		sizeSlider.setBackground(new Color(0, 0, 0));
		sizeSlider.setValue(10);
		sizeSlider.setSnapToTicks(true);
		sizeSlider.setPaintTicks(true);
		sizeSlider.setPaintLabels(true);
		sizeSlider.setMinimum(4);
		sizeSlider.setMaximum(15);
		sizeSlider.setMajorTickSpacing(1);
		sizeSlider.setBounds(25, 160, 400, 45);
		setupWindow.getContentPane().add(sizeSlider);

		JToggleButton toggleEasy = new JToggleButton("Easy");
		toggleEasy.setBackground(Color.black);
		toggleEasy.setBorder(new LineBorder(new Color(0, 255, 0)));
		toggleEasy.setForeground(new Color(0, 255, 0));
		JToggleButton toggleNormal = new JToggleButton("Normal");
		toggleNormal.setBackground(Color.black);
		toggleNormal.setBorder(new LineBorder(new Color(0, 255, 0)));
		toggleNormal.setForeground(new Color(0, 255, 0));
		JToggleButton toggleHard = new JToggleButton("Hard");
		toggleHard.setBackground(Color.black);
		toggleHard.setBorder(new LineBorder(new Color(0, 255, 0)));
		toggleHard.setForeground(new Color(0, 255, 0));
		toggleNormal.setSelected(true); // default setting

		toggleEasy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				diffLevel = 0.5;
				toggleNormal.setSelected(false);
				toggleHard.setSelected(false);
			}
		});
		toggleEasy.setBounds(30, 217, 100, 40);
		setupWindow.getContentPane().add(toggleEasy);

		toggleNormal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				diffLevel = 1;
				toggleEasy.setSelected(false);
				toggleHard.setSelected(false);
			}
		});
		toggleNormal.setBounds(170, 217, 100, 40);
		setupWindow.getContentPane().add(toggleNormal);

		toggleHard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				diffLevel = 2;
				toggleEasy.setSelected(false);
				toggleNormal.setSelected(false);
			}
		});
		toggleHard.setBounds(310, 217, 100, 40);
		setupWindow.getContentPane().add(toggleHard);

		JButton continueButton = new JButton("Continue");
		continueButton.setBackground(Color.black);
		continueButton.setBorder(new LineBorder(new Color(0, 255, 0)));
		continueButton.setForeground(new Color(0, 255, 0));
		continueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamName = inputName.getText();
				if (teamName.length() >= 3 && teamName.length() <= 16 && teamName.matches("[a-zA-Z ]+")) {
					setTime = weekSlider.getValue();
					setSize = sizeSlider.getValue();
					new Player(teamName, diffLevel, setTime, setSize);
					new Market();
					new Stadium();
					MarketWindow teamPicker = new MarketWindow(setupWindow.getX() - 225, setupWindow.getY() - 90);
					teamPicker.setupMode();
					setupWindow.dispose();
				} else {
					new Notification(
							"Invalid team name!" + "\n"
									+ "Name must be between 3 and 16 characters and contain no numbers or symbols.",
							setupWindow.getX() + 25, setupWindow.getY() + 135);
				}
			}
		});
		continueButton.setBounds(145, 273, 150, 40);
		setupWindow.getContentPane().add(continueButton);
		setupWindow.setVisible(true);
	}
}
