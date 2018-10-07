package pvz.controller.datamanager;


import java.util.Optional;
import pvz.controller.constants.Mode;
import pvz.controller.serializable.Player;
import pvz.controller.serializable.Score;
/**
 * DataManagerInterfaces collects all methods that permits,
 * the communication between controller-gameloop and DataManager.<br>
 */
public interface DataManagerInterface extends DataProxy {
    /**
     * This method updates the currentPlayer's story progress.
     */
    void updatePlayerProgress();

    /**
     * It adds a score to the data manager. The score will be registered into
     * player scores or general scores only if it is an high score.
     * 
     * @param score score value
     * @param mode mode
     */

    void addScore(Score score, Mode mode);

    /**
     * It saves the player informations and the high scores in a file.
     */

    void saveData();
    /**
     * Returns the Optional current player.
     * 
     * @return current player.
     */

    Optional<Player> getCurrentPlayer();

}