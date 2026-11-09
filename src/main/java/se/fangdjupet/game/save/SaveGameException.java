package se.fangdjupet.game.save;

/**
 * Thrown when a game cannot be saved or loaded. When the file
 * is missing, cannot be written or contains invalid values.
 * <p>
 * A checked exception, so the caller (the menu) is forced to handle it
 * and can show a friendly message instead of crashing.
 */
public class SaveGameException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * @param message description of what went wrong
     */
    public SaveGameException(String message) {
        super(message);
    }

    /**
     * @param message description of what went wrong
     * @param cause   the original exception like an IOException
     */
    public SaveGameException(String message, Throwable cause) {
        super(message, cause);
    }
}