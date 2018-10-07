package pvz.model.entity;

/**
 * These entities are capable of firing some kind of events during the game.
 */
public interface ObservableEntity extends Entity {

    /**
     * Sets the observer for this entity.
     * 
     * @param observer
     *            the observer
     */
    void setObserver(EntityObserver observer);

}
