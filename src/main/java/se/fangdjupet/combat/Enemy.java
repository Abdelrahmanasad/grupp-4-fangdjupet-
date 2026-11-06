package se.fangdjupet.combat;

/**
 * Represents an enemy in the game.
 * Inherits health and damage handling from AbstractCombatant.
 */
public class Enemy extends AbstractCombatant {

    /**
     * Creates an enemy with a name, current health and maximum health.
     *
     * @param name the enemy's name
     * @param health current health
     * @param maxHealth maximum health
     */
    public Enemy(String name, double health, double maxHealth) {
        super(name, health, maxHealth);
    }
}