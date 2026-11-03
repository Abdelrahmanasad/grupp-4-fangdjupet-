package se.fangdjupet.items;

public class Weapon extends Item {
    private double damage;

    public Weapon(String name, double level, int damage) {
        super(name, level);
        this.damage = damage;
    }

    public double getDamage() {
        return damage;
    }

    @Override
    public String getDescription() {
        return "Weapon: " + getName() + " (Level " + getLevel() + ") - Damage: +" + damage;
    }
}