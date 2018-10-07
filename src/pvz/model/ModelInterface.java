package pvz.model;

import java.util.List;

import pvz.model.entity.plant.PlantType;
import pvz.utility.Vector;

/**
 * This interface describes the generic Model.<br>
 * Any implementation of the <code>Model</code> should be able to compute and
 * return any useful data regarding the game state as time progresses. The Model
 * refers to a coordinate system where the <b>x-axis</b> points <i>right</i>,
 * and the <b>y-axis</b> <i>downwards</i>.
 */
public interface ModelInterface extends LevelProxy {

    /**
     * Places a plant of type <code>plant</code> at the given x and y positions
     * on the grid.<br>
     * The position has to be a percentage of the world's dimension, ranging
     * from 0 to 1.<br>
     * 
     * @param position
     *            plant position
     * @param plant
     *            the type of plant
     */
    void putPlant(Vector position, PlantType plant);

    /**
     * Removes the plant at the given position.<br>
     * The position has to be a percentage of the world's dimension, ranging
     * from 0 to 1.
     * 
     * @param position
     *            plant position
     */
    void removePlant(Vector position);

    /**
     * Tells the model to <i>try</i> and remove the "sun" entity and to collect
     * its resources.<br>
     * The position has to be a percentage of the world's dimension, ranging
     * from 0 to 1.
     * 
     * @param position
     *            sun position
     */
    void harvestEnergy(Vector position);

    /**
     * Returns the amount of energy currently being stored by the player (as an
     * Integer value).
     * 
     * @return current energy
     */
    int getCurrentEnergy();

    /**
     * Returns a list containing all the plants with the current available
     * energy.
     * 
     * @return plant list
     */
    List<PlantType> getAffordablePlants();

}
