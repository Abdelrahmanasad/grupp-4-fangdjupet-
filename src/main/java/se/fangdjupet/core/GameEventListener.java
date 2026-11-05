package se.fangdjupet.core;

/**
 * Listener (Observer) that wants to be notified when something happens in the game.
 * <p>
 * Implemented by ConsoleEventLogger (prints to the console)
 * and EventHistory (stores events in a list).
 * <p>
 * Functional interface, so a listener can also be written as a lambda:
 * {@code bus.subscribe(event -> IO.println(event.message()));}
 */
@FunctionalInterface
public interface GameEventListener {

    /**
     * Called by {@link EventBus} every time an event is published.
     *
     * @param event the event that happened, never null
     */
    void onEvent(GameEvent event);
}