package pvz.model.entity.child.projectile;

import pvz.model.WorldConstants;
import pvz.model.level.LevelInterface;
import pvz.utility.Vector;

/**
 * This is the common projectile. It represents the "green pea" shot by most
 * shooter plants.
 */
public class BasicProjectile extends AbstractProjectile {

    private static final int BASIC_PROJ_DMG = WorldConstants.BASE_PROJ_DMG;

    /**
     * BasicProjectile constructor.
     * 
     * @param position
     *            starting position
     * @param observerLevel
     *            projectile's level
     */
    public BasicProjectile(final Vector position, final LevelInterface observerLevel) {
        super(position, observerLevel);
        // this.damage = BASIC_PROJ_DMG;
        this.setDamage(BASIC_PROJ_DMG);
        // this.speed = WorldConstants.BASIC_PROJ_SPEED;
        this.setSpeed(WorldConstants.BASIC_PROJ_SPEED);
    }

}
