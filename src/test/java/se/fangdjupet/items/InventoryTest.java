package se.fangdjupet.items;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.fangdjupet.factory.ItemFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory(3);
    }

    @Test
    @DisplayName("Should validate Inventory max capacity on creation")
    void testInventoryConstructorValidation() {
        assertThrows(IllegalArgumentException.class, () -> new Inventory(0));
        assertThrows(IllegalArgumentException.class, () -> new Inventory(-5));
    }

    @Test
    @DisplayName("Should add items and enforce max capacity")
    void testAddItemAndCapacity() {
        Item sword = ItemFactory.createItem(ItemType.WEAPON, "Sword", 1, 10.0);
        Item shield = ItemFactory.createItem(ItemType.ARMOR, "Shield", 1, 5.0);
        Item potion = ItemFactory.createItem(ItemType.POTION, "Potion", 1, 15.0);
        Item extraPotion = ItemFactory.createItem(ItemType.POTION, "Extra Potion", 1, 15.0);

        assertTrue(inventory.addItem(sword));
        assertTrue(inventory.addItem(shield));
        assertTrue(inventory.addItem(potion));

        // Capacity is 3, fourth item should fail
        assertFalse(inventory.addItem(extraPotion));
        assertEquals(3, inventory.getItems().size());
    }

    @Test
    @DisplayName("Should throw NullPointerException when adding null item")
    void testAddNullItem() {
        assertThrows(NullPointerException.class, () -> inventory.addItem(null));
    }

    @Test
    @DisplayName("Should successfully remove an existing item from inventory")
    void testRemoveExistingItem() {
        Item sword = ItemFactory.createItem(ItemType.WEAPON, "Sword", 1, 10.0);
        inventory.addItem(sword);

        boolean removed = inventory.removeItem(sword);

        assertTrue(removed, "removeItem should return true when item was present");
        assertEquals(0, inventory.getItems().size(), "Inventory size should be 0 after removal");
    }

    @Test
    @DisplayName("Should return false when attempting to remove an item not in inventory")
    void testRemoveNonExistentItem() {
        Item sword = ItemFactory.createItem(ItemType.WEAPON, "Sword", 1, 10.0);

        boolean removed = inventory.removeItem(sword);

        assertFalse(removed, "removeItem should return false when item was not in inventory");
    }

    @Test
    @DisplayName("Should throw NullPointerException when removing null item")
    void testRemoveNullItem() {
        assertThrows(NullPointerException.class, () -> inventory.removeItem(null));
    }

    @Test
    @DisplayName("Should filter items by type using getItemsOfType()")
    void testGetItemsOfType() {
        inventory.addItem(ItemFactory.createItem(ItemType.WEAPON, "Sword 1", 1, 10.0));
        inventory.addItem(ItemFactory.createItem(ItemType.WEAPON, "Sword 2", 2, 20.0));
        inventory.addItem(ItemFactory.createItem(ItemType.ARMOR, "Shield", 1, 5.0));

        List<Weapon> weapons = inventory.getItemsOfType(Weapon.class);
        List<Armor> armors = inventory.getItemsOfType(Armor.class);

        assertEquals(2, weapons.size());
        assertEquals(1, armors.size());
    }

    @Test
    @DisplayName("Should throw exception when attempting to modify unmodifiable list")
    void testUnmodifiableList() {
        Item sword = ItemFactory.createItem(ItemType.WEAPON, "Sword", 1, 10.0);
        inventory.addItem(sword);

        assertThrows(UnsupportedOperationException.class, () -> {
            inventory.getItems().add(ItemFactory.createItem(ItemType.ARMOR, "Fake Shield", 1, 5.0));
        });
    }

    @Test
    @DisplayName("Should decrease potion quantity on use and remove from inventory when stack becomes empty")
    void testPotionQuantityOnUse() {
        Potion potion = new Potion("Health Potion", 1, 20.0, 2);
        inventory.addItem(potion);

        // Första användningen: 1 kvar i stacken -> finns kvar i inventory
        assertTrue(potion.use(inventory), "Should return true because 1 potion remains");
        assertEquals(1, potion.getQuantity());
        assertEquals(1, inventory.getItems().size());

        // Andra användningen: 0 kvar -> returnerar false och plockas bort ur inventory automatiskt
        assertFalse(potion.use(inventory), "Should return false because 0 potions remain");
        assertEquals(0, potion.getQuantity());
        assertEquals(0, inventory.getItems().size(), "Potion should be automatically removed from inventory");
    }

    @Test
    @DisplayName("Should throw NullPointerException when calling use() with null inventory")
    void testPotionUseNullInventory() {
        Potion potion = new Potion("Health Potion", 1, 20.0, 1);
        assertThrows(NullPointerException.class, () -> potion.use(null));
    }

    @Test
    @DisplayName("Item constructor should validate invalid arguments")
    void testItemValidation() {
        // Name validation
        assertThrows(NullPointerException.class, () -> new Weapon(null, 1, WeaponType.SWORD, 10.0));
        assertThrows(IllegalArgumentException.class, () -> new Weapon("", 1, WeaponType.SWORD, 10.0));
        assertThrows(IllegalArgumentException.class, () -> new Weapon("   ", 1, WeaponType.SWORD, 10.0));

        // Level validation (level < 1 or level > 3)
        assertThrows(IllegalArgumentException.class, () -> new Weapon("Sword", 0, WeaponType.SWORD, 10.0));
        assertThrows(IllegalArgumentException.class, () -> new Weapon("Sword", 4, WeaponType.SWORD, 10.0));

        // Negative stat values validation
        assertThrows(IllegalArgumentException.class, () -> new Weapon("Sword", 1, WeaponType.SWORD, -10.0));
        assertThrows(IllegalArgumentException.class, () -> new Armor("Shield", 1, ArmorSlot.SHIELD, -5.0));
        assertThrows(IllegalArgumentException.class, () -> new Potion("Potion", 1, -50.0));

        // Potion quantity validation
        assertThrows(IllegalArgumentException.class, () -> new Potion("Health Potion", 1, 20.0, 0));
        assertThrows(IllegalArgumentException.class, () -> new Potion("Health Potion", 1, 20.0, -1));
    }
}