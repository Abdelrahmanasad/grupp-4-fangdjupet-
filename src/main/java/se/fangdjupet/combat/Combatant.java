package se.fangdjupet.combat;

/**
 * Gemensamt interface för hjältar och fiender.
 */
public interface Combatant {

    /**
     * Hämtar karaktärens namn.
     *
     * @return karaktärens namn
     */
    String getName();

    /**
     * Hämtar karaktärens nuvarande hälsa.
     *
     * @return nuvarande hälsa
     */
    double getHealth();

    /**
     * Hämtar karaktärens maximala hälsa.
     *
     * @return maximal hälsa
     */
    double maxHealth();

    /**
     * Minskar karaktärens hälsa med angiven skada.
     *
     * @param amount hur mycket skada karaktären tar
     */
    void takeDamage(double amount);

    /**
     * Kontrollerar om karaktären fortfarande lever.
     *
     * @return true om karaktären lever, annars false
     */
    boolean isAlive();
}