package pvz.model;

import java.util.List;

import pvz.model.entity.Entity;

/**
 * This interface defines methods whose execution will be delegated to levels
 * once called on the model. These operations let the Level act on the world's
 * internal logic.
 */
public interface LevelProxy {

    /**
     * Returns the current status of the game level.
     * 
     * @return the game status
     */
    GameStatus getGameStatus();

    /**
     * Tells the level to update its status, moving forward by one tick.
     */
    void update();

    /**
     * Returns a list containing all the entities in the game.
     * 
     * @return active entity list
     */
    List<Entity> getEntityList();

    /**
     * It returns the current level. Each StoryLevel is associated to a number
     * related to the story progress, on the contrary an ArcadeLevel updates its
     * value during the gameplay.
     * 
     * @return current level
     */
    int getCurrentLevel();

}
