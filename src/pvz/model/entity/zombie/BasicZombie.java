package pvz.model.entity.zombie;

import pvz.model.WorldConstants;
import pvz.model.level.LevelInterface;
import pvz.utility.Vector;

/**
 * This is the common zombie.
 */
public class BasicZombie extends Zombie {

    private static final double BASIC_ZOMBIE_SPEED = .003d;
    private static final int BASIC_ZOMBIE_DAMAGE = WorldConstants.BASE_ZOMBIE_DMG;
    private static final int BASIC_ZOMBIE_HEALTH = WorldConstants.TOUGHNESS_LOW;

    /**
     * BasicZombie constructor.
     * 
     * @param position
     *            starting position
     * @param theLevel
     *            zombie's level
     */
    public BasicZombie(final Vector position, final LevelInterface theLevel) {
        super(position, theLevel, BASIC_ZOMBIE_SPEED, BASIC_ZOMBIE_DAMAGE,
                BASIC_ZOMBIE_HEALTH);
    }

}
