package pvz.model.entity.plant.spawner.shooter;

import java.util.List;

import pvz.model.WorldConstants;
import pvz.model.entity.AttackerEntity;
import pvz.model.entity.child.ChildEntity;
import pvz.model.entity.plant.spawner.SpawnerPlant;
import pvz.model.entity.zombie.Zombie;
import pvz.model.level.LevelInterface;
import pvz.utility.Vector;

/**
 * Shooter plants have the ability to fire projectiles when a zombie comes on
 * their lane.
 */
public abstract class ShooterPlant extends SpawnerPlant implements AttackerEntity {

    /**
     * The position where projectiles are first instantiated.
     */
    private final Vector projStartingPosition;
    /**
     * The time between a projectile and the other.
     */
    private final long shootTicks;
    /**
     * True if this plant is attacking, false otherwise.
     */
    private boolean attacking;

    private static final double WIDTH_MULTIPLIER = 1d / 14d;

    /**
     * ShooterPlant constructor.
     * 
     * @param position
     *            plant position
     * @param observer
     *            shooter plant's level
     * @param plantHealth
     *            maximum health
     * @param plantShootTicks
     *            shoot cooldown
     */
    protected ShooterPlant(final Vector position, final LevelInterface observer, final int plantHealth,
            final long plantShootTicks) {
        super(position, observer, plantHealth);
        this.attacking = false;
        this.projStartingPosition = Vector.of(this.getX() + this.getWidth(),
                this.getY() + this.getWidth() * WIDTH_MULTIPLIER);
        this.shootTicks = plantShootTicks;
    }

    @Override
    public boolean isAttacking() {
        return this.attacking;
    }

    @Override
    public abstract List<? extends ChildEntity> getChildren();

    @Override
    public void update() {
        if (!this.shouldAttack()) {
            this.setElapsedTicks(0);
            return;
        }
        this.spawnTick();
        while (this.getElapsedTicks() > this.shootTicks) {
            this.getLevel().notify(this);
            this.setElapsedTicks(this.getElapsedTicks() - this.shootTicks);
        }
    }

    /**
     * Checks if the entity should attack, based on the world's conditions.
     * 
     * @return true if should attack
     */
    protected boolean shouldAttack() {
        final int zombieCount = (int) this.getLevel().getEntityList().stream() //
                .filter(e -> e instanceof Zombie) //
                .filter(e -> e.contains(Vector.of(e.getX() + e.getWidth() / 2, this.getY() + this.getWidth() / 2))) //
                .filter(e -> e.getX() <= WorldConstants.BACKYARD_WIDTH) //
                .count();
        if (zombieCount > 0) {
            this.attacking = true;
            return true;
        }

        this.attacking = false;
        return false;
    }

    /**
     * Returns the position at which every projectile is created.
     * 
     * @return projectile starting position
     */
    protected Vector getProjStartingPosition() {
        return projStartingPosition;
    }

    /**
     * Returns the time gap beetween a projectile and the other.
     * 
     * @return projectile time gap
     */
    protected long getShootTicks() {
        return shootTicks;
    }

}
