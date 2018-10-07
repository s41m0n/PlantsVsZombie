package pvz.model.entity;

/**
 * Defines the basic behavior of a class that is able to handle events fired by
 * an entity.<br>
 * The implementor shall take action according to the type calling class.
 */
public interface EntityObserver {

    /**
     * Informs the observer that something has occurred and needs to be
     * handled.<br>
     * A reference to the calling object is passed as an argument, to help the
     * observer decide what to do.
     * 
     * @param subject
     *            observed entity
     */
    void notify(ObservableEntity subject);

}
