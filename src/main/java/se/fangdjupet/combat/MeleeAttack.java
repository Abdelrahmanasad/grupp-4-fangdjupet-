package se.fangdjupet.combat;

/**
 * Hanterar skada från närstridsattacker.
 */
public class MeleeAttack implements AttackStrategy {

    /**
     * Räknar ut skadan för en närstridsattack.
     *
     * @param baseDamage attackens grundskada
     * @return skadan som attacken gör
     */
    @Override
    public double calculateDamage(double baseDamage) {
        if (baseDamage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative");
        }

        return baseDamage;
    }
}