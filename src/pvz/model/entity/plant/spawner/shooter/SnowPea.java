package pvz.model.entity.plant.spawner.shooter;

import java.util.Arrays;
import java.util.List;

import pvz.model.WorldConstants;
import pvz.model.entity.child.ChildEntity;
import pvz.model.entity.child.projectile.FrozenProjectile;
import pvz.model.level.LevelInterface;
import pvz.utility.Vector;

/**
 * This shooter plant fires frozen projectiles instead of normal ones. Its
 * attacks slow down the enemies.
 */
public class SnowPea extends ShooterPlant {

    private static final int SNOW_PEA_HEALTH = WorldConstants.TOUGHNESS_NORMAL;
    private static final long SNOW_PEA_SHOOT_TICKS = WorldConstants.BASE_SHOOT_TICKS;

    /**
     * SnowPea constructor.
     * 
     * @param position
     *            starting position
     * @param observer
     *            snow pea's level
     */
    public SnowPea(final Vector position, final LevelInterface observer) {
        super(position, observer, SNOW_PEA_HEALTH, SNOW_PEA_SHOOT_TICKS);
    }

    @Override
    public List<? extends ChildEntity> getChildren() {
        return Arrays.asList(new FrozenProjectile(this.getProjStartingPosition(), this.getLevel()));
    }

}
