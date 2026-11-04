package se.fangdjupet.items;

public class Weapon extends Item {
    private final double damage;

    public Weapon(String name, int level, double damage) {
        super(name, level);
        if (damage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative");
        }
        this.damage = damage;
    }

    public double getDamage() {
        return damage;
    }

    @Override
    public ItemType getType() {
        return ItemType.WEAPON;
    }

    @Override
    public String getDescription() {
        return "Weapon: " + getName() + " (Level " + getLevel() + ") - Damage: +" + damage;
    }
}