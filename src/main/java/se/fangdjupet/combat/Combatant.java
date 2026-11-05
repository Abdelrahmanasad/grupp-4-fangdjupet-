package se.fangdjupet.combat;

/**
 * Common interface for heroes and enemies.
 */
public interface Combatant {

    /**
     * Gets the character's name.
     *
     * @return the character's name
     */
    String getName();

    /**
     * Gets the character's current health.
     *
     * @return current health
     */
    double getHealth();

    /**
     * Gets the character's maximum health.
     *
     * @return maximum health
     */
    double maxHealth();

    /**
     * Reduces the character's health by the given damage.
     *
     * @param amount how much damage the character takes
     */
    void takeDamage(double amount);

    /**
     * Checks if the character is still alive.
     *
     * @return true if the character is alive, otherwise false
     */
    boolean isAlive();
}