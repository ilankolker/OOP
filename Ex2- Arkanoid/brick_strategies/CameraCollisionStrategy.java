package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Represents a collision strategy that handles camera creation and behavior when a collision occurs.
 */
public class CameraCollisionStrategy implements CollisionStrategy {

    private final float WINDOW_DIMENSIONS_FACTOR = 1.2f;
    private final GameManager gameManager;
    private final BasicCollisionStrategy basicCollisionStrategy;
    private Ball ball;
    private final Camera camera;

    /**
     * Constructs a CameraCollisionStrategy object.
     *
     * @param gameObjectCollection the GameObjectCollection to apply the collision strategy to
     * @param windowDimensions     the dimensions of the game window
     * @param gameManager          the GameManager managing the game state
     * @param ball                 the Ball object in the game
     * @param bricksCounter        the counter for the number of bricks remaining in the game
     */
    public CameraCollisionStrategy(GameObjectCollection gameObjectCollection, Vector2 windowDimensions,
                                   GameManager gameManager, Ball ball, Counter bricksCounter) {
        this.basicCollisionStrategy = new BasicCollisionStrategy(gameObjectCollection, bricksCounter);
        this.gameManager = gameManager;
        this.camera = new Camera(ball, Vector2.ZERO,
                windowDimensions.mult(WINDOW_DIMENSIONS_FACTOR), windowDimensions);
        this.ball = ball;
    }

    /**
     * Handles the collision between two GameObjects, creating a camera and setting it to follow the ball.
     *
     * @param gameObject1   the first GameObject involved in the collision
     * @param gameObject2   the second GameObject involved in the collision
     */
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {

        basicCollisionStrategy.onCollision(gameObject1, gameObject2);
        if (gameObject2.getTag().equals(ball.getTag()) && gameManager.camera() == null) {
            // create a new camera and set it to follow the ball
            gameManager.setCamera(camera);
            camera.setToFollow(ball, Vector2.ZERO);
            ball.resetCounter();
        }

    }
}
