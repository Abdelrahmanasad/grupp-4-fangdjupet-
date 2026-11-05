package se.fangdjupet.items;

public enum ArmorSlot {
    HELMET(5.0),
    CHESTPLATE(10.0),
    LEGGINGS(8.0),
    SHIELD(15.0);

    private final double baseDefense;

    ArmorSlot(double baseDefense) {
        this.baseDefense = baseDefense;
    }

    public double getBaseDefense() {
        return baseDefense;
    }
}
