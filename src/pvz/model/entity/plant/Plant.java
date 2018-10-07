package pvz.model.entity.plant;

import pvz.model.WorldConstants;
import pvz.model.entity.LivingEntity;
import pvz.utility.Vector;

/**
 * Defines the generic Plant.<br>
 * Specific behavior needs to be implemented in the subclasses.
 */
public abstract class Plant extends LivingEntity {

    /**
     * The energy cost for using this plant.
     */
    private int cost;

    /**
     * Instantiates a plant at the given position.
     * 
     * @param position
     *            plant position
     * @param plantHealth
     *            maximum health
     */
    protected Plant(final Vector position, final int plantHealth) {
        super(position, WorldConstants.CELL_WIDTH, WorldConstants.CELL_WIDTH, plantHealth);
    }

    /**
     * Returns the energy cost of this plant.
     * 
     * @return plant cost
     */
    public int getCost() {
        return this.cost;
    }

}
