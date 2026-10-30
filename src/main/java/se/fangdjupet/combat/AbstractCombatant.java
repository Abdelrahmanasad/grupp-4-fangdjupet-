package se.fangdjupet.combat;

public abstract class AbstractCombatant implements Combatant {

    private final String name;
    private double health;
    private final double maxHealth;

    protected AbstractCombatant(String name, double health, double maxHealth) {
        if (maxHealth <= 0) {
            throw new IllegalArgumentException("Max health must be greater than 0");
        }

        if (health < 0 || health > maxHealth) {
            throw new IllegalArgumentException("Invalid health");
        }

        this.name = name;
        this.health = health;
        this.maxHealth = maxHealth;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getHealth() {
        return health;
    }

    @Override
    public void takeDamage(double amount) {

        if (amount < 0) {
            throw new IllegalArgumentException("Damage cannot be negative");
        }

        health = Math.max(0, health - amount);
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    public void heal(double amount) {

        if (amount < 0) {
            throw new IllegalArgumentException("Healing cannot be negative");
        }

        health = Math.min(maxHealth, health + amount);
    }


}