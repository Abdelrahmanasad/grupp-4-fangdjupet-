package se.fangdjupet.factory;

import se.fangdjupet.combat.*;

/**
 * Factory class for creating different types of enemies.
 */

public final class EnemyFactory {

    private EnemyFactory() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Creates an enemy with scaled stats (+25% per level above 1).
     */
    public static Enemy createEnemy(String name, int level, double baseHealth, double baseDamage, AttackStrategy strategy) {
        double levelMultiplier = 1.0 + (level - 1) * 0.25;
        double scaledHealth = baseHealth * levelMultiplier;
        double scaledDamage = baseDamage * levelMultiplier;


        return new Enemy(name, scaledHealth, scaledDamage, strategy);
    }

    public static Enemy createGoblin(int level) {
        return createEnemy("Goblin", level, 40.0, 8.0, new MeleeAttack());
    }

    public static Enemy createSkeleton(int level) {
        return createEnemy("Skeleton", level, 50.0, 10.0, new RangedAttack());
    }

    public static Enemy createTroll(int level) {
        return createEnemy("Troll", level, 90.0, 15.0, new MeleeAttack());
    }

    public static Enemy createMage(int level) {
        return createEnemy("Mage", level, 45.0, 18.0, new MagicAttack());
    }
}