package se.fangdjupet.combat;

/**
 * Represents an enemy in the game.
 * Inherits health and damage handling from AbstractCombatant.
 */
public class Enemy extends AbstractCombatant {

    private final double attackDamage;
    private final AttackStrategy attackStrategy;

    /**
     * Creates an enemy with a name, current health and maximum health.
     *
     * @param name      the enemy's name
     * @param health    current health
     * @param maxHealth maximum health
     */
    public Enemy(String name, double health, double maxHealth) {
        super(name, health, maxHealth);
        this.attackDamage = 10;
        this.attackStrategy = new MeleeAttack();
    }

    /**
     * Creates an enemy with its own damage and attack strategy.
     *
     * @param name the enemy's name
     * @param maxHealth maximum health
     * @param attackDamage the enemy's attack damage
     * @param attackStrategy the enemy's attack type
     */
    public Enemy(String name, double maxHealth, double attackDamage,
                 AttackStrategy attackStrategy) {
        super(name, maxHealth, maxHealth);

        if (!Double.isFinite(attackDamage) || attackDamage < 0) {
            throw new IllegalArgumentException("Invalid attack damage");
        }

        if (attackStrategy == null) {
            throw new IllegalArgumentException("Attack strategy cannot be null");
        }

        this.attackDamage = attackDamage;
        this.attackStrategy = attackStrategy;
    }

    /**
     * Returns the enemy's attack damage.
     *
     * @return the attack damage
     */
    public double getAttackDamage() {
        return attackDamage;
    }

    /**
     * Returns the enemy's attack strategy.
     *
     * @return the attack strategy
     */
    public AttackStrategy getAttackStrategy() {
        return attackStrategy;
    }
}