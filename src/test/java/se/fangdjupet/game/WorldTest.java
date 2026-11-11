package se.fangdjupet.game;

import org.junit.jupiter.api.Test;
import se.fangdjupet.combat.Enemy;
import se.fangdjupet.items.ArmorSlot;
import se.fangdjupet.items.Potion;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WorldTest {

    private static final double DELTA = 0.001;

    @Test
    void newGameStartsInFirstRoomWithFullHealth() {
        GameState state = World.newGame("Knight");

        assertEquals("Knight", state.getHero().getName());
        assertEquals(World.HERO_MAX_HEALTH, state.getHero().getHealth(), DELTA);
        assertEquals(0, state.getDungeon().getCurrentRoom());
    }

    @Test
    void newGameEquipsStartingItems() {
        GameState state = World.newGame("Knight");

        assertEquals("Rusty Sword", state.getEquippedWeapon().orElseThrow().getName());
        assertTrue(state.getEquippedArmor(ArmorSlot.HELMET).isPresent());
        assertTrue(state.getEquippedArmor(ArmorSlot.SHIELD).isPresent());
        assertEquals(2, state.getInventory().getItemsOfType(Potion.class).getFirst().getQuantity());
    }

    @Test
    void dungeonHasFiveRoomsWithTrollAsBoss() {
        List<Enemy> enemies = World.createEnemies();

        assertEquals(5, enemies.size());
        assertEquals("Goblin", enemies.getFirst().getName());
        assertEquals("Troll", enemies.getLast().getName());
    }

    @Test
    void createEnemiesGivesFreshEnemiesEveryTime() {
        Enemy first = World.createEnemies().getFirst();
        first.takeDamage(10);

        Enemy fresh = World.createEnemies().getFirst();

        assertNotSame(first, fresh);
        assertEquals(fresh.getMaxHealth(), fresh.getHealth(), DELTA);
    }

    @Test
    void everyRoomExceptTheLastGivesReward() {
        int rooms = World.createEnemies().size();

        for (int room = 0; room < rooms - 1; room++) {
            assertTrue(World.rewardAfterRoom(room).isPresent());
        }
        assertTrue(World.rewardAfterRoom(rooms - 1).isEmpty());
    }
}