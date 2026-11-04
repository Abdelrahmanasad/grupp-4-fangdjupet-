package se.fangdjupet.combat;

import org.junit.jupiter.api.Test;
import se.fangdjupet.core.EventBus;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import se.fangdjupet.core.EventType;
import se.fangdjupet.core.GameEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Tester för Battle.
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

        battle.heroAttack(new MeleeAttack(), 15);

        assertEquals(35, enemy.getHealth());
    }

    @Test
    void enemyAttackDamagesHero() {
        Hero hero = new Hero("Hero", 100, 100);
        Enemy enemy = new Enemy("Goblin", 50, 50);
        EventBus eventBus = new EventBus();

        Battle battle = new Battle(hero, enemy, eventBus);

        battle.enemyAttack(new MeleeAttack(), 15);

        assertEquals(85, hero.getHealth());
    }

    @Test
    void heroCannotAttackWhenBattleIsFinished() {
        Hero hero = new Hero("Hero", 100, 100);
        Enemy enemy = new Enemy("Goblin", 50, 50);
        EventBus eventBus = new EventBus();

        Battle battle = new Battle(hero, enemy, eventBus);

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

        battle.heroAttack(new MeleeAttack(), 15);

        assertEquals(1, events.size());
        assertEquals(EventType.ATTACK_PERFORMED, events.get(0).type());
    }

    @Test
    void enemyDefeatedPublishesEvent() {
        Hero hero = new Hero("Hero", 100, 100);
        Enemy enemy = new Enemy("Goblin", 50, 50);
        EventBus eventBus = new EventBus();

        List<GameEvent> events = new ArrayList<>();
        eventBus.subscribe(event -> events.add(event));

        Battle battle = new Battle(hero, enemy, eventBus);

        battle.heroAttack(new MeleeAttack(), 50);

        assertEquals(0, enemy.getHealth());
        assertEquals(2, events.size());
        assertEquals(EventType.ATTACK_PERFORMED, events.get(0).type());
        assertEquals(EventType.ENEMY_DEFEATED, events.get(1).type());
    }

    @Test
    void heroDefeatedPublishesEvent() {
        Hero hero = new Hero("Hero", 100, 100);
        Enemy enemy = new Enemy("Goblin", 50, 50);
        EventBus eventBus = new EventBus();

        List<GameEvent> events = new ArrayList<>();
        eventBus.subscribe(event -> events.add(event));

        Battle battle = new Battle(hero, enemy, eventBus);

        battle.enemyAttack(new MeleeAttack(), 100);

        assertEquals(0, hero.getHealth());
        assertEquals(2, events.size());
        assertEquals(EventType.ATTACK_PERFORMED, events.get(0).type());
        assertEquals(EventType.HERO_DEFEATED, events.get(1).type());
    }
}