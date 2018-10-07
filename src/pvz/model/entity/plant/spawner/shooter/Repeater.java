package pvz.model.entity.plant.spawner.shooter;

import java.util.Arrays;
import java.util.List;

import pvz.controller.constants.GameConstants;
import pvz.model.WorldConstants;
import pvz.model.entity.child.ChildEntity;
import pvz.model.entity.child.projectile.BasicProjectile;
import pvz.model.level.LevelInterface;
import pvz.utility.Vector;

/**
 * This shooter plant fires two basic projectiles at once.
 */
public class Repeater extends ShooterPlant {

    private static final int REPEATER_HEALTH = WorldConstants.TOUGHNESS_NORMAL;
    private static final long REPEATER_SHOOT_TICKS = WorldConstants.BASE_SHOOT_TICKS;
    private static final long REPEATER_SECOND_PROJ_TICKS = GameConstants.UPS / 5;

    private final long secondShotTicks;
    private long elapsedTicks;
    private long secondShotElapsedTicks;
    private boolean shouldShootAgain;

    /**
     * Repeater constructor.
     * 
     * @param position
     *            starting position
     * @param observer
     *            repeater's level
     */
    public Repeater(final Vector position, final LevelInterface observer) {
        super(position, observer, REPEATER_HEALTH, REPEATER_SHOOT_TICKS);
        this.secondShotElapsedTicks = 0;
        this.shouldShootAgain = false;
        this.secondShotTicks = REPEATER_SECOND_PROJ_TICKS;
    }

    @Override
    public void update() {
        if (!this.shouldAttack()) {
            this.elapsedTicks = 0;
            return;
        }

        this.elapsedTicks++;
        while (this.elapsedTicks > this.getShootTicks()) {
            this.getLevel().notify(this);
            this.elapsedTicks -= this.getShootTicks();
            this.shouldShootAgain = true;
        }

        if (this.shouldShootAgain) {
            this.secondShotElapsedTicks++;
            while (this.secondShotElapsedTicks > this.secondShotTicks) {
                this.getLevel().notify(this);
                this.secondShotElapsedTicks -= this.secondShotTicks;
                this.shouldShootAgain = false;
            }
        }
    }

    @Override
    public List<? extends ChildEntity> getChildren() {
        return Arrays.asList(new BasicProjectile(this.getProjStartingPosition(), this.getLevel()));
    }

}
