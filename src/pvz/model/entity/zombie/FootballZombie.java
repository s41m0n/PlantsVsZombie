package pvz.model.entity.zombie;

import java.util.Optional;

import pvz.model.WorldConstants;
import pvz.model.entity.plant.CherryBomb;
import pvz.model.entity.plant.Plant;
import pvz.model.level.LevelInterface;
import pvz.utility.Vector;

/**
 * This zombie sprints towards a plant, destroying the first which he runs into.
 * After that, the zombie becomes slow as usual.
 */
public class FootballZombie extends Zombie {

    private static final double FOOTBALL_ZOMBIE_HIGH_SPEED = .015d;
    private static final double FOOTBALL_ZOMBIE_LOW_SPEED = .003d;
    private static final int FOOTBALL_ZOMBIE_DAMAGE = WorldConstants.BASE_ZOMBIE_DMG;
    private static final int FOOTBALL_ZOMBIE_HEALTH = WorldConstants.TOUGHNESS_HIGH;

    private boolean finishedSprint;

    /**
     * FootballZombie constructor.
     * 
     * @param position
     *            starting position
     * @param theLevel
     *            football zombie's level
     */
    public FootballZombie(final Vector position, final LevelInterface theLevel) {
        super(position, theLevel, FOOTBALL_ZOMBIE_HIGH_SPEED, FOOTBALL_ZOMBIE_DAMAGE,
                FOOTBALL_ZOMBIE_HEALTH);
        this.finishedSprint = false;
    }

    @Override
    public void update() {
        if (this.finishedSprint) {
            this.setSpeed(FOOTBALL_ZOMBIE_LOW_SPEED);
            Optional<Plant> collidingPlant = this.getLevel().getEntityList().stream() //
                    .filter(e -> e instanceof Plant) //
                    .filter(e -> e.collidesWith(this)) //
                    .map(e -> (Plant) e) //
                    .findFirst();

            if (collidingPlant.isPresent()) {
                this.tick();
                this.setAttacking(true);
                while (this.getElapsedTicks() >= BASIC_ZOMBIE_ATTACK_TICKS) {
                    collidingPlant.get().hurt(this.getDamage());
                    this.setElapsedTicks(this.getElapsedTicks() - BASIC_ZOMBIE_ATTACK_TICKS);
                }
                return;
            } else {
                if (this.isSlowed()) {
                    this.move(this.getSpeed() / 2);
                    this.slowTick();
                } else {
                    this.move(this.getSpeed());
                }
                this.setElapsedTicks(0);
                this.setAttacking(false);
            }

            if (this.getSlowTicks() >= SLOWED_TICKS) {
                this.slowReset();
                this.setSlowed(false);
            }
        } else {
            this.move(this.getSpeed());
            this.getLevel().getEntityList().stream() //
                    .filter(e -> e instanceof Plant && !(e instanceof CherryBomb)) //
                    .filter(e -> e.collidesWith(this)) //
                    .findFirst() //
                    .ifPresent(p -> {
                        this.finishedSprint = true;
                        ((Plant) p).remove();
                    });
        }
    }

}
