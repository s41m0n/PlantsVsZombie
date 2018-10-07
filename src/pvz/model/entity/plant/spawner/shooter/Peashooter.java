package pvz.model.entity.plant.spawner.shooter;

import java.util.Arrays;
import java.util.List;

import pvz.model.WorldConstants;
import pvz.model.entity.child.projectile.BasicProjectile;
import pvz.model.level.LevelInterface;
import pvz.utility.Vector;

/**
 * The most basic shooter plant in the game.
 */
public class Peashooter extends ShooterPlant {

    private static final int PEASHOOTER_HEALTH = WorldConstants.TOUGHNESS_NORMAL;
    private static final long PEASHOOTER_SHOOT_TICKS = WorldConstants.BASE_SHOOT_TICKS;

    /**
     * Peashooter contructor.
     * 
     * @param position
     *            starting position
     * @param observer
     *            peashooter's level
     */
    public Peashooter(final Vector position, final LevelInterface observer) {
        super(position, observer, PEASHOOTER_HEALTH, PEASHOOTER_SHOOT_TICKS);
    }

    @Override
    public List<BasicProjectile> getChildren() {
        return Arrays.asList(new BasicProjectile(this.getProjStartingPosition(), this.getLevel()));
    }

}
