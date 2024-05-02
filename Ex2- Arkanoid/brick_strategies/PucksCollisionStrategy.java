
package bricker.brick_strategies;

import bricker.gameobjects.Puck;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Represents a collision strategy for creating multiple Puck objects when a collision occurs.
 */
public class PucksCollisionStrategy implements CollisionStrategy {
    private final float sizeFactor = 0.75f;
    private final int numberOfPucksToCreate = 2;
    private final GameObjectCollection gameObjectCollection;
    private final SoundReader soundReader;
    private final ImageReader imageReader;
    private final BasicCollisionStrategy basicCollisionStrategy;

    /**
     * Constructs a PucksCollisionStrategy object.
     *
     * @param gameObjectCollection the GameObjectCollection to add the Puck objects to
     * @param soundReader          the SoundReader used to read the sound files
     * @param imageReader          the ImageReader used to read the image files
     * @param bricksCounter        the counter for the number of bricks remaining in the game
     */
    public PucksCollisionStrategy(GameObjectCollection gameObjectCollection, SoundReader soundReader,
                                  ImageReader imageReader, Counter bricksCounter) {
        this.basicCollisionStrategy = new BasicCollisionStrategy(gameObjectCollection, bricksCounter);
        this.gameObjectCollection = gameObjectCollection;
        this.soundReader = soundReader;
        this.imageReader = imageReader;
    }

    /**
     * Handles the collision between two GameObjects by creating multiple Puck objects and adding them
     * to the GameObjectCollection.
     *
     * @param gameObject1   the first GameObject involved in the collision
     * @param gameObject2   the second GameObject involved in the collision
     */
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        basicCollisionStrategy.onCollision(gameObject1, gameObject2);
        Vector2 ballDimensions = Vector2.ZERO;
        // Create multiple Puck objects and add them to the GameObjectCollection.
        for (int i = 0; i < numberOfPucksToCreate; i++) {
            Renderable puckImg = imageReader.readImage(Constants.PUCK_IMAGE_PATH, true);
            Sound puckCollisionSound = soundReader.readSound(Constants.PUCK_SOUND_PATH);
            // Get the dimensions of the original ball.
            for (GameObject gameObject : gameObjectCollection) {
                if (gameObject.getTag().equals(Constants.BALL_TAG)) {
                    ballDimensions = gameObject.getDimensions();
                }
            }
            // Create a new Puck object with the same dimensions and velocity as the original ball.
            Puck puck = new Puck(gameObject1.getCenter(), ballDimensions.mult(sizeFactor)
                    , puckImg, puckCollisionSound);
            puck.initBallSpeed(gameObject2.getVelocity());
            gameObjectCollection.addGameObject(puck, Layer.DEFAULT);
        }
    }
}
