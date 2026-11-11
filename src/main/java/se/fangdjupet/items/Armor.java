package se.fangdjupet.items;

import java.util.Locale;
import java.util.Objects;

/**
 * A piece of armor worn in one ArmorSlot that reduces the damage the hero takes.
 * Total defence = (base defence of the slot + bonus defence) x level multiplier.
 */


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
        return (armorSlot.getBaseDefence() + defenceBonus) * levelMultiplier();
    }

    @Override
    public ItemType getType() {
        return ItemType.ARMOR;
    }

    @Override
    public String getDescription() {
        return "Armor (" + armorSlot.name() + "): " + getName() + " (Level " + getLevel() + ") - Defence: +" + String.format(Locale.ROOT, "%.1f", getTotalDefence());

    }
}