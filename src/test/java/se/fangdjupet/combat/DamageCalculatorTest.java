package se.fangdjupet.combat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for DamageCalculator.
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

    @Test
    void damageCalculatorCannotHaveNaNDamage() {
        assertThrows(IllegalArgumentException.class,
                () -> DamageCalculator.calculateDamage(Double.NaN));
    }

    @Test
    void damageCalculatorCannotHaveInfiniteDamage() {
        assertThrows(IllegalArgumentException.class,
                () -> DamageCalculator.calculateDamage(Double.POSITIVE_INFINITY));

        assertThrows(IllegalArgumentException.class,
                () -> DamageCalculator.calculateDamage(Double.NEGATIVE_INFINITY));
    }

    @Test
    void damageCalculatorCannotHaveNaNDefence() {
        assertThrows(IllegalArgumentException.class,
                () -> DamageCalculator.calculateDamage(30, Double.NaN));
    }

    @Test
    void damageCalculatorCannotHaveInfiniteDefence() {
        assertThrows(IllegalArgumentException.class,
                () -> DamageCalculator.calculateDamage(
                        30, Double.POSITIVE_INFINITY));

        assertThrows(IllegalArgumentException.class,
                () -> DamageCalculator.calculateDamage(
                        30, Double.NEGATIVE_INFINITY));
    }
}