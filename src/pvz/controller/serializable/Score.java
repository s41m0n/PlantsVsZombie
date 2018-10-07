package pvz.controller.serializable;

/**
 * Interface for <code>Score</code> objects. The generic <code>Score</code> can
 * be asked to return its value and the player name.
 */
public interface Score {
    /**
     * Returns the player name to which the score is associated.
     * 
     * @return player name
     */
    String getPlayer();

    /**
     * Returns the score value.
     * 
     * @return score
     */
    int getScore();

}