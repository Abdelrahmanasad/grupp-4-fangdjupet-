package se.fangdjupet.items;

public class Potion extends Item {
    private final double healthBonus;

    public Potion(String name, int level, double healthBonus) {
        super(name, level);
        if (healthBonus < 0) {
            throw new IllegalArgumentException("Health bonus cannot be negative");
        }
        this.healthBonus = healthBonus;
    }

    public double getHealthBonus() {
        return healthBonus;
    }

    @Override
    public ItemType getType() {
        return ItemType.POTION;
    }

    @Override
    public String getDescription() {
        return "Potion: " + getName() + " (Level " + getLevel() + ") - Health: +" + healthBonus;
    }
}