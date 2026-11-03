package se.fangdjupet.items;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> items;
    private int maxCapacity;

    public Inventory(int maxCapacity){
        this.maxCapacity = maxCapacity;
        this.items = new ArrayList<>();
    }

    public boolean addItem(Item item){
        if(items.size() < maxCapacity){
            items.add(item);
            return true;
        }
        return false;
    }

    public boolean removeItem(Item item) {
        return items.remove(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public <T extends Item> List<T> getItemsOfType (Class<T> itemType) {
        return items.stream()
                .filter(itemType::isInstance)
                .map(itemType::cast)
                .toList();
    }
}
