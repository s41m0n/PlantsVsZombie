package pvz.model.entity.plant;

import java.util.Optional;

import pvz.controller.constants.GameConstants;
import pvz.model.WorldConstants;
import pvz.model.entity.AttackerEntity;
import pvz.model.entity.zombie.Zombie;
import pvz.model.level.LevelInterface;
import pvz.utility.Vector;

/**
 * This plant explodes when an enemy steps on it, killing that enemy.
 */
public class PotatoMine extends Plant implements AttackerEntity {

    private static final int POTATO_MINE_HEALTH = WorldConstants.TOUGHNESS_LOW;
    private static final long POTATO_DECAY_TICKS = (long) (.5 * GameConstants.UPS);

    private LevelInterface level;
    private long explosionTimer;
    private boolean attacking;

    /**
     * PotatoMine constructor.
     * 
     * @param position
     *            starting position
     * @param theLevel
     *            potato mine's level
     */
    public PotatoMine(final Vector position, final LevelInterface theLevel) {
        super(position, POTATO_MINE_HEALTH);
        this.level = theLevel;
        this.explosionTimer = 0;
        this.attacking = false;
    }

    @Override
    public void update() {
        if (this.isAttacking()) {
            this.explosionTimer++;
            if (this.explosionTimer > POTATO_DECAY_TICKS) {
                this.remove();
            }
            return;
        }

        Optional<Zombie> collidingZombie = this.level.getEntityList().stream() //
                .filter(e -> e instanceof Zombie) //
                .filter(e -> e.collidesWith(this)) //
                .map(e -> (Zombie) e) //
                .findFirst();
        if (collidingZombie.isPresent()) {
            this.attacking = true;
            collidingZombie.get().remove();
        }
    }

    @Override
    public boolean isAttacking() {
        return this.attacking;
    }

}
