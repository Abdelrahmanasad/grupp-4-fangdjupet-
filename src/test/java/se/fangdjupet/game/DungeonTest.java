package se.fangdjupet.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.fangdjupet.combat.Enemy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DungeonTest {

    private Enemy goblin;
    private Enemy troll;
    private Dungeon dungeon;

    @BeforeEach
    void setUp() {
        goblin = enemy("Goblin");
        troll = enemy("Troll");
        dungeon = new Dungeon(List.of(goblin, troll));
    }

    // The only place that creates enemies, so a changed Enemy constructor is a one-line fix
    private static Enemy enemy(String name) {
        return new Enemy(name, 30, 30);
    }

    @Test
    void startsInFirstRoom() {
        assertEquals(0, dungeon.getCurrentRoom());
        assertEquals(2, dungeon.getRoomCount());
        assertSame(goblin, dungeon.getCurrentEnemy());
    }

    @Test
    void advanceMovesToNextEnemyAfterDefeat() {
        goblin.takeDamage(30);

        dungeon.advance();

        assertEquals(1, dungeon.getCurrentRoom());
        assertSame(troll, dungeon.getCurrentEnemy());
    }

    @Test
    void cannotAdvanceWhileEnemyIsAlive() {
        assertThrows(IllegalStateException.class, () -> dungeon.advance());
        assertEquals(0, dungeon.getCurrentRoom());
    }

    @Test
    void isClearedAfterLastEnemyIsDefeated() {
        goblin.takeDamage(30);
        dungeon.advance();
        troll.takeDamage(30);
        dungeon.advance();

        assertTrue(dungeon.isCleared());
        assertThrows(IllegalStateException.class, () -> dungeon.getCurrentEnemy());
    }

    @Test
    void notClearedAtStart() {
        assertFalse(dungeon.isCleared());
    }

    @Test
    void canStartInLaterRoomWhenLoadingGame() {
        Dungeon loaded = new Dungeon(List.of(goblin, troll), 1);

        assertSame(troll, loaded.getCurrentEnemy());
    }

    @Test
    void invalidRoomIndexThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Dungeon(List.of(goblin), -1));
        assertThrows(IllegalArgumentException.class, () -> new Dungeon(List.of(goblin), 2));
    }

    @Test
    void emptyDungeonThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Dungeon(List.of()));
    }

    @Test
    void nullEnemiesThrowException() {
        List<Enemy> withNull = new ArrayList<>();
        withNull.add(null);

        assertThrows(NullPointerException.class, () -> new Dungeon(null));
        assertThrows(NullPointerException.class, () -> new Dungeon(withNull));
    }

    @Test
    void changingOriginalListDoesNotChangeDungeon() {
        List<Enemy> enemies = new ArrayList<>(List.of(goblin));
        Dungeon fromList = new Dungeon(enemies);

        enemies.add(troll);

        assertEquals(1, fromList.getRoomCount());
    }
}