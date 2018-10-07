package pvz.model.entity;

import pvz.utility.Vector;

/**
 * This interface describes a generic game entity.<br>
 * A game entity is any object which has an active impact over the course of the
 * game. It has a position and is capable of colliding with other entities.
 */
public interface Entity {

    /**
     * Returns the horizontal position of this <code>Entity</code> relative to
     * the top-left corner of the game field.
     * 
     * @return x position
     */
    double getX();

    /**
     * Returns the vertical position of this <code>Entity</code> relative to the
     * top-left corner of the game field.
     * 
     * @return y position
     */
    double getY();

    /**
     * Returns the width of this entity.
     * 
     * @return width
     */
    double getWidth();

    /**
     * Returns the height of this entity.
     * 
     * @return height
     */
    double getHeight();

    /**
     * Tells the entity to update its status, moving forward by one tick.
     */
    void update();

    /**
     * Returns <code>true</code> if this entity is colliding with the
     * <code>other</code> entity.
     * 
     * @param other
     *            other entity
     * @return true if collision is happening
     */
    boolean collidesWith(Entity other);

    /**
     * Checks if the point passed as an argument is contained in this entity's
     * bounding box.
     * 
     * @param point
     *            coordinates
     * @return true if point is contained
     */
    boolean contains(Vector point);

    /**
     * Returns <code>true</code> if the game state is such that the entity
     * should be no longer active.
     * 
     * @return true if this should be removed
     */
    boolean shouldBeRemoved();

    /**
     * Returns a unique ID associated with the entity.
     * 
     * @return ID
     */
    long getID();

}