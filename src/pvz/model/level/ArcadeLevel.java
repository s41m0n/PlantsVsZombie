package pvz.model.level;

import java.util.List;

import pvz.model.entity.Entity;
import pvz.model.level.spawner.HordeGenerator;
import pvz.model.level.spawner.ZombieSpawner;

/**
 * Arcade level.
 */
public class ArcadeLevel extends AbstractLevel {

    /** 
     * Public constructor for ArcadeLevel.
     * @param entityList entity list.
     */
    public ArcadeLevel(final List<Entity> entityList) {
        super(entityList, 1);

        this.setZombieSpawner(new ZombieSpawner(HordeGenerator.getHorde(1), this));
    }

    @Override
    protected void win() {
        this.updateLevelProgress();
        this.setZombieSpawner(new ZombieSpawner(HordeGenerator.getHorde(this.getCurrentLevel()), this));
    }

    private void updateLevelProgress() {
        this.setLevel(this.getCurrentLevel() + 1);
    }

}
