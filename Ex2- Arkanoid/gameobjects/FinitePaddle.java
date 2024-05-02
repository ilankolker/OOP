package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Represents a paddle with a limited number of collisions before being removed from the game.
 */
public class FinitePaddle extends Paddle {
    private final Counter paddleCollisionCounter;

    /**
     * Constructs a FinitePaddle object.
     *
     * @param topLeftCorner         the top-left corner position of the FinitePaddle
     * @param dimensions            the dimensions (width and height) of the FinitePaddle
     * @param renderable            the Renderable used to render the FinitePaddle
     * @param inputListener         the UserInputListener for controlling the FinitePaddle
     * @param paddleCollisionCounter the Counter object tracking the remaining collisions before removal
     */
    public FinitePaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                        UserInputListener inputListener, Counter paddleCollisionCounter) {
        super(topLeftCorner, dimensions, renderable, inputListener);
        this.paddleCollisionCounter = paddleCollisionCounter;
    }

    /**
     * Updates the FinitePaddle object.
     * @param deltaTime The time elapsed, in seconds, since the last frame.
     */

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    /**
     * Handles a collision with a GameObject.
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        paddleCollisionCounter.decrement();
    }

    /**
     * Checks if the FinitePaddle needs to be removed from the game.
     *
     * @return true if the FinitePaddle needs to be removed, false otherwise
     */
    public boolean needsToBeRemoved() {
        return paddleCollisionCounter.value() <= 0;
    }
}
