package se.fangdjupet.core;

/**
 * Every type of event that can happen in the game.
 * <p>
 * Used by {@link GameEvent} so that listeners can react differently
 * depending on what happened (e.g. only log battle events).
 */
public enum EventType {

    // Game lifecycle
    GAME_STARTED,
    GAME_SAVED,
    GAME_LOADED,
    GAME_OVER,
    VICTORY,

    // World and rooms
    ROOM_ENTERED,

    // Combat
    BATTLE_STARTED,
    ATTACK_PERFORMED,
    ENEMY_DEFEATED,
    HERO_DEFEATED,
    HERO_HEALED,

    // Items
    ITEM_FOUND,
    ITEM_USED,
    ITEM_EQUIPPED
}