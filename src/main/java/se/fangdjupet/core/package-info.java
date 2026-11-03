/**
 * Gemensamt kontrakt och händelsesystem (Observer).
 * <p>
 * Ansvarig: Oskar.
 * <p>
 * Innehåll:
 * <ul>
 *     <li>{@link se.fangdjupet.core.EventType} - enum med alla typer av händelser i spelet</li>
 *     <li>{@link se.fangdjupet.core.GameEvent} - record som beskriver en enskild händelse</li>
 *     <li>{@link se.fangdjupet.core.GameEventListener} - interface för alla som vill lyssna på händelser</li>
 *     <li>{@link se.fangdjupet.core.EventBus} - klass som skickar händelser vidare till alla lyssnare</li>
 * </ul>
 */
package se.fangdjupet.core;