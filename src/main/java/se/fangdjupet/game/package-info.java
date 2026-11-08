/**
 * World, game engine and console UI.
 * <p>
 * Owner: Oskar.
 * <p>
 * Connects the {@code combat}, {@code items} and {@code core} packages into a playable game.
 * The other packages never depend on {@code game}.
 * <ul>
 *     <li>{@link se.fangdjupet.game.GameState} - everything that describes an ongoing game</li>
 *     <li>{@link se.fangdjupet.game.Dungeon} - the sequence of rooms and enemies</li>
 * </ul>
 * Saving and loading lives in the sub-package {@code se.fangdjupet.game.save}.
 */
package se.fangdjupet.game;