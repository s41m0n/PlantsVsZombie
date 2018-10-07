package pvz.model.entity;

import pvz.utility.Vector;

/**
 * Defines the base implementation for all entities. The update() method is the
 * only piece of code that needs to be defined separately in every subclass;
 * others are <i>template methods</i>.
 */
public abstract class AbstractEntity implements Entity {

    private static final double COLLISION_OFFSET = 0d;
    private static long entityIDCounter = 0;

    /**
     * Unique ID for this entity.
     */
    private final long id;
    /**
     * Horizontal position in the world.
     */
    private double xPos;
    /**
     * Vertical position in the world.
     */
    private double yPos;
    /**
     * True if entity should be removed from the world, false otherwise.
     */
    private boolean toBeRemoved;
    /**
     * Width of this entity.
     */
    private final double width;
    /**
     * Height of this entity.
     */
    private final double height;

    /**
     * Constructor for an <code>AbstractEntity</code>.<br>
     * Position and dimensions need to be specified for the entity to be
     * correctly placed in the world.
     * 
     * @param position
     *            entity position
     * @param width
     *            entity width
     * @param height
     *            entity height
     */
    protected AbstractEntity(final Vector position, final double width, final double height) {
        this.id = entityIDCounter++;
        this.xPos = position.getX();
        this.yPos = position.getY();
        this.toBeRemoved = false;
        this.width = width;
        this.height = height;
    }

    @Override
    public double getX() {
        return this.xPos;
    }

    @Override
    public double getY() {
        return this.yPos;
    }

    @Override
    public double getWidth() {
        return this.width;
    }

    @Override
    public double getHeight() {
        return this.height;
    }

    @Override
    public abstract void update();

    @Override
    public boolean collidesWith(final Entity other) {
        if (this.xPos == other.getX() && this.yPos == other.getY()) {
            return true;
        }
        if (this.xPos + COLLISION_OFFSET < other.getX() + other.getWidth() - COLLISION_OFFSET
                && this.xPos + this.getWidth() - COLLISION_OFFSET > other.getX() + COLLISION_OFFSET
                && this.yPos + COLLISION_OFFSET < other.getY() + other.getHeight() - COLLISION_OFFSET
                && this.yPos + this.getHeight() - COLLISION_OFFSET > other.getY() + COLLISION_OFFSET) {
            return true;
        }
        return false;
    }

    @Override
    public boolean contains(final Vector point) {
        return point.getX() > this.xPos && point.getX() < this.xPos + this.width && point.getY() > this.yPos
                && point.getY() < this.yPos + this.height;
    }

    @Override
    public boolean shouldBeRemoved() {
        return this.toBeRemoved;
    }

    @Override
    public long getID() {
        return this.id;
    }

    /**
     * Marks this entity as <i>dead</i>, causing the model to remove it at the
     * next update cycle.
     */
    public void remove() {
        this.toBeRemoved = true;
    }

    /**
     * Shifts this entity left by a certain value.
     * 
     * @param deltaX
     *            left shift
     */
    protected void move(final double deltaX) {
        this.xPos -= deltaX;
    }

    /**
     * Shifts this entity up by a certain value.
     * 
     * @param deltaY
     *            up shift
     */
    protected void rise(final double deltaY) {
        this.yPos += deltaY;
    }

}
