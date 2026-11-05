package se.fangdjupet.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.fangdjupet.items.*;

import static org.junit.jupiter.api.Assertions.*;

class ItemFactoryTest {

    @Test
    @DisplayName("Should create Weapon correctly via backward-compatible createItem")
    void createWeaponLegacyTest() {
        Item item = ItemFactory.createItem(ItemType.WEAPON, "Iron Sword", 1, 15.0);

        assertInstanceOf(Weapon.class, item);
        assertEquals("Iron Sword", item.getName());
        assertEquals(1, item.getLevel());
        assertEquals(ItemType.WEAPON, item.getType());

        Weapon weapon = (Weapon) item;
        // SWORD baseDamage (18.0) + bonusDamage (15.0) = 33.0
        assertEquals(33.0, weapon.getTotalDamage());
    }

    @Test
    @DisplayName("Should create Armor correctly via backward-compatible createItem")
    void createArmorLegacyTest() {
        Item item = ItemFactory.createItem(ItemType.ARMOR, "Iron Chestplate", 2, 10.0);

        assertInstanceOf(Armor.class, item);
        assertEquals("Iron Chestplate", item.getName());
        assertEquals(2, item.getLevel());
        assertEquals(ItemType.ARMOR, item.getType());

        Armor armor = (Armor) item;
        assertEquals(10.0, armor.getDefenceBonus());
    }

    @Test
    @DisplayName("Should create Potion correctly via backward-compatible createItem")
    void createPotionLegacyTest() {
        Item item = ItemFactory.createItem(ItemType.POTION, "Health Potion", 3, 50.0);

        assertInstanceOf(Potion.class, item);
        assertEquals("Health Potion", item.getName());
        assertEquals(3, item.getLevel());
        assertEquals(ItemType.POTION, item.getType());

        Potion potion = (Potion) item;
        assertEquals(50.0, potion.getHealthBonus());
    }

    @Test
    @DisplayName("Should create specific Weapon type via Enum method")
    void createWeaponEnumTest() {
        Weapon axe = ItemFactory.createWeapon("Battle Axe", 2, WeaponType.AXE, 5.0);

        assertEquals("Battle Axe", axe.getName());
        assertEquals(2, axe.getLevel());
        assertEquals(WeaponType.AXE, axe.getWeaponType());
        assertEquals(5.0, axe.getBonusDamage());
        assertEquals(WeaponType.AXE.getBaseDamage() + 5.0, axe.getTotalDamage());
    }

    @Test
    @DisplayName("Should create specific Armor slot via Enum method")
    void createArmorEnumTest() {
        Armor helmet = ItemFactory.createArmor("Iron Helmet", 1, ArmorSlot.HELMET, 2.0);

        assertEquals("Iron Helmet", helmet.getName());
        assertEquals(1, helmet.getLevel());
        assertEquals(ArmorSlot.HELMET, helmet.getArmorSlot());
        assertEquals(2.0, helmet.getDefenceBonus());
        assertEquals(ArmorSlot.HELMET.getBaseDefense() + 2.0, helmet.getTotalDefence());
    }

    @Test
    @DisplayName("Should create Potion with quantity via specific method")
    void createPotionWithQuantityTest() {
        Potion potion = ItemFactory.createPotion("Big Health Potion", 1, 50.0, 3);

        assertEquals("Big Health Potion", potion.getName());
        assertEquals(50.0, potion.getHealthBonus());
        assertEquals(3, potion.getQuantity());
    }
}