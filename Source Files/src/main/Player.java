package main;

import java.util.ArrayList;
import java.util.Random;

/**
 * Stores information about the player and key variables for the game. It stores
 * the player's {@link main.Team Team}, {@link main.Item Items}, current score,
 * and current money It also stores the chosen difficulty, team size, max number
 * of weeks and the current week, and an instance of the {@link java.util.Random
 * Random} class. This classes' methods and variables are largely static since
 * there should only ever be one instance.
 * 
 * @author mdi48, bsm62
 * @version 1.11
 **/
public class Player {
	/**
	 * This is an instance of the default {@link java.util.Random Random} class used
	 * to provide random numbers to various classes in this package.
	 **/
	public static Random randomStream = new Random();
	private static long money, score;
	private static double difficulty;
	private static int maxWeeks, currentWeek;
	/**
	 * The 'default' size of all {@link main.Team Teams} used in this game, not the
	 * player's current team size.
	 **/
	public static int teamSize;
	private static Team team;
	private static ArrayList<Item> itemList;

	/**
	 * Constructor for the player. Initializes other variables to their default
	 * values, such as setting money and score to zero, and the current week to one.
	 * Also generates an empty {@link main.Team Team} with the players' chosen name.
	 * 
	 * @param name  the name the player has chosen for their team
	 * @param diff  the chosen difficulty (0.5 = easy, 1.0 = normal, 2.0 = hard)
	 * @param weeks the number of weeks the game will run for
	 * @param size  the size of all teams in this game
	 **/
	public Player(String name, double diff, int weeks, int size) {
		score = 0;
		money = 0;
		currentWeek = 1;
		itemList = new ArrayList<Item>();
		difficulty = diff;
		maxWeeks = weeks;
		teamSize = size;
		team = new Team(name, new ArrayList<Athlete>());
		for (int i = 0; i <= size + 5; i++) {
			team.getFullTeam().add(null);
		}
		team.updateSubTeams();
	}

	/**
	 * This is used to end the current week, performing a number of functions in the
	 * process. All {@link main.Athlete Athletes} will have their stamina restored
	 * to full, and the current week will be incremented. The {@link main.Market
	 * Market} will restock its' athletes and {@link main.Item Items}, and the
	 * {@link main.Stadium Stadium} will generate new {@link main.Match Matches}.
	 * There is also a chance of a {@link main.RandomEvent RandomEvent} being
	 * triggered.
	 * 
	 **/
	public static void takeBye() {
		currentWeek++;
		if (Player.getWeek() > Player.getMaxWeeks()) {
			new TitleScreen(Hub.getWindow().getX(), Hub.getWindow().getY());
			Hub.getWindow().dispose();
		} else {
			Hub.updateWeekLabel();
			RandomEvent.event();
			Market.stockItems();
			Market.stockAthletes();
			Stadium.newMatches();
			for (Athlete athlete : team.getFullTeam()) {
				if (athlete != null) {
					athlete.setCurrentStamina(athlete.getStamina());
					athlete.setInjuryWeeks(athlete.getInjuryWeeks() + 1);
					athlete.setWinWeeks(athlete.getWinWeeks() + 1);
				}
			}
		}
	}

	public static Team getTeam() {
		return team;
	}

	public static double getDifficulty() {
		return difficulty;
	}

	public static int getWeek() {
		return currentWeek;
	}

	public static int getMaxWeeks() {
		return maxWeeks;
	}

	public static Random getRandom() {
		return randomStream;
	}

	public static void changeScore(long amount) {
		score += amount;
	}

	public static void changeMoney(long amount) {
		money += amount;
	}

	public static int getTeamSize() {
		return teamSize;
	}

	public static long getMoney() {
		return money;
	}

	public static long getScore() {
		return score;
	}

	public static ArrayList<Item> getItems() {
		return itemList;
	}
}
