package se.fangdjupet.game.save;

import org.junit.jupiter.api.Test;
import se.fangdjupet.items.Armor;
import se.fangdjupet.items.ArmorSlot;
import se.fangdjupet.items.Item;
import se.fangdjupet.items.Potion;
import se.fangdjupet.items.Weapon;
import se.fangdjupet.items.WeaponType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ItemSerializerTest {

    private static final double DELTA = 0.001;

    // Writes the item and reads it back again
    private static Item roundTrip(Item item) throws SaveGameException {
        SaveData data = new SaveData();
        ItemSerializer.write(item, "item.0.", data);
        return ItemSerializer.read("item.0.", data);
    }

    @Test
    void weaponKeepsAllValues() throws SaveGameException {
        Weapon original = new Weapon("Battle Axe", 2, WeaponType.AXE, 2.5);

        Weapon loaded = assertInstanceOf(Weapon.class, roundTrip(original));

        assertEquals("Battle Axe", loaded.getName());
        assertEquals(2, loaded.getLevel());
        assertEquals(WeaponType.AXE, loaded.getWeaponType());
        assertEquals(2.5, loaded.getBonusDamage(), DELTA);
        assertEquals(original.getTotalDamage(), loaded.getTotalDamage(), DELTA);
    }

    @Test
    void armorKeepsAllValues() throws SaveGameException {
        Armor original = new Armor("Wooden Shield", 1, ArmorSlot.SHIELD, 1.0);

        Armor loaded = assertInstanceOf(Armor.class, roundTrip(original));

        assertEquals("Wooden Shield", loaded.getName());
        assertEquals(ArmorSlot.SHIELD, loaded.getArmorSlot());
        assertEquals(1.0, loaded.getDefenceBonus(), DELTA);
    }

    @Test
    void potionKeepsQuantity() throws SaveGameException {
        Potion original = new Potion("Small Health Potion", 1, 25.0, 3);

        Potion loaded = assertInstanceOf(Potion.class, roundTrip(original));

        assertEquals(25.0, loaded.getHealthBonus(), DELTA);
        assertEquals(3, loaded.getQuantity());
    }

    @Test
    void unknownWeaponTypeThrowsSaveGameException() {
        SaveData data = new SaveData();
        ItemSerializer.write(new Weapon("Sword", 1, WeaponType.SWORD), "item.0.", data);
        data.put("item.0.weaponType", "LIGHTSABER");

        assertThrows(SaveGameException.class, () -> ItemSerializer.read("item.0.", data));
    }

    @Test
    void missingValueThrowsSaveGameException() {
        SaveData data = new SaveData();
        data.put("item.0.type", "POTION");
        data.put("item.0.name", "Potion");
        data.put("item.0.level", 1);

        assertThrows(SaveGameException.class, () -> ItemSerializer.read("item.0.", data));
    }
}