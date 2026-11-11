package se.fangdjupet.game;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsoleUiTest {

    private final ByteArrayOutputStream outputBytes = new ByteArrayOutputStream();

    // Creates a ConsoleUi that reads the given text as if the player typed it
    private ConsoleUi uiWithInput(String typed) {
        ByteArrayInputStream in = new ByteArrayInputStream(typed.getBytes(StandardCharsets.UTF_8));
        PrintStream out = new PrintStream(outputBytes, true, StandardCharsets.UTF_8);
        return new ConsoleUi(in, out);
    }

    private String output() {
        return outputBytes.toString(StandardCharsets.UTF_8);
    }

    @Test
    void showMenuPrintsEveryOption() {
        ConsoleUi ui = uiWithInput("");

        ui.showMenu();

        for (MenuOption option : MenuOption.values()) {
            assertTrue(output().contains(option.getKey() + ". " + option.getLabel()));
        }
    }

    @Test
    void showTitleUnderlinesTitle() {
        ConsoleUi ui = uiWithInput("");

        ui.showTitle("Fangdjupet");

        assertTrue(output().contains("Fangdjupet"));
        assertTrue(output().contains("=========="));
    }

    @Test
    void askMenuOptionReturnsChosenOption() {
        ConsoleUi ui = uiWithInput("4\n");

        assertEquals(MenuOption.FIGHT, ui.askMenuOption());
    }

    @Test
    void askMenuOptionAsksAgainAfterInvalidInput() {
        ConsoleUi ui = uiWithInput("hello\n9\ne\n");

        assertEquals(MenuOption.EXIT, ui.askMenuOption());
        assertTrue(output().contains("Unknown choice"));
    }

    @Test
    void askMenuOptionReturnsExitWhenInputEnds() {
        ConsoleUi ui = uiWithInput("");

        assertEquals(MenuOption.EXIT, ui.askMenuOption());
    }

    @Test
    void askTextSkipsBlankAnswers() {
        ConsoleUi ui = uiWithInput("\n   \n  Sir Lancelot  \n");

        assertEquals("Sir Lancelot", ui.askText("Name: "));
        assertTrue(output().contains("Please type something."));
    }

    @Test
    void askNumberAcceptsNumberInRange() {
        ConsoleUi ui = uiWithInput("2\n");

        assertEquals(2, ui.askNumber("Choose: ", 1, 3));
    }

    @Test
    void askNumberAsksAgainForTextAndOutOfRange() {
        ConsoleUi ui = uiWithInput("abc\n0\n7\n3\n");

        assertEquals(3, ui.askNumber("Choose: ", 1, 3));
        assertTrue(output().contains("Choose a number between 1 and 3."));
    }

    @Test
    void askNumberThrowsWhenInputEnds() {
        ConsoleUi ui = uiWithInput("abc\n");

        assertThrows(IllegalStateException.class, () -> ui.askNumber("Choose: ", 1, 3));
    }

    @Test
    void askNumberRejectsInvalidRange() {
        ConsoleUi ui = uiWithInput("1\n");

        assertThrows(IllegalArgumentException.class, () -> ui.askNumber("Choose: ", 5, 1));
    }
}