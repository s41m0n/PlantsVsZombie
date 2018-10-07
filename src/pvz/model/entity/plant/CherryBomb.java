package pvz.model.entity.plant;

import pvz.controller.constants.GameConstants;
import pvz.model.WorldConstants;
import pvz.model.entity.AttackerEntity;
import pvz.model.entity.zombie.Zombie;
import pvz.model.level.LevelInterface;
import pvz.utility.Vector;

/**
 * This is an explosive plant that deals AoE (area of effect) damage almost
 * immediately before disappearing.
 */
public class CherryBomb extends Plant implements AttackerEntity {

    private static final int CHERRY_BOMB_HEALTH = 1000;
    private static final long CHERRY_EXPLOSION_TICKS = 1 * GameConstants.UPS;
    private static final long CHERRY_DECAY_TICKS = (long) (.5 * GameConstants.UPS);
    private static final double CHERRY_EXPLOSION_RADIUS = WorldConstants.CELL_WIDTH * 1.5;

    private LevelInterface level;
    private long explosionTimer;
    private boolean attacking;

    /**
     * CherryBomb constructor.
     * 
     * @param position
     *            starting position
     * @param theLevel
     *            cherry bomb's level
     */
    public CherryBomb(final Vector position, final LevelInterface theLevel) {
        super(position, CHERRY_BOMB_HEALTH);
        this.level = theLevel;
        this.explosionTimer = 0;
        this.attacking = false;
    }

    @Override
    public void update() {
        this.explosionTimer++;
        if (this.explosionTimer > CHERRY_EXPLOSION_TICKS + CHERRY_DECAY_TICKS) {
            this.remove();
        } else if (this.explosionTimer > CHERRY_EXPLOSION_TICKS && !this.attacking) {
            this.attacking = true;
            this.level.getEntityList().stream() //
                    .filter(e -> e instanceof Zombie) //
                    .map(e -> (Zombie) e) //
                    .filter(e -> this.distanceFrom(e) <= CHERRY_EXPLOSION_RADIUS) //
                    .forEach(e -> e.remove());
        }
    }

    private double distanceFrom(final Zombie zombie) {
        final double xDist = Math.abs(this.getX() - zombie.getX());
        final double yDist = Math.abs(this.getY() - zombie.getY());
        return Math.sqrt(xDist * xDist + yDist * yDist);
    }

    @Override
    public boolean isAttacking() {
        return this.attacking;
    }

}
