package se.fangdjupet.factory;

import org.junit.jupiter.api.Test;
import se.fangdjupet.items.Item;
import se.fangdjupet.items.Weapon;
import se.fangdjupet.items.Potion;

import static org.junit.jupiter.api.Assertions.*;

class ItemFactoryTest {

    @Test
    void createItemShouldReturnWeaponWithCorrectValues() {
        Item item = ItemFactory.createItem("weapon", "Iron Sword", 1.0, 15.0);

        // Verifiera att objektet faktiskt är ett Weapon
        assertInstanceOf(Weapon.class, item);
        assertEquals("Iron Sword", item.getName());
        assertEquals(1.0, item.getLevel());


        Weapon weapon = (Weapon) item;
        assertEquals(15.0, weapon.getDamage());
    }

    @Test
    void createItemShouldReturnPotionWithCorrectValues() {
        Item item = ItemFactory.createItem("potion", "Health Potion", 1.0, 50.0);

        assertInstanceOf(Potion.class, item);
        assertEquals("Health Potion", item.getName());

        Potion potion = (Potion) item;
        assertEquals(50.0, potion.getHealthBonus());
    }

    @Test
    void createItemWithInvalidTypeShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            ItemFactory.createItem("banana", "Unknown", 1.0, 0.0);
        });
    }
}