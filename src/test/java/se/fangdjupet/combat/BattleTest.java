
package se.fangdjupet.combat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import se.fangdjupet.core.EventBus;
import se.fangdjupet.core.EventType;
import se.fangdjupet.core.GameEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for Battle.
 */
class BattleTest {

    @Test
    void battleIsNotFinishedWhenBothAreAlive() {
        Hero hero = new Hero("Hero", 100, 100);
        Enemy enemy = new Enemy("Goblin", 50, 50);
        EventBus eventBus = new EventBus();

        Battle battle = new Battle(hero, enemy, eventBus);

        assertFalse(battle.isFinished());
    }

    @Test
    void battleIsFinishedWhenEnemyDies() {
        Hero hero = new Hero("Hero", 100, 100);
        Enemy enemy = new Enemy("Goblin", 50, 50);
        EventBus eventBus = new EventBus();

        enemy.takeDamage(50);

        Battle battle = new Battle(hero, enemy, eventBus);

        assertTrue(battle.isFinished());
    }

    @Test
    void battleIsFinishedWhenHeroDies() {
        Hero hero = new Hero("Hero", 100, 100);
        Enemy enemy = new Enemy("Goblin", 50, 50);
        EventBus eventBus = new EventBus();

        hero.takeDamage(100);

        Battle battle = new Battle(hero, enemy, eventBus);

        assertTrue(battle.isFinished());
    }

    @Test
    void heroAttackDamagesEnemy() {
        Hero hero = new Hero("Hero", 100, 100);
        Enemy enemy = new Enemy("Goblin", 50, 50);
        EventBus eventBus = new EventBus();

        Battle battle = new Battle(hero, enemy, eventBus);
        battle.start();

        battle.heroAttack(new MeleeAttack(), 15);

        assertEquals(35, enemy.getHealth());
    }

    @Test
    void enemyAttackDamagesHero() {
        Hero hero = new Hero("Hero", 100, 100);
        Enemy enemy = new Enemy("Goblin", 50, 50);
        EventBus eventBus = new EventBus();

        Battle battle = new Battle(hero, enemy, eventBus);
        battle.start();

        battle.enemyAttack(new MeleeAttack(), 15);

        assertEquals(85, hero.getHealth());
    }

    @Test
    void heroCannotAttackWhenBattleIsFinished() {
        Hero hero = new Hero("Hero", 100, 100);
        Enemy enemy = new Enemy("Goblin", 50, 50);
        EventBus eventBus = new EventBus();

        Battle battle = new Battle(hero, enemy, eventBus);
        battle.start();

        hero.takeDamage(100);

        assertThrows(IllegalStateException.class,
                () -> battle.heroAttack(new MeleeAttack(), 15));

        assertEquals(50, enemy.getHealth());
    }

    @Test
    void heroCannotAttackWithNullStrategy() {
        Hero hero = new Hero("Hero", 100, 100);
        Enemy enemy = new Enemy("Goblin", 50, 50);
        EventBus eventBus = new EventBus();

        Battle battle = new Battle(hero, enemy, eventBus);
        battle.start();

        assertThrows(IllegalArgumentException.class,
                () -> battle.heroAttack(null, 15));

        assertEquals(50, enemy.getHealth());
    }

    @Test
    void battleCannotBeCreatedWithNullArguments() {
        Hero hero = new Hero("Hero", 100, 100);
        Enemy enemy = new Enemy("Goblin", 50, 50);
        EventBus eventBus = new EventBus();

        assertThrows(IllegalArgumentException.class,
                () -> new Battle(null, enemy, eventBus));

        assertThrows(IllegalArgumentException.class,
                () -> new Battle(hero, null, eventBus));

        assertThrows(IllegalArgumentException.class,
                () -> new Battle(hero, enemy, null));
    }

    @Test
    void heroAttackPublishesEvent() {
        Hero hero = new Hero("Hero", 100, 100);
        Enemy enemy = new Enemy("Goblin", 50, 50);
        EventBus eventBus = new EventBus();

        List<GameEvent> events = new ArrayList<>();
        eventBus.subscribe(event -> events.add(event));

        Battle battle = new Battle(hero, enemy, eventBus);
        battle.start();

        battle.heroAttack(new MeleeAttack(), 15);

        assertEquals(2, events.size());
        assertEquals(EventType.BATTLE_STARTED, events.get(0).type());
        assertEquals(EventType.ATTACK_PERFORMED, events.get(1).type());
    }

    @Test
    void enemyDefeatedPublishesEvent() {
        Hero hero = new Hero("Hero", 100, 100);
        Enemy enemy = new Enemy("Goblin", 50, 50);
        EventBus eventBus = new EventBus();

        List<GameEvent> events = new ArrayList<>();
        eventBus.subscribe(event -> events.add(event));

        Battle battle = new Battle(hero, enemy, eventBus);
        battle.start();

        battle.heroAttack(new MeleeAttack(), 50);

        assertEquals(0, enemy.getHealth());
        assertEquals(3, events.size());
        assertEquals(EventType.BATTLE_STARTED, events.get(0).type());
        assertEquals(EventType.ATTACK_PERFORMED, events.get(1).type());
        assertEquals(EventType.ENEMY_DEFEATED, events.get(2).type());
    }

    @Test
    void heroDefeatedPublishesEvent() {
        Hero hero = new Hero("Hero", 100, 100);
        Enemy enemy = new Enemy("Goblin", 50, 50);
        EventBus eventBus = new EventBus();

        List<GameEvent> events = new ArrayList<>();
        eventBus.subscribe(event -> events.add(event));

        Battle battle = new Battle(hero, enemy, eventBus);
        battle.start();

        battle.enemyAttack(new MeleeAttack(), 100);

        assertEquals(0, hero.getHealth());
        assertEquals(3, events.size());
        assertEquals(EventType.BATTLE_STARTED, events.get(0).type());
        assertEquals(EventType.ATTACK_PERFORMED, events.get(1).type());
        assertEquals(EventType.HERO_DEFEATED, events.get(2).type());
    }

    @Test
    void magicAttackIgnoresDefence() {
        Hero hero = new Hero("Hero", 100, 100);
        Enemy enemy = new Enemy("Goblin", 50, 50);
        EventBus eventBus = new EventBus();

        Battle battle = new Battle(hero, enemy, eventBus);
        battle.start();

        battle.enemyAttack(new MagicAttack(), 30, 10);

        assertEquals(70, hero.getHealth());
    }

    @Test
    void meleeAttackIsReducedByDefence() {
        Hero hero = new Hero("Hero", 100, 100);
        Enemy enemy = new Enemy("Goblin", 50, 50);
        EventBus eventBus = new EventBus();

        Battle battle = new Battle(hero, enemy, eventBus);
        battle.start();

        battle.enemyAttack(new MeleeAttack(), 30, 10);

        assertEquals(80, hero.getHealth());
    }

    @Test
    void heroCannotAttackBeforeBattleStarts() {
        Hero hero = new Hero("Hero", 100, 100);
        Enemy enemy = new Enemy("Goblin", 50, 50);
        EventBus eventBus = new EventBus();

        Battle battle = new Battle(hero, enemy, eventBus);

        assertThrows(IllegalStateException.class,
                () -> battle.heroAttack(new MeleeAttack(), 15));

        assertEquals(50, enemy.getHealth());
    }

    @Test
    void enemyCannotAttackBeforeBattleStarts() {
        Hero hero = new Hero("Hero", 100, 100);
        Enemy enemy = new Enemy("Goblin", 50, 50);
        EventBus eventBus = new EventBus();

        Battle battle = new Battle(hero, enemy, eventBus);

        assertThrows(IllegalStateException.class,
                () -> battle.enemyAttack(new MeleeAttack(), 15));

        assertEquals(100, hero.getHealth());
    }

    @Test
    void heroCannotHealBeforeBattleStarts() {
        Hero hero = new Hero("Hero", 100, 100);
        Enemy enemy = new Enemy("Goblin", 50, 50);
        EventBus eventBus = new EventBus();

        hero.takeDamage(30);

        Battle battle = new Battle(hero, enemy, eventBus);

        assertThrows(IllegalStateException.class,
                () -> battle.healHero(15));

        assertEquals(70, hero.getHealth());
    }

    @Test
    void battleCannotStartTwice() {
        Hero hero = new Hero("Hero", 100, 100);
        Enemy enemy = new Enemy("Goblin", 50, 50);
        EventBus eventBus = new EventBus();

        Battle battle = new Battle(hero, enemy, eventBus);
        battle.start();

        assertThrows(IllegalStateException.class, battle::start);
    }

    @Test
    void heroCanHealAfterBattleStarts() {
        Hero hero = new Hero("Hero", 100, 100);
        Enemy enemy = new Enemy("Goblin", 50, 50);
        EventBus eventBus = new EventBus();

        hero.takeDamage(30);

        Battle battle = new Battle(hero, enemy, eventBus);
        battle.start();

        battle.healHero(15);

        assertEquals(85, hero.getHealth());
    }
}
