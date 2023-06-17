package main;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Font;

/**
 * GUI for the {@link main.Stadium Stadium} class that displays currently
 * available {@link main.Match Matches}.
 * 
 * @author mdi48, bsm62
 * @version 1.06
 **/
public class StadiumWindow {
	private static JFrame stadiumWindow;
	private ArrayList<MatchPanel> panelList;

	/**
	 * Constructor that creates the window and adds all of the graphical elements.
	 * Displays a list of {@link main.MatchPanel MatchPanels} that represent
	 * available {@link main.Match Matches}.
	 * 
	 * @param x used to inherit the horizontal position of the previous window
	 * @param y used to inherit the vertical position of the previous window
	 */
	public StadiumWindow(int x, int y) {
		stadiumWindow = new JFrame();
		stadiumWindow.setResizable(false);
		stadiumWindow.setTitle("Stadium");
		stadiumWindow.getContentPane().setBackground(Color.BLACK);
		stadiumWindow.setBounds(x, y, 900, 600);
		stadiumWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		stadiumWindow.getContentPane().setLayout(null);
		panelList = new ArrayList<MatchPanel>();

		int position = 0;
		int offset = 285 - 55 * Stadium.getMatches().size();
		for (Match match : Stadium.getMatches()) {
			MatchPanel panel = new MatchPanel(match);
			panel.setBounds(100, offset + 110 * position, 500, 100);
			panelList.add(panel);
			stadiumWindow.getContentPane().add(panel);
			position++;
		}

		JButton returnButton = new JButton("Return");
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Hub(getWindow().getX() + 150, getWindow().getY() + 100);
				stadiumWindow.dispose();
			}
		});
		returnButton.setBounds(685, 300, 130, 40);
		returnButton.setBackground(Color.black);
		returnButton.setBorder(new LineBorder(new Color(0, 255, 0)));
		returnButton.setForeground(new Color(0, 255, 0));
		stadiumWindow.getContentPane().add(returnButton);

		JLabel matchesAvailable = new JLabel(Stadium.getMatches().size() + " Matches available");
		matchesAvailable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		matchesAvailable.setForeground(Color.GREEN);
		matchesAvailable.setHorizontalAlignment(SwingConstants.CENTER);
		matchesAvailable.setBounds(650, 200, 200, 50);
		stadiumWindow.getContentPane().add(matchesAvailable);
		stadiumWindow.setVisible(true);
	}

	public static JFrame getWindow() {
		return stadiumWindow;
	}
}
