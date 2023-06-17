package main;

/**
 * Represents an item and that can be purchased by the {@link main.Player
 * Player} at the {@link main.Market Market}. Since items are less complex than
 * {@link main.Athlete Athletes}, instead of generating separate instances of
 * identical items, a single instance is created, and the quantities currently
 * owned by the player and Market respectively are stored in fields. Items have
 * various values that will modify various athlete statistics when used.
 *
 * @author mdi48, bsm62
 * @version 1.04
 **/
public class Item extends Buyable {
	private int staminaBuff, offenceBuff, defenceBuff, staminaGain, playerCount, storeCount;

	/**
	 * Constructor that creates the window and adds graphical elements and buttons.
	 * 
	 * @param name        the name of the Item
	 * @param stamina     the amount that an athletes' max stamina will increase by
	 *                    when used
	 * @param offence     the amount that an athletes' offence will increase by when
	 *                    used
	 * @param defence     the amount that an athletes' defence will increase by when
	 *                    used
	 * @param restoration the amount of stamina that will be added to an athlete's
	 *                    current stamina when used
	 * @param price       the items' base price
	 */
	public Item(String name, int stamina, int offence, int defence, int restoration, int price) {
		super(name, price);
		playerCount = 0;
		storeCount = 0;
		staminaBuff = stamina;
		offenceBuff = offence;
		defenceBuff = defence;
		staminaGain = restoration;
	}

	/**
	 * Increases, or decreases, the number of this item that the {@link main.Market
	 * Market} owns.
	 * 
	 * @param number the amount to add or subtract
	 */
	public void changeStoreCount(int number) {
		storeCount += number;
	}

	/**
	 * Increases, or decreases, the number of this item that the {@link main.Player
	 * Player} owns.
	 * 
	 * @param number the amount to add or subtract
	 */
	public void changePlayerCount(int number) {
		playerCount += number;
	}

	public int getStaminaBuff() {
		return staminaBuff;
	}

	public int getStaminaGain() {
		return staminaGain;
	}

	public int getOffenceBuff() {
		return offenceBuff;
	}

	public int getDefenceBuff() {
		return defenceBuff;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public int getStoreCount() {
		return storeCount;
	}
}