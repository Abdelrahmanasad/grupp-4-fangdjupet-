package se.fangdjupet.combat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HeroTest {

    @Test
    void heroTakesDamage() {
        Hero hero = new Hero("Knight", 100, 100);
        hero.takeDamage(25);
        assertEquals(75.0, hero.getHealth());
    }

    @Test
    void heroHealthCannotGoBelowZero() {
        Hero hero = new Hero("Knight", 100, 100);
        hero.takeDamage(150);
        assertEquals(0.0, hero.getHealth());
    }

    @Test
    void heroCanHeal() {
        Hero hero = new Hero("Knight", 100, 100);
        hero.takeDamage(30);
        hero.heal(20);
        assertEquals(90.0, hero.getHealth());
    }

    @Test
    void heroCannotHealAboveMaxHealth() {
        Hero hero = new Hero("Knight", 90, 100);
        hero.heal(30);
        assertEquals(100.0, hero.getHealth());
    }

    @Test
    void heroIsDeadAtZeroHealth() {
        Hero hero = new Hero("Knight", 100, 100);
        hero.takeDamage(100);
        assertFalse(hero.isAlive());
    }

    @Test
    void heroIsAliveWithOneHealth() {
        Hero hero = new Hero("Knight", 1, 100);
        assertTrue(hero.isAlive());
    }

    @Test
    void heroCannotTakeNegativeDamage() {
        Hero hero = new Hero("Knight", 100, 100);

        assertThrows(IllegalArgumentException.class,
                () -> hero.takeDamage(-10));
    }

    @Test
    void heroCannotHealNegativeAmount() {
        Hero hero = new Hero("Knight", 100, 100);

        assertThrows(IllegalArgumentException.class,
                () -> hero.heal(-10));
    }




}
