package se.fangdjupet.game;

import se.fangdjupet.combat.Enemy;

import java.util.List;
import java.util.Objects;

/**
 * The dungeon: an ordered sequence of rooms with one enemy in each room.
 * <p>
 * Keeps track of which room the hero is in. The hero moves on to the next
 * room by defeating the current enemy and then calling {@link #advance()}.
 */
public class Dungeon {

    private final List<Enemy> enemies;
    private int currentRoom;

    /**
     * Creates a dungeon where the hero starts in the first room.
     *
     * @param enemies one enemy per room, in the order they are met
     */
    public Dungeon(List<Enemy> enemies) {
        this(enemies, 0);
    }

    /**
     * Creates a dungeon where the hero starts in a given room.
     * Used when a saved game is loaded.
     *
     * @param enemies     one enemy per room, in the order they are met
     * @param currentRoom index of the current room (0 = first room,
     *                    number of rooms = dungeon cleared)
     * @throws NullPointerException     if enemies or any enemy is null
     * @throws IllegalArgumentException if there are no enemies or the room index is invalid
     */
    public Dungeon(List<Enemy> enemies, int currentRoom) {
        Objects.requireNonNull(enemies, "Enemies cannot be null");

        if (enemies.isEmpty()) {
            throw new IllegalArgumentException("Dungeon must have at least one room");
        }

        if (currentRoom < 0 || currentRoom > enemies.size()) {
            throw new IllegalArgumentException("Invalid room index: " + currentRoom);
        }

        // List.copyOf makes the list unmodifiable and rejects null enemies
        this.enemies = List.copyOf(enemies);
        this.currentRoom = currentRoom;
    }

    /**
     * @return the enemy in the current room
     * @throws IllegalStateException if the dungeon is already cleared
     */
    public Enemy getCurrentEnemy() {
        if (isCleared()) {
            throw new IllegalStateException("Dungeon is already cleared");
        }

        return enemies.get(currentRoom);
    }

    /**
     * Moves the hero to the next room.
     *
     * @throws IllegalStateException if the dungeon is cleared or the current enemy is still alive
     */
    public void advance() {
        if (getCurrentEnemy().isAlive()) {
            throw new IllegalStateException("Defeat the enemy before moving on");
        }

        currentRoom++;
    }

    /**
     * @return true when every room has been cleared
     */
    public boolean isCleared() {
        return currentRoom >= enemies.size();
    }

    /**
     * @return index of the current room, starting at 0
     */
    public int getCurrentRoom() {
        return currentRoom;
    }

    /**
     * @return total number of rooms in the dungeon
     */
    public int getRoomCount() {
        return enemies.size();
    }
}