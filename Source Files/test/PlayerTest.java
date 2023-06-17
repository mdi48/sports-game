package test;

import main.Athlete;
import main.Player;
import main.Team;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
*
* @author mdi48
*
* JUnit test case for Player
*/

class PlayerTest {

    @BeforeEach
    void setUp() {
        // Set up the Player before each test
        double difficulty = 2;
        int time = 10;
        int teamSize = 11;
        Player testPlayer = new Player("TestPlayer", difficulty, time, teamSize);
    }

    @Test
    void testGetTeam() {
        // Test the getTeam() method
        Team team = Player.getTeam();

        assertNotNull(team);
        assertEquals("TestPlayer", team.getName());
        for (Athlete ath : team.getFullTeam()) {
        	assertEquals(null, ath);
        }
    }

    @Test
    void testGetDifficulty() {
        // Test the getDifficulty() method
        double difficulty = Player.getDifficulty();

        assertEquals(2,  difficulty);
    }

    @Test
    void testGetWeek() {
        // Test the getWeek() method
        int currentWeek = Player.getWeek();

        assertEquals(1, currentWeek);
    }

    @Test
    void testGetMaxWeeks() {
        // Test the getMaxWeeks() method
        int maxWeeks = Player.getMaxWeeks();

        assertEquals(10, maxWeeks);
    }

    @Test
    void testGetTeamSize() {
        // Test the getTeamSize() method
        int teamSize = Player.getTeamSize();

        assertEquals(11, teamSize);
    }

    @Test
    void testChangeMoney() {
        // Test the changeMoney() method
        long initialMoney = Player.getMoney();

        Player.changeMoney(1000);

        assertEquals(initialMoney + 1000, Player.getMoney());
    }
    
    void testScore() {
    	// Test the getScore() method
    	long ourScore = Player.getScore();
    	
    	Player.changeScore(20);
    	
    	assertEquals(ourScore + 20, Player.getScore());
    }

}
