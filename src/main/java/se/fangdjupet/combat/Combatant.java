package se.fangdjupet.combat;

public interface Combatant {

    String getName();

    double getHealth();

    void takeDamage(double amount);

    boolean isAlive();
}