package pvz.model.entity.zombie;

import java.util.Optional;

import pvz.controller.constants.GameConstants;
import pvz.model.WorldConstants;
import pvz.model.entity.AttackerEntity;
import pvz.model.entity.LivingEntity;
import pvz.model.entity.plant.Plant;
import pvz.model.level.LevelInterface;
import pvz.utility.Vector;

/**
 * Defines the generic Zombie.<br>
 * Specific behavior needs to be implemented in the subclasses.
 */
public abstract class Zombie extends LivingEntity implements AttackerEntity {

    /**
     * The zombie's collision box dimension.
     */
    protected static final double ZOMBIE_DIM = WorldConstants.CELL_WIDTH;

    /**
     * The time gap between an attack and the other.
     */
    protected static final long BASIC_ZOMBIE_ATTACK_TICKS = GameConstants.UPS * 1;

    /**
     * The time before the zombie's speed can go back to nnormal after it has
     * been slowed.
     */
    protected static final long SLOWED_TICKS = 5 * GameConstants.UPS;

    /**
     * Zombie speed.
     */
    private double speed;

    /**
     * Zombie damage.
     */
    private int damage;

    /**
     * The level this zombie belongs to.
     */
    private LevelInterface level;

    /**
     * True if this zombie is currently slowed.
     */
    private boolean slowed;

    /**
     * Counter for checking slow decay.
     */
    private long slowTimer;

    /**
     * Currently elapsed ticks between an attack and the other.
     */
    private long elapsedTicks;

    /**
     * True if this zombie is currently attacking.
     */
    private boolean attacking;

    /**
     * Zombie constructor.
     * 
     * @param position
     *            starting position
     * @param theLevel
     *            zombie's level
     * @param zombieSpeed
     *            zombie speed
     * @param zombieDamage
     *            zombie damage
     * @param zombieHealth
     *            maximum health
     */
    protected Zombie(final Vector position, final LevelInterface theLevel,
            final double zombieSpeed, final int zombieDamage, final int zombieHealth) {
        super(position, ZOMBIE_DIM, ZOMBIE_DIM, zombieHealth);
        this.level = theLevel;
        this.speed = zombieSpeed;
        this.damage = zombieDamage;
        this.elapsedTicks = 0;
        this.attacking = false;
        this.slowed = false;
        this.slowTimer = 0;
    }

    @Override
    public void update() {
        Optional<Plant> collidingPlant = this.level.getEntityList().stream() //
                .filter(e -> e instanceof Plant) //
                .filter(e -> e.collidesWith(this)) //
                .map(e -> (Plant) e) //
                .findFirst();

        if (collidingPlant.isPresent()) {
            this.elapsedTicks++;
            this.attacking = true;
            while (this.elapsedTicks >= BASIC_ZOMBIE_ATTACK_TICKS) {
                collidingPlant.get().hurt(this.damage);
                this.elapsedTicks -= BASIC_ZOMBIE_ATTACK_TICKS;
            }
        } else {
            if (this.slowed) {
                this.move(this.speed / 2);
                this.slowTimer++;
            } else {
                this.move(this.speed);
            }
            this.elapsedTicks = 0;
            this.attacking = false;
        }

        if (this.slowTimer >= SLOWED_TICKS) {
            this.slowTimer = 0;
            this.slowed = false;
        }
    }

    @Override
    public boolean isAttacking() {
        return this.attacking;
    }

    /**
     * Slows this zombie.
     */
    public void slow() {
        this.slowed = true;
    }

    /**
     * Returns true if this zombie is currently slowed, false otherwise.
     * 
     * @return true if slowed
     */
    protected boolean isSlowed() {
        return this.slowed;
    }

    /**
     * Sets the slowed status of this zombie.
     * 
     * @param slowed
     *            true if slowed
     */
    protected void setSlowed(final boolean slowed) {
        this.slowed = slowed;
    }

    /**
     * Returns the currently elapsed ticks since this zombie has been slowed.
     * 
     * @return elapsed slow ticks
     */
    protected long getSlowTicks() {
        return this.slowTimer;
    }

    /**
     * Increments the slow timer.
     */
    protected void slowTick() {
        this.slowTimer++;
    }

    /**
     * Resets the slow timer.
     */
    protected void slowReset() {
        this.slowTimer = 0;
    }

    /**
     * Returns the level containing this zombie.
     * 
     * @return level
     */
    protected LevelInterface getLevel() {
        return this.level;
    }

    /**
     * Returns the walking speed of this zombie.
     * 
     * @return speed
     */
    protected double getSpeed() {
        return this.speed;
    }

    /**
     * Sets the walking speed of this zombie.
     * 
     * @param newSpeed
     *            new walking speed
     */
    protected void setSpeed(final double newSpeed) {
        this.speed = newSpeed;
    }

    /**
     * Returns the damage dealt by this zombie.
     * 
     * @return damage
     */
    protected int getDamage() {
        return this.damage;
    }

    /**
     * Marks this zombie as attacking.
     * 
     * @param attacking
     *            true if is attacking
     */
    protected void setAttacking(final boolean attacking) {
        this.attacking = attacking;
    }

    /**
     * Returns the elapsed ticks since the previous attack.
     * 
     * @return elapsed ticks
     */
    protected long getElapsedTicks() {
        return this.elapsedTicks;
    }

    /**
     * Sets the elapsed ticks since the previous attack to a new value.
     * 
     * @param newTicks
     *            new elapsed ticks
     */
    protected void setElapsedTicks(final long newTicks) {
        this.elapsedTicks = newTicks;
    }

    /**
     * Increments the attack ticks counter.
     */
    protected void tick() {
        this.elapsedTicks++;
    }

}
