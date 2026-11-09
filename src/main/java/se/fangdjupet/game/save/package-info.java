/**
 * Saving and loading an ongoing game to and from a file.
 * <p>
 * Owner: Oskar.
 * <ul>
 *     <li>{@link se.fangdjupet.game.save.SaveManager} - saves and loads a {@link se.fangdjupet.game.GameState}</li>
 *     <li>{@link se.fangdjupet.game.save.SaveGameException} - thrown when saving or loading fails</li>
 *     <li>{@code ItemSerializer} - converts items to and from the save format (package-private)</li>
 *     <li>{@code SaveData} - typed key/value access to the save file (package-private)</li>
 * </ul>
 */
package se.fangdjupet.game.save;