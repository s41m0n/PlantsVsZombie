package pvz.model.entity.child.projectile;

import pvz.model.WorldConstants;
import pvz.model.entity.zombie.Zombie;
import pvz.model.level.LevelInterface;
import pvz.utility.Vector;

/**
 * This is the projectile shot by Snowpeas. It slows down the enemy.
 */
public class FrozenProjectile extends AbstractProjectile {

    private static final int FROZEN_PROJ_DMG = WorldConstants.BASE_PROJ_DMG;

    /**
     * FrozenProjectile constructor.
     * 
     * @param position
     *            starting position
     * @param observerLevel
     *            frozen projectile's level
     */
    public FrozenProjectile(final Vector position, final LevelInterface observerLevel) {
        super(position, observerLevel);
        this.setDamage(FROZEN_PROJ_DMG);
        this.setSpeed(WorldConstants.BASIC_PROJ_SPEED);
    }

    @Override
    public void update() {
        this.move(-this.getSpeed());
        this.getLevel().getEntityList().stream() //
                .filter(e -> e instanceof Zombie) //
                .map(e -> (Zombie) e) //
                .filter(z -> z.collidesWith(this)) //
                .findFirst() //
                .ifPresent(z -> {
                    z.slow();
                    z.hurt(this.getDamage());
                    this.remove();
                });
    }

}
