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
}