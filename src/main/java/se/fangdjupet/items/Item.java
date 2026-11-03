package se.fangdjupet.items;

public abstract class Item {
    private String name;
    private double level;

    public Item(String name, double level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public double getLevel() {
        return level;
    }

    public abstract String getDescription();

}
