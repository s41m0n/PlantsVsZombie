package pvz.model.entity.zombie;

import pvz.model.WorldConstants;
import pvz.model.level.LevelInterface;
import pvz.utility.Vector;

/**
 * This is a zombie with a bucket on his head. It is generally much tougher than
 * common zombies.
 */
public class BucketZombie extends Zombie {

    private static final double BUCKET_ZOMBIE_SPEED = .003d;
    private static final int BUCKET_ZOMBIE_DAMAGE = WorldConstants.BASE_ZOMBIE_DMG;
    private static final int BUCKET_ZOMBIE_HEALTH = WorldConstants.TOUGHNESS_HIGH;

    /**
     * BucketZombie constructor.
     * 
     * @param position
     *            starting position
     * @param theLevel
     *            zombie's level
     */
    public BucketZombie(final Vector position, final LevelInterface theLevel) {
        super(position, theLevel, BUCKET_ZOMBIE_SPEED, BUCKET_ZOMBIE_DAMAGE,
                BUCKET_ZOMBIE_HEALTH);
    }

}
