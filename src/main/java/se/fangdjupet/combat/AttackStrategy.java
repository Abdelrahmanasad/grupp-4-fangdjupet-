package se.fangdjupet.combat;

/**
 * Gemensamt interface för olika typer av attacker.
 */
public interface AttackStrategy {

    /**
     * Räknar ut skadan utifrån attacktypen.
     *
     * @param baseDamage skadan innan attacktypen påverkar den
     * @return den beräknade skadan
     */
    double calculateDamage(double baseDamage);
}