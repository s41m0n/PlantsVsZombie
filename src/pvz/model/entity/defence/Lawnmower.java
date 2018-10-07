package pvz.model.entity.defence;

import pvz.model.WorldConstants;
import pvz.model.entity.AbstractEntity;
import pvz.model.entity.AttackerEntity;
import pvz.model.entity.zombie.Zombie;
import pvz.model.level.LevelInterface;
import pvz.utility.Vector;

/**
 * The lawnmower is the house's ultimate defence. When a zombie come nearby, it
 * starts going on a straight line, killing all the enemies on that lane.<br>
 * If there is no lawnmower at the end of a lane, the house is vulnerable to
 * zombie attacks, and if an enemy manages to pass, the game is over.
 */
public final class Lawnmower extends AbstractEntity implements AttackerEntity {

    private static final double LAWNMOWER_WIDTH = WorldConstants.CELL_WIDTH;
    private static final double LAWNMOWER_SPEED = .07d;

    private LevelInterface level;
    private boolean triggered;

    /**
     * Lawnmower constructor.
     * 
     * @param position
     *            starting position
     * @param theLevel
     *            lawnmower's level
     */
    public Lawnmower(final Vector position, final LevelInterface theLevel) {
        super(position, LAWNMOWER_WIDTH, LAWNMOWER_WIDTH);
        this.level = theLevel;
        this.triggered = false;
    }

    @Override
    public void update() {
        final int zombieCount = (int) this.level.getEntityList().stream() //
                .filter(e -> e instanceof Zombie) //
                .filter(e -> e.collidesWith(this)) //
                .count();
        if (zombieCount > 0) {
            this.triggered = true;
        }
        if (this.triggered) {
            this.move(-LAWNMOWER_SPEED);
            this.level.getEntityList().stream() //
                    .filter(e -> e instanceof Zombie) //
                    .filter(e -> e.collidesWith(this)) //
                    .map(e -> (Zombie) e) //
                    .forEach(e -> e.remove());
        }
    }

    @Override
    public boolean isAttacking() {
        return this.triggered;
    }

}
