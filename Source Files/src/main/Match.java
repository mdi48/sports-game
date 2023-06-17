package main;

import java.util.ArrayList;

/**
 * A match that is available for the {@link main.Player Player} to compete in.
 * It contains an enemy {@link main.Team Team} that the player will compete
 * against, and values for the points and money that will be rewarded if one.
 * The mechanics for playing the match are also contained in this class as
 * methods. A match consists of 10 rounds, with the win going to whoever has
 * more points at the end. The basic process is the "all-rounder" positions in
 * each team face off against eachother, combining both their offence and
 * defence statistics. Whichever side wins the faceoff then goes on the
 * offensive with their attacker positions, while the other team defends with
 * their defenders. If the attackers succeed, their team receives a point.
 * Otherwise no points are awarded to either side. The next round then begins.
 *
 * @author mdi48, bsm62
 * @version 1.08
 **/
public class Match {
	private long rewardScore;
	private long rewardMoney;
	private Team enemyTeam;
	private int playerScore = 0;
	private int enemyScore = 0;
	private int round = 1;
	private ArrayList<Athlete> attackingTeam;
	private ArrayList<Athlete> defendingTeam;

	/**
	 * Constructor for a new Match that will be available to the {@link main.Player
	 * Player}. Generates a new {@link main.Team Team} with a random name and random
	 * {@link main.Athlete Athletes}, as well as reward amounts using the
	 * {@link main.GenerateStats GenerateStats} class.
	 **/
	public Match() {
		enemyTeam = new Team();
		rewardScore = GenerateStats.generateScore();
		rewardMoney = GenerateStats.generateMoney();
	}

	/**
	 * Starts the process to play this match if the requirements are met. Closes the
	 * current {@link main.StadiumWindow StadiumWindow} and creates a new
	 * {@link main.MatchWindow MatchWindow}.
	 **/
	public void playMatch() {
		if (checkRequirements() == true) {
			new MatchWindow(this, StadiumWindow.getWindow().getX(), StadiumWindow.getWindow().getY());
			Stadium.getMatches().remove(this);
			StadiumWindow.getWindow().dispose();
		}
	}

	/**
	 * Starts a new round. This faces the "AllRounder" positions of the
	 * {@link main.Player Player} and enemy {@link main.Team Teams} off against each
	 * other. The outcome is randomly determined, but is weighted by the combined
	 * total offence and defence of each group. So while having higher statistics
	 * does not guarantee a win, it does make it more likely. Based on the outcome
	 * the method then sets up the conditions for the attacking stage of the match.
	 **/
	public void startRound() {
		int playerTotal = 0;
		int enemyTotal = 0;
		for (Athlete attacker : Player.getTeam().getAllRounders()) {
			playerTotal += attacker.getOffence() + attacker.getDefence();
		}
		for (Athlete defender : enemyTeam.getAllRounders()) {
			enemyTotal += defender.getOffence() + defender.getDefence();
		}
		if (playerTotal >= Player.getRandom().nextInt(playerTotal + enemyTotal)) {
			deductStamina(1.0, Player.getTeam().getAllRounders());
			deductStamina(1.5, enemyTeam.getAllRounders());
			attackingTeam = Player.getTeam().getAttackers();
			defendingTeam = enemyTeam.getDefenders();
			MatchWindow.showAttackButton();
			MatchWindow.showFaceOff(attackingTeam, defendingTeam);
			MatchWindow.setStatus(Player.getTeam().getName() + " won the faceoff");
		} else {
			deductStamina(1.5, Player.getTeam().getAllRounders());
			deductStamina(1.0, enemyTeam.getAllRounders());
			attackingTeam = enemyTeam.getAttackers();
			defendingTeam = Player.getTeam().getDefenders();
			MatchWindow.showDefendButton();
			MatchWindow.showFaceOff(defendingTeam, attackingTeam);
			MatchWindow.setStatus(enemyTeam.getName() + " won the faceoff");
		}
	}

	/**
	 * This is the second stage of a round. This faces the "Attacker" positions of
	 * the {@link main.Team Team} that won the first stage against the "Defender"
	 * positions of the team that last. The outcome is randomly determined, but is
	 * weighted by the total offense of the attackers compared to the total defense
	 * of the defenders. So while having higher statistics does not guarantee a win,
	 * it does make it more likely. If the attackers win, their team receives a
	 * point. Otherwise no points are awarded. Either way, the next round is then
	 * set up.
	 * 
	 * @param isPlayer determines if the attacking team is the players'
	 **/
	public void attack(boolean isPlayer) {
		int attackTotal = 0;
		int defenseTotal = 0;
		for (Athlete attacker : attackingTeam) {
			attackTotal += attacker.getOffence();
		}
		for (Athlete defender : defendingTeam) {
			defenseTotal += defender.getDefence();
		}
		if (attackTotal >= Player.getRandom().nextInt(attackTotal + defenseTotal)) {
			deductStamina(1.0, attackingTeam);
			deductStamina(1.5, defendingTeam);
			if (isPlayer == true) {
				MatchWindow.setStatus(Player.getTeam().getName() + " won round " + round);
				playerScore++;
			} else {
				MatchWindow.setStatus(enemyTeam.getName() + " won round " + round);
				enemyScore++;
			}
		} else {
			deductStamina(1.5, attackingTeam);
			deductStamina(1.0, defendingTeam);
			MatchWindow.setStatus("Round " + round + " was a tie");
		}
		checkRound();
	}

	/**
	 * This checks whether the current round is the final round of the match. If so,
	 * the winner is determined and the match is ended. If the {@link main.Player
	 * Player} has won, they are given their reward points and money. In the event
	 * of a tie, half of the reward is given. No reward is given for a loss.
	 **/
	public void checkRound() {
		if (round == 10) {
			if (playerScore > enemyScore) {
				MatchWindow.setStatus("Congratulations, you won the match!");
				for (Athlete athlete : Player.getTeam().getFullTeam()) {
					athlete.setWinWeeks(0);
				}
				Player.changeScore(rewardScore);
				Player.changeMoney(rewardMoney);
			} else if (playerScore == enemyScore) {
				MatchWindow.setStatus("It's a draw!");
				Player.changeScore(rewardScore / 2);
				Player.changeMoney(rewardMoney / 2);
			} else {
				MatchWindow.setStatus("You lost the match. Better luck next time!");
			}
			MatchWindow.finish();
			MatchWindow.updateLabels();
		} else {
			round++;
			MatchWindow.showStartButton();
			benchAthletes();
			MatchWindow.updateLabels();
			MatchWindow.showFaceOff(Player.getTeam().getAllRounders(), enemyTeam.getAllRounders());
		}
	}

	/**
	 * Checks whether the {@link main.Player Player} meets the requirements to play
	 * the match. The requirements are that the all of the {@link main.Team Teams}'
	 * active positions are filled and that every {@link main.Athlete Athlete} on
	 * the team has more than zero current stamina.
	 * 
	 * @return whether or not the player meets the requirements
	 **/
	public boolean checkRequirements() {
		int x = StadiumWindow.getWindow().getX() + 250;
		int y = StadiumWindow.getWindow().getY() + 245;
		Athlete athlete;
		for (int i = 0; i < Player.getTeamSize(); i++) {
			athlete = Player.getTeam().getFullTeam().get(i);
			if (athlete == null) {
				new Notification("You do not have enough active players to compete in this match!", x, y);
				return false;
			}
			if (athlete.getCurrentStamina() == 0) {
				new Notification(athlete.getName() + " does not have enough Stamina to compete in this match!", x, y);
				return false;
			}
		}
		return true;
	}

	/**
	 * Removes any {@link main.Athlete Athletes}' in the match whose stamina is
	 * zero, then checks whether the match can continue. Calls
	 * {@link #removeAthletes(Team, boolean) removeAthletes()} method on both
	 * {@link main.Team Teams}. Then checks that there is at least one athlete still
	 * in each teams' attacker, defender, and all-rounder positions. The enemy team
	 * is checked first. If they do not have enough athletes they forfeit and the
	 * player wins. Then the {@link main.Player Player} team is checked. If they do
	 * not have enough athletes the player loses. If both teams pass, the match
	 * continues.
	 **/
	public void benchAthletes() {
		removeAthletes(enemyTeam, false);
		removeAthletes(Player.getTeam(), true);
		if (enemyTeam.getAttackers().size() == 0 || enemyTeam.getAllRounders().size() == 0
				|| enemyTeam.getDefenders().size() == 0) {
			new Notification("Too many enemy players have been injured to continue playing.",
					MatchWindow.getWindow().getX() + 250, MatchWindow.getWindow().getY() + 245);
			MatchWindow.setStatus("Congratulations, you won the match!");
			Player.changeScore(rewardScore);
			Player.changeMoney(rewardMoney);
			MatchWindow.finish();
		} else if (Player.getTeam().getAttackers().size() == 0 || Player.getTeam().getAllRounders().size() == 0
				|| Player.getTeam().getDefenders().size() == 0) {
			new Notification("Too many of your players have been injured to continue playing.",
					MatchWindow.getWindow().getX() + 250, MatchWindow.getWindow().getY() + 245);
			MatchWindow.setStatus("You lost the match. Better luck next time!");
			MatchWindow.finish();
		}
	}

	/**
	 * Removes any{@link main.Athlete Athletes} who have zero stamina remaining from
	 * the provided {@link main.Team Team}. If an athlete from the
	 * {@link main.Player Players}' team is removed, a {@link main.Notification
	 * Notification} is created to inform the player.
	 * 
	 * @param team     the team to have athletes removed from
	 * @param isPlayer whether this is the players' team
	 **/
	public void removeAthletes(Team team, boolean isPlayer) {
		for (Athlete athlete : team.getFullTeam()) {
			if (athlete != null) {
				if (athlete.getCurrentStamina() <= 0 & athlete.getInjuryWeeks() != 0) {
					athlete.setInjuryWeeks(0);
					team.getAttackers().remove(athlete);
					team.getAllRounders().remove(athlete);
					team.getDefenders().remove(athlete);
					if (isPlayer == true) {
						new Notification(athlete.getName() + " has been injured and has to leave the field",
								MatchWindow.getWindow().getX() + 250, MatchWindow.getWindow().getY() + 245);
					}
				}
			}
		}
	}

	/**
	 * Deducts a random amount of stamina for every {@link main.Athlete Athlete} in
	 * the provided list. The amount of stamina deducted is small by default, but
	 * can be multiplied by the provided multiplier.
	 * 
	 * @param multiplier  the amount to multiply the stamina reduction by
	 * @param athleteList the list of athletes to have stamina deducted
	 **/
	public void deductStamina(double multiplier, ArrayList<Athlete> athleteList) {
		for (Athlete athlete : athleteList) {
			athlete.setCurrentStamina(
					(int) (athlete.getCurrentStamina() - Player.getRandom().nextInt(1, 6) * multiplier));
		}
	}

	/**
	 * Closes the current {@link main.StadiumWindow MatchWindow} and opens a new
	 * {@link main.StadiumWindow StadiumWindow}.
	 **/
	public void returnToStadium() {
		Player.getTeam().updateSubTeams();
		new StadiumWindow(MatchWindow.getWindow().getX(), MatchWindow.getWindow().getY());
		MatchWindow.getWindow().dispose();
	}

	public int getPlayerScore() {
		return playerScore;
	}

	public int getEnemyScore() {
		return enemyScore;
	}

	public long getRewardScore() {
		return rewardScore;
	}

	public long getRewardMoney() {
		return rewardMoney;
	}

	public int getRound() {
		return round;
	}

	public Team getEnemyTeam() {
		return enemyTeam;
	}
}
