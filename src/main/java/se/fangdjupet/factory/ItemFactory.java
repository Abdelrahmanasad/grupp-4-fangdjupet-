package se.fangdjupet.factory;

import se.fangdjupet.items.*;

/**
 * Factory class for creating different types of items.
 */
public final class ItemFactory {

    private ItemFactory() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Creates a new weapon with bonus damage.
     */
    public static Weapon createWeapon(String name, int level, WeaponType weaponType, double bonusDamage) {
        return new Weapon(name, level, weaponType, bonusDamage);
    }

    /**
     * Creates a new weapon without bonus damage.
     */
    public static Weapon createWeapon(String name, int level, WeaponType weaponType) {
        return createWeapon(name, level, weaponType, 0.0);
    }

    /**
     * Creates a new armor piece with bonus defence.
     */
    public static Armor createArmor(String name, int level, ArmorSlot armorSlot, double defenceBonus) {
        return new Armor(name, level, armorSlot, defenceBonus);
    }

    /**
     * Creates a new armor piece without bonus defence.
     */
    public static Armor createArmor(String name, int level, ArmorSlot armorSlot) {
        return createArmor(name, level, armorSlot, 0.0);
    }

    /**
     * Creates a new potion with a specified quantity.
     */
    public static Potion createPotion(String name, int level, double healthBonus, int quantity) {
        return new Potion(name, level, healthBonus, quantity);
    }

    /**
     * Creates a new potion with a default quantity of 1.
     */
    public static Potion createPotion(String name, int level, double healthBonus) {
        return createPotion(name, level, healthBonus, 1);
    }
}