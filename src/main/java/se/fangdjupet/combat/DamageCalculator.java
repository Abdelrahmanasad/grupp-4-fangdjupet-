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
}