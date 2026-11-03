package se.fangdjupet.core;

/**
 * Alla typer av händelser som kan inträffa i spelet.
 * Används av GameEvent, så att lyssnare kan reagera olika
 * beroende på vad som hänt t.ex bara logga strider.
 */
public enum EventType {

    // Spelets livscykel
    GAME_STARTED,
    GAME_SAVED,
    GAME_LOADED,
    GAME_OVER,
    VICTORY,

    // Värld och rum
    ROOM_ENTERED,

    // Strid
    BATTLE_STARTED,
    ATTACK_PERFORMED,
    ENEMY_DEFEATED,
    HERO_DEFEATED,
    HERO_HEALED,

    // Föremål
    ITEM_FOUND,
    ITEM_USED,
    ITEM_EQUIPPED
}