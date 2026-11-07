package se.fangdjupet.items;

import java.util.Objects;

/** * A weapon that increases the hero's damage. * Total damage = (base damage of the WeaponType + bonus damage)
 * * level multiplier. */

public class Weapon extends Item {
    private final WeaponType weaponType;
    private final double bonusDamage;

    public Weapon(String name, int level, WeaponType weaponType, double bonusDamage) {
        super(name, level);
        this.weaponType = Objects.requireNonNull(weaponType, "WeaponType cannot be null");
        if (bonusDamage < 0) {
            throw new IllegalArgumentException("Bonus damage cannot be negative");
        }
        this.bonusDamage = bonusDamage;
    }

    public Weapon(String name, int level, WeaponType weaponType) {
        this(name, level, weaponType, 0.0);
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public double getBonusDamage() {
        return bonusDamage;
    }

    public double getTotalDamage() {
        return (weaponType.getBaseDamage() + bonusDamage) * levelMultiplier();
    }

    @Override
    public ItemType getType() {
        return ItemType.WEAPON;
    }

    @Override
    public String getDescription() {
        return "Weapon (" + weaponType.name() + "): " + getName() + " (Level " + getLevel() + ") - Damage: " + getTotalDamage();
    }
}