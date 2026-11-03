package se.fangdjupet.items;

public class Potion extends Item{
    private double healthBonus;

    public Potion(String name, double level, double healthBonus){
        super(name, level);
        this.healthBonus = healthBonus;
    }

    public double getHealthBonus(){
        return healthBonus;
    }

    @Override
    public String getDescription() {
        return "Potion: " + getName() + " (Level " + getLevel() + ") - Health: +" + healthBonus;
    }
}
