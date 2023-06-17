package main;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a team that consists of {@link main.Athlete Athletes}. The
 * athletes are stored in a list that is also divided into smaller subsections,
 * referred to as 'subteams', which are used to group athletes into positions.
 * The Attackers subteam consists of the Attacker positions, the Defender
 * subteam consists of the defender positions, and so on. Reserves are also
 * treated as a subteam, though do not actively participate in {@link main.Match
 * Matches} The size of the team, and hence size of the subteams (excluding
 * reserves which is always 5), is determined at the start of the game.
 *
 * @author mdi48, bsm62
 * @version 1.12
 **/
public class Team {
	private String name;
	private static int[] positions;
	private ArrayList<Athlete> attackers, allRounders, defenders, reserves, fullTeam = new ArrayList<Athlete>();

	/**
	 * Constructor for the {@link main.Player Players}' team. The name and
	 * {@link main.Athlete Athletes} are chosen by the player during setup.
	 * 
	 * @param teamName sets the name of the team
	 * @param teamlist list of athletes to be added to the team.
	 */
	public Team(String teamName, ArrayList<Athlete> teamlist) throws InvalidTeamException {
		setPositions();
		if (athleteCount() < 0) {
			throw new InvalidTeamException("Not enough players on your team!");
		} else {
			fullTeam = teamlist;
			name = teamName;
		}
	}

	/**
	 * Constructor used to generate enemy teams. The name is randomly selected and
	 * it is automatically filled with {@link main.Athlete Athletes}.
	 */
	public Team() {
		name = GenerateStats.generateTeamName();
		setPositions();
		fillSlots();
		updateSubTeams();
		joinTeams();
	}

	/**
	 * Returns the sum current stamina of all {@link main.Athlete Athletes} in the
	 * team, excluding those in reserves.
	 * 
	 * @return the total stamina
	 */
	public int totalStamina() {
		int stamina = 0;
		for (int i = 0; i < (fullTeam.size() - positions[3]); i++) {
			Athlete athlete = fullTeam.get(i);
			if (athlete != null) {
				stamina += athlete.getCurrentStamina();
			}
		}
		return stamina;
	}

	/**
	 * Returns the number of {@link main.Athlete Athletes} currently in the team.
	 * 
	 * @return the number of athletes
	 */
	public int athleteCount() {
		int size = 0;
		for (Athlete athlete : fullTeam) {
			if (athlete != null) {
				size++;
			}
		}
		return size;
	}

	/**
	 * Swaps two {@link main.Athlete Athletes}' to eachother's positions, or swaps
	 * an athlete with an empty slot.
	 * 
	 * @param firstIndex  the index of the first Athlete to be swapped
	 * @param secondIndex the index of the second Athlete to be swapped
	 */
	public void swapAthletes(int firstIndex, int secondIndex) {
		Collections.swap(fullTeam, firstIndex, secondIndex);
		updateSubTeams();
	}

	/**
	 * Fills all of the active positions of the team with appropriate types of
	 * {@link main.Athlete Athletes}.
	 */
	public void fillSlots() {
		for (int i = 0; i < positions[0]; i++) {
			fullTeam.add(new Athlete("Attacker"));
		}
		for (int i = positions[0]; i < positions[1]; i++) {
			fullTeam.add(new Athlete("All-Rounder"));
		}
		for (int i = positions[1]; i < positions[2]; i++) {
			fullTeam.add(new Athlete("Defender"));
		}
		for (int i = positions[2]; i < positions[3]; i++) {
			fullTeam.add(null);
		}
	}

	/**
	 * Updates the sub-teams to match the full team list.
	 */
	public void updateSubTeams() {
		attackers = new ArrayList<Athlete>(fullTeam.subList(0, positions[0]));
		allRounders = new ArrayList<Athlete>(fullTeam.subList(positions[0], positions[1]));
		defenders = new ArrayList<Athlete>(fullTeam.subList(positions[1], positions[2]));
		reserves = new ArrayList<Athlete>(fullTeam.subList(positions[2], positions[3]));
	}

	/**
	 * Updates the full team list to match the sub-teams.
	 */
	public void joinTeams() {
		fullTeam.clear();
		fullTeam.addAll(attackers);
		fullTeam.addAll(allRounders);
		fullTeam.addAll(defenders);
		fullTeam.addAll(reserves);
	}

	/**
	 * Sets the start and finish indices of each subTeam within the main team. This
	 * is used to slice the full team list into smaller lists that are made up of
	 * four different positions: Attackers, All-Rounders, Defenders, and Reserves.
	 */
	public void setPositions() {
		positions = new int[4];
		int size = Player.getTeamSize();
		positions[0] = (int) Math.round((double) size / 3.0);
		positions[2] = (int) Math.round((double) size / 3.0);
		positions[1] = size - positions[0] - positions[2];
		positions[1] += positions[0];
		positions[2] += positions[1];
		positions[3] = positions[2] + 5;
	}

	public ArrayList<Athlete> getFullTeam() {
		return fullTeam;
	}

	public ArrayList<Athlete> getAttackers() {
		return attackers;
	}

	public ArrayList<Athlete> getDefenders() {
		return defenders;
	}

	public ArrayList<Athlete> getAllRounders() {
		return allRounders;
	}

	public ArrayList<Athlete> getReserves() {
		return reserves;
	}

	public int[] getPositions() {
		return positions;
	}

	public String getName() {
		return name;
	}
}
