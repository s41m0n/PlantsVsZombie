package pvz.model.entity.child.projectile;

import pvz.model.WorldConstants;
import pvz.model.entity.AbstractEntity;
import pvz.model.entity.child.ChildEntity;
import pvz.model.entity.zombie.Zombie;
import pvz.model.level.LevelInterface;
import pvz.utility.Vector;

/**
 * Provides a basic implementation of the Projectile behavior.
 */
public class AbstractProjectile extends AbstractEntity implements ChildEntity {

    /**
     * Standard projectile dimension.
     */
    protected static final double PROJ_DIM = WorldConstants.CELL_WIDTH / 3;

    /**
     * Travelling speed of the projectile.
     */
    private double speed;
    /**
     * Damage dealt by the projectile.
     */
    private int damage;
    /**
     * The level that the projectile belongs to.
     */
    private LevelInterface level;

    /**
     * AbstractProjectile constructor.
     * 
     * @param position
     *            projectile starting position
     * @param observerLevel
     *            projectile's level
     */
    protected AbstractProjectile(final Vector position, final LevelInterface observerLevel) {
        super(position, PROJ_DIM, PROJ_DIM);
        this.level = observerLevel;
    }

    @Override
    public void update() {
        this.move(-this.getSpeed());
        this.level.getEntityList().stream() //
                .filter(e -> e instanceof Zombie) //
                .map(e -> (Zombie) e) //
                .filter(z -> z.collidesWith(this)) //
                .findFirst() //
                .ifPresent(z -> {
                    z.hurt(this.damage);
                    this.remove();
                });
    }

    /**
     * Returns the speed of the projectile.
     * 
     * @return projectile speed
     */
    protected double getSpeed() {
        return this.speed;
    }

    /**
     * Sets the speed of the projectile.
     * 
     * @param speed
     *            projectile speed
     */
    protected void setSpeed(final double speed) {
        this.speed = speed;
    }

    /**
     * Returns the damage dealt by the projectile.
     * 
     * @return projectile damage
     */
    protected int getDamage() {
        return damage;
    }

    /**
     * Sets the damage dealt by the projectile.
     * 
     * @param damage
     *            projectile damage
     */
    protected void setDamage(final int damage) {
        this.damage = damage;
    }

    /**
     * Returns the level containing this projectile.
     * 
     * @return level
     */
    protected LevelInterface getLevel() {
        return level;
    }

}
