package se.fangdjupet.items;

public enum WeaponType {
    SWORD(18.0),
    AXE(14.0),
    DAGGER(8.0),
    BOW(16.0),
    STAFF(12.0);

    private final double baseDamage;

    WeaponType(double baseDamage) {
        this.baseDamage = baseDamage;
    }

    public double getBaseDamage() {
        return baseDamage;
    }
}
