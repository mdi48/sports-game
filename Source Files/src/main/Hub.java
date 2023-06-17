package main;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.LineBorder;

/**
 * GUI that acts as central hub of the game, displaying important information
 * and used to access other GUIs. In particular it provides access to the
 * {@link main.Club Club}, {@link main.MarketWindow MarketWindow}, and
 * {@link main.StadiumWindow StadiumWindow}, and also displays the
 * {@link main.Player Players}' current score, money, and the current week, as
 * well as providing the option to advance to the next week.
 *
 * @author mdi48
 * @version 1.05
 **/
public class Hub {
	private static JFrame hubWindow;
	private static JLabel weekLabel;
	private static JButton nextWeekButton;

	/**
	 * Constructor that creates the window and adds graphical elements and buttons.
	 * 
	 * @param x used to inherit the horizontal position of the previous window
	 * @param y used to inherit the vertical position of the previous window
	 */
	public Hub(int x, int y) {
		hubWindow = new JFrame();
		hubWindow.getContentPane().setBackground(new Color(0, 0, 0));
		hubWindow.setResizable(false);
		hubWindow.setTitle("Sports Tournament");
		hubWindow.setBounds(x, y, 600, 400);
		hubWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		hubWindow.getContentPane().setLayout(null);

		JLabel teamNameLabel = new JLabel("" + Player.getTeam().getName());
		teamNameLabel.setForeground(new Color(0, 255, 0));
		teamNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		teamNameLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		teamNameLabel.setBounds(150, 12, 300, 45);
		hubWindow.getContentPane().add(teamNameLabel);

		JLabel moneyAvailable = new JLabel("Money: $" + Player.getMoney());
		moneyAvailable.setForeground(new Color(0, 255, 0));
		moneyAvailable.setFont(new Font("Dialog", Font.BOLD, 16));
		moneyAvailable.setBounds(200, 60, 200, 25);
		moneyAvailable.setHorizontalAlignment(SwingConstants.CENTER);
		hubWindow.getContentPane().add(moneyAvailable);

		weekLabel = new JLabel("Week " + Player.getWeek() + "/" + Player.getMaxWeeks());
		weekLabel.setForeground(new Color(0, 255, 0));
		weekLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		weekLabel.setBounds(200, 170, 200, 40);
		weekLabel.setHorizontalAlignment(SwingConstants.CENTER);
		hubWindow.getContentPane().add(weekLabel);

		JButton marketButton = new JButton("Market");
		marketButton.setBackground(Color.black);
		marketButton.setBorder(new LineBorder(new Color(0, 255, 0)));
		marketButton.setForeground(new Color(0, 255, 0));
		marketButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MarketWindow(Hub.hubWindow.getX() - 150, Hub.hubWindow.getY() - 100);
				Hub.hubWindow.dispose();
			}
		});
		marketButton.setBounds(60, 300, 120, 40);
		hubWindow.getContentPane().add(marketButton);

		JButton teamPageButton = new JButton("Club");
		teamPageButton.setBackground(Color.black);
		teamPageButton.setBorder(new LineBorder(new Color(0, 255, 0)));
		teamPageButton.setForeground(new Color(0, 255, 0));
		teamPageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Club(Hub.hubWindow.getX() - 150, Hub.hubWindow.getY() - 100);
				Hub.hubWindow.dispose();
			}
		});
		teamPageButton.setBounds(240, 300, 120, 40);
		hubWindow.getContentPane().add(teamPageButton);

		JButton stadiumButton = new JButton("Stadium");
		stadiumButton.setBackground(Color.black);
		stadiumButton.setBorder(new LineBorder(new Color(0, 255, 0)));
		stadiumButton.setForeground(new Color(0, 255, 0));
		stadiumButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new StadiumWindow(Hub.hubWindow.getX() - 150, Hub.hubWindow.getY() - 100);
				Hub.hubWindow.dispose();
			}
		});

		stadiumButton.setBounds(420, 300, 120, 40);
		hubWindow.getContentPane().add(stadiumButton);

		nextWeekButton = new JButton("Next Week");
		if (Player.getWeek() == Player.getMaxWeeks()) {
			nextWeekButton.setText("End Game");
		}
		nextWeekButton.setBackground(Color.black);
		nextWeekButton.setBorder(new LineBorder(new Color(0, 255, 0)));
		nextWeekButton.setForeground(new Color(0, 255, 0));
		nextWeekButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Player.takeBye();
			}
		});
		nextWeekButton.setBounds(240, 220, 120, 40);
		hubWindow.getContentPane().add(nextWeekButton);

		JLabel scoreLabel = new JLabel("Score: " + Player.getScore());
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scoreLabel.setForeground(Color.GREEN);
		scoreLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		scoreLabel.setBounds(200, 100, 200, 25);
		hubWindow.getContentPane().add(scoreLabel);

		hubWindow.setVisible(true);
		hubWindow.requestFocusInWindow();
	}

	/**
	 * Updates the label displaying the current week. If it is the final week,
	 * updates the 'next week' button to say 'End Game'.
	 */
	public static void updateWeekLabel() {
		weekLabel.setText("Week " + Player.getWeek() + "/" + Player.getMaxWeeks());
		if (Player.getWeek() == Player.getMaxWeeks()) {
			nextWeekButton.setText("End Game");
		}
	}

	public static JFrame getWindow() {
		return hubWindow;
	}
}
