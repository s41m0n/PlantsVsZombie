package pvz.controller;

import java.util.Optional;
import java.util.Set;
import pvz.model.entity.plant.PlantType;
import pvz.controller.constants.Mode;
import pvz.controller.datamanager.DataProxy;
import pvz.controller.gameloop.GameLoopProxy;

/**
 * ControllerInterface collects all basic method of a generic controller.
 */
public interface ControllerInterface extends DataProxy, GameLoopProxy {
    /**
     * It quits the game.
     */

    void quit();

    /**
     * It starts the gameLoop specifying the level and plants selected.
     * 
     * @param level 
     * @param plants 
     */
    void startGame(Optional<Integer> level, Set<PlantType> plants);

    /**
     * It sets game's FPS(FRAME-PER-SECOND).
     * 
     * @param fps frame per second.
     */

    void setFPS(int fps);

    /**
     * It returns the current FPS value.
     * 
     * @return fps
     */

    int getFPS();

    /**
     * It sets the current mode.
     * 
     * @param mode game mode.
     */

    void setMode(Mode mode);

}
