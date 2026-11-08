package se.fangdjupet.items;

/**
 * Where a piece of armor is worn, and its base defence.
 * The hero can wear one piece of armor per slot.
 */

public enum ArmorSlot {
    HELMET(2.0),
    CHESTPLATE(3.0),
    LEGGINGS(2.0),
    SHIELD(3.0);

    private final double baseDefence;

    ArmorSlot(double baseDefence) {
        this.baseDefence = baseDefence;
    }

    public double getBaseDefence() {
        return baseDefence;
    }
}
