
package bricker.brick_strategies;

import danogl.GameObject;
/**
 * Interface for defining collision strategies between GameObjects.
 */
public interface CollisionStrategy {
    /**
     * Handles the collision between two GameObjects.
     *
     * @param gameObject1   the first GameObject involved in the collision
     * @param gameObject2   the second GameObject involved in the collision
     */
    void onCollision(GameObject gameObject1, GameObject gameObject2);
}
