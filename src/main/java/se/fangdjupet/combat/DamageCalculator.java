package se.fangdjupet.combat;

/**
 * Hanterar gemensamma beräkningar av skada.
 */
public final class DamageCalculator {

    /**
     * Kontrollerar grundskadan och returnerar den.
     *
     * @param baseDamage attackens grundskada
     * @return den beräknade skadan
     */
    public static double calculateDamage(double baseDamage) {
        if (baseDamage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative");
        }

        return baseDamage;
    }

    /**
     * Beräknar skada efter försvar.
     *
     * @param baseDamage attackens grundskada
     * @param defenceBonus försvar från rustning
     * @return skadan efter försvar, minst 0
     */
    public static double calculateDamage(double baseDamage, double defenceBonus) {
        if (defenceBonus < 0) {
            throw new IllegalArgumentException("Defence bonus cannot be negative");
        }

        return Math.max(0, calculateDamage(baseDamage) - defenceBonus);
    }
}