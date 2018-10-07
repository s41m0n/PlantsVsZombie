package pvz.controller.serializable;

import java.io.Serializable;

import pvz.controller.constants.GameConstants;

/**
 * Basic implementation of Player.<br>
 * Each player is identified by a name and stores his progress in the Story mode.
 *
 */
public class PlayerImpl implements Player, Serializable {

    private static final long serialVersionUID = 1L;

    private String playerName;
    private int levelProgress;
    /**
     * Basic constructor for player.
     * @param playerName player's name.
     */
    public PlayerImpl(final String playerName) {
        this.playerName = playerName;
        this.levelProgress = 1;

    }

    @Override
    public String getName() {
        return this.playerName;
    }

    @Override
    public int getLevelProgress() {
        return this.levelProgress;
    }

    @Override
    public void updateHistoryProgress() {
        if (this.levelProgress < GameConstants.STORY_LEVELS) {
            this.levelProgress++;
        }
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
        if (!(obj instanceof PlayerImpl)) {
            return false;
        }

        Player player = (PlayerImpl) obj;
        return this.playerName.equals(player.getName());
    }

}
