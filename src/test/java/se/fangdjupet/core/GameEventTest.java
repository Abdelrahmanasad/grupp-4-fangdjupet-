package se.fangdjupet.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameEventTest {

    @Test
    void createsEventWithTypeAndMessage() {
        GameEvent event = new GameEvent(EventType.ENEMY_DEFEATED, "The hero defeated the Troll");

        assertEquals(EventType.ENEMY_DEFEATED, event.type());
        assertEquals("The hero defeated the Troll", event.message());
    }

    @Test
    void nullTypeThrowsException() {
        assertThrows(NullPointerException.class,
                () -> new GameEvent(null, "Something happened"));
    }

    @Test
    void nullMessageThrowsException() {
        assertThrows(NullPointerException.class,
                () -> new GameEvent(EventType.GAME_STARTED, null));
    }

    @Test
    void blankMessageThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameEvent(EventType.GAME_STARTED, "   "));
    }
}