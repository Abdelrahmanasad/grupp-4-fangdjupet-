package se.fangdjupet.combat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for MagicAttack.
 */
public class MagicAttackTest {

    @Test
    void magicAttackReturnsBaseDamage() {
        MagicAttack attack = new MagicAttack();

        double damage = attack.calculateDamage(15.0);

        assertEquals(15.0, damage);
    }

    @Test
    void magicAttackCannotHaveNegativeDamage() {
        MagicAttack attack = new MagicAttack();

        assertThrows(IllegalArgumentException.class,
                () -> attack.calculateDamage(-10));
    }
}