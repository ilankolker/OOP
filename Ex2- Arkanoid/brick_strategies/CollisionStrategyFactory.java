
package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import danogl.GameManager;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.util.Counter;
import danogl.util.Vector2;
/**
 * Factory class for creating CollisionStrategy objects based on the chosen strategy.
 */
public class CollisionStrategyFactory {
    /**
     * Represents a collision strategy for pucks.
     */
    public final int PUCKS_COLLISION_STRATEGY = 6;

    /**
     * Represents a collision strategy for finite paddles.
     */
    public final int FINITE_PADDLE_COLLISION_STRATEGY = 7;

    /**
     * Represents a collision strategy for cameras.
     */
    public final int CAMERA_COLLISION_STRATEGY = 8;

    /**
     * Represents an additional heart collision strategy.
     */
    public final int ADDITIONAL_HEART_COLLISION_STRATEGY = 9;

    /**
     * Represents a collision strategy for double behavior.
     */
    public final int DOUBLE_BEHAVIOR_COLLISION_STRATEGY = 10;

    /**
     * Represents the total number of collision strategies available.
     */
    public final int NUM_OF_STRATEGIES = 10;

    private final GameObjectCollection gameObjectCollection;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final UserInputListener userInputListener;
    private final Vector2 windowDimensions;
    private final GameManager gameManager;
    private final Ball ball;
    private final Counter bricksCounter;

    /**
     * Constructs a CollisionStrategyFactory object.
     *
     * @param gameObjectCollection the GameObjectCollection to apply collision strategies to
     * @param imageReader          the ImageReader used to read image files
     * @param soundReader          the SoundReader used to read sound files
     * @param userInputListener    the UserInputListener used to handle user input
     * @param windowDimensions     the dimensions of the game window
     * @param gameManager          the GameManager managing the game state
     * @param ball                 the Ball object in the game
     * @param bricksCounter        the counter for the number of bricks remaining in the game
     */
    public CollisionStrategyFactory(GameObjectCollection gameObjectCollection,
                                    ImageReader imageReader, SoundReader soundReader,
                                    UserInputListener userInputListener,
                                    Vector2 windowDimensions,
                                    GameManager gameManager, Ball ball, Counter bricksCounter) {
        this.gameObjectCollection = gameObjectCollection;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.userInputListener = userInputListener;
        this.windowDimensions = windowDimensions;
        this.gameManager = gameManager;
        this.ball = ball;
        this.bricksCounter = bricksCounter;
    }

    /**
     * Creates a CollisionStrategy object based on the chosen strategy.
     *
     * @param chosenStrategy the chosen strategy identifier
     * @return a CollisionStrategy object corresponding to the chosen strategy
     */
    public CollisionStrategy createStrategy(int chosenStrategy) {
        CollisionStrategy collisionStrategy;
         // Switch statement to create the CollisionStrategy object corresponding to the chosen strategy.
        switch (chosenStrategy) {
            case PUCKS_COLLISION_STRATEGY:
                collisionStrategy = new PucksCollisionStrategy(gameObjectCollection,
                        soundReader, imageReader, bricksCounter);
                break;

            case FINITE_PADDLE_COLLISION_STRATEGY:
                collisionStrategy = new FinitePaddleCollisionStrategy(
                        gameObjectCollection, userInputListener, imageReader, windowDimensions,
                        bricksCounter);
                break;

            case CAMERA_COLLISION_STRATEGY:
                collisionStrategy = new CameraCollisionStrategy(gameObjectCollection,
                        windowDimensions, gameManager, ball, bricksCounter);
                break;

            case ADDITIONAL_HEART_COLLISION_STRATEGY:
                collisionStrategy = new AdditionalHeartCollisionStrategy(
                        gameObjectCollection, imageReader, bricksCounter);
                break;

            case DOUBLE_BEHAVIOR_COLLISION_STRATEGY:
                collisionStrategy = new DoubleBehaviorCollisionStrategy(gameObjectCollection,
                        this, bricksCounter);
                break;
            default:
                collisionStrategy = new BasicCollisionStrategy(gameObjectCollection, bricksCounter);
        }
        return collisionStrategy;
    }
}
