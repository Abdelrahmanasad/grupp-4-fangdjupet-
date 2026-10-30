package se.fangdjupet.combat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeroTest {

    @Test
    void heroTakesDamage() {
        Hero hero = new Hero("Knight", 100, 100);
        hero.takeDamage(25);
        assertEquals(75.0, hero.getHealth());
    }




}
