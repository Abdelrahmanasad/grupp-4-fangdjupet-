package se.fangdjupet.game;

import se.fangdjupet.combat.Hero;
import se.fangdjupet.items.Armor;
import se.fangdjupet.items.Inventory;
import se.fangdjupet.items.Item;
import se.fangdjupet.items.Potion;
import se.fangdjupet.items.Weapon;

import java.util.Objects;
import java.util.Optional;

/**
 * Everything that describes an ongoing game: the hero, the inventory,
 * the equipped items and the progress through the dungeon.
 * <p>
 * This is the object that is written to and read from the save file.
 * It also connects the {@code combat} and {@code items} packages, so that
 * they never have to know about each other.
 */
public class GameState {

    /**
     * Damage the hero deals without a weapon.
     */
    public static final double BASE_ATTACK_DAMAGE = 5.0;

    private final Hero hero;
    private final Inventory inventory;
    private final Dungeon dungeon;
    private Weapon equippedWeapon;
    private Armor equippedArmor;

    /**
     * Creates a game state.
     *
     * @param hero      the player's hero
     * @param inventory the hero's inventory
     * @param dungeon   the dungeon the hero is exploring
     * @throws NullPointerException if any argument is null
     */
    public GameState(Hero hero, Inventory inventory, Dungeon dungeon) {
        this.hero = Objects.requireNonNull(hero, "Hero cannot be null");
        this.inventory = Objects.requireNonNull(inventory, "Inventory cannot be null");
        this.dungeon = Objects.requireNonNull(dungeon, "Dungeon cannot be null");
    }

    /**
     * Equips a weapon from the inventory. Replaces the current weapon.
     *
     * @param weapon the weapon to equip
     * @throws IllegalArgumentException if the weapon is not in the inventory
     */
    public void equip(Weapon weapon) {
        requireInInventory(weapon);
        equippedWeapon = weapon;
    }

    /**
     * Equips armor from the inventory. Replaces the current armor.
     *
     * @param armor the armor to equip
     * @throws IllegalArgumentException if the armor is not in the inventory
     */
    public void equip(Armor armor) {
        requireInInventory(armor);
        equippedArmor = armor;
    }

    /**
     * @return the equipped weapon, or an empty Optional if the hero fights bare-handed
     */
    public Optional<Weapon> getEquippedWeapon() {
        return Optional.ofNullable(equippedWeapon);
    }

    /**
     * @return the equipped armor, or an empty Optional if the hero wears none
     */
    public Optional<Armor> getEquippedArmor() {
        return Optional.ofNullable(equippedArmor);
    }

    /**
     * @return base damage plus the damage of the equipped weapon
     */
    public double getAttackDamage() {
        return BASE_ATTACK_DAMAGE + getEquippedWeapon().map(Weapon::getDamage).orElse(0.0);
    }

    /**
     * @return defence from the equipped armor, 0 without armor
     */
    public double getDefence() {
        return getEquippedArmor().map(Armor::getDefenceBonus).orElse(0.0);
    }

    /**
     * Drinks a potion: heals the hero and uses up one potion from the inventory.
     *
     * @param potion the potion to drink
     * @return how much health was actually restored (never above max health)
     * @throws IllegalArgumentException if the potion is not in the inventory
     */
    public double drinkPotion(Potion potion) {
        requireInInventory(potion);

        double healthBefore = hero.getHealth();
        hero.heal(potion.getHealthBonus());
        potion.use(inventory);

        return hero.getHealth() - healthBefore;
    }

    /**
     * @return true if the hero has died
     */
    public boolean isGameOver() {
        return !hero.isAlive();
    }

    /**
     * @return true if the hero is alive and every room is cleared
     */
    public boolean isVictory() {
        return hero.isAlive() && dungeon.isCleared();
    }

    public Hero getHero() {
        return hero;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    // Shared check for equip() and drinkPotion()
    private void requireInInventory(Item item) {
        Objects.requireNonNull(item, "Item cannot be null");

        if (!inventory.getItems().contains(item)) {
            throw new IllegalArgumentException(item.getName() + " is not in the inventory");
        }
    }
}