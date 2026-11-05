package se.fangdjupet.factory;

import se.fangdjupet.items.*;

public final class ItemFactory {

    private ItemFactory() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // Weapon factory methods
    public static Weapon createWeapon(String name, int level, WeaponType weaponType, double bonusDamage) {
        validateName(name);
        validateLevel(level);
        if (weaponType == null) {
            throw new IllegalArgumentException("WeaponType cannot be null");
        }
        if (bonusDamage < 0) {
            throw new IllegalArgumentException("Bonus damage cannot be negative");
        }

        return new Weapon(name, level, weaponType, bonusDamage);
    }

    public static Weapon createWeapon(String name, int level, WeaponType weaponType) {
        return createWeapon(name, level, weaponType, 0.0);
    }

    // Armor factory methods
    public static Armor createArmor(String name, int level, ArmorSlot armorSlot, double defenceBonus) {
        validateName(name);
        validateLevel(level);
        if (armorSlot == null) {
            throw new IllegalArgumentException("ArmorSlot cannot be null");
        }
        if (defenceBonus < 0) {
            throw new IllegalArgumentException("Defence bonus cannot be negative");
        }

        return new Armor(name, level, armorSlot, defenceBonus);
    }

    public static Armor createArmor(String name, int level, ArmorSlot armorSlot) {
        return createArmor(name, level, armorSlot, 0.0);
    }

    // Potion factory methods
    public static Potion createPotion(String name, int level, double healthBonus, int quantity) {
        validateName(name);
        validateLevel(level);
        if (healthBonus <= 0) {
            throw new IllegalArgumentException("Health bonus must be greater than zero");
        }
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity must be at least 1");
        }

        return new Potion(name, level, healthBonus, quantity);
    }

    public static Potion createPotion(String name, int level, double healthBonus) {
        return createPotion(name, level, healthBonus, 1);
    }


    public static Item createItem(ItemType type, String name, int level, double value) {
        if (type == null) {
            throw new IllegalArgumentException("ItemType cannot be null");
        }

        return switch (type) {
            case WEAPON -> createWeapon(name, level, WeaponType.SWORD, value);
            case ARMOR  -> createArmor(name, level, ArmorSlot.CHESTPLATE, value);
            case POTION -> createPotion(name, level, value);
        };
    }


    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Item name cannot be null or empty");
        }
    }

    private static void validateLevel(int level) {
        if (level < 1) {
            throw new IllegalArgumentException("Item level must be at least 1");
        }
    }
}