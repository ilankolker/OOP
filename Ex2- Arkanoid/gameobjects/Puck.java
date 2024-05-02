package bricker.gameobjects;

import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * A class representing the puck in the game.
 */
public class Puck extends Ball {

    /**
     * Construct a new GameObject instance.
     * @param topLeftCorner     Position of the object, in window coordinates (pixels).
     * @param dimensions        Width and height in window coordinates.
     * @param renderable        The renderable representing the object.
     * @param collisionSound    The sound to play when the puck collides with something.
     */
    public Puck(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable, collisionSound);
        setTag(Constants.PUCK_TAG);
    }

    /**
     * Called upon collision with another GameObject.
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
    }
}
