package se.fangdjupet.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.fangdjupet.core.EventBus;
import se.fangdjupet.core.EventType;
import se.fangdjupet.core.GameEvent;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsoleEventLoggerTest {

    private ByteArrayOutputStream outputBytes;
    private ConsoleEventLogger logger;

    @BeforeEach
    void setUp() {
        outputBytes = new ByteArrayOutputStream();
        logger = new ConsoleEventLogger(new PrintStream(outputBytes, true, StandardCharsets.UTF_8));
    }

    private String output() {
        return outputBytes.toString(StandardCharsets.UTF_8);
    }

    @Test
    void printsCategoryAndMessage() {
        logger.onEvent(new GameEvent(EventType.ENEMY_DEFEATED, "Goblin was defeated."));

        assertTrue(output().contains("[BATTLE] Goblin was defeated."));
    }

    @Test
    void printsEventsPublishedOnEventBus() {
        EventBus bus = new EventBus();
        bus.subscribe(logger);

        bus.publish(EventType.GAME_STARTED, "The adventure begins.");
        bus.publish(EventType.HERO_HEALED, "Knight healed 25.0 health.");

        assertTrue(output().contains("[GAME] The adventure begins."));
        assertTrue(output().contains("[HEAL] Knight healed 25.0 health."));
    }

    @Test
    void everyEventTypeHasCategory() {
        for (EventType type : EventType.values()) {
            assertFalse(ConsoleEventLogger.categoryOf(type).isBlank());
        }
    }

    @Test
    void itemEventsAreShownAsItem() {
        assertEquals("ITEM", ConsoleEventLogger.categoryOf(EventType.ITEM_EQUIPPED));
        assertEquals("ROOM", ConsoleEventLogger.categoryOf(EventType.ROOM_ENTERED));
    }
}