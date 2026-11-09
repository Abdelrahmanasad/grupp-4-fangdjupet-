
package se.fangdjupet.combat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for Enemy.
 */
class EnemyTest {

    @Test
    void enemyHasDefaultAttackValues() {
        Enemy enemy = new Enemy("Goblin", 40, 50);

        assertEquals(10, enemy.getAttackDamage());
        assertInstanceOf(MeleeAttack.class, enemy.getAttackStrategy());
        assertEquals(40, enemy.getHealth());
    }

    @Test
    void enemyHasCustomAttackValues() {
        Enemy enemy = new Enemy("Skeleton", 50, 8, new RangedAttack());

        assertEquals(8, enemy.getAttackDamage());
        assertInstanceOf(RangedAttack.class, enemy.getAttackStrategy());
    }

    @Test
    void newEnemyStartsWithFullHealth() {
        Enemy enemy = new Enemy("Troll", 90, 15, new MeleeAttack());

        assertEquals(90, enemy.getHealth());
        assertEquals(90, enemy.getMaxHealth());
    }

    @Test
    void enemyCannotHaveNegativeAttackDamage() {
        assertThrows(IllegalArgumentException.class,
                () -> new Enemy("Goblin", 40, -5, new MeleeAttack()));
    }

    @Test
    void enemyCannotHaveInvalidAttackDamage() {
        assertThrows(IllegalArgumentException.class,
                () -> new Enemy("Goblin", 40, Double.NaN, new MeleeAttack()));

        assertThrows(IllegalArgumentException.class,
                () -> new Enemy("Goblin", 40,
                        Double.POSITIVE_INFINITY, new MeleeAttack()));
    }

    @Test
    void enemyCannotHaveNullAttackStrategy() {
        assertThrows(IllegalArgumentException.class,
                () -> new Enemy("Goblin", 40, 8, null));
    }
}
