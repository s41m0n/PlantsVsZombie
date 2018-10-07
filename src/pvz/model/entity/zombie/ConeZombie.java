package pvz.model.entity.zombie;

import pvz.model.WorldConstants;
import pvz.model.level.LevelInterface;
import pvz.utility.Vector;

/**
 * This is a zombie with a traffic cone on its head. It is a bit tougher than
 * the common zombie.
 */
public class ConeZombie extends Zombie {

    private static final double CONE_ZOMBIE_SPEED = .003d;
    private static final int CONE_ZOMBIE_DAMAGE = WorldConstants.BASE_ZOMBIE_DMG;
    private static final int CONE_ZOMBIE_HEALTH = WorldConstants.TOUGHNESS_NORMAL;

    /**
     * ConeZombie constructor.
     * 
     * @param position
     *            starting position
     * @param observer
     *            cone zombie's level
     */
    public ConeZombie(final Vector position, final LevelInterface observer) {
        super(position, observer, CONE_ZOMBIE_SPEED, CONE_ZOMBIE_DAMAGE, CONE_ZOMBIE_HEALTH);
    }

}
