package se.fangdjupet.game;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Plays the game with typed-in input and checks what is printed.
 */
class GameTest {

    @TempDir
    Path tempDir;

    // Runs a whole game where the player types the given lines, and returns everything printed
    private String play(String... lines) {
        String typed = String.join("\n", lines) + "\n";
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        Game game = new Game(
                new ByteArrayInputStream(typed.getBytes(StandardCharsets.UTF_8)),
                new PrintStream(output, true, StandardCharsets.UTF_8),
                tempDir.resolve("savegame.txt"));
        game.run();

        return output.toString(StandardCharsets.UTF_8);
    }

    @Test
    void exitShowsTitleMenuAndGoodbye() {
        String output = play("e");

        assertTrue(output.contains("Fangdjupet"));
        assertTrue(output.contains("1. Start new adventure"));
        assertTrue(output.contains("Goodbye!"));
    }

    @Test
    void actionsNeedAStartedGame() {
        String output = play("3", "4", "5", "e");

        assertTrue(output.contains("Start a new adventure first"));
    }

    @Test
    void newGameShowsHeroAndStartingEquipment() {
        String output = play("1", "Knight", "3", "e");

        assertTrue(output.contains("[GAME] Knight enters Fangdjupet."));
        assertTrue(output.contains("Rusty Sword"));
        assertTrue(output.contains("Leather Cap"));
        assertTrue(output.contains("Small Health Potion"));
    }

    @Test
    void firstBattleIsWonAndGivesReward() {
        // 4 = fight, then attack twice: the Goblin has 40 HP and the hero hits for 23
        String output = play("1", "Knight", "4", "1", "1", "e");

        assertTrue(output.contains("[ROOM] Room 1/5: a Goblin blocks the way!"));
        assertTrue(output.contains("[BATTLE] Knight fights Goblin."));
        assertTrue(output.contains("[BATTLE] Goblin was defeated."));
        assertTrue(output.contains("[ITEM] Knight equips Battle Axe."));
        assertTrue(output.contains("Next up: room 2/5"));
    }

    @Test
    void drinkingPotionUsesTheTurn() {
        String output = play("1", "Knight", "4", "2", "1", "1", "e");

        assertTrue(output.contains("[ITEM] Knight drinks Small Health Potion"));
        assertTrue(output.contains("[BATTLE] Goblin was defeated."));
    }

    @Test
    void savedGameCanBeContinued() {
        play("1", "Knight", "4", "1", "1", "5", "e");

        String output = play("2", "3", "e");

        assertTrue(output.contains("[GAME] Welcome back, Knight! You are in room 2/5."));
        assertTrue(output.contains("Battle Axe"));
    }

    @Test
    void continueWithoutSaveShowsMessage() {
        String output = play("2", "e");

        assertTrue(output.contains("No saved game found."));
    }

    @Test
    void wholeDungeonCanBeClearedByAttacking() {
        // Attacks needed per room: Goblin 2, Skeleton 2, Goblin 2, Mage 3, Troll 5
        String output = play("1", "Knight",
                "4", "1", "1",
                "4", "1", "1",
                "4", "1", "1",
                "4", "1", "1", "1",
                "4", "1", "1", "1", "1", "1",
                "e");

        assertTrue(output.contains("[GAME] Knight has cleared Fangdjupet!"));
        assertTrue(output.contains("Enemies defeated: 5"));
    }
}