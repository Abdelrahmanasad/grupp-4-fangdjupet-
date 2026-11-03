package se.fangdjupet.combat;

/**
 * Representerar en fiende i spelet.
 * Ärver hälsa och skadehantering från AbstractCombatant.
 */
public class Enemy extends AbstractCombatant {

    /**
     * Skapar en fiende med namn, nuvarande hälsa och maximal hälsa.
     *
     * @param name fiendens namn
     * @param health nuvarande hälsa
     * @param maxHealth maximal hälsa
     */
    public Enemy(String name, double health, double maxHealth) {
        super(name, health, maxHealth);
    }
}