package pvz.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.Assert;
import org.junit.Test;

import pvz.model.entity.Entity;
import pvz.model.entity.child.projectile.AbstractProjectile;
import pvz.model.entity.child.projectile.BasicProjectile;
import pvz.model.entity.child.projectile.FrozenProjectile;
import pvz.model.entity.plant.PlantType;
import pvz.model.entity.plant.Wallnut;
import pvz.model.entity.plant.spawner.Sunflower;
import pvz.model.entity.zombie.BasicZombie;
import pvz.model.entity.zombie.FootballZombie;
import pvz.model.entity.zombie.Zombie;
import pvz.model.level.ArcadeLevel;
import pvz.model.level.LevelInterface;
import pvz.utility.Vector;

/**
 * Game world automatic tests.
 */
public class ModelTest {

    private static final long ZOMBIE_WALK_TICKS = 120;
    private static final double WALLNUT_EXPECTED_HEALTH_PERC = (double) (200 - 10) / 200d;
    private static final double FOOTBALL_POS_LOWER_BOUND = 0.014;
    private static final double FOOTBALL_POS_UPPER_BOUND = 0.016;
    private static final double SLOW_FOOTBALL_POS_LOWER_BOUND = 0.002;
    private static final double SLOW_FOOTBALL_POS_UPPER_BOUND = 0.004;
    private static final double PREV_ZOMBIE_SPEED = 0.003d;
    private static final int LAWNMOWER_AMOUNT = 5;
    private static final double ZOMBIE_EXPECTED_HEALTH_PERC = (double) (70 - 10) / 70d;
    private static final int ZOMBIES_TO_SPAWN = 5;

    /**
     * Test for proper energy check when putting down a plant.
     */
    @Test
    public void testPlantingFail() {
        final ModelInterface model = new Model(new HashSet<>(Arrays.asList(PlantType.SUNFLOWER)));

        model.putPlant(Vector.of(0, 0), PlantType.SUNFLOWER);
        Assert.assertTrue(model.getEntityList().stream() //
                .filter(e -> e instanceof Sunflower) //
                .count() == 0);

    }

    /**
     * Test for zombie damage and speed.
     */
    @Test
    public void testZombies() {
        final List<Entity> list = new ArrayList<>();
        final LevelInterface level = new ArcadeLevel(list);
        final Zombie zombie = new BasicZombie(Vector.of(6, 0), level);
        final Wallnut wallnut = new Wallnut(Vector.of(3, 0));
        list.add(zombie);
        list.add(wallnut);

        /* Testing zombie damage */

        LongStream.range(0, ZOMBIE_WALK_TICKS).forEach(i -> level.update());
        level.update();
        Assert.assertTrue(wallnut.getHealthPercentage() == WALLNUT_EXPECTED_HEALTH_PERC);

        /* Testing FootballZombie first collision */

        final Zombie zombie2 = new FootballZombie(Vector.of(6, 3), level);
        final Wallnut wallnut2 = new Wallnut(Vector.of(0, 3));
        list.add(zombie2);
        list.add(wallnut2);

        double prevX = zombie2.getX();
        level.update();
        Assert.assertTrue(
                prevX - zombie2.getX() > FOOTBALL_POS_LOWER_BOUND && prevX - zombie2.getX() < FOOTBALL_POS_UPPER_BOUND);
        while (!zombie2.collidesWith(wallnut2)) {
            level.update();
        }
        prevX = zombie2.getX();
        level.update();
        Assert.assertTrue(wallnut2.shouldBeRemoved());
        Assert.assertTrue(prevX - zombie2.getX() > SLOW_FOOTBALL_POS_LOWER_BOUND
                && prevX - zombie2.getX() < SLOW_FOOTBALL_POS_UPPER_BOUND);

    }

    /**
     * Test for projectile behavior.
     */
    @Test
    public void testProjectiles() {
        final List<Entity> list = new ArrayList<>();
        final LevelInterface level = new ArcadeLevel(list);
        final Zombie zombie = new BasicZombie(Vector.of(27, 0), level);
        final AbstractProjectile proj = new BasicProjectile(Vector.of(0, 0), level);
        list.add(zombie);
        list.add(proj);

        /* Testing proj damage */

        while (!proj.collidesWith(zombie)) {
            level.update();
        }
        level.update();

        Assert.assertTrue(level.getEntityList().size() == 1 + LAWNMOWER_AMOUNT);
        Assert.assertTrue(zombie.getHealthPercentage() == ZOMBIE_EXPECTED_HEALTH_PERC);

        /* Testing frozen proj. slow debuff */

        list.add(new FrozenProjectile(Vector.of(0, 0), level));
        while (!proj.collidesWith(zombie)) {
            level.update();
        }

        final double prevX = zombie.getX();
        level.update();
        Assert.assertFalse(zombie.getX() - prevX == PREV_ZOMBIE_SPEED);

    }

    /**
     * Test for game over mechanics.
     */
    @Test
    public void testGameLost() {
        final List<Entity> list = new ArrayList<>();
        final LevelInterface level = new ArcadeLevel(list);

        IntStream.range(0, ZOMBIES_TO_SPAWN) //
                .mapToObj(i -> new BasicZombie(Vector.of(0, i * 3), level)) //
                .forEach(list::add);

        IntStream.range(0, 3).forEach(i -> level.update());
        Assert.assertTrue(list.stream().filter(e -> e instanceof Zombie).count() == 0);

        IntStream.range(0, 100).forEach(i -> level.update());
        list.add(new BasicZombie(Vector.of(0, 0), level));
        IntStream.range(0, 1000).forEach(i -> level.update());
        Assert.assertTrue(level.getGameStatus() == GameStatus.LOST);
    }

}
