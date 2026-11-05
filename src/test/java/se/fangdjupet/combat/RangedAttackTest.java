package se.fangdjupet.combat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for RangedAttack.
 */
public class RangedAttackTest {

    @Test
    void rangedAttackReturnsBaseDamage() {
        RangedAttack attack = new RangedAttack();

        double damage = attack.calculateDamage(15.0);

        assertEquals(15.0, damage);
    }

    @Test
    void rangedAttackCannotHaveNegativeDamage() {
        RangedAttack attack = new RangedAttack();

        assertThrows(IllegalArgumentException.class,
                () -> attack.calculateDamage(-10));
    }
}