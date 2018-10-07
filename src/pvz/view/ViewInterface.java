package pvz.view;

import pvz.model.entity.Entity;
import pvz.model.entity.plant.PlantType;
import pvz.view.input.InputInterface;
import pvz.controller.constants.Mode;
import pvz.model.GameStatus;

import java.util.List;

/**
 * Interface for a generic view. All these methods are called by controller.
 * 
 */
public interface ViewInterface {

    /**
     * It initializes and start the view.
     *
     */
    void init();

    /**
     * Method to update the view.
     * 
     * @param listEntities
     *            the list of the entities to draw.
     * @param buttonsAffordable
     *            the list of buttons (plant) the user can click.
     * @param currentEnergy
     *            the current energy.
     * @param currentLevel
     *            the current level (useful mainly in arcade mode)
     */
    void render(List<Entity> listEntities, List<PlantType> buttonsAffordable, int currentEnergy, int currentLevel);

    /**
     * Method to notify the view that a level is ended.
     * 
     * @param state
     *            The state of the game (PLAYING, WON, LOST)
     * @param mode
     *            The mode, Arcade or History.
     */
    void notifyLevelEnd(GameStatus state, Mode mode);

    /**
     * Method the controller calls to get all inputs.
     * 
     * @return the list of all inputs
     */
    List<InputInterface> getInput();
}
