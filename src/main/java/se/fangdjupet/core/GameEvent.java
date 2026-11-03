package se.fangdjupet.core;

import java.util.Objects;

/**
 * Something that happened in the game, e.g. "The hero defeated the Troll".
 * A record, because an event only carries data and must never change
 * after it has been created.
 * @param type    what kind of event it is
 * @param message readable text that can be shown to the player
 */
public record GameEvent(EventType type, String message) {

    /**
     * Compact constructor that validates the input.
     *
     * @throws NullPointerException     if type or message is null
     * @throws IllegalArgumentException if message is blank
     */
    public GameEvent {
        Objects.requireNonNull(type, "Event type cannot be null");
        Objects.requireNonNull(message, "Event message cannot be null");

        if (message.isBlank()) {
            throw new IllegalArgumentException("Event message cannot be blank");
        }
    }
}