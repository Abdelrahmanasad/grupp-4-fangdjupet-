package se.fangdjupet.game.save;

import se.fangdjupet.combat.Enemy;
import se.fangdjupet.combat.Hero;
import se.fangdjupet.game.Dungeon;
import se.fangdjupet.game.GameState;
import se.fangdjupet.items.Armor;
import se.fangdjupet.items.ArmorSlot;
import se.fangdjupet.items.Inventory;
import se.fangdjupet.items.Item;
import se.fangdjupet.items.Weapon;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Saves an ongoing game to a file and loads it again.
 * <p>
 * What is saved: the hero (name and health), the inventory with all items,
 * the equipped weapon and armor, and which room the hero has reached.
 * The game is saved between battles, so enemies are not saved: when loading,
 * the caller passes in fresh enemies and the hero continues from the saved room.
 */
public class SaveManager {

    /**
     * Version of the file format. Increase it if the format changes,
     * so that old save files are rejected with a clear message.
     */
    static final int FORMAT_VERSION = 1;

    private final Path saveFile;

    /**
     * @param saveFile the file to save to and load from
     */
    public SaveManager(Path saveFile) {
        this.saveFile = Objects.requireNonNull(saveFile, "Save file cannot be null");
    }

    /**
     * @return true if there is a saved game to continue
     */
    public boolean hasSave() {
        return Files.exists(saveFile);
    }

    /**
     * Saves the game. An existing save is replaced.
     *
     * @param state the game to save
     * @throws SaveGameException if the file cannot be written
     */
    public void save(GameState state) throws SaveGameException {
        Objects.requireNonNull(state, "Game state cannot be null");

        SaveData data = new SaveData();
        data.put("version", FORMAT_VERSION);

        Hero hero = state.getHero();
        data.put("hero.name", hero.getName());
        data.put("hero.health", hero.getHealth());
        data.put("hero.maxHealth", hero.getMaxHealth());

        data.put("dungeon.room", state.getDungeon().getCurrentRoom());

        Inventory inventory = state.getInventory();
        List<Item> items = inventory.getItems();
        data.put("inventory.capacity", inventory.getMaxCapacity());
        data.put("inventory.size", items.size());

        for (int i = 0; i < items.size(); i++) {
            ItemSerializer.write(items.get(i), "item." + i + ".", data);
        }

        // Equipped items are saved as their position in the inventory
        state.getEquippedWeapon()
                .ifPresent(weapon -> data.put("equipped.weapon", items.indexOf(weapon)));

        for (Map.Entry<ArmorSlot, Armor> entry : state.getEquippedArmor().entrySet()) {
            data.put("equipped.armor." + entry.getKey().name(), items.indexOf(entry.getValue()));
        }

        data.writeTo(saveFile);
    }

    /**
     * Loads a saved game.
     *
     * @param enemies fresh enemies for the dungeon, in the order they are met
     * @return the loaded game, with the hero in the saved room
     * @throws SaveGameException if there is no save file, or it is broken or invalid
     */
    public GameState load(List<Enemy> enemies) throws SaveGameException {
        Objects.requireNonNull(enemies, "Enemies cannot be null");

        SaveData data = SaveData.readFrom(saveFile);

        int version = data.getInt("version");
        if (version != FORMAT_VERSION) {
            throw new SaveGameException("Unsupported save file version: " + version);
        }

        try {
            Hero hero = new Hero(
                    data.getString("hero.name"),
                    data.getDouble("hero.health"),
                    data.getDouble("hero.maxHealth"));

            Inventory inventory = new Inventory(data.getInt("inventory.capacity"));
            List<Item> items = new ArrayList<>();
            int itemCount = data.getInt("inventory.size");

            for (int i = 0; i < itemCount; i++) {
                Item item = ItemSerializer.read("item." + i + ".", data);

                if (!inventory.addItem(item)) {
                    throw new SaveGameException("Save file has more items than the inventory can hold");
                }
                items.add(item);
            }

            Dungeon dungeon = new Dungeon(enemies, data.getInt("dungeon.room"));
            GameState state = new GameState(hero, inventory, dungeon);

            if (data.has("equipped.weapon")) {
                state.equip(itemAt(items, data.getInt("equipped.weapon"), Weapon.class));
            }

            for (ArmorSlot slot : ArmorSlot.values()) {
                String key = "equipped.armor." + slot.name();
                if (data.has(key)) {
                    state.equip(itemAt(items, data.getInt(key), Armor.class));
                }
            }

            return state;
        } catch (IllegalArgumentException e) {
            // Thrown by the constructors' validation, e.g. health above max health
            throw new SaveGameException("Save file contains invalid values: " + e.getMessage(), e);
        }
    }

    // Generic helper: returns the item at the index if it has the expected type
    private static <T extends Item> T itemAt(List<Item> items, int index, Class<T> type)
            throws SaveGameException {
        if (index < 0 || index >= items.size() || !type.isInstance(items.get(index))) {
            throw new SaveGameException("Invalid equipped " + type.getSimpleName() + " in save file");
        }

        return type.cast(items.get(index));
    }
}