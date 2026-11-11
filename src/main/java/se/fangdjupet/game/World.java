package se.fangdjupet.game;

import se.fangdjupet.combat.Enemy;
import se.fangdjupet.combat.Hero;
import se.fangdjupet.factory.EnemyFactory;
import se.fangdjupet.factory.EnemyType;
import se.fangdjupet.factory.ItemCatalog;
import se.fangdjupet.items.Armor;
import se.fangdjupet.items.Inventory;
import se.fangdjupet.items.Item;
import se.fangdjupet.items.Potion;
import se.fangdjupet.items.Weapon;

import java.util.List;
import java.util.Optional;

/**
 * The content of Fangdjupet: the hero's starting equipment, which enemy waits
 * in each room and which reward the hero finds after each victory.
 * <p>
 * Everything is created through the factories, so all game balance lives in
 * this one class and the game loop in {@link Game} stays free of numbers.
 */
public final class World {

    public static final double HERO_MAX_HEALTH = 100.0;
    public static final int INVENTORY_CAPACITY = 10;

    private World() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Creates a new game: a hero with starting equipment in the first room.
     *
     * @param heroName the hero's name
     * @return the new game state
     */
    public static GameState newGame(String heroName) {
        Hero hero = new Hero(heroName, HERO_MAX_HEALTH, HERO_MAX_HEALTH);
        Inventory inventory = new Inventory(INVENTORY_CAPACITY);

        Weapon sword = ItemCatalog.Weapons.createRustySword();
        Armor cap = ItemCatalog.Armors.createLeatherCap();
        Armor shield = ItemCatalog.Armors.createWoodenShield();
        Potion potions = ItemCatalog.Potions.createSmallHealthPotion();
        potions.addQuantity(1);

        inventory.addItem(sword);
        inventory.addItem(cap);
        inventory.addItem(shield);
        inventory.addItem(potions);

        GameState state = new GameState(hero, inventory, new Dungeon(createEnemies()));
        state.equip(sword);
        state.equip(cap);
        state.equip(shield);
        return state;
    }

    /**
     * Creates fresh enemies, one per room, in the order the hero meets them.
     * Also used when a saved game is loaded.
     *
     * @return the enemies of the dungeon, the boss last
     */
    public static List<Enemy> createEnemies() {
        return List.of(
                EnemyFactory.createEnemy(EnemyType.GOBLIN, 1),
                EnemyFactory.createEnemy(EnemyType.SKELETON, 1),
                EnemyFactory.createEnemy(EnemyType.GOBLIN, 2),
                EnemyFactory.createEnemy(EnemyType.MAGE, 2),
                EnemyFactory.createEnemy(EnemyType.TROLL, 3));
    }

    /**
     * @param roomIndex the room that was just cleared, starting at 0
     * @return the item found in that room, or empty if the room has no reward
     */
    public static Optional<Item> rewardAfterRoom(int roomIndex) {
        return switch (roomIndex) {
            case 0 -> Optional.of(ItemCatalog.Weapons.createBattleAxe());
            case 1 -> Optional.of(ItemCatalog.Armors.createIronChestplate());
            case 2 -> Optional.of(ItemCatalog.Potions.createLargeHealthPotion());
            case 3 -> Optional.of(ItemCatalog.Weapons.createLongbow());
            default -> Optional.empty();
        };
    }
}