package se.fangdjupet.factory;

import se.fangdjupet.items.Armor;
import se.fangdjupet.items.Item;
import se.fangdjupet.items.Potion;
import se.fangdjupet.items.Weapon;

public class ItemFactory {

    public static Item createItem(String type, String name, double level, double value) {
        return switch (type.toLowerCase()) {
            case "weapon" -> new Weapon(name, level, value);
            case "armor" -> new Armor(name, level, value);
            case "potion" -> new Potion(name, level, value);
            default -> throw new IllegalArgumentException("Okänd föremålstyp: " + type);
        };
    }
}