package se.fangdjupet.game;

import se.fangdjupet.core.EventType;
import se.fangdjupet.core.GameEvent;
import se.fangdjupet.core.GameEventListener;

import java.io.PrintStream;
import java.util.Objects;

/**
 * Observer that prints every game event to the console,
 * example : {@code [BATTLE] Goblin was defeated.}
 * <p>
 * Battle, the inventory and the game engine only publish events to the
 * EventBus. This class is the only place that decides how they are shown.
 */
public class ConsoleEventLogger implements GameEventListener {

    private final PrintStream output;

    /**
     * @param output where to print, {@code System.out}
     */
    public ConsoleEventLogger(PrintStream output) {
        this.output = Objects.requireNonNull(output, "Output cannot be null");
    }

    @Override
    public void onEvent(GameEvent event) {
        output.println("[" + categoryOf(event.type()) + "] " + event.message());
    }

    /**
     * Groups the event types into a few readable categories.
     * A switch expression over an enum must cover every value, so the
     * compiler reminds us to add a category when a new event type is added.
     *
     * @param type the event type
     * @return the category shown in front of the message
     */
    static String categoryOf(EventType type) {
        return switch (type) {
            case GAME_STARTED, GAME_SAVED, GAME_LOADED, GAME_OVER, VICTORY -> "GAME";
            case ROOM_ENTERED -> "ROOM";
            case BATTLE_STARTED, ATTACK_PERFORMED, ENEMY_DEFEATED, HERO_DEFEATED -> "BATTLE";
            case HERO_HEALED -> "HEAL";
            case ITEM_FOUND, ITEM_USED, ITEM_EQUIPPED -> "ITEM";
        };
    }
}