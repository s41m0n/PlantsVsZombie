package pvz.model.entity.child;

import pvz.model.entity.Entity;

/**
 * Empty interface. All implementing entites won't be able to exist unless their
 * creation is triggered by some other entity.<br>
 * As an example, a Peashooter will schedule the creation of a certain
 * projectile at a fixed rate. Therefore, such projectile becomes by all means a
 * <i>child</i> of the former entity.<br>
 * <br>
 * <i>The Sun entity represents the only exception, since it may also be spawned
 * by the level.</i>
 */
public interface ChildEntity extends Entity {
}
