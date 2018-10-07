package pvz.controller.serializable;

import java.io.Serializable;

/**
 * Basic implementation of score. Each score has a reference to the player and
 * <br>
 * an integer value for the score amount.
 */

public class ScoreImpl implements Score, Serializable {

    private static final long serialVersionUID = 2L;
    private int score;
    private String playerName;

    /**
     * Basic Constructor for ScoreImpl.
     * 
     * @param score scose value.
     * @param playerName player's name.
     */
    public ScoreImpl(final int score, final String playerName) {

        if (score > 0 && !playerName.equals(null)) {
            this.score = score;
            this.playerName = playerName;
        }

    }

    @Override
    public String getPlayer() {
        return this.playerName;
    }

    @Override
    public int getScore() {
        return this.score;
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    @Override
    public boolean equals(final Object obj) {

        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ScoreImpl)) {
            return false;
        }

        Score score = (ScoreImpl) obj;
        return this.getScore() == score.getScore() && this.getPlayer().equals(score.getPlayer());
    }
}