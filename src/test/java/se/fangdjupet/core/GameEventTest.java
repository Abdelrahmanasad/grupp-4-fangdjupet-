package se.fangdjupet.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameEventTest {

    @Test
    void createsEventWithTypeAndMessage() {
        GameEvent event = new GameEvent(EventType.ENEMY_DEFEATED, "Hjälten besegrade Trollet");

        assertEquals(EventType.ENEMY_DEFEATED, event.type());
        assertEquals("Hjälten besegrade Trollet", event.message());
    }

    @Test
    void nullTypeThrowsException() {
        assertThrows(NullPointerException.class,
                () -> new GameEvent(null, "Något hände"));
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