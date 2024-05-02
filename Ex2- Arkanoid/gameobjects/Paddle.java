package bricker.gameobjects;

import bricker.main.Constants;
import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * This class represents the paddle game object.
 */
public class Paddle extends GameObject {

    private final UserInputListener inputListener;

    /**
     * Constructs a new Paddle object.
     * @param topLeftCorner      The top left corner of the paddle.
     * @param dimensions         The dimensions of the paddle.
     * @param renderable         The renderable of the paddle.
     * @param inputListener      The input listener of the paddle.
     */

    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
    }

    /**
     * This method moves the paddle according to the user input.
     * If the paddle is at the edge of the screen, it will not move.
     * If the user presses the left or right arrow keys, the paddle will move accordingly.
     * @param deltaTime The time elapsed, in seconds, since the last frame.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            if (this.getCenter().x() - Constants.PADDLE_DIMENSIONS.x() / 2 >= 0)
            {
                movementDir = movementDir.add(Vector2.LEFT);
            }
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            if (this.getCenter().x() + Constants.PADDLE_DIMENSIONS.x() / 2 <= Constants.WINDOW_DIMENSIONS_X)
            {
                movementDir = movementDir.add(Vector2.RIGHT);
            }
        }
        setVelocity(movementDir.mult(Constants.PADDLE_MOVEMENT_SPEED));
    }
}
