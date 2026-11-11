package se.fangdjupet.game;

import se.fangdjupet.combat.Battle;
import se.fangdjupet.combat.Enemy;
import se.fangdjupet.combat.Hero;
import se.fangdjupet.core.EventBus;
import se.fangdjupet.core.EventType;
import se.fangdjupet.game.save.SaveGameException;
import se.fangdjupet.game.save.SaveManager;
import se.fangdjupet.items.Armor;
import se.fangdjupet.items.ArmorSlot;
import se.fangdjupet.items.Item;
import se.fangdjupet.items.Potion;
import se.fangdjupet.items.Weapon;

import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

/**
 * The game engine: the main menu loop and the battles room by room.
 * <p>
 * Game only connects the other parts: {@link ConsoleUi} for input and output,
 * {@link Battle} for the fights, {@link SaveManager} for saving and the
 * {@link EventBus} for everything that happens. It never prints game events
 * itself; the listeners ({@link ConsoleEventLogger}, {@link EventHistory}) do that.
 */
public class Game {

    /**
     * Where the game is saved when started from {@code Main}.
     */
    public static final Path DEFAULT_SAVE_FILE = Path.of("savegame.txt");

    private static final int ATTACK = 1;
    private static final int DRINK_POTION = 2;

    private final ConsoleUi ui;
    private final SaveManager saveManager;
    private final EventBus eventBus = new EventBus();
    private final EventHistory history = new EventHistory();

    // null until a game is started or loaded
    private GameState state;

    /**
     * @param in       where the player's input comes from, e.g. {@code System.in}
     * @param out      where everything is printed, e.g. {@code System.out}
     * @param saveFile the file to save to and load from
     */
    public Game(InputStream in, PrintStream out, Path saveFile) {
        this.ui = new ConsoleUi(in, out);
        this.saveManager = new SaveManager(saveFile);

        // Observer pattern: two listeners react to every event
        eventBus.subscribe(new ConsoleEventLogger(out));
        eventBus.subscribe(history);
    }

    /**
     * Runs the main menu until the player chooses to exit.
     */
    public void run() {
        ui.showTitle("Fangdjupet");

        boolean running = true;
        while (running) {
            ui.showMenu();
            running = handle(ui.askMenuOption());
        }

        ui.println("Goodbye!");
    }

    // Returns false when the game should stop
    private boolean handle(MenuOption option) {
        switch (option) {
            case NEW_GAME -> startNewGame();
            case CONTINUE -> continueGame();
            case SHOW_EQUIPMENT -> {
                if (hasGame()) {
                    showEquipment();
                }
            }
            case FIGHT -> {
                if (hasGame()) {
                    fightNextEnemy();
                }
            }
            case SAVE -> {
                if (hasGame()) {
                    saveGame();
                }
            }
            case EXIT -> {
                return false;
            }
        }
        return true;
    }

    private boolean hasGame() {
        if (state == null) {
            ui.println("Start a new adventure first (1) or continue a saved one (2).");
            return false;
        }
        return true;
    }

    private void startNewGame() {
        String name = ui.askText("Name your hero: ");
        state = World.newGame(name);
        history.clear();

        eventBus.publish(EventType.GAME_STARTED, name + " enters Fangdjupet. "
                + state.getDungeon().getRoomCount() + " rooms wait below.");
    }

    private void continueGame() {
        if (!saveManager.hasSave()) {
            ui.println("No saved game found.");
            return;
        }

        try {
            state = saveManager.load(World.createEnemies());
            history.clear();
            eventBus.publish(EventType.GAME_LOADED, "Welcome back, " + state.getHero().getName()
                    + "! You are in room " + roomText() + ".");
        } catch (SaveGameException e) {
            ui.println("Could not load the game: " + e.getMessage());
        }
    }

    private void saveGame() {
        try {
            saveManager.save(state);
            eventBus.publish(EventType.GAME_SAVED, "Game saved.");
        } catch (SaveGameException e) {
            ui.println("Could not save the game: " + e.getMessage());
        }
    }

    private void showEquipment() {
        Hero hero = state.getHero();

        ui.println();
        ui.println(hero.getName() + "  HP " + format(hero.getHealth()) + "/" + format(hero.getMaxHealth())
                + "  |  Room " + roomText());
        ui.println("Attack: " + format(state.getAttackDamage())
                + " (" + state.getAttackStrategy().getClass().getSimpleName() + ")"
                + "  |  Defence: " + format(state.getDefence()));

        ui.println("Weapon: " + state.getEquippedWeapon().map(Item::getDescription).orElse("none"));
        for (ArmorSlot slot : ArmorSlot.values()) {
            ui.println("  " + slot + ": " + state.getEquippedArmor(slot).map(Item::getDescription).orElse("-"));
        }

        List<Item> items = state.getInventory().getItems();
        ui.println("Inventory (" + items.size() + "/" + state.getInventory().getMaxCapacity() + "):");
        for (Item item : items) {
            ui.println("  - " + item.getDescription());
        }
    }

    private void fightNextEnemy() {
        Dungeon dungeon = state.getDungeon();
        if (dungeon.isCleared()) {
            ui.println("The dungeon is already cleared.");
            return;
        }

        Hero hero = state.getHero();
        Enemy enemy = dungeon.getCurrentEnemy();
        eventBus.publish(EventType.ROOM_ENTERED, "Room " + roomText() + ": a " + enemy.getName() + " blocks the way!");

        Battle battle = new Battle(hero, enemy, eventBus);
        battle.start();

        while (!battle.isFinished()) {
            showBattleStatus(hero, enemy);

            boolean heroActed = switch (askBattleAction()) {
                case ATTACK -> {
                    battle.heroAttack(state.getAttackStrategy(), state.getAttackDamage());
                    yield true;
                }
                default -> drinkPotion();
            };

            if (heroActed && !battle.isFinished()) {
                battle.enemyAttack(enemy.getAttackStrategy(), enemy.getAttackDamage(), state.getDefence());
            }
        }

        if (hero.isAlive()) {
            winBattle();
        } else {
            endGame(EventType.GAME_OVER, "Game over. " + hero.getName() + " fell in room " + roomText() + ".");
        }
    }

    private void showBattleStatus(Hero hero, Enemy enemy) {
        ui.println();
        ui.println(hero.getName() + " " + format(hero.getHealth()) + "/" + format(hero.getMaxHealth()) + " HP"
                + "   vs   " + enemy.getName() + " " + format(enemy.getHealth()) + "/" + format(enemy.getMaxHealth()) + " HP");
    }

    private int askBattleAction() {
        String weaponName = state.getEquippedWeapon().map(Item::getName).orElse("bare hands");
        ui.println(ATTACK + ". Attack with " + weaponName
                + " (" + state.getAttackStrategy().getClass().getSimpleName() + ")");
        ui.println(DRINK_POTION + ". Drink a potion");
        return ui.askNumber("> ", ATTACK, DRINK_POTION);
    }

    // Returns true if a potion was drunk, which uses the hero's turn
    private boolean drinkPotion() {
        List<Potion> potions = state.getInventory().getItemsOfType(Potion.class);
        if (potions.isEmpty()) {
            ui.println("You have no potions!");
            return false;
        }

        Potion potion = potions.getFirst();
        if (potions.size() > 1) {
            for (int i = 0; i < potions.size(); i++) {
                ui.println((i + 1) + ". " + potions.get(i).getDescription());
            }
            potion = potions.get(ui.askNumber("Which potion? ", 1, potions.size()) - 1);
        }

        double healed = state.drinkPotion(potion);
        eventBus.publish(EventType.ITEM_USED, state.getHero().getName() + " drinks " + potion.getName()
                + " and heals " + format(healed) + " HP.");
        return true;
    }

    private void winBattle() {
        Dungeon dungeon = state.getDungeon();
        int clearedRoom = dungeon.getCurrentRoom();
        dungeon.advance();

        if (state.isVictory()) {
            endGame(EventType.VICTORY, state.getHero().getName() + " has cleared Fangdjupet!");
            return;
        }

        World.rewardAfterRoom(clearedRoom).ifPresent(this::collect);
        ui.println("Next up: room " + roomText() + ". Save your game (5) or fight on (4).");
    }

    private void collect(Item item) {
        if (!state.getInventory().addItem(item)) {
            ui.println("Your inventory is full, you leave the " + item.getName() + " behind.");
            return;
        }

        eventBus.publish(EventType.ITEM_FOUND, "Found " + item.getDescription() + "!");
        equipIfBetter(item);
    }

    // Pattern matching switch with guards: only equip what is better than the current item
    private void equipIfBetter(Item item) {
        switch (item) {
            case Weapon weapon when isBetter(weapon) -> {
                state.equip(weapon);
                eventBus.publish(EventType.ITEM_EQUIPPED, state.getHero().getName() + " equips " + weapon.getName() + ".");
            }
            case Armor armor when isBetter(armor) -> {
                state.equip(armor);
                eventBus.publish(EventType.ITEM_EQUIPPED, state.getHero().getName() + " equips " + armor.getName() + ".");
            }
            default -> {
                // Potions and worse items just stay in the inventory
            }
        }
    }

    private boolean isBetter(Weapon weapon) {
        return state.getEquippedWeapon()
                .map(current -> weapon.getTotalDamage() > current.getTotalDamage())
                .orElse(true);
    }

    private boolean isBetter(Armor armor) {
        return state.getEquippedArmor(armor.getArmorSlot())
                .map(current -> armor.getTotalDefence() > current.getTotalDefence())
                .orElse(true);
    }

    private void endGame(EventType type, String message) {
        eventBus.publish(type, message);
        ui.println("Enemies defeated: " + history.count(EventType.ENEMY_DEFEATED));
        state = null;
    }

    private String roomText() {
        Dungeon dungeon = state.getDungeon();
        int room = Math.min(dungeon.getCurrentRoom() + 1, dungeon.getRoomCount());
        return room + "/" + dungeon.getRoomCount();
    }

    private static String format(double value) {
        return String.format(Locale.ROOT, "%.1f", value);
    }
}