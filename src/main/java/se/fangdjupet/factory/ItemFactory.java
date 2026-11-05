package se.fangdjupet.factory;

import se.fangdjupet.items.*;

public final class ItemFactory {

    private ItemFactory() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // Specifika fabriksmetoder (nya)
    public static Weapon createWeapon(String name, int level, WeaponType weaponType, double bonusDamage) {
        return new Weapon(name, level, weaponType, bonusDamage);
    }

    public static Weapon createWeapon(String name, int level, WeaponType weaponType) {
        return new Weapon(name, level, weaponType, 0.0);
    }

    public static Armor createArmor(String name, int level, ArmorSlot armorSlot, double defenceBonus) {
        return new Armor(name, level, armorSlot, defenceBonus);
    }

    public static Armor createArmor(String name, int level, ArmorSlot armorSlot) {
        return new Armor(name, level, armorSlot, 0.0);
    }

    public static Potion createPotion(String name, int level, double healthBonus, int quantity) {
        return new Potion(name, level, healthBonus, quantity);
    }

    public static Potion createPotion(String name, int level, double healthBonus) {
        return new Potion(name, level, healthBonus, 1);
    }


    public static Item createItem(ItemType type, String name, int level, double value) {
        return switch (type) {
            case WEAPON -> createWeapon(name, level, WeaponType.SWORD, value);
            case ARMOR  -> createArmor(name, level, ArmorSlot.CHESTPLATE, value);
            case POTION -> createPotion(name, level, value);
        };
    }
}