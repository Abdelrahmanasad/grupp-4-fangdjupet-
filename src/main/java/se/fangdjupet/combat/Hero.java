package se.fangdjupet.combat;

/**
 * Representerar hjälten i spelet.
 * Ärver hälsa och skadehantering från AbstractCombatant.
 */
public class Hero extends AbstractCombatant {

    /**
     * Skapar en hjälte med namn, nuvarande hälsa och maximal hälsa.
     *
     * @param name hjältens namn
     * @param health nuvarande hälsa
     * @param maxHealth maximal hälsa
     */
    public Hero(String name, double health, double maxHealth) {
        super(name, health, maxHealth);
    }
}