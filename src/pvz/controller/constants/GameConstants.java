package pvz.controller.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class collects all static parameters of the game.
 */
public final class GameConstants {
    /**
     * Amount of levels for Story mode.
     */
    public static final int STORY_LEVELS = 5;
    /**
     * Number of basic plants at the beginnig of Story mode.
     */
    public static final int BASIC_PLANTS = 2;
    /**
     * Fps 25.
     */
    public static final int FPS_25 = 25;
    /**
     * Fps 60.
     */
    public static final int FPS_60 = 60;
    /**
     * Fps 120.
     */
    public static final  int FPS_120 = 120;
    /**
     * Ms for pause.
     */
    public static final int PAUSE_MS = 500;
    /**
     * Update per second.
     */
    public static final int UPS = 120;
    /**
     * Ms per update.
     */
    public static final int MS_PER_UPDATE = 1000 / UPS;
    /**
     * List of available fps.
     */
    public static final List<Integer> AVAILABLE_FPS = new ArrayList<>(Arrays.asList(FPS_25, FPS_60, FPS_120));
    /**
     * Available modes in highscores dir.
     */
    public static final List<Mode> AVAILABLE_MODES_FILES = new ArrayList<>(Arrays.asList(Mode.ARCADE));

    /**
     * GameConstants constructor.
     */
    private GameConstants() { }
}
