package pvz.model.entity.plant;

/**
 * This is a list of all the possible plants that can be encountered during the
 * gameplay.
 */
public enum PlantType {
    /**
     * Sunflower.
     */
    SUNFLOWER(50, false),
    /**
     * Peashooter.
     */
    PEASHOOTER(100, true),
    /**
     * Wallnut.
     */
    WALLNUT(50, false),
    /**
     * Repeater.
     */
    REPEATER(200, true),
    /**
     * Snow pea.
     */
    SNOW_PEA(175, true),
    /**
     * Cherry bomb.
     */
    CHERRY_BOMB(150, true),
    /**
     * Potato mine.
     */
    POTATO_MINE(25, true),
    /**
     * Chomper.
     */
    CHOMPER(150, true);

    private int cost;
    private boolean canAttack;

    PlantType(final int plantCost, final boolean canAttack) {
        this.cost = plantCost;
        this.canAttack = canAttack;
    }

    /**
     * Returns the spawning cost of the plants of this type.
     * 
     * @return plant cost
     */
    public int getCost() {
        return this.cost;
    }

    /**
     * Checks if this entity is able to attack.
     * 
     * @return true if this entity can attack
     */
    public boolean canAttack() {
        return this.canAttack;
    }
}