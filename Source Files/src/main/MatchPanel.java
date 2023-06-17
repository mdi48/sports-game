package main;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.Font;

/**
 * Graphical element that represents a {@link main.Match Match} as a panel in
 * the {@link main.StadiumWindow StadiumWindow}. Displays the name of the
 * opposing {@link main.Team Team}, as well as the reward points and money for
 * winning the Match. Allows the {@link main.Player Player} to select the match
 * to play it.
 *
 * @author bsm62
 * @version 1.0
 **/
public class MatchPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Match match;

	/**
	 * Constructs panel for use in the {@link main.StadiumWindow StadiumWindow}
	 * class.
	 * 
	 * @param setMatch determines the match that this panel will represent
	 */
	public MatchPanel(Match setMatch) {
		match = setMatch;
		setBorder(new LineBorder(new Color(0, 255, 0)));
		setBackground(Color.black);
		setLayout(null);

		JLabel nameLabel = new JLabel(match.getEnemyTeam().getName());
		nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setBounds(20, 15, 250, 25);
		nameLabel.setForeground(new Color(0, 255, 0));
		add(nameLabel);

		JButton playButton = new JButton("Play Match");
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				match.playMatch();
			}
		});
		playButton.setBounds(350, 30, 130, 40);
		playButton.setBackground(Color.black);
		playButton.setBorder(new LineBorder(new Color(0, 255, 0)));
		playButton.setForeground(new Color(0, 255, 0));
		add(playButton);

		JLabel pointsLabel = new JLabel("Reward points: " + match.getRewardScore());
		pointsLabel.setBounds(20, 50, 150, 15);
		pointsLabel.setForeground(new Color(0, 255, 0));
		add(pointsLabel);

		JLabel moneyLabel = new JLabel("Reward money: " + match.getRewardMoney());
		moneyLabel.setBounds(180, 50, 150, 15);
		moneyLabel.setForeground(new Color(0, 255, 0));
		add(moneyLabel);

	}
}
