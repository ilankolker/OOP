/**
 * Represents a collision strategy that adds an additional Heart GameObject to the GameObjectCollection
 * when a collision occurs.
 */
package bricker.brick_strategies;

import bricker.gameobjects.Heart;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * This class represents a collision strategy that adds an additional Heart
 * GameObject to the GameObjectCollection
 */

public class AdditionalHeartCollisionStrategy implements CollisionStrategy {
    private final ImageReader imageReader;
    private final BasicCollisionStrategy basicCollisionStrategy;
    private final GameObjectCollection gameObjectCollection;

    /**
     * Constructs an AdditionalHeartCollisionStrategy object.
     *
     * @param gameObjectCollection the GameObjectCollection to apply the collision strategy to
     * @param imageReader          the ImageReader used to read image files
     * @param bricksCounter        the counter for the number of bricks remaining in the game
     */
    public AdditionalHeartCollisionStrategy(GameObjectCollection gameObjectCollection,
                                            ImageReader imageReader, Counter bricksCounter) {
        this.gameObjectCollection = gameObjectCollection;
        this.imageReader = imageReader;
        this.basicCollisionStrategy = new BasicCollisionStrategy(gameObjectCollection, bricksCounter);
    }

    /**
     * Handles the collision between two GameObjects by adding an additional
     * Heart GameObject to the collection.
     *
     * @param gameObject1   the first GameObject involved in the collision
     * @param gameObject2   the second GameObject involved in the collision
     */
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        basicCollisionStrategy.onCollision(gameObject1, gameObject2);
        // Add a new Heart GameObject to the collection at the center of the brick.
        Renderable heartImg = imageReader.readImage(Constants.HEART_IMAGE_PATH, true);
        Vector2 heartVel = new Vector2(0, Constants.HEART_SPEED);
        Heart heart = new Heart(Vector2.ZERO, Constants.HEART_DIMENSIONS, heartImg);
        heart.setTag(Constants.HEART_TAG);
        heart.setCenter(gameObject1.getCenter());
        heart.setVelocity(heartVel);
        gameObjectCollection.addGameObject(heart);
    }
}
