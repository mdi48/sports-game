package main;

import java.util.ArrayList;

/**
 * Generates and stores {@link main.Match Matches} available to the
 * {@link main.Player Player}.
 * 
 * @author mdi48
 * @version 1.07
 **/
public class Stadium {
	private static ArrayList<Match> matchList;
	/**
	 * A list of enemy {@link main.Team Team} names in the current list of
	 * {@link main.Match Matches}. Used to prevent two matches with the same enemy
	 * team names being generated.
	 **/
	public static ArrayList<String> usedNames;

	/**
	 * Constructor for the class.
	 **/
	public Stadium() {
		matchList = new ArrayList<Match>();
		usedNames = new ArrayList<String>();
		newMatches();
	}

	/**
	 * Clears the current list of {@link main.Match Matches} and generates new ones.
	 **/
	public static void newMatches() {
		matchList.clear();
		usedNames.clear();
		usedNames.add(Player.getTeam().getName());
		while (matchList.size() < Player.getRandom().nextInt(3, 6)) {
			Match match = new Match();
			if (usedNames.contains(match.getEnemyTeam().getName()) == false) {
				matchList.add(match);
				usedNames.add(match.getEnemyTeam().getName());
			}
		}
	}

	public static ArrayList<Match> getMatches() {
		return matchList;
	}
}
