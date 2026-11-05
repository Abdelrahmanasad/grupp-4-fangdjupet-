package se.fangdjupet.combat;

/**
 * Handles shared damage calculations.
 */
public final class DamageCalculator {

    private DamageCalculator() {
    }

    /**
     * Checks the base damage and returns it.
     *
     * @param baseDamage the base damage of the attack
     * @return the calculated damage
     */
    public static double calculateDamage(double baseDamage) {
        if (baseDamage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative");
        }

        return baseDamage;
    }

    /**
     * Calculates damage after defence.
     *
     * @param baseDamage the base damage of the attack
     * @param defenceBonus defence from armor
     * @return damage after defence, at least 0
     */
    public static double calculateDamage(
            double baseDamage, double defenceBonus) {
        if (defenceBonus < 0) {
            throw new IllegalArgumentException(
                    "Defence bonus cannot be negative");
        }

        return Math.max(0, calculateDamage(baseDamage) - defenceBonus);
    }
}