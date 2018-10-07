package pvz.model.level;

import pvz.model.LevelProxy;
import pvz.model.entity.EntityObserver;

/**
 * This interface describes the general functionalities of a Level.<br>
 * A level handles the information regarding the waves of enemies (zombies) to
 * be spawned in time, the entities - in particular, those who may spawn other
 * entities, e.g. Sunflowers - and are supposed to keep track of the progression
 * of the game - namely, the game status.<br>
 * <br>
 * A level should only take care of the part of the game logic that is
 * <i>independent from the user input</i>. In fact, the insertion and deletion
 * of Plants in the game world is managed <b>separately</b> by the Model, which,
 * by the way, may also access the entities' information.<br>
 * <br>
 * Lastly, the level handles some certain events generated during the fight. The
 * entities who fire these events will be <i>observed</i> by the implementation
 * of this interface.
 */
public interface LevelInterface extends LevelProxy, EntityObserver {
}
