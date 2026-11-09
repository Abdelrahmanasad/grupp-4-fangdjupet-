package se.fangdjupet.game.save;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import se.fangdjupet.combat.Enemy;
import se.fangdjupet.combat.Hero;
import se.fangdjupet.game.Dungeon;
import se.fangdjupet.game.GameState;
import se.fangdjupet.items.Armor;
import se.fangdjupet.items.ArmorSlot;
import se.fangdjupet.items.Inventory;
import se.fangdjupet.items.Potion;
import se.fangdjupet.items.Weapon;
import se.fangdjupet.items.WeaponType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SaveManagerTest {

    private static final double DELTA = 0.001;

    // JUnit creates a new empty folder for every test and deletes it afterwards
    @TempDir
    Path tempDir;

    private Path saveFile;
    private SaveManager saveManager;
    private Hero hero;
    private Inventory inventory;
    private GameState state;

    @BeforeEach
    void setUp() {
        saveFile = tempDir.resolve("save.properties");
        saveManager = new SaveManager(saveFile);

        hero = new Hero("Knight", 100, 100);
        inventory = new Inventory(10);
        state = new GameState(hero, inventory, new Dungeon(enemies()));
    }

    // Fresh enemies, as the game creates them when a save is loaded
    private static List<Enemy> enemies() {
        return List.of(new Enemy("Goblin", 30, 30), new Enemy("Troll", 50, 50), new Enemy("Dragon", 80, 80));
    }

    // Rewrites one line in the save file to simulate a broken or edited file
    private void replaceInSaveFile(String from, String to) throws IOException {
        String content = Files.readString(saveFile);
        Files.writeString(saveFile, content.replace(from, to));
    }

    @Test
    void hasSaveIsFalseBeforeSavingAndTrueAfter() throws SaveGameException {
        assertFalse(saveManager.hasSave());

        saveManager.save(state);

        assertTrue(saveManager.hasSave());
    }

    @Test
    void heroIsRestored() throws SaveGameException {
        hero.takeDamage(37.5);

        saveManager.save(state);
        GameState loaded = saveManager.load(enemies());

        assertEquals("Knight", loaded.getHero().getName());
        assertEquals(62.5, loaded.getHero().getHealth(), DELTA);
        assertEquals(100, loaded.getHero().getMaxHealth(), DELTA);
    }

    @Test
    void dungeonRoomIsRestored() throws SaveGameException {
        state.getDungeon().getCurrentEnemy().takeDamage(30);
        state.getDungeon().advance();

        saveManager.save(state);
        GameState loaded = saveManager.load(enemies());

        assertEquals(1, loaded.getDungeon().getCurrentRoom());
        assertEquals("Troll", loaded.getDungeon().getCurrentEnemy().getName());
    }

    @Test
    void inventoryIsRestoredInSameOrder() throws SaveGameException {
        inventory.addItem(new Weapon("Rusty Sword", 1, WeaponType.SWORD));
        inventory.addItem(new Potion("Small Health Potion", 1, 25, 2));
        inventory.addItem(new Armor("Leather Cap", 1, ArmorSlot.HELMET));

        saveManager.save(state);
        Inventory loaded = saveManager.load(enemies()).getInventory();

        assertEquals(10, loaded.getMaxCapacity());
        assertEquals(3, loaded.getItems().size());
        assertInstanceOf(Weapon.class, loaded.getItems().get(0));
        assertEquals(2, assertInstanceOf(Potion.class, loaded.getItems().get(1)).getQuantity());
        assertEquals("Leather Cap", loaded.getItems().get(2).getName());
    }

    @Test
    void equippedWeaponAndArmorAreRestored() throws SaveGameException {
        Weapon sword = new Weapon("Rusty Sword", 1, WeaponType.SWORD);
        Armor helmet = new Armor("Leather Cap", 1, ArmorSlot.HELMET);
        Armor shield = new Armor("Wooden Shield", 1, ArmorSlot.SHIELD, 1);
        inventory.addItem(sword);
        inventory.addItem(helmet);
        inventory.addItem(shield);
        state.equip(sword);
        state.equip(helmet);
        state.equip(shield);

        saveManager.save(state);
        GameState loaded = saveManager.load(enemies());

        assertEquals("Rusty Sword", loaded.getEquippedWeapon().orElseThrow().getName());
        assertEquals("Wooden Shield", loaded.getEquippedArmor(ArmorSlot.SHIELD).orElseThrow().getName());
        assertEquals(state.getAttackDamage(), loaded.getAttackDamage(), DELTA);
        assertEquals(state.getDefence(), loaded.getDefence(), DELTA);
    }

    @Test
    void nothingEquippedStaysEmpty() throws SaveGameException {
        inventory.addItem(new Weapon("Rusty Sword", 1, WeaponType.SWORD));

        saveManager.save(state);
        GameState loaded = saveManager.load(enemies());

        assertTrue(loaded.getEquippedWeapon().isEmpty());
        assertTrue(loaded.getEquippedArmor().isEmpty());
    }

    @Test
    void decimalsSurviveOnSwedishComputer() throws SaveGameException {
        Locale original = Locale.getDefault();
        try {
            // Swedish locale writes 33,3 instead of 33.3 if you use String.format
            Locale.setDefault(Locale.of("sv", "SE"));
            hero.takeDamage(66.7);

            saveManager.save(state);
            GameState loaded = saveManager.load(enemies());

            assertEquals(33.3, loaded.getHero().getHealth(), DELTA);
        } finally {
            Locale.setDefault(original);
        }
    }

    @Test
    void namesWithSpecialCharactersSurvive() throws SaveGameException {
        GameState special = new GameState(new Hero("Sir Örjan = the Brave", 100, 100),
                inventory, new Dungeon(enemies()));

        saveManager.save(special);

        assertEquals("Sir Örjan = the Brave", saveManager.load(enemies()).getHero().getName());
    }

    @Test
    void savingAgainReplacesOldSave() throws SaveGameException {
        saveManager.save(state);
        hero.takeDamage(50);

        saveManager.save(state);

        assertEquals(50, saveManager.load(enemies()).getHero().getHealth(), DELTA);
    }

    @Test
    void loadingWithoutSaveFileThrowsSaveGameException() {
        assertThrows(SaveGameException.class, () -> saveManager.load(enemies()));
    }

    @Test
    void brokenNumberThrowsSaveGameException() throws SaveGameException, IOException {
        saveManager.save(state);
        replaceInSaveFile("hero.health=100.0", "hero.health=lots");

        assertThrows(SaveGameException.class, () -> saveManager.load(enemies()));
    }

    @Test
    void invalidHealthThrowsSaveGameException() throws SaveGameException, IOException {
        saveManager.save(state);
        replaceInSaveFile("hero.health=100.0", "hero.health=500.0");

        assertThrows(SaveGameException.class, () -> saveManager.load(enemies()));
    }

    @Test
    void roomOutsideDungeonThrowsSaveGameException() throws SaveGameException, IOException {
        saveManager.save(state);
        replaceInSaveFile("dungeon.room=0", "dungeon.room=99");

        assertThrows(SaveGameException.class, () -> saveManager.load(enemies()));
    }

    @Test
    void unsupportedVersionThrowsSaveGameException() throws SaveGameException, IOException {
        saveManager.save(state);
        replaceInSaveFile("version=1", "version=999");

        assertThrows(SaveGameException.class, () -> saveManager.load(enemies()));
    }
}