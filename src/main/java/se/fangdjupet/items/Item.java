package se.fangdjupet.items;

import java.util.Objects;

public abstract class Item {
    private final String name;
    private final int level;

    public Item(String name, int level) {
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

    public abstract ItemType getType();

    public abstract String getDescription();
}