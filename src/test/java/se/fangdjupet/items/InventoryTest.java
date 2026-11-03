package se.fangdjupet.items;

import org.junit.jupiter.api.Test;
import se.fangdjupet.factory.ItemFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    @Test
    void addItemShouldReturnFalseWhenInventoryIsFull() {
        Inventory inv = new Inventory(2);
        Item w1 = ItemFactory.createItem("weapon", "Sword", 1.0, 10.0);
        Item w2 = ItemFactory.createItem("weapon", "Axe", 1.0, 15.0);
        Item p1 = ItemFactory.createItem("potion", "Healthpotion", 1.0, 20.0);

        assertTrue(inv.addItem(w1));
        assertTrue(inv.addItem(w2));
        assertFalse(inv.addItem(p1)); // Väskan är full (max 2)
        assertEquals(2, inv.getItems().size());
    }

    @Test
    void getItemsOfTypeShouldReturnOnlySpecifiedType() {
        Inventory inv = new Inventory(5);
        inv.addItem(ItemFactory.createItem("weapon", "Sword", 1.0, 10.0));
        inv.addItem(ItemFactory.createItem("armor", "Shield", 1.0, 5.0));
        inv.addItem(ItemFactory.createItem("potion", "Potion", 1.0, 15.0));

        List<Weapon> weapons = inv.getItemsOfType(Weapon.class);

        assertEquals(1, weapons.size());
        assertEquals("Sword", weapons.get(0).getName());
    }

    @Test
    void createItemWithInvalidTypeShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            ItemFactory.createItem("banana", "Unknown", 1.0, 0.0);
        });
    }

    @Test
    void addItemShouldSuccessfullyAddItemToInventory() {
        Inventory inv = new Inventory(3);
        Item sword = ItemFactory.createItem("weapon", "Sword", 1.0, 10.0);

        boolean added = inv.addItem(sword);

        assertTrue(added);
        assertEquals(1, inv.getItems().size());
        assertTrue(inv.getItems().contains(sword));
    }

    @Test
    void removeItemShouldRemoveExistingItemAndReturnTrue() {
        Inventory inv = new Inventory(3);
        Item sword = ItemFactory.createItem("weapon", "Sword", 1.0, 10.0);
        inv.addItem(sword);

        boolean removed = inv.removeItem(sword);

        assertTrue(removed);
        assertEquals(0, inv.getItems().size());
        assertFalse(inv.getItems().contains(sword));
    }
}