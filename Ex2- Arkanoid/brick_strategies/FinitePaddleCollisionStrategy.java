
package bricker.brick_strategies;

import bricker.gameobjects.FinitePaddle;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Represents a collision strategy for handling collisions involving a FinitePaddle GameObject.
 */
public class FinitePaddleCollisionStrategy implements CollisionStrategy {
    private final GameObjectCollection gameObjectCollection;
    private final UserInputListener inputListener;
    private final ImageReader imageReader;
    private final BasicCollisionStrategy basicCollisionStrategy;

    /**
     * Constructs a FinitePaddleCollisionStrategy object.
     *
     * @param gameObjectCollection the GameObjectCollection to add the FinitePaddle object to
     * @param inputListener        the UserInputListener used to handle user input for the FinitePaddle
     * @param imageReader          the ImageReader used to read the image files
     * @param windowDimensions     the dimensions of the game window
     * @param bricksCounter        the counter for the number of bricks remaining in the game
     */
    public FinitePaddleCollisionStrategy(GameObjectCollection gameObjectCollection,
                                         UserInputListener inputListener,
                                         ImageReader imageReader, Vector2 windowDimensions,
                                         Counter bricksCounter) {
        this.gameObjectCollection = gameObjectCollection;
        this.basicCollisionStrategy = new BasicCollisionStrategy(gameObjectCollection, bricksCounter);
        this.inputListener = inputListener;
        this.imageReader = imageReader;
    }

    /**
     * Handles the collision between two GameObjects, adding a FinitePaddle object to the
     * GameObjectCollection if none exists.
     *
     * @param gameObject1   the first GameObject involved in the collision
     * @param gameObject2   the second GameObject involved in the collision
     */
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        basicCollisionStrategy.onCollision(gameObject1, gameObject2);
        // Check if a FinitePaddle object already exists in the GameObjectCollection.
        for (GameObject gameObject : gameObjectCollection) {
            if (gameObject.getTag().equals(Constants.FINITE_PADDLE_TAG)) {
                return;
            }
        }
        // Add a FinitePaddle object to the GameObjectCollection if none exists.
        Renderable paddleImg =
                imageReader.readImage(Constants.PADDLE_IMAGE_PATH, true);
        Counter finitePaddleCounter = new Counter(Constants.FINITE_PADDLE_LIVES);
        GameObject finitePaddle = new FinitePaddle(Vector2.ZERO, Constants.PADDLE_DIMENSIONS, paddleImg,
                inputListener, finitePaddleCounter);
        finitePaddle.setCenter(Constants.MIDDLE_OF_WINDOW);
        finitePaddle.setTag(Constants.FINITE_PADDLE_TAG);
        gameObjectCollection.addGameObject(finitePaddle, Layer.DEFAULT);
    }
}
