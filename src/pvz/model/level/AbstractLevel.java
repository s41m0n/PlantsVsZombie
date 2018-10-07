package pvz.model.level;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pvz.model.GameStatus;
import pvz.model.WorldConstants;
import pvz.model.entity.Entity;
import pvz.model.entity.ObservableEntity;
import pvz.model.entity.defence.Lawnmower;
import pvz.model.entity.plant.spawner.SpawnerPlant;
import pvz.model.entity.zombie.Zombie;
import pvz.model.level.spawner.EnergySpawner;
import pvz.model.level.spawner.EntitySpawner;
import pvz.model.level.spawner.ZombieSpawner;
import pvz.utility.Vector;
/**
 * This class define the basic behavior of a level.
 */
public abstract class AbstractLevel implements LevelInterface {

    private static final Double GAMEOVER_LINE = -WorldConstants.CELL_WIDTH;
    private GameStatus gameStatus;
    private List<Entity> entityList;
    private List<Lawnmower> lawnmowers;
    private List<Entity> newEntitiesBuffer = new ArrayList<>();
    private EntitySpawner zombieSpawner;
    private EntitySpawner energySpawner;
    private int level;

    /**
     * Constructor  for Abstract level.
     * @param entityList list of entity.
     * @param level level value.
     */
    public AbstractLevel(final List<Entity> entityList, final  int level) {
        this.gameStatus = GameStatus.PLAYING;
        this.entityList = entityList;
        this.level = level;
        this.energySpawner = new EnergySpawner();

        final Double xPos = -(WorldConstants.CELL_WIDTH);
        this.lawnmowers = Stream.iterate(0, e -> e + 1).limit(WorldConstants.CELL_AMOUNT_Y)
                .map(i -> new Lawnmower(Vector.of(xPos, i * WorldConstants.CELL_WIDTH), this)).collect(Collectors.toList());
        this.entityList.addAll(this.lawnmowers);
    }

    @Override
    public GameStatus getGameStatus() {
        return this.gameStatus;
    }
    @Override
    public void update() {
        this.entityList.addAll(newEntitiesBuffer);
        this.newEntitiesBuffer.clear();
        this.entityList.removeIf(e -> e.shouldBeRemoved());
        this.entityList.forEach(e -> e.update());

        if (this.getGameStatus().equals(GameStatus.PLAYING)) {
            if (this.zombieSpawner.isOver()) {
                final List<Entity> zombieList = this.getEntityList().stream().filter(e -> e instanceof Zombie)
                        .collect(Collectors.toList());
                if (zombieList.isEmpty()) {
                    this.win();
                }

            } else {
                this.zombieSpawner.tick();
                if (this.zombieSpawner.isReadyToSpawn()) {
                    this.newEntitiesBuffer.addAll(this.zombieSpawner.getEntities());
                }
            }

            boolean res = this.getEntityList().stream().filter(e -> e instanceof Zombie)
                    .anyMatch(e -> e.getX() <= GAMEOVER_LINE);

            if (res) {
                this.setGameStatus(GameStatus.LOST);
            }

            this.energySpawner.tick();
            if (this.energySpawner.isReadyToSpawn()) {
                this.newEntitiesBuffer.addAll(this.energySpawner.getEntities());
            }
        }
    }

    @Override
    public void notify(final ObservableEntity subject) {
        if (subject instanceof SpawnerPlant) {
            this.newEntitiesBuffer.addAll(((SpawnerPlant) subject).getChildren());
        }
    }

    @Override
    public List<Entity> getEntityList() {
        return this.entityList;
    }
    @Override
    public int getCurrentLevel() {
        return this.level;
    }
    /**
     * This method sets the game status.
     * @param gameStatus game status.
     */
    protected void setGameStatus(final GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }
    /**
     * This method must implements the behaviour of the level when the player defeat an horde of zombies.
     */
    protected abstract void win();
    /**
     * ZombieSpawner setter.
     * @param zombieSpawner zombie spawner.
     */
    protected void setZombieSpawner(final ZombieSpawner zombieSpawner) {
        this.zombieSpawner = zombieSpawner;
    }
    /**
     * Level value setter.
     * @param level level value
     */
    protected void setLevel(final int level) {
        this.level = level;
    }

}
