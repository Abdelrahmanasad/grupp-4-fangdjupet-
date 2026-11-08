package se.fangdjupet.game;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MenuOptionTest {

    @Test
    void findsOptionByKey() {
        assertEquals(Optional.of(MenuOption.FIGHT), MenuOption.fromKey("4"));
        assertEquals(Optional.of(MenuOption.NEW_GAME), MenuOption.fromKey("1"));
    }

    @Test
    void ignoresCaseAndSpaces() {
        assertEquals(Optional.of(MenuOption.EXIT), MenuOption.fromKey("E"));
        assertEquals(Optional.of(MenuOption.SAVE), MenuOption.fromKey("  5 "));
    }

    @Test
    void unknownInputGivesEmptyOptional() {
        assertTrue(MenuOption.fromKey("9").isEmpty());
        assertTrue(MenuOption.fromKey("fight").isEmpty());
        assertTrue(MenuOption.fromKey("").isEmpty());
        assertTrue(MenuOption.fromKey(null).isEmpty());
    }

    @Test
    void everyOptionHasUniqueKey() {
        long uniqueKeys = Arrays.stream(MenuOption.values())
                .map(MenuOption::getKey)
                .distinct()
                .count();

        assertEquals(MenuOption.values().length, uniqueKeys);
    }
}