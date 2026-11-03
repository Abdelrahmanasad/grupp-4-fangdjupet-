package se.fangdjupet.core;

import java.util.Objects;

/**
 * En händelse som har inträffat i spelet, t.ex. "Hjälten besegrade Trollet".
 * Record eftersom en händelse bara bär data och aldrig ska ändras efter att den skapats.
 */
public record GameEvent(EventType type, String message) {

    public GameEvent {
        Objects.requireNonNull(type, "Event type cannot be null");
        Objects.requireNonNull(message, "Event message cannot be null");

        if (message.isBlank()) {
            throw new IllegalArgumentException("Event message cannot be blank");
        }
    }
}