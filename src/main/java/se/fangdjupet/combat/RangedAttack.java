package se.fangdjupet.combat;

/**
 * Hanterar skada från distansattacker.
 */
public class RangedAttack implements AttackStrategy {

    /**
     * Räknar ut skadan för en distansattack.
     *
     * @param baseDamage attackens grundskada
     * @return skadan som attacken gör
     */
    @Override
    public double calculateDamage(double baseDamage) {
        return DamageCalculator.calculateDamage(baseDamage);
    }
}