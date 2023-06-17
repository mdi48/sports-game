package main;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Generates and stores {@link main.Athlete Athletes} and {@link main.Item
 * Items} available for sale to the {@link main.Player Player}. The player can
 * also sell athletes and items back to the Market. This class is also used to
 * help create the player's {@link main.Team Team} during setup.
 * 
 * @author bsm62, mdi48
 * @version 1.09
 **/
public class Market {
	private static ArrayList<Item> referenceList, itemList;
	private static ArrayList<Athlete> athleteList;

	/**
	 * Constructor for the class. Initializes the market and creates a reference
	 * list of available {@link main.Item Items}, as well as creating a current
	 * stock of items and {@link main.Athlete Athletes}.
	 **/
	public Market() {
		referenceList = new ArrayList<Item>();
		referenceList.add(new Item("Protein Shake", 5, 5, 5, 0, 500));
		referenceList.add(new Item("Steroids", 10, 10, 10, -35, 1000));
		referenceList.add(new Item("Energy Drink", 0, 0, 0, 25, 500));
		referenceList.add(new Item("Ice Pack", 0, 0, 0, 10, 250));
		referenceList.add(new Item("Training Weights", 0, 0, 10, 0, 500));
		referenceList.add(new Item("Running Shoes", 10, 0, 0, 0, 500));
		referenceList.add(new Item("Practice ball", 0, 10, 0, 0, 500));
		referenceList.add(new Item("Placebo pills", 0, 0, 0, 0, 100));
		itemList = new ArrayList<Item>();
		int teamSize = Player.getTeamSize();
		stockItems();
		athleteList = new ArrayList<Athlete>();
		ArrayList<Long> minCost = new ArrayList<Long>();
		int startingCash = 0;
		for (int i = 0; i < teamSize + Player.getRandom().nextInt(3, 6); i++) {
			athleteList.add(new Athlete());
			athleteList.get(i).toggleBuyBack();
			minCost.add(athleteList.get(i).getContractPrice());
		}
		Collections.sort(minCost);
		for (int i = 0; i <= teamSize; i++) {
			startingCash += minCost.get(i);
		}
		if (Player.getDifficulty() < 2.0) {
			startingCash += 250 * teamSize / Player.getDifficulty();
		}
		Player.changeMoney(1000 * Math.round((startingCash / 1000) + 0.5));
	}

	/**
	 * Clears the markets' list of {@link main.Item Items} and generates a new list.
	 * A random number from 0 to 5 of each item in the reference list is added.
	 **/
	public static void stockItems() {
		itemList.clear();
		for (Item item : referenceList) {
			item.changeStoreCount(Player.getRandom().nextInt(6) - item.getStoreCount());
			if (item.getStoreCount() > 0) {
				itemList.add(item);
			}
		}
	}

	/**
	 * Clears the markets' list of {@link main.Athlete Athletes} and generates a new
	 * list. A random number from 3 to 5 athletes are randomly generated.
	 **/
	public static void stockAthletes() {
		athleteList.clear();
		for (int i = 0; i < Player.getRandom().nextInt(3, 6); i++) {
			athleteList.add(new Athlete());
		}
	}

	/**
	 * Calls {@link main.Buyable#toggleBuyBack toggleBuyBack()} method for all
	 * {@link main.Athlete Athletes} in the markets' stock and {@link main.Player
	 * Player} inventory. This allows athletes to be sold back to the Market for the
	 * same price they were bought for, allowing easier {@link main.Team Team}
	 * creation during setup.
	 **/
	public static void athleteBuyBack() {
		for (Athlete athlete : athleteList) {
			if (athlete != null) {
				athlete.toggleBuyBack();
			}
		}
		for (Athlete athlete : Player.getTeam().getFullTeam()) {
			if (athlete != null) {
				athlete.toggleBuyBack();
			}
		}
	}

	/**
	 * Buys the specified {@link main.Item Item}, reducing its' store quantity and
	 * increasing its' {@link main.Player Player} quantity. If the items' player
	 * quantity was previously zero, adds the item to the players' list of items. If
	 * the items' store quantity is now zero, removes the item from the Markets'
	 * list of items.
	 * 
	 * @param item the item being bought
	 **/
	public static void buyItem(Item item) {
		if (Player.getMoney() >= item.getContractPrice()) {
			item.changePlayerCount(1);
			item.changeStoreCount(-1);
			Player.changeMoney(-item.getContractPrice());
			if (item.getPlayerCount() == 1) {
				Player.getItems().add(item);
				MarketWindow.getPlayerItemPanel().getPanels().add(new ItemPanel(item, false));
				MarketWindow.getPlayerItemPanel().updateItems();
			}
			if (item.getStoreCount() == 0) {
				Market.getItems().remove(item);
				MarketWindow.getStoreItemPanel().updateItems();
			}
			MarketWindow.updateItemPanels();
		} else {
			new Notification("You cannot afford this!", MarketWindow.getWindow().getX() + 250,
					MarketWindow.getWindow().getY() + 200);
		}

	}

	/**
	 * Sells the specified {@link main.Item Item}, reducing its' {@link main.Player
	 * Player} quantity and increasing its' store quantity. If the items' store
	 * quantity was previously zero, adds the item to the Markets' list of items. If
	 * the items' player quantity is now zero, removes the item from the players'
	 * list of items.
	 * 
	 * @param item the item being sold
	 **/
	public static void sellItem(Item item) {
		item.changePlayerCount(-1);
		item.changeStoreCount(1);
		Player.changeMoney(item.getBuyBackPrice());
		if (item.getPlayerCount() == 0) {
			Player.getItems().remove(item);
			MarketWindow.getPlayerItemPanel().updateItems();
		}
		if (item.getStoreCount() == 1) {
			Market.getItems().add(item);
			MarketWindow.getStoreItemPanel().getPanels().add(new ItemPanel(item, true));
			MarketWindow.getStoreItemPanel().updateItems();
		}
		MarketWindow.updateItemPanels();
	}

	/**
	 * Buys the specified {@link main.Athlete Athlete}. Removes the athlete from the
	 * Markets's list of athletes and adds it to the {@link main.Player Players}'s
	 * {@link main.Team Team}.
	 * 
	 * @param athlete the athlete being bought
	 **/
	public static void buyAthlete(AthletePanel panel, Athlete athlete) {
		if (Player.getMoney() >= athlete.getContractPrice()) {
			if (Player.getTeam().getFullTeam().contains(null)) {
				Player.getTeam().getFullTeam().set(Player.getTeam().getFullTeam().indexOf(null), athlete);
				Player.changeMoney(-athlete.getContractPrice());
				Player.getTeam().updateSubTeams();
				MarketWindow.getStoreAthletePanel().getAthletes().remove(athlete);
				MarketWindow.getStoreAthletePanel().getPanels().remove(panel);
				panel.showSellButton();
				MarketWindow.getPlayerAthletePanel().getPanels().set(Player.getTeam().getFullTeam().indexOf(athlete),
						panel);
				MarketWindow.updateAthletePanels();
			} else {
				new Notification("Your team is full!", MarketWindow.getWindow().getX() + 250,
						MarketWindow.getWindow().getY() + 200);
			}
		} else {
			new Notification("You cannot afford this!", MarketWindow.getWindow().getX() + 250,
					MarketWindow.getWindow().getY() + 200);
		}
	}

	/**
	 * Sells the specified {@link main.Athlete Athlete}. Removes the athlete from
	 * the {@link main.Player Players}' {@link main.Team Team} and adds it to the
	 * Markets's list of athletes.
	 * 
	 * @param athlete the athlete being sold
	 **/
	public static void sellAthlete(AthletePanel panel, Athlete athlete) {
		MarketWindow.getPlayerAthletePanel().getPanels().set(Player.getTeam().getFullTeam().indexOf(athlete),
				new AthletePanel());
		Player.getTeam().getFullTeam().set(Player.getTeam().getFullTeam().indexOf(athlete), null);
		Player.changeMoney(athlete.getBuyBackPrice());
		Player.getTeam().updateSubTeams();
		MarketWindow.getStoreAthletePanel().getAthletes().add(athlete);
		MarketWindow.getStoreAthletePanel().getPanels().add(panel);
		panel.showBuyButton();
		MarketWindow.updateAthletePanels();
	}

	public static ArrayList<Item> getItems() {
		return itemList;
	}

	public static ArrayList<Athlete> getAthletes() {
		return athleteList;
	}
}
