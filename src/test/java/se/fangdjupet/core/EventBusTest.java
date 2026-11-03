package se.fangdjupet.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EventBusTest {

    private EventBus bus;
    private List<GameEvent> received;

    @BeforeEach
    void setUp() {
        bus = new EventBus();
        received = new ArrayList<>();
    }

    @Test
    void subscribedListenerReceivesPublishedEvent() {
        bus.subscribe(received::add);

        bus.publish(EventType.ENEMY_DEFEATED, "The hero defeated the Troll");

        assertEquals(1, received.size());
        assertEquals(EventType.ENEMY_DEFEATED, received.getFirst().type());
    }

    @Test
    void allListenersReceiveTheSameEvent() {
        List<GameEvent> second = new ArrayList<>();
        bus.subscribe(received::add);
        bus.subscribe(second::add);

        bus.publish(EventType.GAME_STARTED, "The adventure begins");

        assertEquals(1, received.size());
        assertEquals(1, second.size());
    }

    @Test
    void unsubscribedListenerReceivesNothing() {
        GameEventListener listener = received::add;
        bus.subscribe(listener);
        bus.unsubscribe(listener);

        bus.publish(EventType.GAME_STARTED, "The adventure begins");

        assertTrue(received.isEmpty());
    }

    @Test
    void sameListenerIsOnlyRegisteredOnce() {
        GameEventListener listener = received::add;
        bus.subscribe(listener);
        bus.subscribe(listener);

        bus.publish(EventType.GAME_STARTED, "The adventure begins");

        assertEquals(1, bus.listenerCount());
        assertEquals(1, received.size());
    }

    @Test
    void listenerCanUnsubscribeItselfDuringPublish() {
        GameEventListener selfRemoving = new GameEventListener() {
            @Override
            public void onEvent(GameEvent event) {
                received.add(event);
                bus.unsubscribe(this);
            }
        };
        bus.subscribe(selfRemoving);

        bus.publish(EventType.GAME_STARTED, "First");
        bus.publish(EventType.GAME_STARTED, "Second");

        assertEquals(1, received.size());
    }

    @Test
    void publishingWithoutListenersDoesNothing() {
        bus.publish(EventType.GAME_STARTED, "Nobody is listening");

        assertEquals(0, bus.listenerCount());
    }

    @Test
    void nullListenerThrowsException() {
        assertThrows(NullPointerException.class, () -> bus.subscribe(null));
    }

    @Test
    void nullEventThrowsException() {
        assertThrows(NullPointerException.class, () -> bus.publish(null));
    }
}