/**
 * Shared contract and event system (Observer pattern).
 * <p>
 * Owner: Oskar.
 * <p>
 * Contents:
 * <ul>
 *     <li>{@link se.fangdjupet.core.EventType} - enum with every type of event in the game</li>
 *     <li>{@link se.fangdjupet.core.GameEvent} - record describing a single event</li>
 *     <li>{@link se.fangdjupet.core.GameEventListener} - interface for everything that listens to events</li>
 *     <li>{@link se.fangdjupet.core.EventBus} - class that forwards events to all listeners</li>
 * </ul>
 */
package se.fangdjupet.core;