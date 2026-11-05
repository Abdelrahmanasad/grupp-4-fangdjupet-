package se.fangdjupet.combat;

/**
 * Represents the hero in the game.
 * Inherits health and damage handling from AbstractCombatant.
 */
public class Hero extends AbstractCombatant {

    /**
     * Creates a hero with a name, current health and maximum health.
     *
     * @param name the hero's name
     * @param health current health
     * @param maxHealth maximum health
     */
    public Hero(String name, double health, double maxHealth) {
        super(name, health, maxHealth);
    }
}