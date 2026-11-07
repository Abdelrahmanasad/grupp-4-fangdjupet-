package se.fangdjupet.combat;

/**
 * Handles damage from melee attacks.
 */
public class MeleeAttack implements AttackStrategy {

    /**
     * Calculates the damage for a melee attack.
     *
     * @param baseDamage the base damage of the attack
     * @return the damage dealt by the attack
     */
    @Override
    public double calculateDamage(double baseDamage) {
        return DamageCalculator.calculateDamage(baseDamage);
    }
}