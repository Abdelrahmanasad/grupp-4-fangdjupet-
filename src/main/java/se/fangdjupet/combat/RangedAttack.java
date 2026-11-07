package se.fangdjupet.combat;

/**
 * Handles damage from ranged attacks.
 */
public class RangedAttack implements AttackStrategy {

    /**
     * Multiplier for ranged attack damage.
     */
    private static final double DAMAGE_MULTIPLIER = 1.2;

    /**
     * Calculates the damage for a ranged attack.
     *
     * @param baseDamage the base damage of the attack
     * @return the damage dealt by the attack
     */
    @Override
    public double calculateDamage(double baseDamage) {
        return DamageCalculator.calculateDamage(baseDamage * DAMAGE_MULTIPLIER);
    }
}