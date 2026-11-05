package se.fangdjupet.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.fangdjupet.combat.Enemy;
import se.fangdjupet.combat.Hero;
import se.fangdjupet.items.Armor;
import se.fangdjupet.items.Inventory;
import se.fangdjupet.items.Potion;
import se.fangdjupet.items.Weapon;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameStateTest {

    private static final double DELTA = 0.001;

    private Hero hero;
    private Inventory inventory;
    private Enemy goblin;
    private GameState state;

    @BeforeEach
    void setUp() {
        hero = new Hero("Knight", 100, 100);
        inventory = new Inventory(5);
        goblin = new Enemy("Goblin", 30, 30);
        state = new GameState(hero, inventory, new Dungeon(List.of(goblin)));
    }

    @Test
    void attackDamageWithoutWeaponIsBaseDamage() {
        assertEquals(GameState.BASE_ATTACK_DAMAGE, state.getAttackDamage(), DELTA);
        assertTrue(state.getEquippedWeapon().isEmpty());
    }

    @Test
    void equippedWeaponAddsDamage() {
        Weapon sword = new Weapon("Sword", 1, 10);
        inventory.addItem(sword);

        state.equip(sword);

        assertSame(sword, state.getEquippedWeapon().orElseThrow());
        assertEquals(GameState.BASE_ATTACK_DAMAGE + 10, state.getAttackDamage(), DELTA);
    }

    @Test
    void equippingNewWeaponReplacesOldOne() {
        Weapon sword = new Weapon("Sword", 1, 10);
        Weapon axe = new Weapon("Axe", 2, 14);
        inventory.addItem(sword);
        inventory.addItem(axe);

        state.equip(sword);
        state.equip(axe);

        assertSame(axe, state.getEquippedWeapon().orElseThrow());
    }

    @Test
    void defenceComesFromEquippedArmor() {
        Armor shield = new Armor("Shield", 1, 4);
        inventory.addItem(shield);

        assertEquals(0, state.getDefence(), DELTA);
        state.equip(shield);

        assertEquals(4, state.getDefence(), DELTA);
    }

    @Test
    void cannotEquipItemThatIsNotInInventory() {
        Weapon sword = new Weapon("Sword", 1, 10);

        assertThrows(IllegalArgumentException.class, () -> state.equip(sword));
        assertTrue(state.getEquippedWeapon().isEmpty());
    }

    @Test
    void drinkingPotionHealsHeroAndUsesPotion() {
        Potion potion = new Potion("Health Potion", 1, 25, 2);
        inventory.addItem(potion);
        hero.takeDamage(40);

        double healed = state.drinkPotion(potion);

        assertEquals(25, healed, DELTA);
        assertEquals(85, hero.getHealth(), DELTA);
        assertEquals(1, potion.getQuantity());
    }

    @Test
    void lastPotionIsRemovedFromInventory() {
        Potion potion = new Potion("Health Potion", 1, 25);
        inventory.addItem(potion);
        hero.takeDamage(40);

        state.drinkPotion(potion);

        assertFalse(inventory.getItems().contains(potion));
    }

    @Test
    void potionNeverHealsAboveMaxHealth() {
        Potion potion = new Potion("Health Potion", 1, 25);
        inventory.addItem(potion);
        hero.takeDamage(10);

        double healed = state.drinkPotion(potion);

        assertEquals(10, healed, DELTA);
        assertEquals(100, hero.getHealth(), DELTA);
    }

    @Test
    void gameOverWhenHeroDies() {
        assertFalse(state.isGameOver());

        hero.takeDamage(100);

        assertTrue(state.isGameOver());
        assertFalse(state.isVictory());
    }

    @Test
    void victoryWhenDungeonIsCleared() {
        goblin.takeDamage(30);
        state.getDungeon().advance();

        assertTrue(state.isVictory());
    }

    @Test
    void nullArgumentsThrowException() {
        Dungeon dungeon = new Dungeon(List.of(goblin));

        assertThrows(NullPointerException.class, () -> new GameState(null, inventory, dungeon));
        assertThrows(NullPointerException.class, () -> new GameState(hero, null, dungeon));
        assertThrows(NullPointerException.class, () -> new GameState(hero, inventory, null));
    }
}