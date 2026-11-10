package se.fangdjupet.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.fangdjupet.combat.Enemy;
import se.fangdjupet.combat.MagicAttack;
import se.fangdjupet.combat.MeleeAttack;
import se.fangdjupet.combat.RangedAttack;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class EnemyFactoryTest {

    @Test
    @DisplayName("createGoblin creates a Goblin with level 1 base stats and MeleeAttack")
    void testCreateGoblinLevel1() {
        Enemy goblin = EnemyFactory.createGoblin(1);

        assertEquals("Goblin", goblin.getName());
        assertEquals(40.0, goblin.getMaxHealth(), 0.001);
        assertEquals(8.0, goblin.getAttackDamage(), 0.001);
        assertInstanceOf(MeleeAttack.class, goblin.getAttackStrategy());
    }

    @Test
    @DisplayName("createSkeleton creates a Skeleton with level 1 base stats and RangedAttack")
    void testCreateSkeletonLevel1() {
        Enemy skeleton = EnemyFactory.createSkeleton(1);

        assertEquals("Skeleton", skeleton.getName());
        assertEquals(50.0, skeleton.getMaxHealth(), 0.001);
        assertEquals(10.0, skeleton.getAttackDamage(), 0.001);
        assertInstanceOf(RangedAttack.class, skeleton.getAttackStrategy());
    }

    @Test
    @DisplayName("createTroll creates a Troll with level 1 base stats and MeleeAttack")
    void testCreateTrollLevel1() {
        Enemy troll = EnemyFactory.createTroll(1);

        assertEquals("Troll", troll.getName());
        assertEquals(90.0, troll.getMaxHealth(), 0.001);
        assertEquals(15.0, troll.getAttackDamage(), 0.001);
        assertInstanceOf(MeleeAttack.class, troll.getAttackStrategy());
    }

    @Test
    @DisplayName("createMage creates a Mage with level 1 base stats and MagicAttack")
    void testCreateMageLevel1() {
        Enemy mage = EnemyFactory.createMage(1);

        assertEquals("Mage", mage.getName());
        assertEquals(45.0, mage.getMaxHealth(), 0.001);
        assertEquals(18.0, mage.getAttackDamage(), 0.001);
        assertInstanceOf(MagicAttack.class, mage.getAttackStrategy());
    }

    @Test
    @DisplayName("Stats scale correctly at higher levels (+25% per level above 1)")
    void testLevelScaling() {
        // Level 3 calculation: 1.0 + (3 - 1) * 0.25 = 1.5 multiplier (+50%)
        // Goblin base: 40 HP -> 60 HP, 8 DMG -> 12 DMG
        Enemy scaledGoblin = EnemyFactory.createGoblin(3);

        assertEquals(60.0, scaledGoblin.getMaxHealth(), 0.001);
        assertEquals(12.0, scaledGoblin.getAttackDamage(), 0.001);
    }

    @Test
    @DisplayName("createEnemy creates custom enemies correctly")
    void testCustomCreateEnemy() {
        Enemy boss = EnemyFactory.createEnemy("Boss", 2, 100.0, 20.0, new MagicAttack());

        // Level 2 multiplier: 1.25
        assertEquals("Boss", boss.getName());
        assertEquals(125.0, boss.getMaxHealth(), 0.001);
        assertEquals(25.0, boss.getAttackDamage(), 0.001);
        assertInstanceOf(MagicAttack.class, boss.getAttackStrategy());
    }

    @Test
    @DisplayName("Private constructor throws UnsupportedOperationException when invoked")
    void testPrivateConstructor() throws NoSuchMethodException {
        Constructor<EnemyFactory> constructor = EnemyFactory.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        InvocationTargetException exception = assertThrows(
                InvocationTargetException.class,
                constructor::newInstance
        );

        assertInstanceOf(UnsupportedOperationException.class, exception.getCause());
        assertEquals("Utility class cannot be instantiated", exception.getCause().getMessage());
    }
}