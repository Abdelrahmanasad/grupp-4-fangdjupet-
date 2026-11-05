package se.fangdjupet.combat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void magicAttackIgnoresDefence() {
        MagicAttack attack = new MagicAttack();

        assertTrue(attack.ignoresDefence());
    }
}