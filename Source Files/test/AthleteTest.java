package test;

import main.Athlete;
import main.Player;
import main.Item;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

/*
*
* @author mdi48
*
* JUnit test case for Athlete class
*/


class AthleteTest {

    @BeforeEach
    void testPlayerNPC() {
        Athlete athlete = new Athlete();
        
        // Verify that the athlete type is not null
        assertNotNull(athlete.getType());
        
        // Verify that the athlete stamina is within the valid range
        assertTrue(athlete.getStamina() >= 0 && athlete.getStamina() <= 100);
        
        // Verify that the athlete offence is within the valid range
        assertTrue(athlete.getOffence() >= 0 && athlete.getOffence() <= 100);
        
        // Verify that the athlete defence is within the valid range
        assertTrue(athlete.getDefence() >= 0 && athlete.getDefence() <= 100);
        
        // Verify that the athlete's current stamina is initialized correctly
        assertEquals(athlete.getStamina(), athlete.getCurrentStamina());
    }

    @Test
    void testEnemyNPC() {
        String athleteType = "Defender";
        Athlete athlete = new Athlete(athleteType);
        
        // Verify that the athlete type is set correctly
        assertEquals(athleteType, athlete.getType());
        
        // Verify that the athlete stamina is within the valid range
        assertTrue(athlete.getStamina() >= 0 && athlete.getStamina() <= 100);
        
        // Verify that the athlete offence is within the valid range
        assertTrue(athlete.getOffence() >= 0 && athlete.getOffence() <= 100);
        
        // Verify that the athlete defence is within the valid range
        assertTrue(athlete.getDefence() >= 0 && athlete.getDefence() <= 100);
        
        // Verify that the athlete's current stamina is initialized correctly
        assertEquals(athlete.getStamina(), athlete.getCurrentStamina());
    }

    @Test
    void testUseItem() {
        Athlete athlete = new Athlete();
        Item item = new Item("Test Item", 10, 20, 30, 40, 1);
        
        athlete.useItem(item);
        
        // Verify that the athlete's stamina is updated correctly
        assertEquals(item.getStaminaBuff() + athlete.getStamina(), athlete.getStamina());
        
        // Verify that the athlete's current stamina is updated correctly
        assertEquals(item.getStaminaGain() + item.getStaminaBuff() + athlete.getCurrentStamina(), athlete.getCurrentStamina());
        
        // Verify that the athlete's offence is updated correctly
        assertEquals(item.getOffenceBuff() + athlete.getOffence(), athlete.getOffence());
        
        // Verify that the athlete's defence is updated correctly
        assertEquals(item.getDefenceBuff() + athlete.getDefence(), athlete.getDefence());
        
        // Verify that the item's player count is decremented
        assertEquals(0, item.getPlayerCount());
        
        // Verify that the item is removed from the player's items list
        assertFalse(Player.getItems().contains(item));
    }
}

