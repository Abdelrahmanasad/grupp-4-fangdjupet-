package se.fangdjupet.combat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MeleeAttackTest {

    @Test
    void meleeAttackReturnsBaseDamage() {
        MeleeAttack attack = new MeleeAttack();

        double damage = attack.calculateDamage(15.0);

        assertEquals(15.0, damage);
    }

    @Test
    void meleeAttackCannotHaveNegativeDamage() {
        MeleeAttack attack = new MeleeAttack();

        assertThrows(IllegalArgumentException.class,
                () -> attack.calculateDamage(-10));
    }
}