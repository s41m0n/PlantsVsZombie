package pvz.model.level.spawner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pvz.controller.constants.GameConstants;
import pvz.model.WorldConstants;
import pvz.model.entity.Entity;
import pvz.model.entity.zombie.BasicZombie;
import pvz.model.entity.zombie.BucketZombie;
import pvz.model.entity.zombie.ConeZombie;
import pvz.model.entity.zombie.FootballZombie;
import pvz.model.entity.zombie.Zombie;
import pvz.model.entity.zombie.ZombieType;
import pvz.model.level.LevelInterface;
import pvz.utility.Pair;
import pvz.utility.Vector;
/**
 * This class defines a basic implementation of ZombieSpawner.
 */
public class ZombieSpawner implements EntitySpawner {

    private Map<ZombieType, Integer> currentZombieMap;
    private List<Pair<Map<ZombieType, Integer>, Integer>> allZombieList;
    private long start;
    private long currentDelay;
    private LevelInterface level;
    /**
     * Basic constructor for ZombieSpawner.
     * 
     * @param zombieList horde of zombie
     * @param level level 
     */
    public ZombieSpawner(final List<Pair<Map<ZombieType, Integer>, Integer>> zombieList, final LevelInterface level) {

        this.level = level;
        if (!zombieList.isEmpty()) {
            this.currentZombieMap = zombieList.get(0).getX();
            this.currentDelay = (zombieList.get(0).getY()) * GameConstants.UPS;
            this.start = 0L;
            this.allZombieList = zombieList;
            this.allZombieList.remove(0);

        } else {

            this.currentZombieMap = new HashMap<>();
        }
    }
    @Override
    public void tick() {
        this.start++;
    }
    @Override
    public boolean isReadyToSpawn() {
        if (this.isOver()) {
            return false;
        }
        return this.start >= this.currentDelay;
    }
    @Override
    public List<Entity> getEntities() {
        if (this.isReadyToSpawn()) {
            final List<Entity> zombieToSpawn = this.generateRandomHorde();
            if (!this.allZombieList.isEmpty()) {
                this.currentZombieMap = this.allZombieList.get(0).getX();
                this.currentDelay = this.allZombieList.get(0).getY() * GameConstants.UPS;
                this.start = 0L;
                this.allZombieList.remove(0);
            } else {

                this.currentZombieMap = Collections.emptyMap();
            }
            return zombieToSpawn;
        } else {
            return Collections.emptyList();
        }
    }
    @Override
    public boolean isOver() {
        return this.currentZombieMap.isEmpty();
    }

    private List<Entity> generateRandomHorde() {
        List<Entity> zombieList = new ArrayList<>();

        for (ZombieType type : this.currentZombieMap.keySet()) {

            List<Zombie> list = Stream.generate(() -> this.newZombie(type)).limit(this.currentZombieMap.get(type))
                    .collect(Collectors.toList());

            zombieList.addAll(list);

        }
        return zombieList;
    }

    private Zombie newZombie(final ZombieType type) {
        switch (type) {
        case BASIC_ZOMBIE:
            return new BasicZombie(this.randomPosition(), this.level);
        case CONE_ZOMBIE:
            return new ConeZombie(this.randomPosition(), this.level);
        case BUCKET_ZOMBIE:
            return new BucketZombie(this.randomPosition(), this.level);
        case FOOTBALL_ZOMBIE:
            return new FootballZombie(this.randomPosition(), this.level);
        default:
            return new BasicZombie(this.randomPosition(), this.level);
        }
    }

    private Vector randomPosition() {
        Random rnd = new Random();

        return Vector.of(WorldConstants.BACKYARD_WIDTH + (WorldConstants.CELL_WIDTH * rnd.nextDouble()),
                WorldConstants.getLane(rnd.nextInt(WorldConstants.CELL_AMOUNT_Y)));
    }
}
