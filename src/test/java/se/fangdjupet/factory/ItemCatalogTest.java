package se.fangdjupet.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.fangdjupet.items.*;

import java.lang.reflect.Constructor;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemCatalogTest {

    @Test
    @DisplayName("Should create weapons from catalog with correct stats")
    void testWeaponsCatalog() {
        Weapon sword = ItemCatalog.Weapons.createRustySword();
        assertNotNull(sword);
        assertEquals("Rusty Sword", sword.getName());
        assertEquals(WeaponType.SWORD, sword.getWeaponType());

        List<Weapon> weapons = ItemCatalog.Weapons.getAll();
        assertEquals(4, weapons.size());
    }

    @Test
    @DisplayName("Should create armors from catalog with correct stats")
    void testArmorsCatalog() {
        Armor cap = ItemCatalog.Armors.createLeatherCap();
        assertNotNull(cap);
        assertEquals("Leather Cap", cap.getName());
        assertEquals(ArmorSlot.HELMET, cap.getArmorSlot());

        List<Armor> armors = ItemCatalog.Armors.getAll();
        assertEquals(4, armors.size());
    }

    @Test
    @DisplayName("Should create potions from catalog with correct stats")
    void testPotionsCatalog() {
        Potion potion = ItemCatalog.Potions.createSmallHealthPotion();
        assertNotNull(potion);
        assertEquals("Small Health Potion", potion.getName());
        assertEquals(25.0, potion.getHealthBonus());

        List<Potion> potions = ItemCatalog.Potions.getAll();
        assertEquals(2, potions.size());
    }

    @Test
    @DisplayName("Private constructor should be private")
    void testPrivateConstructor() throws Exception {
        Constructor<ItemCatalog> constructor = ItemCatalog.class.getDeclaredConstructor();
        assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}