package pvz.model.level.spawner;

import java.util.List;

import pvz.model.entity.Entity;
/**
 * This interface describes a generic EntitySpawner.
 */
public interface EntitySpawner {
    /**
     * This method advance the internal time of the spawner.
     */
    void tick();
    /**
     * Thi method returns true if the spawner in ready to spawn.
     * @return is ready to spawn.
     */
    boolean isReadyToSpawn();
    /**
     * Returns a list of entity to spawn.
     * @return entityList.
     */
    List<Entity> getEntities();
    /**
     * This method returns true if the spawner has finished the entity to spawn.
     * @return isOver
     */
    boolean isOver();
}
