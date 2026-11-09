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
        if (!Double.isFinite(baseDamage) || baseDamage < 0) {
            throw new IllegalArgumentException("Invalid base damage");
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
        if (!Double.isFinite(defenceBonus) || defenceBonus < 0) {
            throw new IllegalArgumentException(
                    "Invalid defence bonus");
        }

        return Math.max(0, calculateDamage(baseDamage) - defenceBonus);
    }
}