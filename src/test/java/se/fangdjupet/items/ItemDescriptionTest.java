package se.fangdjupet.items;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemDescriptionTest {

    @Test
    @DisplayName("Weapon description shows damage with one decimal")
    void weaponDescriptionIsRounded() {
        // (16 + 1.5) * 1.25 = 21.875
        Weapon longbow = new Weapon("Longbow", 2, WeaponType.BOW, 1.5);

        assertTrue(longbow.getDescription().endsWith("Damage: 21.9"));
    }

    @Test
    @DisplayName("Armor description shows defence with one decimal")
    void armorDescriptionIsRounded() {
        // (3 + 2) * 1.25 = 6.25
        Armor chestplate = new Armor("Iron Chestplate", 2, ArmorSlot.CHESTPLATE, 2.0);

        assertTrue(chestplate.getDescription().endsWith("Defence: +6.3"));
    }

    @Test
    @DisplayName("Potion description shows quantity and healing")
    void potionDescriptionShowsQuantity() {
        Potion potion = new Potion("Small Health Potion", 1, 25.0, 3);

        assertTrue(potion.getDescription().contains("x3"));
        assertTrue(potion.getDescription().endsWith("Heals: +25.0 HP"));
    }
}
