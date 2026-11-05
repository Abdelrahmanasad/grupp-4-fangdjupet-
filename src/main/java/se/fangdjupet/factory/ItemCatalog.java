package se.fangdjupet.factory;

import se.fangdjupet.items.*;

import java.util.List;

public class ItemCatalog {

    private ItemCatalog() {}

    // --- WEAPONS ---
    public static class Weapons {
        public static Weapon createRustySword() {
            return ItemFactory.createWeapon("Rusty Sword", 1, WeaponType.SWORD, 0.0);
        }

        public static Weapon createBattleAxe() {
            return ItemFactory.createWeapon("Battle Axe", 2, WeaponType.AXE, 4.0);
        }

        public static Weapon createLongbow() {
            return ItemFactory.createWeapon("Longbow", 2, WeaponType.BOW, 3.0);
        }

        public static Weapon createWizardStaff() {
            return ItemFactory.createWeapon("Wizard Staff", 3, WeaponType.STAFF, 8.0);
        }

        public static List<Weapon> getAll() {
            return List.of(createRustySword(), createBattleAxe(), createLongbow(), createWizardStaff());
        }
    }

    // --- ARMORS ---
    public static class Armors {
        public static Armor createLeatherCap() {
            return ItemFactory.createArmor("Leather Cap", 1, ArmorSlot.HELMET, 1.0);
        }

        public static Armor createIronChestplate() {
            return ItemFactory.createArmor("Iron Chestplate", 2, ArmorSlot.CHESTPLATE, 5.0);
        }

        public static Armor createChainmailLeggings() {
            return ItemFactory.createArmor("Chainmail Leggings", 2, ArmorSlot.LEGGINGS, 3.0);
        }

        public static Armor createWoodenShield() {
            return ItemFactory.createArmor("Wooden Shield", 1, ArmorSlot.SHIELD, 2.0);
        }

        public static List<Armor> getAll() {
            return List.of(createLeatherCap(), createIronChestplate(), createChainmailLeggings(), createWoodenShield());
        }
    }

    // --- POTIONS ---
    public static class Potions {
        public static Potion createSmallHealthPotion() {
            return ItemFactory.createPotion("Small Health Potion", 1, 25.0, 1);
        }

        public static Potion createLargeHealthPotion() {
            return ItemFactory.createPotion("Large Health Potion", 3, 75.0, 1);
        }

        public static List<Potion> getAll() {
            return List.of(createSmallHealthPotion(), createLargeHealthPotion());
        }
    }
}