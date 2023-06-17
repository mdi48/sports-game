package main;

/**
 * Represents the athletes that make up a {@link main.Team Team}. Athletes have
 * various attributes that are used when they compete in {@link main.Match
 * Matches}. Offense determines the attacking power of the athlete, and goes up
 * against the defence of the enemy team's athletes. Stamina is a third
 * attribute that is deducted at the end of each round, and any athletes who
 * have zero stamina are removed from play. Athletes can also use
 * {@link main.Item items} to improve their attributes.
 *
 * @author mdi48 , bsm62
 * @version 1.11
 **/
public class Athlete extends Buyable {
	private String athleteType;
	private int athleteOffence, athleteDefence;
	private int athleteStamina;
	private int currentStamina;
	/**
	 * Represents the number of weeks since the athlete was last injured. Lower
	 * values increase the chance of an athlete quitting, while higher values have
	 * no effect. The default value is 10 to distinguish from 0, which is used when
	 * an athlete has been injured in the current week.
	 */
	public int injuryWeeks = 10;
	/**
	 * Represents the number of weeks since the athlete last won a {@link main.Match
	 * Match}. Lower values increase the chance of an athlete gaining bonuses from
	 * training, while higher values have no effect. The default value is 10 to
	 * distinguish from 0, which is used when an athlete has won a match in the
	 * current week.
	 */
	public int winWeeks = 10;

	/**
	 * Constructs a playable instance of Athlete. These are used to populate
	 * {@link main.Market Market} with athletes that can be bought and used by the
	 * player. The athlete's class, name, and attributes are randomly assigned using
	 * {@link main.GenerateStats GenerateStats}.
	 */
	public Athlete() {
		super(GenerateStats.generateName(), GenerateStats.generatePrice());
		String[] typeList = { "Defender", "Attacker", "All-Rounder" };
		athleteType = typeList[Player.getRandom().nextInt(3)];
		athleteStamina = GenerateStats.generateStamina(athleteType, false);
		athleteOffence = GenerateStats.generateOffence(athleteType, false);
		athleteDefence = GenerateStats.generateDefence(athleteType, false);
		currentStamina = athleteStamina;
	}

	/**
	 * Constructs a non-playable instance of Athlete, used to populate NPC
	 * {@link main.Team Teams}. The athletes' class must be specified since they are
	 * used to fill specific team positions. The name and attributes are randomly
	 * assigned using {@link main.GenerateStats GenerateStats}.
	 * 
	 * @param type sets the athlete's class
	 */
	public Athlete(String type) {
		super(GenerateStats.generateName(), 0);
		athleteType = type;
		athleteStamina = GenerateStats.generateStamina(type, true);
		athleteOffence = GenerateStats.generateOffence(type, true);
		athleteDefence = GenerateStats.generateDefence(type, true);
		currentStamina = athleteStamina;
	}

	/**
	 * Applies effects of an {@link main.Item Item} to the athlete. This modifies
	 * the athletes statistics, and removes one of the Item from the Player's
	 * inventor.
	 * 
	 * @param item specifics the instance of Item to be used
	 */
	public void useItem(Item item) {
		setStamina(item.getStaminaBuff() + athleteStamina);
		setCurrentStamina(item.getStaminaGain() + item.getStaminaBuff() + currentStamina);
		setOffence(item.getOffenceBuff() + athleteOffence);
		setDefence(item.getDefenceBuff() + athleteDefence);
		item.changePlayerCount(-1);
		if (item.getPlayerCount() == 0) {
			Player.getItems().remove(item);
		}
	}

	/**
	 * Returns a value if inside a specified range. If the value is inside the
	 * range, returns the value unchanged. If the value is greater than the upper
	 * bound, returns the upper bound instead If the value is less than the lower
	 * bound, returns the lower bound instead. In this case the lower bound is
	 * always zero.
	 * 
	 * @param value the value to be limited
	 * @param max   the upper bound.
	 * @return the value or nearest bound
	 */
	public int limitValue(int value, int max) {
		return Math.min(Math.max(value, 0), max);
	}

	public int getStamina() {
		return athleteStamina;
	}

	public void setStamina(int stamina) {
		athleteStamina = limitValue(stamina, 100);
	}

	public int getOffence() {
		return athleteOffence;
	}

	public void setOffence(int offence) {
		athleteOffence = limitValue(offence, 100);
	}

	public int getDefence() {
		return athleteDefence;
	}

	public void setDefence(int defence) {
		athleteDefence = limitValue(defence, 100);
	}

	public String getType() {
		return athleteType;
	}

	public int getInjuryWeeks() {
		return injuryWeeks;
	}

	public void setInjuryWeeks(int weeks) {
		injuryWeeks = weeks;
	}

	public int getWinWeeks() {
		return winWeeks;
	}

	public void setWinWeeks(int weeks) {
		winWeeks = weeks;
	}

	public int getCurrentStamina() {
		return currentStamina;
	}

	public void setCurrentStamina(int stamina) {
		currentStamina = limitValue(stamina, athleteStamina);
	}
}
