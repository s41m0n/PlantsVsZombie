package pvz.model.entity.plant;

import pvz.model.WorldConstants;
import pvz.utility.Vector;

/**
 * The basic defence plant. It blocks most zombies, forcing them to attack this
 * very plant.
 */
public class Wallnut extends Plant {

    private static final int WALLNUT_HEALTH = WorldConstants.TOUGHNESS_HIGH;

    /**
     * Wallnut constructor.
     * 
     * @param position
     *            starting position
     */
    public Wallnut(final Vector position) {
        super(position, WALLNUT_HEALTH);
    }

    @Override
    public void update() {
        // Do nothing and absorb incoming damage.
    }

}
