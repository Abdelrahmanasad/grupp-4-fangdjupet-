package se.fangdjupet.factory;

import se.fangdjupet.items.*;

public class ItemFactory {

    public static Item createItem(ItemType type, String name, int level, double value) {
        switch (type) {
            case WEAPON:
                return new Weapon(name, level, value);
            case ARMOR:
                return new Armor(name, level, value);
            case POTION:
                return new Potion(name, level, value);
        }
        throw new IllegalArgumentException("Unknown ItemType: " + type);
    }
}