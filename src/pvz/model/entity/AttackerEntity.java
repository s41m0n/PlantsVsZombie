package pvz.model.entity;

/**
 * Empty interface. It is used to recognize entities that can attack from those
 * who cannot.
 */
public interface AttackerEntity extends Entity {

    /**
     * Checks if this entity is currently performing an attack on the
     * battlefield.
     * 
     * @return true if attacking
     */
    boolean isAttacking();

}
