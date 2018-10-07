package pvz.model.entity;

import pvz.utility.Vector;

/**
 * Defines the base living entity.<br>
 * Such an entity has an health value, and therefore can be damaged.
 */
public abstract class LivingEntity extends AbstractEntity {

    /**
     * The entity's current health.
     */
    private int health;
    /**
     * The entity's maximum health.
     */
    private int maxHealth;

    /**
     * Instantiates a living entity at the given position.
     * 
     * @param position
     *            entity position
     * @param width
     *            entity's width
     * @param height
     *            entity's height
     * @param entityHealth
     *            maximum health
     */
    protected LivingEntity(final Vector position, final double width, final double height, final int entityHealth) {
        super(position, width, height);
        this.maxHealth = entityHealth;
        this.health = maxHealth;
    }

    /**
     * Damages this entity by the given damage amount.
     * 
     * @param damage
     *            damage value
     */
    public void hurt(final int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.remove();
        }
    }

    /**
     * Returns the remaining health percentage as a floating point value ranging
     * from 0.0 to 1.0.
     * 
     * @return health percentage
     */
    public double getHealthPercentage() {
        return (double) this.health / this.maxHealth;
    }

}
