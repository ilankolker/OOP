package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import java.util.Random;

/**
 * Represents a ball GameObject that moves around and interacts with other objects in the game.
 */
public class Ball extends GameObject {
    private Counter collisionCounter;
    private final Sound collisionSound;

    /**
     * Constructs a Ball object.
     *
     * @param topLeftCorner      the top-left corner position of the Ball
     * @param dimensions         the dimensions (width and height) of the Ball
     * @param renderable         the Renderable used to render the Ball
     * @param collisionSound     the Sound played when the Ball collides with another object
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        collisionCounter = new Counter(0);
    }

    /**
     * Handles a collision between the Ball and another GameObject.
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);

        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        collisionSound.play();
        collisionCounter.increment();
    }

    /**
     * Initializes the ball speed with a random direction based on the given velocity.
     *
     * @param ballVelocity the initial velocity of the ball
     */
    public void initBallSpeed(Vector2 ballVelocity) {
        float ballVelX = ballVelocity.x();
        float ballVelY = ballVelocity.y();
        Random rand = new Random();
        if (rand.nextBoolean()) {
            ballVelX *= -1;
        }
        if (rand.nextBoolean()) {
            ballVelY *= -1;
        }
        this.setVelocity(new Vector2(ballVelX, ballVelY));
    }

    /**
     * Resets the collision counter of the ball.
     */
    public void resetCounter() {
        collisionCounter.reset();
    }

    /**
     * Returns the collision counter of the ball.
     *
     * @return the collision counter of the ball
     */
    public int getCollisionCounter() {
        return collisionCounter.value();
    }
}
