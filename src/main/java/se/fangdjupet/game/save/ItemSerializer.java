package se.fangdjupet.game.save;

import se.fangdjupet.factory.ItemFactory;
import se.fangdjupet.items.Armor;
import se.fangdjupet.items.ArmorSlot;
import se.fangdjupet.items.Item;
import se.fangdjupet.items.ItemType;
import se.fangdjupet.items.Potion;
import se.fangdjupet.items.Weapon;
import se.fangdjupet.items.WeaponType;

/**
 * Converts items to and from the save format.
 * <p>
 * Every item is stored under its own prefix - {@code item.0.name=Rusty Sword}.
 * When loading, the items are recreated through {@link ItemFactory},
 * so the same validation rules apply as in the rest of the game.
 */
final class ItemSerializer {

    private ItemSerializer() {
    }

    /**
     * Writes an item to the save data.
     *
     * @param item   the item to save
     * @param prefix key prefix - {@code "item.0."}
     * @param data   where to write
     * @throws IllegalArgumentException if the item type is unknown
     */
    static void write(Item item, String prefix, SaveData data) {
        data.put(prefix + "type", item.getType());
        data.put(prefix + "name", item.getName());
        data.put(prefix + "level", item.getLevel());

        // Pattern matching switch: each case gets a variable of the right type
        switch (item) {
            case Weapon weapon -> {
                data.put(prefix + "weaponType", weapon.getWeaponType());
                data.put(prefix + "bonusDamage", weapon.getBonusDamage());
            }
            case Armor armor -> {
                data.put(prefix + "armorSlot", armor.getArmorSlot());
                data.put(prefix + "defenceBonus", armor.getDefenceBonus());
            }
            case Potion potion -> {
                data.put(prefix + "healthBonus", potion.getHealthBonus());
                data.put(prefix + "quantity", potion.getQuantity());
            }
            default -> throw new IllegalArgumentException(
                    "Cannot save item of type " + item.getClass().getSimpleName());
        }
    }

    /**
     * Reads an item from the save data.
     *
     * @param prefix key prefix - {@code "item.0."}
     * @param data   where to read from
     * @return the recreated item
     * @throws SaveGameException        if a value is missing or has the wrong format
     * @throws IllegalArgumentException if a value is rejected by the item's validation
     */
    static Item read(String prefix, SaveData data) throws SaveGameException {
        ItemType type = data.getEnum(prefix + "type", ItemType.class);
        String name = data.getString(prefix + "name");
        int level = data.getInt(prefix + "level");

        return switch (type) {
            case WEAPON -> ItemFactory.createWeapon(name, level,
                    data.getEnum(prefix + "weaponType", WeaponType.class),
                    data.getDouble(prefix + "bonusDamage"));
            case ARMOR -> ItemFactory.createArmor(name, level,
                    data.getEnum(prefix + "armorSlot", ArmorSlot.class),
                    data.getDouble(prefix + "defenceBonus"));
            case POTION -> ItemFactory.createPotion(name, level,
                    data.getDouble(prefix + "healthBonus"),
                    data.getInt(prefix + "quantity"));
        };
    }
}