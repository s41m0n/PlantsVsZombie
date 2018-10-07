package pvz.controller.serializable;
/**
 * This interface collects all methods related to a basic player.
 */
public interface Player {

    /**
     * Returns the player's name.
     * 
     * @return player name
     */
    String getName();

    /**
     * Returns the maximum level reached by the Player in story mode.
     * 
     * @return maximum
     */
    int getLevelProgress();

    /**
     * It upgrades the level progress.
     * 
     */
    void updateHistoryProgress();

}