package pvz.model.entity.plant.spawner;

import java.util.Arrays;
import java.util.List;

import pvz.controller.constants.GameConstants;
import pvz.model.WorldConstants;
import pvz.model.entity.child.ChildEntity;
import pvz.model.entity.child.resource.Sun;
import pvz.model.level.LevelInterface;
import pvz.utility.Vector;

/**
 * This plant spawns a single Sun at a fixed rate in time.
 */
public class Sunflower extends SpawnerPlant {

    private static final int SUNFLOWER_HEALTH = WorldConstants.TOUGHNESS_LOW;
    private static final long SUN_SPAWN_TICKS = 12 * GameConstants.UPS;

    private long elapsedTicks;

    /**
     * Sunflower constructor.
     * 
     * @param position
     *            starting position
     * @param observer
     *            sunflower's level
     */
    public Sunflower(final Vector position, final LevelInterface observer) {
        super(position, observer, SUNFLOWER_HEALTH);
    }

    @Override
    public void update() {
        this.elapsedTicks++;
        while (this.elapsedTicks > SUN_SPAWN_TICKS) {
            this.getLevel().notify(this);
            this.elapsedTicks -= SUN_SPAWN_TICKS;
        }
    }

    @Override
    public List<ChildEntity> getChildren() {
        return Arrays.asList(new Sun(Vector.of(this.getX(), this.getY()), false));
    }

}
