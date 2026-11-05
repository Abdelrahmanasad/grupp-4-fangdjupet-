package se.fangdjupet.combat;

/**
 * Handles damage from ranged attacks.
 */
public class RangedAttack implements AttackStrategy {

    /**
     * Calculates the damage for a ranged attack.
     *
     * @param baseDamage the base damage of the attack
     * @return the damage dealt by the attack
     */
    @Override
    public double calculateDamage(double baseDamage) {
        return DamageCalculator.calculateDamage(baseDamage);
    }
}