package main;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Used for the random generation of various values used throughout the game.
 * Generates names for {@link main.Team Team} and {@link main.Athlete Athletes},
 * prices for athletes, and reward values for {@link main.Match Matches}.
 * 
 * @author bsm62, mdi48
 * @version 1.11
 **/
public class GenerateStats {
	private static ArrayList<String> firstNames;
	private static ArrayList<String> lastNames;
	private static ArrayList<String> teamNames;
	/**
	 * A list of {@link main.Athlete Athlete} names that have already been used
	 * which is checked against to prevent duplicates.
	 */
	public static ArrayList<String> usedNames;
	/**
	 * Constructor for the class. 
	 * Creates lists of names for use in methods.
	 * 
	 */
	GenerateStats() {
		usedNames = new ArrayList<String>();
		firstNames = new ArrayList<>(Arrays.asList(new String[] {"James", "John", "Robert", "Michael", "Willia", "David", "Richard", "Charles", 
				"Joseph", "Thomas", "Daniel", "Paul", "Mark", "Donald", "George", "Kenneth", "Steven", "Edward", "Brian", "Ronald", "Anthony", 
				"Kevin", "Jason", "Matthew", "Gary", "Timothy", "Jose", "Larry", "Jeffrey", "Frank", "Scott", "Eric", "Stephen", "Andrew", 
				"Raymond", "Gregory", "Joshua", "Jerry ,Dennis", "Walter", "Patrick", "Peter", "Harold", "Douglas", "Henry", "Carl", "Arthur", "Ryan", "Roger", "Joe"}));
		lastNames = new ArrayList<>(Arrays.asList(new String[] {"Smith", "Johnson", "Brown", "Jones", "Garcia", "Miller", "Davis", "Martinez", "Lopez", "Wilson", "Anderson", 
				"Thomas", "Taylor", "Moore", "Jackson", "Martin", "Lee", "Thompson", "White", "Harris", "Sanchez", "Clark", "Lewis", "Robinson", "Walker", "Young", "Allen", 
				"King", "Wright", "Scott", "Torres", "Hill", "Flores", "Green", "Adams", "Nelson", "Baker", "Hall", "Rivera", "Campbell", "Mitchell", "Carter", "Roberts", 
				"Gomez", "Phillips", "Evans", "Turner", "Edwards", "Collins", "Stewart"}));
		teamNames = new ArrayList<>(Arrays.asList(new String[] {"Dolphins", "Eagles", "Berserkers", "Vikings", "Lions", "Wolves", "Sharks", "Bears", "Tigers", "Samurai", "Spiders", 
				"Scorpions", "Swordfish", "Bats", "Dragons", "Snakes", "Ravens", "Wolverines", "Dingoes", "Hyenas", "Orcas", "Knights", "Wasps", "Ocelots", "Demons", "Mystics", 
				"Warlords", "Barbarians", "Hounds", "Hornets", "Stallions", "Beasts", "Titans", "Slayers", "Giants", "Soldiers", "Wizards", "Vultures", 
				"Rhinos", "Pythons", "Boas", "Piranhas", "Thrashers", "Killers", "Breakers", "Skulls", "Druids", "Hawks", "Jellyfish", "Jackals"}));
	}
	public static String generateTeamName() { // generates name from namelists
		return teamNames.get(Player.getRandom().nextInt(teamNames.size()));
	}

	/**
	 * Generates a name for an {@link main.Athlete Athlete} by randomly selecting a
	 * first name and last name from two respective lists. The combination is
	 * checked against a list of previously used names to prevent duplication.
	 * 
	 * @return the generated name for the athlete
	 */
	public static String generateName() {
		String temp = firstNames.get(Player.getRandom().nextInt(firstNames.size())) + " "
				+ lastNames.get(Player.getRandom().nextInt(firstNames.size()));
		while (usedNames.contains(temp) == true) {
			temp = firstNames.get(Player.getRandom().nextInt(firstNames.size())) + " "
					+ lastNames.get(Player.getRandom().nextInt(firstNames.size()));
		}
		usedNames.add(temp);
		return temp;
	}

	/**
	 * Returns a value if inside a specified range. If the value is inside the
	 * range, returns the value unchanged. If the value is greater than the upper
	 * bound, returns the upper bound instead. If the value is less than the lower
	 * bound, returns the lower bound instead.
	 * 
	 * @param value the value to be limited
	 * @param max   the upper bound.
	 * @param min   the lower bound.
	 * @return the value or nearest bound
	 */
	private static int limitValue(int value, int min, int max) {
		return Math.min(Math.max(value, min), max);
	}

	/**
	 * Returns a value determined by the current week and, optionally, by game
	 * difficulty. This value is a 'multiplier', used to increase other values such
	 * as prices and {@link main.Athlete Athlete} statistics.
	 * 
	 * @param scaleDifficulty determines whether game difficulty will be used to
	 *                        determine the multiplier
	 * @return the randomly generated multiplier value
	 */
	private static double multiplier(boolean scaleDifficulty) { // used to increase stats based on difficulty/weeks
																// elapsed.
		if (scaleDifficulty = true) {
			return 1 + (0.05 * Player.getDifficulty()) + (0.02 * Player.getWeek());
		} else {
			return 1 + (0.02 * Player.getWeek());
		}

	}

	/**
	 * Generates a random stamina level for an {@link main.Athlete Athlete}. If the
	 * athlete is an enemy, the value is scaled based on game difficulty.
	 * 
	 * @param type    the athletes' specialization
	 * @param isEnemy whether the athlete is an enemy NPC or playable
	 * @return the randomly generated stamina level
	 */
	public static int generateStamina(String type, boolean isEnemy) {
		int staminaLevel = (int) (Player.getRandom().nextGaussian(50 * multiplier(isEnemy), 10)); // generates stamina
																									// stat from 0-100
		return limitValue(staminaLevel, 0, 100);
	}

	/**
	 * Generates a random defence level for an {@link main.Athlete Athlete}. The
	 * value is higher if the athletes' specialization is a defender, or lower if
	 * they are an attacker. If the athlete is an enemy, the value is scaled based
	 * on game difficulty.
	 * 
	 * @param type    the athletes' specialization
	 * @param isEnemy whether the athlete is an enemy NPC or playable
	 * @return the randomly generated defence level
	 */
	public static int generateDefence(String type, boolean isEnemy) {
		int defenceLevel = (int) (Player.getRandom().nextGaussian(50 * multiplier(isEnemy), 10)); // generates defense
																									// stat from 0-100
		if (type == "Defender") {
			defenceLevel += 10;
		}
		if (type == "Attacker") {
			defenceLevel -= 10;
		}
		return limitValue(defenceLevel, 0, 100);
	}

	/**
	 * Generates a random offence level for an {@link main.Athlete Athlete}. The
	 * value is higher if the athletes' specialization is a attacker, or lower if
	 * they are an defender. If the athlete is an enemy, the value is scaled based
	 * on game difficulty.
	 * 
	 * @param type    the athletes' specialization
	 * @param isEnemy whether the athlete is an enemy NPC or playable
	 * @return the randomly generated offence level
	 */
	public static int generateOffence(String type, boolean isEnemy) {
		int offenceLevel = (int) (Player.getRandom().nextGaussian(50 * multiplier(isEnemy), 10)); // generates offense
																									// stat from 0-100
		if (type == "Attacker") {
			offenceLevel += 10;
		}
		if (type == "Defender") {
			offenceLevel -= 10;
		}
		return limitValue(offenceLevel, 0, 100);
	}

	/**
	 * Generates a random price for an {@link main.Athlete Athlete}.
	 * 
	 * @return the randomly generated price
	 */
	public static int generatePrice() {
		int basePrice = (int) Player.getRandom().nextGaussian(1000, 200);
		return (int) Math.round(limitValue(basePrice, 500, 1500));
	}

	/**
	 * Generates a random reward score for a {@link main.Match Match}.
	 * 
	 * @return the randomly generated score
	 */
	public static long generateScore() {
		return (long) limitValue((int) (Player.getRandom().nextGaussian(500, 100) * multiplier(true)), 0, 1000);
	}

	/**
	 * Generates a random cash prize amount for a {@link main.Match Match}.
	 * 
	 * @return the randomly generated prize amount
	 */
	public static long generateMoney() {
		return (long) limitValue((int) ((Player.getRandom().nextGaussian(2500, 500))), 1000, 5000);
	}
}
