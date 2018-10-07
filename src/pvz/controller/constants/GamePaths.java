package pvz.controller.constants;

/**
 * This class collects all game paths.
 *
 */
public final class GamePaths {
    /**
     * Home.
     */
    public static final String HOME = System.getProperty("user.home");
    /**
     * Separator.
     */
    public static final String SEPARATOR = System.getProperty("file.separator");
    /**
     * Plants vs Zombies directory.
     */
    public static final String PVZ_DIR = HOME + SEPARATOR + ".pvz";
    /**
     * Highscores directory.
     */
    public static final String HIGHSCORES_DIR = PVZ_DIR + SEPARATOR + "Highscores";
    /**
     * Players directory.
     */
    public static final String PLAYERSINFO_DIR = PVZ_DIR + SEPARATOR + "Players";
    /**
     * Story highscoes file.
     */
    public static final String HISTORY_HIGHSCORES_FILE = HIGHSCORES_DIR + SEPARATOR + "history_highscores.json";
    /**
     * Arcade highscores file.
     */
    public static final String ARCADE_HIGHSCORES_FILE = HIGHSCORES_DIR + SEPARATOR + "arcade_highscores.json";
    /**
     * Credits file.
     */
    public static final String CREDITS_FILE = "/credits/credits.txt";

    /**
     * Public constructor for
     */
    private GamePaths() {
    }
}
