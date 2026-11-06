package se.fangdjupet.items;

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
