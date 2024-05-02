package bricker.brick_strategies;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

/**
 * Represents a basic collision strategy that removes a GameObject from the GameObjectCollection
 * when a collision occurs.
 */
public class BasicCollisionStrategy implements CollisionStrategy {


    private final GameObjectCollection gameObjectCollection;
    private final Counter bricksCounter;


    /**
     * Constructs a BasicCollisionStrategy object.
     *
     * @param gameObjectCollection the GameObjectCollection to apply the collision strategy to
     */
    public BasicCollisionStrategy(GameObjectCollection gameObjectCollection, Counter bricksCounter) {
        this.gameObjectCollection = gameObjectCollection;
        this.bricksCounter = bricksCounter;
    }

    /**
     * Handles the collision between two GameObjects by removing one of them from the GameObjectCollection.
     *
     * @param gameObject1   the first GameObject involved in the collision
     * @param gameObject2   the second GameObject involved in the collision
     */
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        if (gameObjectCollection.removeGameObject(gameObject1, Layer.STATIC_OBJECTS)) {
            bricksCounter.decrement();
        }
    }
}
