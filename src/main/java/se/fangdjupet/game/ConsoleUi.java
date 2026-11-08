package se.fangdjupet.game;

import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

/**
 * Everything the game reads from and writes to the console.
 * <p>
 * Input and output are passed in through the constructor instead of using
 * {@code System.in} / {@code System.out} directly. The game uses the real
 * console, but the tests can send in fake input and read the output as text.
 * <p>
 * Every question repeats until the player gives a valid answer, so the
 * rest of the game never has to handle bad input.
 */
public class ConsoleUi {

    private final Scanner input;
    private final PrintStream output;

    /**
     * @param in  where to read the player's input from - {@code System.in}
     * @param out where to print - {@code System.out}
     */
    public ConsoleUi(InputStream in, PrintStream out) {
        this.input = new Scanner(Objects.requireNonNull(in, "Input cannot be null"), StandardCharsets.UTF_8);
        this.output = Objects.requireNonNull(out, "Output cannot be null");
    }

    /**
     * Prints a line of text.
     *
     * @param text the text to print
     */
    public void println(String text) {
        output.println(text);
    }

    /**
     * Prints an empty line.
     */
    public void println() {
        output.println();
    }

    /**
     * Prints a title with a line under it - the game name.
     *
     * @param title the title to print
     */
    public void showTitle(String title) {
        output.println(title);
        output.println("=".repeat(title.length()));
    }

    /**
     * Prints the main menu with all options.
     */
    public void showMenu() {
        output.println();
        for (MenuOption option : MenuOption.values()) {
            output.println(option.getKey() + ". " + option.getLabel());
        }
    }

    /**
     * Asks the player to choose from the main menu until a valid option is typed.
     * If the input ends (example: Ctrl+D or the end of a test), EXIT is returned
     * so the game can shut down cleanly.
     *
     * @return the chosen option
     */
    public MenuOption askMenuOption() {
        while (true) {
            Optional<String> line = readLine("> ");

            if (line.isEmpty()) {
                return MenuOption.EXIT;
            }

            Optional<MenuOption> option = MenuOption.fromKey(line.get());
            if (option.isPresent()) {
                return option.get();
            }

            output.println("Unknown choice, try again.");
        }
    }

    /**
     * Asks for a text that is not blank - the hero's name.
     *
     * @param prompt the question to show
     * @return the answer without surrounding spaces
     * @throws IllegalStateException if the input ends before a valid answer
     */
    public String askText(String prompt) {
        while (true) {
            String answer = readLine(prompt).orElseThrow(ConsoleUi::noMoreInput).trim();

            if (!answer.isEmpty()) {
                return answer;
            }

            output.println("Please type something.");
        }
    }

    /**
     * Asks for a whole number between min and max (both included),
     * (which attack or item to use)
     *
     * @param prompt the question to show
     * @param min    lowest allowed number
     * @param max    highest allowed number
     * @return the chosen number
     * @throws IllegalArgumentException if min is greater than max
     * @throws IllegalStateException    if the input ends before a valid answer
     */
    public int askNumber(String prompt, int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("min cannot be greater than max");
        }

        while (true) {
            String answer = readLine(prompt).orElseThrow(ConsoleUi::noMoreInput).trim();

            try {
                int number = Integer.parseInt(answer);
                if (number >= min && number <= max) {
                    return number;
                }
            } catch (NumberFormatException e) {
                // Not a number: fall through and ask again
            }

            output.println("Choose a number between " + min + " and " + max + ".");
        }
    }

    // Shows the prompt and reads one line, or returns empty if there is no more input
    private Optional<String> readLine(String prompt) {
        output.print(prompt);
        output.flush();

        if (!input.hasNextLine()) {
            return Optional.empty();
        }

        return Optional.of(input.nextLine());
    }

    private static IllegalStateException noMoreInput() {
        return new IllegalStateException("No more input");
    }
}