package se.fangdjupet.combat;

/**
 * Handles damage from magic attacks.
 */
public class MagicAttack implements AttackStrategy {

    /**
     * Calculates the damage for a magic attack.
     *
     * @param baseDamage the base damage of the attack
     * @return the damage dealt by the attack
     */
    @Override
    public double calculateDamage(double baseDamage) {
        return DamageCalculator.calculateDamage(baseDamage);
    }

    /**
     * Checks if magic attacks ignore defence.
     *
     * @return true because magic ignores defence
     */
    @Override
    public boolean ignoresDefence() {
        return true;
    }
}