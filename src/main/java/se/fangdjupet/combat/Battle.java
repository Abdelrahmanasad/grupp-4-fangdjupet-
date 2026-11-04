package se.fangdjupet.combat;
import se.fangdjupet.core.EventBus;
import se.fangdjupet.core.EventType;

/**
 * Hanterar strider mellan hjältar och fiender.
 */
public class Battle {

    private final Hero hero;
    private final Enemy enemy;
    private final EventBus eventBus;
    private boolean started;

    /**
     * Skapar en strid mellan en hjälte och en fiende.
     *
     * @param hero hjälten som deltar i striden
     * @param enemy fienden som deltar i striden
     * @param eventBus hanterar händelser under striden
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
     * Kontrollerar om striden är slut.
     *
     * @return true om hjälten eller fienden är död, annars false
     */
    public boolean isFinished() {
        return !hero.isAlive() || !enemy.isAlive();
    }

    /**
     * Startar striden och skickar en händelse.
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
     * Kontrollerar att striden fortfarande pågår.
     */
    private void ensureBattleActive() {
        if (isFinished()) {
            throw new IllegalStateException("Battle is already finished");
        }
    }

    /**
     * Genomför en attack mot en karaktär.
     *
     * @param target karaktären som tar skada
     * @param strategy vilken attacktyp som används
     * @param baseDamage attackens grundskada
     * @param defenceBonus målets försvar
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
     * Låter hjälten attackera fienden.
     *
     * @param strategy vilken typ av attack som används
     * @param baseDamage attackens grundskada
     */
    public void heroAttack(AttackStrategy strategy, double baseDamage) {
        performAttack(enemy, strategy, baseDamage, 0);
    }

    /**
     * Låter fienden attackera hjälten.
     *
     * @param strategy vilken typ av attack som används
     * @param baseDamage attackens grundskada
     */
    public void enemyAttack(AttackStrategy strategy, double baseDamage) {
        performAttack(hero, strategy, baseDamage, 0);
    }

    /**
     * Låter fienden attackera hjälten med hänsyn till försvar.
     *
     * @param strategy vilken typ av attack som används
     * @param baseDamage attackens grundskada
     * @param defenceBonus hjältens försvar
     */
    public void enemyAttack(AttackStrategy strategy,
                            double baseDamage,
                            double defenceBonus) {
        performAttack(hero, strategy, baseDamage, defenceBonus);
    }

    /**
     * Läker hjälten under striden.
     *
     * @param amount hur mycket hälsa som ska återställas
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