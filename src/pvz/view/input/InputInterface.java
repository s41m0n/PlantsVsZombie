package pvz.view.input;

import java.util.Optional;

import pvz.model.entity.plant.PlantType;

/**
 * Interface for a generic Input.
 *
 */
public interface InputInterface {

    /**
     * Returns the horizontal position of the player's click in the window.
     * 
     * @return x position
     */
    double getX();

    /**
     * Returns the vertical position of the player's click in the window.
     * 
     * @return y position
     */
    double getY();

    /**
     * Method to return the type of the Input.
     * 
     * @return InputType (ADD,REMOVE,HARVEST)
     */
    InputType getInputType();

    /**
     * 
     * @return the plant type of the plant added
     */
    Optional<PlantType> getPlant();

}
