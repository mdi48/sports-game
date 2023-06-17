package main;

import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;

/**
 * Graphical element that creates and holds multiple {@link main.AthletePanel
 * AthletePanels}. This is used as a helper function for {@link main.Athlete
 * Athletes}, where it represents them as panels so that we can easily view
 * their name and attributes. and move them around in the {@link main.Club
 * Club}, and purchase or sell them in the Market.
 * 
 * @author bsm62
 * @version 1.03
 * @see main.AthletePanel
 * 
 */
public class AthleteContainer extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Athlete> athleteList;
	private ArrayList<AthletePanel> panelList;

	/**
	 * Constructs a panel for displaying the {@link main.Player Players}' athletes
	 */
	public AthleteContainer() {
		athleteList = Player.getTeam().getFullTeam();
		panelList = new ArrayList<AthletePanel>();
		setBackground(Color.black);
		setBorder(new LineBorder(new Color(0, 255, 0)));
		setPreferredSize(new Dimension((Player.getTeamSize() + 5) * 140 + 10, 170));
		setLayout(null);
		int position = 0;
		for (Athlete athlete : Player.getTeam().getFullTeam()) {
			AthletePanel panel;
			if (athlete == null) {
				panel = new AthletePanel();
			} else {
				panel = new AthletePanel(athlete, false);
			}
			panelList.add(panel);
			panel.setBounds(10 + 140 * position, 10, 130, 150);
			this.add(panel);
			position++;
		}
	}

	/**
	 * Constructs for displaying the {@link main.Market Markets}' athletes.
	 * 
	 * @param athletes list of athletes to be displayed in the container
	 */
	public AthleteContainer(ArrayList<Athlete> athletes) {
		athleteList = athletes;
		panelList = new ArrayList<AthletePanel>();

		setBackground(Color.black);
		setBorder(new LineBorder(new Color(0, 255, 0)));
		setPreferredSize(new Dimension(athleteList.size() * 140 + 10, 170));
		setLayout(null);

		int position = 0;
		for (Athlete athlete : athleteList) {
			AthletePanel panel = new AthletePanel(athlete, true);
			panelList.add(panel);
			panel.setBounds(10 + 140 * position, 10, 130, 150);
			this.add(panel);
			position++;
		}
	}

	public ArrayList<Athlete> getAthletes() {
		return athleteList;
	}

	public ArrayList<AthletePanel> getPanels() {
		return panelList;
	}

}
