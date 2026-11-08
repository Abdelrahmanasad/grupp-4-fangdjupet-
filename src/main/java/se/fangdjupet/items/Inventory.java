package se.fangdjupet.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * The hero's inventory with a maximum capacity.
 * Uses generics in getItemsOfType() to filter items by class, e.g. only weapons.
 */

public class Inventory {
    private final int maxCapacity;
    private final List<Item> items;

    public Inventory(int maxCapacity) {
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("Max capacity must be greater than 0");
        }
        this.maxCapacity = maxCapacity;
        this.items = new ArrayList<>();
    }

    public boolean addItem(Item item) {
        Objects.requireNonNull(item, "Item to add cannot be null");
        if (items.size() >= maxCapacity) {
            return false;
        }
        return items.add(item);
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    public <T extends Item> List<T> getItemsOfType(Class<T> type) {
        List<T> filteredList = new ArrayList<>();
        for (Item item : items) {
            if (type.isInstance(item)) {
                filteredList.add(type.cast(item));
            }
        }
        return filteredList;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public boolean removeItem(Item item) {
        Objects.requireNonNull(item, "Item to remove cannot be null");
        return items.remove(item);
    }
}