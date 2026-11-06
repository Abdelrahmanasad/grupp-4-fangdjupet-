package se.fangdjupet.factory;

import se.fangdjupet.items.*;

import java.util.List;

/**
 * Catalog of predefined default items in the game.
 */
public final class ItemCatalog {

    private ItemCatalog() {}

    /**
     * Catalog of predefined weapons.
     */
    public static class Weapons {

        /**
         * Creates a Rusty Sword.
         */
        public static Weapon createRustySword() {
            return ItemFactory.createWeapon("Rusty Sword", 1, WeaponType.SWORD, 0.0);
        }

        /**
         * Creates a Battle Axe.
         */
        public static Weapon createBattleAxe() {
            return ItemFactory.createWeapon("Battle Axe", 2, WeaponType.AXE, 2.0);
        }

        /**
         * Creates a Longbow.
         */
        public static Weapon createLongbow() {
            return ItemFactory.createWeapon("Longbow", 2, WeaponType.BOW, 1.5);
        }

        /**
         * Creates a Wizard Staff.
         */
        public static Weapon createWizardStaff() {
            return ItemFactory.createWeapon("Wizard Staff", 3, WeaponType.STAFF, 3.0);
        }

        /**
         * Gets a list of all predefined weapons.
         */
        public static List<Weapon> getAll() {
            return List.of(createRustySword(), createBattleAxe(), createLongbow(), createWizardStaff());
        }
    }

    /**
     * Catalog of predefined armors.
     */
    public static class Armors {

        /**
         * Creates a Leather Cap.
         */
        public static Armor createLeatherCap() {
            return ItemFactory.createArmor("Leather Cap", 1, ArmorSlot.HELMET, 0.0);
        }

        /**
         * Creates an Iron Chestplate.
         */
        public static Armor createIronChestplate() {
            return ItemFactory.createArmor("Iron Chestplate", 2, ArmorSlot.CHESTPLATE, 2.0);
        }

        /**
         * Creates Chainmail Leggings.
         */
        public static Armor createChainmailLeggings() {
            return ItemFactory.createArmor("Chainmail Leggings", 2, ArmorSlot.LEGGINGS, 1.0);
        }

        /**
         * Creates a Wooden Shield.
         */
        public static Armor createWoodenShield() {
            return ItemFactory.createArmor("Wooden Shield", 1, ArmorSlot.SHIELD, 1.0);
        }

        /**
         * Gets a list of all predefined armors.
         */
        public static List<Armor> getAll() {
            return List.of(createLeatherCap(), createIronChestplate(), createChainmailLeggings(), createWoodenShield());
        }
    }

    /**
     * Catalog of predefined potions.
     */
    public static class Potions {

        /**
         * Creates a Small Health Potion.
         */
        public static Potion createSmallHealthPotion() {
            return ItemFactory.createPotion("Small Health Potion", 1, 25.0, 1);
        }

        /**
         * Creates a Large Health Potion.
         */
        public static Potion createLargeHealthPotion() {
            return ItemFactory.createPotion("Large Health Potion", 3, 75.0, 1);
        }

        /**
         * Gets a list of all predefined potions.
         */
        public static List<Potion> getAll() {
            return List.of(createSmallHealthPotion(), createLargeHealthPotion());
        }
    }
}