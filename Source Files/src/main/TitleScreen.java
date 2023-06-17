package main;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

/**
 * The title screen for the game, contains the main method for this package. It
 * gives the {@link main.Player Player} the option to play or exit the game. It
 * is also shown when a game is finished, showing the score and allowing the
 * game to be played again.
 * 
 * @author mdi48
 * @version 1.08
 */
public class TitleScreen {
	private static JFrame frmSportsTournament;
	private JButton startButton;
	private JLabel titleLogo;

	/**
	 * The Main method of the program.
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new TitleScreen();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Default constructor, used at start of game. Initial position is set to center
	 * it on a 1920 x 1080 screen.
	 */
	public TitleScreen() {
		initialize(660, 340);
	}
	/**
	 * Constructor for creating a variation of the title screen shown at the end of
	 * a game. This will thank the for playing, as well as displaying their final
	 * score, and give them the option to play again.
	 * 
	 * @param x used to inherit the horizontal position of the previous window
	 * @param y used to inherit the vertical position of the previous window
	 */
	public TitleScreen(int x, int y) {
		initialize(x, y);
		JLabel scoreLabel = new JLabel("Final Score: " + Player.getScore());
		scoreLabel.setFont(new Font("Century Gothic", Font.BOLD, 24));
		scoreLabel.setForeground(Color.GREEN);
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scoreLabel.setBounds(140, 120, 300, 120);
		frmSportsTournament.getContentPane().add(scoreLabel);
		titleLogo.setText("Thank You For Playing!");
		startButton.setText("Play again?");
	}

	/**
	 * Method that creates the window and adds various graphical elements to it.
	 * This is called by both constructors.
	 * 
	 * @param x used to inherit the horizontal position of the previous window
	 * @param y used to inherit the vertical position of the previous window
	 */
	private void initialize(int x, int y) {
		frmSportsTournament = new JFrame();
		frmSportsTournament.setResizable(false);
		frmSportsTournament.getContentPane().setBackground(new Color(0, 0, 0));
		frmSportsTournament.setTitle("Sports Tournament");
		frmSportsTournament.setBounds(x, y, 600, 400);
		frmSportsTournament.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSportsTournament.getContentPane().setLayout(null);

		startButton = new JButton("START");
		startButton.setBackground(Color.black);
		startButton.setBorder(new LineBorder(new Color(0, 255, 0)));
		startButton.setForeground(new Color(0, 255, 0));
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new GenerateStats();
				new SetupWindow(frmSportsTournament.getX() + 80, frmSportsTournament.getY() + 20);
				frmSportsTournament.dispose();
			}
		});

		startButton.setBounds(230, 250, 120, 30);
		frmSportsTournament.getContentPane().add(startButton);

		JButton exitButton = new JButton("EXIT");
		exitButton.setBackground(Color.black);
		exitButton.setBorder(new LineBorder(new Color(0, 255, 0)));
		exitButton.setForeground(new Color(0, 255, 0));
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitButton.setBounds(230, 300, 120, 30);
		frmSportsTournament.getContentPane().add(exitButton);

		titleLogo = new JLabel("Sports Tournament!");
		titleLogo.setForeground(new Color(0, 255, 51));
		titleLogo.setFont(new Font("Century Gothic", Font.BOLD, 34));
		titleLogo.setBounds(50, 60, 500, 50);
		titleLogo.setHorizontalAlignment(SwingConstants.CENTER);
		frmSportsTournament.getContentPane().add(titleLogo);
		frmSportsTournament.setVisible(true);
	}
}
