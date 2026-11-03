package se.fangdjupet.combat;

/**
 * Gemensam grund för hjältar och fiender.
 * Hanterar namn, hälsa, skada och läkning.
 */
public abstract class AbstractCombatant implements Combatant {

    private final String name;
    private double health;
    private final double maxHealth;

    /**
     * Skapar en karaktär med namn, nuvarande hälsa och maximal hälsa.
     *
     * @param name karaktärens namn
     * @param health nuvarande hälsa
     * @param maxHealth maximal hälsa
     */
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

    /**
     * Hämtar karaktärens namn.
     *
     * @return karaktärens namn
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Hämtar karaktärens nuvarande hälsa.
     *
     * @return nuvarande hälsa
     */
    @Override
    public double getHealth() {
        return health;
    }

    /**
     * Minskar hälsan utan att gå under noll.
     *
     * @param amount hur mycket skada karaktären tar
     */
    @Override
    public void takeDamage(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Damage cannot be negative");
        }

        health = Math.max(0, health - amount);
    }

    /**
     * Kontrollerar om karaktären fortfarande lever.
     *
     * @return true om hälsan är över noll, annars false
     */
    @Override
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * Ökar hälsan utan att gå över maxHealth.
     *
     * @param amount hur mycket hälsa som ska återställas
     */
    public void heal(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Healing cannot be negative");
        }

        health = Math.min(maxHealth, health + amount);
    }
}