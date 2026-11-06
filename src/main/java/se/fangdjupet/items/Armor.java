package se.fangdjupet.items;

import java.util.Objects;

public class Armor extends Item {
    private final ArmorSlot armorSlot;
    private final double defenceBonus;

    public Armor(String name, int level, ArmorSlot armorSlot, double defenceBonus) {
        super(name, level);
        this.armorSlot = Objects.requireNonNull(armorSlot, "ArmorSlot cannot be null");
        if (defenceBonus < 0) {
            throw new IllegalArgumentException("Defence bonus cannot be negative");
        }
        this.defenceBonus = defenceBonus;
    }

    public Armor(String name, int level, ArmorSlot armorSlot) {
        this(name, level, armorSlot, 0.0);
    }

    public ArmorSlot getArmorSlot() {
        return armorSlot;
    }

    public double getDefenceBonus() {
        return defenceBonus;
    }

    public double getTotalDefence() {
        double baseDefence = armorSlot.getBaseDefence();
        return (baseDefence + defenceBonus) * (1 + 0.25 * (getLevel() - 1));
    }

    @Override
    public ItemType getType() {
        return ItemType.ARMOR;
    }

    @Override
    public String getDescription() {
        return "Armor (" + armorSlot.name() + "): " + getName() + " (Level " + getLevel() + ") - Defence: +" + getTotalDefence();
    }
}