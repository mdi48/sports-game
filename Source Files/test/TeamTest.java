package test;

import org.junit.Assert;
import org.junit.Test;

import main.Athlete;
import main.InvalidTeamException;
import main.Player;
import main.Team;

import java.util.ArrayList;

/*
*
* @author mdi48
*
* JUnit test case for Team class
*/

class TeamTest {

    @Test
    void testConstructorWithTeamList() throws InvalidTeamException {
        String teamName = "Player Team";
        ArrayList<Athlete> teamList = new ArrayList<>();
        teamList.add(new Athlete("Attacker"));
        teamList.add(new Athlete("All-Rounder"));
        teamList.add(new Athlete("Defender"));
        
        Team team = new Team(teamName, teamList);

        // Verify that the team name is set correctly
        Assert.assertEquals(teamName, team.getName());

        // Verify that the full team list is set correctly
        Assert.assertEquals(teamList, team.getFullTeam());
    }

    @Test(expected = InvalidTeamException.class)
    void testConstructorWithInvalidTeamList() throws InvalidTeamException {
        String teamName = "Player Team";
        ArrayList<Athlete> teamList = new ArrayList<>(); // Empty team list

        Team team = new Team(teamName, teamList);

        // The above line should throw an InvalidTeamException
    }

    @Test
    void testFillSlots() {
        Team team = new Team();
        team.fillSlots();

        // Verify that the full team list is filled with the correct number of athletes
        Assert.assertEquals(Player.getTeamSize(), team.getFullTeam().size());

        // Verify that the number of attackers is correct
        Assert.assertEquals(team.getPositions()[0], team.getAttackers().size());

        // Verify that the number of all-rounders is correct
        Assert.assertEquals(team.getPositions()[1] - team.getPositions()[0], team.getAllRounders().size());

        // Verify that the number of defenders is correct
        Assert.assertEquals(team.getPositions()[2] - team.getPositions()[1], team.getDefenders().size());

        // Verify that the number of reserves is correct
        Assert.assertEquals(5, team.getReserves().size());
    }

    @Test
    void testUpdateSubTeams() {
        Team team = new Team();
        team.fillSlots();

        // Update the sub teams
        team.updateSubTeams();

        // Verify that the sub teams are updated correctly
        Assert.assertEquals(team.getFullTeam().subList(0, team.getPositions()[0]), team.getAttackers());
        Assert.assertEquals(team.getFullTeam().subList(team.getPositions()[0], team.getPositions()[1]), team.getAllRounders());
        Assert.assertEquals(team.getFullTeam().subList(team.getPositions()[1], team.getPositions()[2]), team.getDefenders());
        Assert.assertEquals(team.getFullTeam().subList(team.getPositions()[2], team.getPositions()[3]), team.getReserves());
    }

    @Test
    void testJoinTeams() {
        Team team = new Team();
        team.fillSlots();
        team.updateSubTeams();

        // Join the sub teams
        team.joinTeams();

        // Verify that the full team list is merged correctly
        ArrayList<Athlete> expectedFullTeam = new ArrayList<>(team.getAttackers());
        expectedFullTeam.addAll(team.getAllRounders());
        expectedFullTeam.addAll(team.getDefenders());
        expectedFullTeam.addAll(team.getReserves());

        Assert.assertEquals(expectedFullTeam, team.getFullTeam());
    }

    @Test
    void testTotalStamina() {
        Team team = new Team();
        team.fillSlots();
        team.updateSubTeams();

        // Calculate the expected total stamina
        int expectedTotalStamina = 0;
        for (Athlete athlete : team.getFullTeam()) {
            if (athlete != null) {
                expectedTotalStamina += athlete.getCurrentStamina();
            }
        }

        // Verify that the total stamina is calculated correctly
        Assert.assertEquals(expectedTotalStamina, team.totalStamina());
    }

    @Test
    void testMoveAthlete() {
        Team team = new Team();
        team.fillSlots();
        team.updateSubTeams();

        Athlete athlete1 = team.getAttackers().get(0);
        Athlete athlete2 = team.getDefenders().get(0);

        // Move athlete2 to the position of athlete1
        team.swapAthletes(team.getFullTeam().indexOf(athlete2), team.getFullTeam().indexOf(athlete1));

        // Verify that athlete1 is now in the position of athlete2 and vice-versa
        Assert.assertEquals(athlete2, team.getFullTeam().get(team.getFullTeam().indexOf(athlete1)));
        Assert.assertEquals(athlete1, team.getFullTeam().get(team.getFullTeam().indexOf(athlete2)));

        // Verify that the sub teams and full team are updated correctly
        team.updateSubTeams();
        team.joinTeams();
        Assert.assertEquals(team.getAttackers().get(0), athlete2);
        Assert.assertEquals(team.getDefenders().get(0), athlete1);
    }
}

