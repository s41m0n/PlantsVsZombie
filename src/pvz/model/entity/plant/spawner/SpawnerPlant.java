package pvz.model.entity.plant.spawner;

import pvz.model.entity.ChildFactory;
import pvz.model.entity.EntityObserver;
import pvz.model.entity.ObservableEntity;
import pvz.model.entity.plant.Plant;
import pvz.model.level.LevelInterface;
import pvz.utility.Vector;

/**
 * Basic definition of a plant capable of spawning other entities, like
 * projectiles or resources.
 */
public abstract class SpawnerPlant extends Plant implements ObservableEntity, ChildFactory {

    /**
     * The level this plant belongs to.
     */
    private LevelInterface level;
    /**
     * The currently elapsed ticks between one spawn and the other.
     */
    private long elapsedTicks;

    /**
     * SpawnerPlant constructor.
     * 
     * @param position
     *            starting position
     * @param observer
     *            spawner plant's level
     * @param plantHealth
     *            maximum health
     */
    protected SpawnerPlant(final Vector position, final EntityObserver observer, final int plantHealth) {
        super(position, plantHealth);
        this.setObserver(observer);
        this.elapsedTicks = 0;
    }

    @Override
    public void setObserver(final EntityObserver observer) {
        this.level = (LevelInterface) observer;
    }

    /**
     * Returns the level containing this entity.
     * 
     * @return level
     */
    protected LevelInterface getLevel() {
        return level;
    }

    /**
     * Returns the currently elapsed ticks after the last spawned entity.
     * 
     * @return elapsed ticks
     */
    protected long getElapsedTicks() {
        return elapsedTicks;
    }

    /**
     * Sets the elapsed ticks after the last spawned entity.
     * 
     * @param elapsedTicks
     *            currently elapsed ticks
     */
    protected void setElapsedTicks(final long elapsedTicks) {
        this.elapsedTicks = elapsedTicks;
    }

    /**
     * Increments the elapsed ticks counter.
     */
    protected void spawnTick() {
        this.elapsedTicks++;
    }

}
