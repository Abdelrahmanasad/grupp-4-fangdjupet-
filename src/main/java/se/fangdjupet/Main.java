package se.fangdjupet;

import se.fangdjupet.game.Game;

/**
 * Starts Fangdjupet in the console.
 */
public class Main {

    public static void main(String[] args) {
        new Game(System.in, System.out, Game.DEFAULT_SAVE_FILE).run();
    }
}