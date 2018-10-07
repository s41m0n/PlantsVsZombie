package pvz.controller.datamanager;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import pvz.controller.constants.Mode;
import pvz.controller.serializable.Score;
import pvz.model.entity.plant.PlantType;

/**
 * DataProxy collects all methods that permits the communication with
 * DataManager from external components.<br>
 * All requests are accepted by the controller that transfers the real execution
 * to the DataManager.
 */
public interface DataProxy {

    /**
     * This method sets the current player.
     * 
     * @param playerName player's name.
     */

    void loadPlayer(String playerName);

    /**
     * It registers a new player only if player name is not already used.
     * 
     * @param playerName player's name.
     * @return isRegistered 
     */

    boolean registerPlayer(String playerName);

    /**
     * It returns a list of current player's high scores for each mode.
     * 
     * @param playerName player's name.
     * @param mode mode.
     * @return current player's high scores
     */

    List<Score> getPlayerHighScores(String playerName, Mode mode);

    /**
     * It returns the ranking of all players' scores.
     * 
     * @return allPlayersHighScores
     */

    Map<Mode, List<Score>> getHighScores();

    /**
     * Returns the list of all registered players.
     * 
     * @return registered players
     */
    List<String> getRegisteredPlayers();

    /**
     * Returns game's credits.
     * 
     * @return credits
     * @throws IOException exception.
     */
    List<String> getCredits() throws IOException;

    /**
     * This method removes the informations of a registered player.
     * @param playerName player's name.
     */
    void removePlayer(String playerName);

    /**
     * This method returns the current player's unlocked plants.<br>
     * It can be used only if a player is set.
     * @return plants unlocked;
     */
    List<PlantType> getPlantsUnlocked();

    /**
     * This method returns the amount of unlocked storyLevels of the current
     * player.
     * It can be used only if a player is set.
     * 
     * @return levels unlocked
     */
    int getLevelsUnlocked();

}
