package pvz.model.entity.child.resource;

import pvz.model.entity.AbstractEntity;
import pvz.model.entity.child.ChildEntity;
import pvz.utility.Vector;

/**
 * Basic definition of resource. It carries some kind of <i>value</i> that can
 * be used by the player to do certain actions.
 */
public abstract class ResourceEntity extends AbstractEntity implements ChildEntity {

    /**
     * The energy held by this resource.
     */
    private final int energy;

    /**
     * ResourceEntity constructor.
     * 
     * @param position
     *            starting position
     * @param width
     *            entity width
     * @param height
     *            entity height
     * @param energy
     *            energy relased when harvested
     */
    protected ResourceEntity(final Vector position, final double width, final double height, final int energy) {
        super(position, width, height);
        this.energy = energy;
    }

    /**
     * Returns the amount of energy stored in this resource.
     * 
     * @return energy
     */
    public int getEnergy() {
        return this.energy;
    }

}
