package pvz.model.entity;

import java.util.List;

import pvz.model.entity.child.ChildEntity;

/**
 * The entities implementing this interface must provide a factory method for
 * list of new child entities to be added eventually to the game world.
 */
public interface ChildFactory {

    /**
     * Factory method. Returns a list containing new entities that the
     * ChildFactory wants to see added to the game.
     * 
     * @return new entities list
     */
    List<? extends ChildEntity> getChildren();

}
