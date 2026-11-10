package se.fangdjupet.items;

import java.util.Objects;

/**
 * Base class for everything the hero can carry: weapons, armor and potions.
 * Every item has a name and a level from 1 to 3.
 */

public abstract class Item {
    private final String name;
    private final int level;

    protected Item(String name, int level) {
        Objects.requireNonNull(name, "Name cannot be null");
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        if (level < 1 || level > 3) {
            throw new IllegalArgumentException("Level must be between 1 and 3");
        }

        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    /**
     * Makes higher-level items stronger: level 1 = x1.0, level 2 = x1.25, level 3 = x1.5.
     * Shared by Weapon and Armor so the formula exists in one place only.
     *
     * @return the stat multiplier for this item's level
     */
    protected double levelMultiplier() {
        return 1 + 0.25 * (level - 1);
    }

    public abstract ItemType getType();


    public abstract String getDescription();
}