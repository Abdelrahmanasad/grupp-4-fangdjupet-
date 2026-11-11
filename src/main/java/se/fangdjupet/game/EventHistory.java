package se.fangdjupet.game;

import se.fangdjupet.core.EventType;
import se.fangdjupet.core.GameEvent;
import se.fangdjupet.core.GameEventListener;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * Observer that remembers the latest game events to show
 * "Enemies defeated: 3" when the game ends.
 * <p>
 * Only the newest {@code maxSize} events are kept, so a long game
 * never fills up the memory. The oldest event is dropped first.
 */
public class EventHistory implements GameEventListener {

    /**
     * How many events are kept if nothing else is given.
     */
    public static final int DEFAULT_MAX_SIZE = 100;

    private final Deque<GameEvent> events = new ArrayDeque<>();
    private final int maxSize;

    /**
     * Creates a history that keeps the latest {@value #DEFAULT_MAX_SIZE} events.
     */
    public EventHistory() {
        this(DEFAULT_MAX_SIZE);
    }

    /**
     * @param maxSize how many events to keep, at least 1
     * @throws IllegalArgumentException if maxSize is less than 1
     */
    public EventHistory(int maxSize) {
        if (maxSize < 1) {
            throw new IllegalArgumentException("Max size must be at least 1");
        }
        this.maxSize = maxSize;
    }

    @Override
    public void onEvent(GameEvent event) {
        if (events.size() == maxSize) {
            events.removeFirst();
        }
        events.addLast(event);
    }

    /**
     * @return all remembered events, oldest first (read-only copy)
     */
    public List<GameEvent> getEvents() {
        return List.copyOf(events);
    }

    /**
     * @param type the type to look for
     * @return the remembered events of the given type, oldest first
     */
    public List<GameEvent> getEvents(EventType type) {
        return events.stream()
                .filter(event -> event.type() == type)
                .toList();
    }

    /**
     * @param type the type to count - ENEMY_DEFEATED
     * @return how many remembered events have the given type
     */
    public int count(EventType type) {
        return getEvents(type).size();
    }

    /**
     * @return number of remembered events
     */
    public int size() {
        return events.size();
    }

    /**
     * Forgets all events when a new game starts.
     */
    public void clear() {
        events.clear();
    }
}