package se.fangdjupet.game;

import java.util.Optional;

/**
 * The choices in the main menu, each with the key the player types.
 * <p>
 * An enum keeps every menu choice in one place, so the menu text and the
 * code that handles the choice can never get out of sync.
 */
public enum MenuOption {

    NEW_GAME("1", "Start new adventure"),
    CONTINUE("2", "Continue saved adventure"),
    SHOW_EQUIPMENT("3", "Show hero's equipment"),
    FIGHT("4", "Fight next enemy"),
    SAVE("5", "Save game"),
    EXIT("e", "Exit");

    private final String key;
    private final String label;

    MenuOption(String key, String label) {
        this.key = key;
        this.label = label;
    }

    public String getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    /**
     * Finds the option the player typed. Ignores spaces and upper/lower case,
     * so " E " also means EXIT.
     *
     * @param input what the player typed
     * @return the matching option, or an empty Optional if nothing matches
     */
    public static Optional<MenuOption> fromKey(String input) {
        if (input == null) {
            return Optional.empty();
        }

        String cleaned = input.trim();
        for (MenuOption option : values()) {
            if (option.key.equalsIgnoreCase(cleaned)) {
                return Optional.of(option);
            }
        }

        return Optional.empty();
    }
}