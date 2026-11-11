/**
 * Factory pattern: creates items and enemies from a type and a level.
 * * <p> * Owner: Abbe. * <ul> * <li>{@link se.fangdjupet.factory.ItemFactory} - creates weapons, armor and potions</li> * <li>{@link se.fangdjupet.factory.ItemCatalog}
 * - ready-made items with fixed names and stats</li> * <li>{@link se.fangdjupet.factory.EnemyFactory}
 * - creates enemies from an {@link se.fangdjupet.factory.EnemyType} and a level</li> * <li>{@link se.fangdjupet.factory.EnemyType}
 * - the kinds of enemies in the dungeon</li> * </ul>
 */
package se.fangdjupet.factory;
