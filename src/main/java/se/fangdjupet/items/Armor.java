package se.fangdjupet.items;

public class Armor extends Item{
    private double defenceBonus;

    public Armor(String name, double level, double DefenceBonus){
        super(name, level);
        this.defenceBonus = DefenceBonus;
    }

    public double getDefenceBonus(){
        return defenceBonus;
    }

    @Override
    public String getDescription() {
        return "Armor: " + getName() + " (Level " + getLevel() + ") - Defence: +" + defenceBonus;
    }
}
