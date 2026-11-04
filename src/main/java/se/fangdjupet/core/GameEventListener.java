package se.fangdjupet.core;

 // Listener (Observer) that wants to be notified when something happens in the game.
 // Implemented by ConsoleEventLogger (prints to the console)
 // and EventHistory (stores events in a list).
@FunctionalInterface
public interface GameEventListener {

    void onEvent(GameEvent event);
}