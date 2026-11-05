package se.fangdjupet.combat;

/**
 * Common base for heroes and enemies.
 * Handles name, health, damage and healing.
 */
public abstract class AbstractCombatant implements Combatant {

    private final String name;
    private double health;
    private final double maxHealth;

    /**
     * Creates a character with a name, current health and maximum health.
     *
     * @param name the character's name
     * @param health current health
     * @param maxHealth maximum health
     */
    protected AbstractCombatant(String name, double health, double maxHealth) {
        if (maxHealth <= 0) {
            throw new IllegalArgumentException(
                    "Max health must be greater than 0");
        }

        if (health < 0 || health > maxHealth) {
            throw new IllegalArgumentException("Invalid health");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }

        this.name = name;
        this.health = health;
        this.maxHealth = maxHealth;
    }

    /**
     * Gets the character's name.
     *
     * @return the character's name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Gets the character's current health.
     *
     * @return current health
     */
    @Override
    public double getHealth() {
        return health;
    }

    /**
     * Gets the character's maximum health.
     *
     * @return maximum health
     */
    @Override
    public double getMaxHealth() {
        return maxHealth;
    }

    /**
     * Reduces health without going below zero.
     *
     * @param amount how much damage the character takes
     */
    @Override
    public void takeDamage(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Damage cannot be negative");
        }

        health = Math.max(0, health - amount);
    }

    /**
     * Checks if the character is still alive.
     *
     * @return true if health is above zero, otherwise false
     */
    @Override
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * Increases health without going above maxHealth.
     *
     * @param amount how much health to restore
     */
    public void heal(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Healing cannot be negative");
        }

        health = Math.min(maxHealth, health + amount);
    }
}