package pvz.model.entity.plant;

import java.util.Optional;

import pvz.controller.constants.GameConstants;
import pvz.model.WorldConstants;
import pvz.model.entity.AttackerEntity;
import pvz.model.entity.zombie.Zombie;
import pvz.model.level.LevelInterface;
import pvz.utility.Vector;

/**
 * This is a melee plant that immediately eats a zombie in front of it. However,
 * it takes some time to reload.
 */
public class Chomper extends Plant implements AttackerEntity {

    private static final int CHOMPER_HEALTH = WorldConstants.TOUGHNESS_LOW;
    private static final long CHOMPER_RELOAD_TIME = 42 * GameConstants.UPS;
    private static final double CHOMPER_RANGE = 2 * WorldConstants.CELL_WIDTH;

    private LevelInterface level;
    private long reloadTicks;
    private boolean attacking;

    /**
     * Chomper constructor.
     * 
     * @param position
     *            starting position
     * @param theLevel
     *            chomper's level
     */
    public Chomper(final Vector position, final LevelInterface theLevel) {
        super(position, CHOMPER_HEALTH);
        this.level = theLevel;
        this.reloadTicks = 0;
        this.attacking = false;
    }

    @Override
    public void update() {
        if (this.attacking) {
            this.reloadTicks++;
            while (this.reloadTicks > CHOMPER_RELOAD_TIME) {
                this.reloadTicks -= CHOMPER_RELOAD_TIME;
                this.attacking = false;
            }
            return;
        }

        Optional<Zombie> zombieVictim = this.level.getEntityList().stream() //
                .filter(e -> e instanceof Zombie) //
                .map(e -> (Zombie) e) //
                .filter(e -> this.horizontalDistanceFrom(e) > 0 && this.horizontalDistanceFrom(e) < CHOMPER_RANGE
                        && this.getY() == e.getY()) //
                .findFirst();
        if (zombieVictim.isPresent()) {
            this.attacking = true;
            zombieVictim.get().remove();
        }
    }

    @Override
    public boolean isAttacking() {
        return this.attacking;
    }

    private double horizontalDistanceFrom(final Zombie zombie) {
        return zombie.getX() - this.getX();
    }

}
