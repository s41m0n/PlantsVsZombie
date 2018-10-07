package pvz.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import pvz.model.entity.Entity;
import pvz.model.entity.child.resource.ResourceEntity;
import pvz.model.entity.plant.CherryBomb;
import pvz.model.entity.plant.Chomper;
import pvz.model.entity.plant.Plant;
import pvz.model.entity.plant.PlantType;
import pvz.model.entity.plant.PotatoMine;
import pvz.model.entity.plant.Wallnut;
import pvz.model.entity.plant.spawner.Sunflower;
import pvz.model.entity.plant.spawner.shooter.Peashooter;
import pvz.model.entity.plant.spawner.shooter.Repeater;
import pvz.model.entity.plant.spawner.shooter.SnowPea;
import pvz.model.level.ArcadeLevel;
import pvz.model.level.LevelInterface;
import pvz.model.level.StoryLevel;
import pvz.utility.Vector;

/**
 * Model class. <br>
 * This component holds information about the active entities and the current
 * level. The Model itself is capable of responding to the user's requests, such
 * as a harvesting energy, putting plants, or removing them.
 */
public class Model implements ModelInterface {

    private List<Entity> entities;
    private LevelInterface level;
    private int energy;
    private Set<PlantType> selected;

    private static final int INITIAL_ENERGY = 50;

    /**
     * This constructor instantiates an Arcade Mode model. There will be only
     * one level, with randomly generated enemy waves.
     * 
     * @param selectedPlants
     *            plants selected by the user
     */
    public Model(final Set<PlantType> selectedPlants) {
        this.entities = new ArrayList<Entity>();
        this.energy = INITIAL_ENERGY;
        this.level = new ArcadeLevel(this.entities);
        this.selected = selectedPlants;
    }

    /**
     * This constructor instantiates a Story mode model. The integer passed as
     * an argument is the number of the level that will be loaded (level
     * progression is supposed to be handled by the client).
     * 
     * @param level
     *            the level to be loaded
     * @param selectedPlants
     *            plants selected by the user
     */
    public Model(final int level, final Set<PlantType> selectedPlants) {
        this.entities = new ArrayList<Entity>();
        this.energy = INITIAL_ENERGY;
        this.level = new StoryLevel(level, this.entities);
        this.selected = selectedPlants;
    }

    @Override
    public List<Entity> getEntityList() {
        return new ArrayList<Entity>(this.entities);
    }

    @Override
    public void update() {
        this.level.update();
    }

    @Override
    public void putPlant(final Vector position, final PlantType plant) {
        final double plantX = this.transformPositionX(position.getX());
        final double plantY = this.transformPositionY(position.getY());

        if (this.energy < plant.getCost()) {
            return;
        }

        final int selectedCellCount = (int) this.entities.stream() //
                .filter(e -> e instanceof Plant) //
                .filter(e -> e.contains(Vector.of(position.getX() * WorldConstants.BACKYARD_WIDTH,
                        position.getY() * WorldConstants.BACKYARD_HEIGHT))) //
                .count();

        if (selectedCellCount > 0) {
            return;
        }

        switch (plant) {
        case SUNFLOWER:
            this.entities.add(new Sunflower(Vector.of(plantX, plantY), this.level));
            break;
        case PEASHOOTER:
            this.entities.add(new Peashooter(Vector.of(plantX, plantY), this.level));
            break;
        case WALLNUT:
            this.entities.add(new Wallnut(Vector.of(plantX, plantY)));
            break;
        case REPEATER:
            this.entities.add(new Repeater(Vector.of(plantX, plantY), this.level));
            break;
        case SNOW_PEA:
            this.entities.add(new SnowPea(Vector.of(plantX, plantY), this.level));
            break;
        case CHERRY_BOMB:
            this.entities.add(new CherryBomb(Vector.of(plantX, plantY), this.level));
            break;
        case POTATO_MINE:
            this.entities.add(new PotatoMine(Vector.of(plantX, plantY), this.level));
            break;
        case CHOMPER:
            this.entities.add(new Chomper(Vector.of(plantX, plantY), this.level));
            break;
        default:
            break;
        }

        this.energy -= plant.getCost();
    }

    @Override
    public void removePlant(final Vector position) {
        final double plantX = this.transformPositionX(position.getX());
        final double plantY = this.transformPositionY(position.getY());
        this.entities.removeIf(e -> e instanceof Plant && e.getX() == plantX && e.getY() == plantY);
    }

    @Override
    public void harvestEnergy(final Vector position) {
        final double resourceX = position.getX() * WorldConstants.BACKYARD_WIDTH;
        final double resourceY = position.getY() * WorldConstants.BACKYARD_HEIGHT;
        this.entities.stream() //
                .filter(e -> e instanceof ResourceEntity) //
                .filter(e -> e.contains(Vector.of(resourceX, resourceY))) //
                .map(e -> (ResourceEntity) e) //
                .peek(e -> this.energy += e.getEnergy()) //
                .forEach(e -> e.remove());
    }

    @Override
    public int getCurrentEnergy() {
        return this.energy;
    }

    @Override
    public GameStatus getGameStatus() {
        return this.level.getGameStatus();
    }

    @Override
    public int getCurrentLevel() {
        return this.level.getCurrentLevel();
    }

    @Override
    public List<PlantType> getAffordablePlants() {
        return Arrays.asList(PlantType.values()).stream() //
                .filter(p -> this.selected.contains(p)) //
                .filter(p -> p.getCost() <= this.energy) //
                .collect(Collectors.toList());
    }

    private double transformPositionX(final double coordX) {
        return transformPosition(coordX, true);
    }

    private double transformPositionY(final double coordY) {
        return transformPosition(coordY, false);
    }

    private double transformPosition(final double coord, final boolean isHorizontal) {
        final double scale = isHorizontal ? WorldConstants.BACKYARD_WIDTH : WorldConstants.BACKYARD_HEIGHT;
        final double chosenPosition = coord * scale;

        int cellIndex = 0;
        while (cellIndex < WorldConstants.CELL_AMOUNT_X
                && Math.abs(chosenPosition - cellIndex * WorldConstants.CELL_WIDTH) >= WorldConstants.CELL_WIDTH) {
            cellIndex++;
        }
        return cellIndex * WorldConstants.CELL_WIDTH;
    }

}
