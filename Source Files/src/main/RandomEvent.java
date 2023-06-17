package main;

import java.util.ArrayList;
import java.lang.Math;

/**
 * Determines the random events that may (or may not) occur during the course of
 * the game. There are three random events that can occur for the
 * Player{@link main.Player}. First, an Athlete{@link main.Athlete} may improve
 * one of their attributes through training. Second, a random athlete may decide
 * to join the Team{@link main.Team}. Finally, one of the players' athletes may
 * decide to leave the team.
 * 
 * @author mdi48, bsm62
 * @version 1.08
 */
public class RandomEvent {
	/**
	 * This is the parent method for producing a random event. It randomly selects
	 * one of the three event types, then calls the relevant method.
	 */
	public static void event() {
		int eventType = Player.getRandom().nextInt(3);
		if (eventType == 0) {
			athleteBuff();
		}
		if (eventType == 1) {
			athleteQuits();
		}
		if (eventType == 2) {
			athleteJoins();
		}
	}

	/**
	 * This randomly selects an Athlete{@link main.Athlete} from the
	 * Players{@link main.Player}' current Team{@link main.Team}.
	 * 
	 * @return the chosen athlete
	 */
	private static Athlete pickAthlete() {
		ArrayList<Athlete> athletes = new ArrayList<Athlete>();
		for (Athlete athlete : Player.getTeam().getFullTeam()) {
			if (athlete != null) {
				athletes.add(athlete);
			}
		}
		if (athletes.size() > 0) {
			return athletes.get(Player.getRandom().nextInt(athletes.size()));
		} else {
			return null;
		}
	}

	/**
	 * This method has a random chance of causing an Athlete{@link main.Athlete} to
	 * increase one of their attributes. The probability of this occurring increases
	 * if the athlete has recently won a Match{@}. It creates a
	 * Notification{@link main.notification} to inform the
	 * Player{@link main.Player}.
	 */
	private static void athleteBuff() {
		int x = Hub.getWindow().getX() + 100;
		int y = Hub.getWindow().getY() + 115;
		Athlete athlete = pickAthlete();
		if (athlete != null) {
			int chance = 3;
			int weeks = athlete.getWinWeeks();
			if (weeks < 3) {
				chance = 1 + weeks;
			}
			if (Player.getRandom().nextInt(chance) == 0) {
				int gainType = Player.getRandom().nextInt(2);
				int gain = Math.abs((int) (Player.getRandom().nextGaussian() * 5) + 5);
				if (gainType == 0) {
					athlete.setStamina(athlete.getStamina() + gain);
					new Notification(
							athlete.getName() + " has increased thier max stamina by " + gain + " through training.", x,
							y);
				}
				if (gainType == 1) {
					athlete.setOffence(athlete.getOffence() + gain);
					new Notification(
							athlete.getName() + " has increased thier offence by " + gain + " through training.", x, y);
				}
				if (gainType == 2) {
					athlete.setDefence(athlete.getDefence() + gain);
					new Notification(
							athlete.getName() + " has increased thier defence by " + gain + " through training.", x, y);
				}
			}
		}
	}

	/**
	 * This method has a random chance of causing an Athlete{@link main.Athlete} to
	 * quit the Team{@link main.Team}. The probability of this occurring increases
	 * if the athlete has recently won a Match{@}. It creates a
	 * Notification{@link main.notification} to inform the
	 * Player{@link main.Player}.
	 */
	private static void athleteQuits() {
		Athlete athlete = pickAthlete();
		if (athlete != null) {
			int chance = 10;
			int weeks = athlete.getInjuryWeeks();
			if (weeks < 5) {
				chance = 5 + weeks;
			}
			if (Player.getRandom().nextInt(chance) == 0) {
				new Notification(athlete.getName() + " has left the team.", Hub.getWindow().getX() + 100,
						Hub.getWindow().getY() + 115);
				Player.getTeam().getFullTeam().set(Player.getTeam().getFullTeam().indexOf(athlete), null);
				Player.getTeam().updateSubTeams();
			}
		}
	}

	/**
	 * This method has a random chance of causing a new Athlete{@link main.Athlete}
	 * to join the Team{@link main.Team}. The probability of this occurring
	 * increases if the athlete has recently won a Match{@}. It creates a
	 * Notification{@link main.notification} to inform the
	 * Player{@link main.Player}.
	 */
	private static void athleteJoins() {
		ArrayList<Athlete> reserves = Player.getTeam().getReserves();
		int emptySlots = 0;
		for (int i = 0; i < reserves.size(); i++) { // counts empty reserve slots
			if (reserves.get(i) == null) {
				emptySlots++;
			}
		}
		if (emptySlots > 0) {
			if (Player.getRandom().nextInt(6 - emptySlots) == 0) { // higher chance if more empty slots
				Athlete athlete = new Athlete();
				reserves.set(reserves.indexOf(null), athlete);
				Player.getTeam().joinTeams();
				new Notification(athlete.getName() + " has joined the team.", Hub.getWindow().getX() + 100,
						Hub.getWindow().getY() + 115);
			}
		}
	}
}
