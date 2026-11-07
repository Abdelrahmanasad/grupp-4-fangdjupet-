package se.fangdjupet.items;

import java.util.Objects;

/** * A stack of healing potions. Using one lowers the quantity,
 * * and the last one is removed from the inventory. */

public class Potion extends Item {
    private final double healthBonus;
    private int quantity;

    public Potion(String name, int level, double healthBonus, int quantity) {
        super(name, level);
        if (healthBonus <= 0) {
            throw new IllegalArgumentException("Health bonus must be greater than 0");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be at least 1");
        }
        this.healthBonus = healthBonus;
        this.quantity = quantity;
    }

    public Potion(String name, int level, double healthBonus) {
        this(name, level, healthBonus, 1);
    }

    public double getHealthBonus() {
        return healthBonus;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount to add must be greater than 0");
        }
        this.quantity += amount;
    }


    public boolean use(Inventory inventory) {
        Objects.requireNonNull(inventory, "Inventory cannot be null");
        if (quantity > 0) {
            quantity--;
        }

        if (quantity == 0) {
            inventory.removeItem(this);
            return false;
        }

        return true;
    }

    @Override
    public ItemType getType() {
        return ItemType.POTION;
    }

    @Override
    public String getDescription() {
        return "Potion: " + getName() + " (Level " + getLevel() + ", x" + quantity + ") - Heals: +" + healthBonus + " HP";
    }
}