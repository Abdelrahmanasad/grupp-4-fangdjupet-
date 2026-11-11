/**
 * World, game engine and console UI.
 * <p>
 * Owner: Oskar.
 * <p>
 * Connects the {@code combat}, {@code items}, {@code factory} and {@code core} packages
 * into a playable game. The other packages never depend on {@code game}.
 * <ul>
 *     <li>{@link se.fangdjupet.game.Game} - the main menu loop and the battles</li>
 *     <li>{@link se.fangdjupet.game.World} - starting equipment, enemies per room and rewards</li>
 *     <li>{@link se.fangdjupet.game.GameState} - everything that describes an ongoing game</li>
 *     <li>{@link se.fangdjupet.game.Dungeon} - the sequence of rooms and enemies</li>
 *     <li>{@link se.fangdjupet.game.ConsoleUi} and {@link se.fangdjupet.game.MenuOption} - console input and output</li>
 *     <li>{@link se.fangdjupet.game.ConsoleEventLogger} and {@link se.fangdjupet.game.EventHistory}
 *         - listeners in the Observer pattern</li>
 * </ul>
 * Saving and loading lives in the sub-package {@code se.fangdjupet.game.save}.
 */
package se.fangdjupet.game;