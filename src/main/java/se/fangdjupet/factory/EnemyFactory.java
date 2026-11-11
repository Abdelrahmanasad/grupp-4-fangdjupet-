package se.fangdjupet.factory;

import se.fangdjupet.combat.AttackStrategy;
import se.fangdjupet.combat.Enemy;
import se.fangdjupet.combat.MagicAttack;
import se.fangdjupet.combat.MeleeAttack;
import se.fangdjupet.combat.RangedAttack;

import java.util.Objects;

/**
 * Factory class for creating different types of enemies.
 */
public final class EnemyFactory {

    private EnemyFactory() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Creates an enemy from its type and level (Factory pattern).
     *
     * @param type  what kind of enemy
     * @param level the enemy's level, at least 1
     * @return a new enemy with full health
     * @throws NullPointerException     if type is null
     * @throws IllegalArgumentException if level is less than 1
     */
    public static Enemy createEnemy(EnemyType type, int level) {
        Objects.requireNonNull(type, "EnemyType cannot be null");

        return switch (type) {
            case GOBLIN -> createGoblin(level);
            case SKELETON -> createSkeleton(level);
            case TROLL -> createTroll(level);
            case MAGE -> createMage(level);
        };
    }

    /**
     * Creates an enemy with scaled stats (+25% per level above 1).
     *
     * @throws IllegalArgumentException if level is less than 1
     */
    public static Enemy createEnemy(String name, int level, double baseHealth, double baseDamage, AttackStrategy strategy) {
        if (level < 1) {
            throw new IllegalArgumentException("Level must be at least 1");
        }

        double levelMultiplier = 1.0 + (level - 1) * 0.25;
        double scaledHealth = baseHealth * levelMultiplier;
        double scaledDamage = baseDamage * levelMultiplier;

        return new Enemy(name, scaledHealth, scaledDamage, strategy);
    }

    /**
     * Creates a Goblin: weak melee enemy.
     */
    public static Enemy createGoblin(int level) {
        return createEnemy("Goblin", level, 40.0, 8.0, new MeleeAttack());
    }

    /**
     * Creates a Skeleton: ranged enemy.
     */
    public static Enemy createSkeleton(int level) {
        return createEnemy("Skeleton", level, 50.0, 10.0, new RangedAttack());
    }

    /**
     * Creates a Troll: strong melee enemy with a lot of health.
     */
    public static Enemy createTroll(int level) {
        return createEnemy("Troll", level, 90.0, 15.0, new MeleeAttack());
    }

    /**
     * Creates a Mage: magic enemy whose attacks ignore armor.
     */
    public static Enemy createMage(int level) {
        return createEnemy("Mage", level, 45.0, 18.0, new MagicAttack());
    }
}
