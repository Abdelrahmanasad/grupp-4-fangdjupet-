package se.fangdjupet.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Central "notice board" for events (the Subject in the Observer pattern).
 * Whoever creates an event (Battle) does not need to know who is
 * listening. It just calls GameEvent and the EventBus
 * forwards the event to every registered listener.
 */
public class EventBus {

    private final List<GameEventListener> listeners = new ArrayList<>();

    /**
     * Registers a listener that will receive all future events.
     * The same listener is only registered once.
     */
    public void subscribe(GameEventListener listener) {
        Objects.requireNonNull(listener, "Listener cannot be null");

        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    //Removes a listener so that it no longer receives events.
    public void unsubscribe(GameEventListener listener) {
        listeners.remove(listener);
    }

    /**
     * Sends an event to every registered listener, in the order
     * they were registered. throws NullPointerException if event is null.
     */
    public void publish(GameEvent event) {
        Objects.requireNonNull(event, "Event cannot be null");

        // Iterate over a copy so that a listener can unsubscribe
        // during publish() without a ConcurrentModificationException.
        for (GameEventListener listener : List.copyOf(listeners)) {
            listener.onEvent(event);
        }
    }


    //Convenience shortcut - creates and publishes an event in one step.
    public void publish(EventType type, String message) {
        publish(new GameEvent(type, message));
    }

    /**
     * @return number of registered listeners (obviuously just for tests)
     */
    public int listenerCount() {
        return listeners.size();
    }
}