package bricker.gameobjects;

import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
/**
 * Represents a heart GameObject that can be collected in the game.
 */
public class Heart extends GameObject {
    private boolean needsToBeRemoved = false;

    /**
     * Constructs a Heart object.
     *
     * @param topLeftCorner the top-left corner position of the Heart
     * @param dimensions the dimensions (width and height) of the Heart
     * @param renderable the Renderable used to render the Heart
     */
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
    }

    /**
     * This method is called when a collision occurs between this GameObject and another GameObject.
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        needsToBeRemoved = true;
    }

    /**
     * Checks if the Heart should collide with the other GameObject.
     * @param other The other GameObject.
     * @return true if the Heart should collide with the other GameObject, false otherwise.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other.getTag().equals(Constants.PADDLE_TAG);
    }

    /**
     * Checks if the Heart needs to be removed from the game.
     *
     * @return true if the Heart needs to be removed, false otherwise
     */
    public boolean needsToBeRemoved() {
        return needsToBeRemoved;
    }
}
