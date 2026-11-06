package se.fangdjupet.combat;

/**
 * Common interface for different types of attacks.
 */
public interface AttackStrategy {

    /**
     * Calculates damage based on the attack type.
     *
     * @param baseDamage damage before the attack type affects it
     * @return the calculated damage
     */
    double calculateDamage(double baseDamage);

    /**
     * Checks if the attack ignores defence.
     *
     * @return true if defence is ignored, otherwise false
     */
    default boolean ignoresDefence() {
        return false;
    }
}