package pvz.model;

import pvz.controller.constants.GameConstants;

/**
 * This class contains a series of values describing the metrics of the logical
 * world where the gameplay takes place.<br>
 * It also contains some reference values: these should be took into account
 * when setting various entity parameters.<br>
 * It cannot be instantiated.
 */
public final class WorldConstants {

    /* WORLD DIMENSIONS */

    /**
     * Width of the lawn grid (in cells).
     */
    public static final int CELL_AMOUNT_X = 9;
    /**
     * Height of the lawn grid (in cells).
     */
    public static final int CELL_AMOUNT_Y = 5;
    /**
     * Lawn cell width.
     */
    public static final double CELL_WIDTH = 3d;
    /**
     * Width of the backyard.
     */
    public static final double BACKYARD_WIDTH = CELL_WIDTH * CELL_AMOUNT_X;
    /**
     * Height of the backyard.
     */
    public static final double BACKYARD_HEIGHT = CELL_WIDTH * CELL_AMOUNT_Y;

    /* TOUGHNESS */

    /**
     * Standard toughness for low-defence entities.
     */
    public static final int TOUGHNESS_LOW = 70;
    /**
     * Standard toughness for normal-defence entities.
     */
    public static final int TOUGHNESS_NORMAL = 100;
    /**
     * Standard toughness for high-defence entities.
     */
    public static final int TOUGHNESS_HIGH = 200;

    /* PROJECTILE DAMAGE */

    /**
     * Standard damage dealt by basic projectiles.
     */
    public static final int BASE_PROJ_DMG = 10;

    /* ZOMBIE DAMAGE */

    /**
     * Standard damage dealt by basic zombies.
     */
    public static final int BASE_ZOMBIE_DMG = 10;

    /* SPEED */

    /**
     * Standard travelling speed of basic projectiles.
     */
    public static final double BASIC_PROJ_SPEED = .1d;
    /**
     * Standard walking speed of basic zombies.
     */
    public static final double BASIC_ZOMBIE_SPEED = .003d;
    /**
     * Standard gravity. It only affects Sun entities spawned by Sunflowers.
     */
    public static final double GRAVITY = .002d;
    /**
     * Standard horizontal speed of a Sun spawned by a Sunflower.
     */
    public static final double SUN_SPAWN_SPEED_X = .01d;
    /**
     * Standard vertical speed of a Sun spawned by a Sunflower.
     */
    public static final double SUN_SPAWN_SPEED_Y = .1d;
    /**
     * Standard Sun falling speed from the sky.
     */
    public static final double SUN_FALLING_SPEED = .01d;

    /* COOLDOWNS */

    /**
     * Standard time gap between two projectiles shot by a ShooterPlant.
     */
    public static final long BASE_SHOOT_TICKS = (long) (1.5 * GameConstants.UPS);

    private WorldConstants() {
    }

    /**
     * Returns a vertical position corresponding to a certain lane, offset by a
     * percentage (0 to 1).
     * 
     * @param lane
     *            selected lane (0 to 4)
     * @return vertical position
     */
    public static double getLane(final int lane) {
        if (lane < 0 || lane > 4) {
            throw new IllegalArgumentException("Lane should be an integer value between 0 and 4.");
        }
        return CELL_WIDTH * (lane);
    }

}
