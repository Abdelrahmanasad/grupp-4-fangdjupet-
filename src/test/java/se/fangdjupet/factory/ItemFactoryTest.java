package se.fangdjupet.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.fangdjupet.items.*;

import static org.junit.jupiter.api.Assertions.*;

class ItemFactoryTest {

    @Test
    @DisplayName("Should create Weapon correctly via ItemFactory")
    void createWeaponTest() {
        Item item = ItemFactory.createItem(ItemType.WEAPON, "Iron Sword", 1, 15.0);

        assertInstanceOf(Weapon.class, item);
        assertEquals("Iron Sword", item.getName());
        assertEquals(1, item.getLevel());
        assertEquals(ItemType.WEAPON, item.getType());

        Weapon weapon = (Weapon) item;
        assertEquals(15.0, weapon.getDamage());
    }

    @Test
    @DisplayName("Should create Armor correctly via ItemFactory")
    void createArmorTest() {
        Item item = ItemFactory.createItem(ItemType.ARMOR, "Iron Shield", 2, 10.0);

        assertInstanceOf(Armor.class, item);
        assertEquals("Iron Shield", item.getName());
        assertEquals(2, item.getLevel());
        assertEquals(ItemType.ARMOR, item.getType());

        Armor armor = (Armor) item;
        assertEquals(10.0, armor.getDefenceBonus());
    }

    @Test
    @DisplayName("Should create Potion correctly via ItemFactory")
    void createPotionTest() {
        Item item = ItemFactory.createItem(ItemType.POTION, "Health Potion", 3, 50.0);

        assertInstanceOf(Potion.class, item);
        assertEquals("Health Potion", item.getName());
        assertEquals(3, item.getLevel());
        assertEquals(ItemType.POTION, item.getType());

        Potion potion = (Potion) item;
        assertEquals(50.0, potion.getHealthBonus());
    }
}