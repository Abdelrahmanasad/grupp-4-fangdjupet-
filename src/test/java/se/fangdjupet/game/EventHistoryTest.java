package se.fangdjupet.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.fangdjupet.core.EventBus;
import se.fangdjupet.core.EventType;
import se.fangdjupet.core.GameEvent;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EventHistoryTest {

    private EventHistory history;

    @BeforeEach
    void setUp() {
        history = new EventHistory();
    }

    @Test
    void remembersEventsInOrder() {
        history.onEvent(new GameEvent(EventType.GAME_STARTED, "Start"));
        history.onEvent(new GameEvent(EventType.ROOM_ENTERED, "Room 1"));

        List<GameEvent> events = history.getEvents();

        assertEquals(2, events.size());
        assertEquals("Start", events.get(0).message());
        assertEquals("Room 1", events.get(1).message());
    }

    @Test
    void filtersAndCountsByType() {
        history.onEvent(new GameEvent(EventType.ENEMY_DEFEATED, "Goblin was defeated."));
        history.onEvent(new GameEvent(EventType.ATTACK_PERFORMED, "Troll took 10.0 damage."));
        history.onEvent(new GameEvent(EventType.ENEMY_DEFEATED, "Troll was defeated."));

        assertEquals(2, history.count(EventType.ENEMY_DEFEATED));
        assertEquals("Troll was defeated.", history.getEvents(EventType.ENEMY_DEFEATED).get(1).message());
        assertEquals(0, history.count(EventType.HERO_DEFEATED));
    }

    @Test
    void dropsOldestEventWhenFull() {
        EventHistory small = new EventHistory(2);

        small.onEvent(new GameEvent(EventType.ROOM_ENTERED, "Room 1"));
        small.onEvent(new GameEvent(EventType.ROOM_ENTERED, "Room 2"));
        small.onEvent(new GameEvent(EventType.ROOM_ENTERED, "Room 3"));

        assertEquals(2, small.size());
        assertEquals("Room 2", small.getEvents().get(0).message());
    }

    @Test
    void returnedListCannotBeChanged() {
        history.onEvent(new GameEvent(EventType.GAME_STARTED, "Start"));

        assertThrows(UnsupportedOperationException.class,
                () -> history.getEvents().add(new GameEvent(EventType.GAME_OVER, "Hack")));
    }

    @Test
    void clearForgetsEverything() {
        history.onEvent(new GameEvent(EventType.GAME_STARTED, "Start"));

        history.clear();

        assertTrue(history.getEvents().isEmpty());
    }

    @Test
    void recordsEventsPublishedOnEventBus() {
        EventBus bus = new EventBus();
        bus.subscribe(history);

        bus.publish(EventType.ENEMY_DEFEATED, "Goblin was defeated.");

        assertEquals(1, history.count(EventType.ENEMY_DEFEATED));
    }

    @Test
    void invalidMaxSizeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new EventHistory(0));
    }
}