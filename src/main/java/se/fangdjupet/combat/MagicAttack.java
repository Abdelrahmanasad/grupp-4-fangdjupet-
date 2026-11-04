package se.fangdjupet.combat;

/**
 * Hanterar skada från magiska attacker.
 */
public class MagicAttack implements AttackStrategy {

    /**
     * Räknar ut skadan för en magisk attack.
     *
     * @param baseDamage attackens grundskada
     * @return skadan som attacken gör
     */
    @Override
    public double calculateDamage(double baseDamage) {
        return DamageCalculator.calculateDamage(baseDamage);
    }
}