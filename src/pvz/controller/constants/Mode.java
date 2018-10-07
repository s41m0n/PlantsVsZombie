package pvz.controller.constants;

/**
 * This enumeration collects all the game modes.
 *
 */

public enum Mode {

    /**
     * In this mode the player must overcome some levels, each of them will
     * allow you to unlock new plants.
     */
    HISTORY(GamePaths.HISTORY_HIGHSCORES_FILE),

    /**
     * In this mode the player has battle through a series of hordes.The purpose
     * of this level is to defeat more hordes possible.
     */
    ARCADE(GamePaths.ARCADE_HIGHSCORES_FILE);

    private String backUpsPath;

    Mode(final String backUpsPath) {

        this.backUpsPath = backUpsPath;
    }
 
    /**
     * This method returns the path of the backups' file. 
     * @return backups path
     */
    public String getModePath() {
        return this.backUpsPath;
    }

}
