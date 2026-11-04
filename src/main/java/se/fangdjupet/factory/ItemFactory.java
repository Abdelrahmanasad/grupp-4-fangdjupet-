package se.fangdjupet.factory;

import se.fangdjupet.items.*;

public final class ItemFactory {

    private ItemFactory() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static Item createItem(ItemType type, String name, int level, double value) {
        return switch (type) {
            case WEAPON -> new Weapon(name, level, value);
            case ARMOR -> new Armor(name, level, value);
            case POTION -> new Potion(name, level, value);
        };
    }
}