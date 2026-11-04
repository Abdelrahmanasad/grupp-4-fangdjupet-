package se.fangdjupet.combat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tester för DamageCalculator.
 */
public class DamageCalculatorTest {

    @Test
    void damageCalculatorReturnsBaseDamage() {
        double damage = DamageCalculator.calculateDamage(15.0);

        assertEquals(15.0, damage);
    }

    @Test
    void damageCalculatorCannotHaveNegativeDamage() {
        assertThrows(IllegalArgumentException.class,
                () -> DamageCalculator.calculateDamage(-10));
    }
}