package se.fangdjupet.combat;
import se.fangdjupet.core.EventBus;
import se.fangdjupet.core.EventType;

/**
 * Handles battles between heroes and enemies.
 */
public class Battle {

    private final Hero hero;
    private final Enemy enemy;
    private final EventBus eventBus;
    private boolean started;

    /**
     * Creates a battle between a hero and an enemy.
     *
     * @param hero the hero taking part in the battle
     * @param enemy the enemy taking part in the battle
     * @param eventBus handles events during the battle
     */
    public Battle(Hero hero, Enemy enemy, EventBus eventBus) {
        if (hero == null) {
            throw new IllegalArgumentException("Hero cannot be null");
        }
        if (enemy == null) {
            throw new IllegalArgumentException("Enemy cannot be null");
        }
        if (eventBus == null) {
            throw new IllegalArgumentException("EventBus cannot be null");
        }

        this.hero = hero;
        this.enemy = enemy;
        this.eventBus = eventBus;
    }

    /**
     * Checks if the battle is finished.
     *
     * @return true if the hero or enemy is dead, otherwise false
     */
    public boolean isFinished() {
        return !hero.isAlive() || !enemy.isAlive();
    }

    /**
     * Starts the battle and sends an event.
     */
    public void start() {
        ensureBattleActive();

        if (started) {
            throw new IllegalStateException("Battle has already started");
        }

        started = true;

        eventBus.publish(EventType.BATTLE_STARTED,
                hero.getName() + " fights " + enemy.getName() + ".");
    }

    /**
     * Checks that the battle is still active.
     */
    private void ensureBattleActive() {
        if (isFinished()) {
            throw new IllegalStateException("Battle is already finished");
        }
    }

    /**
     * Performs an attack against a character.
     *
     * @param target the character taking damage
     * @param strategy the type of attack used
     * @param baseDamage the base damage of the attack
     * @param defenceBonus the target's defence
     */
    private void performAttack(Combatant target,
                               AttackStrategy strategy,
                               double baseDamage,
                               double defenceBonus) {
        ensureBattleActive();

        if (strategy == null) {
            throw new IllegalArgumentException("Attack strategy cannot be null");
        }

        double attackDamage = strategy.calculateDamage(baseDamage);
        double damage = DamageCalculator.calculateDamage(attackDamage, defenceBonus);

        target.takeDamage(damage);

        eventBus.publish(EventType.ATTACK_PERFORMED,
                target.getName() + " took " + damage + " damage.");

        if (target == enemy && !enemy.isAlive()) {
            eventBus.publish(EventType.ENEMY_DEFEATED,
                    enemy.getName() + " was defeated.");
        }

        if (target == hero && !hero.isAlive()) {
            eventBus.publish(EventType.HERO_DEFEATED,
                    hero.getName() + " was defeated.");
        }
    }

    /**
     * Lets the hero attack the enemy.
     *
     * @param strategy the type of attack used
     * @param baseDamage the base damage of the attack
     */
    public void heroAttack(AttackStrategy strategy, double baseDamage) {
        performAttack(enemy, strategy, baseDamage, 0);
    }

    /**
     * Lets the enemy attack the hero.
     *
     * @param strategy the type of attack used
     * @param baseDamage the base damage of the attack
     */
    public void enemyAttack(AttackStrategy strategy, double baseDamage) {
        performAttack(hero, strategy, baseDamage, 0);
    }

    /**
     * Lets the enemy attack the hero while considering defence.
     *
     * @param strategy the type of attack used
     * @param baseDamage the base damage of the attack
     * @param defenceBonus the hero's defence
     */
    public void enemyAttack(AttackStrategy strategy,
                            double baseDamage,
                            double defenceBonus) {
        performAttack(hero, strategy, baseDamage, defenceBonus);
    }

    /**
     * Heals the hero during the battle.
     *
     * @param amount how much health to restore
     */
    public void healHero(double amount) {
        ensureBattleActive();

        double healthBefore = hero.getHealth();
        hero.heal(amount);

        double healed = hero.getHealth() - healthBefore;

        if (healed > 0) {
            eventBus.publish(EventType.HERO_HEALED,
                    hero.getName() + " healed " + healed + " health.");
        }
    }
}