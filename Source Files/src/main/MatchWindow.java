package main;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Color;

/**
 * GUI for playing {@link main.Match Matches}. It shows the {@link main.Player
 * Player} the two {@link main.Team Teams} and their scores, which
 * {@link main.Athlete Athletes} are facing off against each other, and the
 * current round. There are action buttons to proceed with each round, and a
 * forfeit button that allows the player to leave at any point, at the cost of
 * losing the Match.
 *
 * @author mdi48, bsm62
 * @version 1.10
 **/
public class MatchWindow {
	private static JFrame matchWindow;
	private static JLabel playerTeamLabel, playerScoreLabel, enemyTeamLabel, enemyScoreLabel, roundLabel, statusLabel;
	private static Match match;
	private static JButton returnButton;
	private static JButton startButton, attackButton, defendButton;
	private static ArrayList<AthletePanel> playerPanels = new ArrayList<AthletePanel>();
	private static ArrayList<AthletePanel> enemyPanels = new ArrayList<AthletePanel>();
	private JLabel topScoreLabel;
	private JLabel bottomScoreLabel;
	private JLabel versusLabel;

	/**
	 * Constructor that creates the window and adds all of the graphical elements.
	 * This displays the scores of both {@link main.Team Teams}, the current round,
	 * and creates buttons to perform actions.
	 * 
	 * @param setMatch the match this window is representing
	 * @param x        used to inherit the horizontal position of the previous
	 *                 window
	 * @param y        used to inherit the vertical position of the previous window
	 */
	public MatchWindow(Match setMatch, int x, int y) {
		match = setMatch;
		matchWindow = new JFrame();
		matchWindow.setResizable(false);
		matchWindow.getContentPane().setBackground(Color.BLACK);
		matchWindow.setTitle("Sports Tournament");
		matchWindow.setBounds(x, y, 900, 600);
		matchWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		matchWindow.getContentPane().setLayout(null);

		enemyTeamLabel = new JLabel("" + match.getEnemyTeam().getName());
		enemyTeamLabel.setFont(new Font("Dialog", Font.BOLD, 18));
		enemyTeamLabel.setForeground(Color.GREEN);
		enemyTeamLabel.setHorizontalAlignment(SwingConstants.CENTER);
		enemyTeamLabel.setBounds(290, 45, 200, 40);
		matchWindow.getContentPane().add(enemyTeamLabel);

		enemyScoreLabel = new JLabel("" + match.getEnemyScore());
		enemyScoreLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		enemyScoreLabel.setForeground(Color.GREEN);
		enemyScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		enemyScoreLabel.setBounds(800, 140, 40, 40);
		matchWindow.getContentPane().add(enemyScoreLabel);

		playerTeamLabel = new JLabel("" + Player.getTeam().getName());
		playerTeamLabel.setFont(new Font("Dialog", Font.BOLD, 18));
		playerTeamLabel.setHorizontalAlignment(SwingConstants.CENTER);
		playerTeamLabel.setForeground(Color.GREEN);
		playerTeamLabel.setBounds(290, 430, 200, 40);
		matchWindow.getContentPane().add(playerTeamLabel);

		playerScoreLabel = new JLabel("" + match.getPlayerScore());
		playerScoreLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		playerScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		playerScoreLabel.setForeground(Color.GREEN);
		playerScoreLabel.setBounds(800, 340, 40, 40);
		matchWindow.getContentPane().add(playerScoreLabel);

		roundLabel = new JLabel("Round: " + match.getRound());
		roundLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		roundLabel.setForeground(Color.GREEN);
		roundLabel.setHorizontalAlignment(SwingConstants.CENTER);
		roundLabel.setBounds(734, 21, 150, 40);
		matchWindow.getContentPane().add(roundLabel);

		statusLabel = new JLabel("The match is about to begin!");
		statusLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		statusLabel.setForeground(Color.GREEN);
		statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		statusLabel.setBounds(125, 10, 525, 40);
		matchWindow.getContentPane().add(statusLabel);

		startButton = new JButton("Start Round " + match.getRound());
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				match.startRound();
			}
		});
		startButton.setBounds(315, 490, 150, 40);
		startButton.setBackground(Color.black);
		startButton.setBorder(new LineBorder(new Color(0, 255, 0)));
		startButton.setForeground(new Color(0, 255, 0));
		matchWindow.getContentPane().add(startButton);

		attackButton = new JButton("Attack!");
		attackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				match.attack(true);
			}
		});
		attackButton.setBounds(315, 490, 150, 40);
		attackButton.setBackground(Color.black);
		attackButton.setBorder(new LineBorder(new Color(0, 255, 0)));
		attackButton.setForeground(new Color(0, 255, 0));
		matchWindow.getContentPane().add(attackButton);

		defendButton = new JButton("Defend!");
		defendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				match.attack(false);
			}
		});
		defendButton.setBounds(315, 490, 150, 40);
		defendButton.setBackground(Color.black);
		defendButton.setBorder(new LineBorder(new Color(0, 255, 0)));
		defendButton.setForeground(new Color(0, 255, 0));
		matchWindow.getContentPane().add(defendButton);

		returnButton = new JButton("Forfeit");
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				match.returnToStadium();
			}
		});
		returnButton.setBounds(700, 490, 150, 40);
		returnButton.setBackground(Color.black);
		returnButton.setBorder(new LineBorder(new Color(0, 255, 0)));
		returnButton.setForeground(new Color(0, 255, 0));
		matchWindow.getContentPane().add(returnButton);

		topScoreLabel = new JLabel("Score:");
		topScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		topScoreLabel.setForeground(Color.GREEN);
		topScoreLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		topScoreLabel.setBounds(720, 100, 200, 40);
		matchWindow.getContentPane().add(topScoreLabel);

		bottomScoreLabel = new JLabel("Score:");
		bottomScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bottomScoreLabel.setForeground(Color.GREEN);
		bottomScoreLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		bottomScoreLabel.setBounds(720, 300, 200, 40);
		matchWindow.getContentPane().add(bottomScoreLabel);

		versusLabel = new JLabel("VS");
		versusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		versusLabel.setForeground(Color.GREEN);
		versusLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		versusLabel.setBounds(287, 235, 201, 49);
		matchWindow.getContentPane().add(versusLabel);

		showFaceOff(Player.getTeam().getAllRounders(), match.getEnemyTeam().getAllRounders());
		showStartButton();
		matchWindow.setVisible(true);
		matchWindow.requestFocusInWindow();
	}

	/**
	 * Displays the {@link main.Athlete Athletes} currently engaging eachother.
	 * Calls {@link MatchWindow#drawTeam(ArrayList, ArrayList, int) drawTeam()}
	 * method for both groups of athletes to creates rows in the top and bottom of
	 * the window.
	 * 
	 * @param playerTeam the list of player athletes in the current faceoff.
	 * @param enemyTeam  the list of enemy athletes in the current faceoff.
	 */
	public static void showFaceOff(ArrayList<Athlete> playerTeam, ArrayList<Athlete> enemyTeam) {
		drawTeam(enemyTeam, enemyPanels, 100);
		drawTeam(playerTeam, playerPanels, 300);
		matchWindow.repaint();
	}

	/**
	 * Creates a row of {@link main.AthletePanel AthletePanels} at a specified
	 * height in the window.
	 * 
	 * @param athleteList the list of athletes to draw
	 * @param panelList   the list of panels being updated
	 * @param rowHeight   the vertical position in the window of the row
	 */
	public static void drawTeam(ArrayList<Athlete> athleteList, ArrayList<AthletePanel> panelList, int rowHeight) {
		for (AthletePanel panel : panelList) {
			matchWindow.remove(panel);
		}
		panelList.clear();
		AthletePanel panel;
		int offset = ((5 - athleteList.size()) * 75) + 25;
		for (int i = 0; i < athleteList.size(); i++) {
			panel = new AthletePanel(athleteList.get(i));
			panelList.add(panel);
			matchWindow.getContentPane().add(panel);
			panel.setBounds(offset + 150 * i, rowHeight, 130, 120);
		}
	}

	/**
	 * Updates the scores of each {@link main.Team Team}, and the current round.
	 */
	public static void updateLabels() {
		enemyScoreLabel.setText("" + match.getEnemyScore());
		playerScoreLabel.setText("" + match.getPlayerScore());
		roundLabel.setText("Round: " + match.getRound());
	}

	/**
	 * Sets the text of status indicator at the top of the window. This is used to
	 * inform the player of what is happening in the {@link main.Match Match}.
	 * 
	 * @param status the string to display
	 */
	public static void setStatus(String status) {
		statusLabel.setText(status);
	}

	/**
	 * Shows the start button, hides the attack and defend buttons. Also updates the
	 * round number on the start button.
	 */
	public static void showStartButton() {
		startButton.setVisible(true);
		startButton.setText("Start Round " + match.getRound());
		attackButton.setVisible(false);
		defendButton.setVisible(false);
	}

	/**
	 * Shows the attack button, hides the start and defend buttons.
	 */
	public static void showAttackButton() {
		startButton.setVisible(false);
		attackButton.setVisible(true);
		defendButton.setVisible(false);
	}

	/**
	 * Shows the defend button, hides the start and attack buttons.
	 */
	public static void showDefendButton() {
		startButton.setVisible(false);
		attackButton.setVisible(false);
		defendButton.setVisible(true);
	}

	/**
	 * Finishes the match, showing the {@link main.Player Player} the result. Hides
	 * the action buttons and places the return button in their location.
	 */
	public static void finish() {
		attackButton.setVisible(false);
		defendButton.setVisible(false);
		startButton.setVisible(false);
		returnButton.setText("Leave");
		returnButton.setBounds(315, 490, 150, 40);
	}

	public static JFrame getWindow() {
		return matchWindow;
	}
}
