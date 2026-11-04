package se.fangdjupet.items;

public class Armor extends Item {
    private final double defenceBonus;

    public Armor(String name, int level, double defenceBonus) {
        super(name, level);
        if (defenceBonus < 0) {
            throw new IllegalArgumentException("Defence bonus cannot be negative");
        }
        this.defenceBonus = defenceBonus;
    }

    public double getDefenceBonus() {
        return defenceBonus;
    }

    @Override
    public String getDescription() {
        return "Armor: " + getName() + " (Level " + getLevel() + ") - Defence: +" + defenceBonus;
    }
}
