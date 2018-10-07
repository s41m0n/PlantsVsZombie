package pvz.model.level.spawner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import pvz.controller.constants.GameConstants;
import pvz.model.WorldConstants;
import pvz.model.entity.Entity;
import pvz.model.entity.child.resource.Sun;
import pvz.utility.Vector;
/**
 * This class has to dispence suns.
 */
public class EnergySpawner implements EntitySpawner {

    private static final int ENERGY_DELAY = 10 * GameConstants.UPS;
    private long start;
    /**
     * Public constructor for EnergySpawner.
     */
    public EnergySpawner() {
        this.start = 0L;
    }

    @Override
    public void tick() {
        this.start++;
    }

    @Override
    public boolean isReadyToSpawn() {
        return this.start >= ENERGY_DELAY;
    }

    @Override
    public List<Entity> getEntities() {
        if (this.isReadyToSpawn()) {
            Random rnd = new Random();
            this.start = 0L;
            final Double yCoord = -WorldConstants.CELL_WIDTH / 2;
            final Double xCoord = (WorldConstants.BACKYARD_WIDTH - WorldConstants.CELL_WIDTH) * rnd.nextDouble();

            return new ArrayList<>(Arrays.asList(new Sun(Vector.of(xCoord, yCoord), true)));
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean isOver() {
        return false;
    }

}
