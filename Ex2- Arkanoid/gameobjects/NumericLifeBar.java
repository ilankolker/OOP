package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;

/**
 * NumericLifeBar class is responsible for displaying the number of lives left in the game.
 */
public class NumericLifeBar extends GameObject {

    private final TextRenderable numericLifeBarRenderable;
    private final Counter livesCounter;

    /**
     * Constructs a NumericLifeBar object.
     *
     * @param topLeftCorner              the top-left corner position of the NumericLifeBar
     * @param dimensions                 the dimensions (width and height) of the NumericLifeBar
     * @param numericLifeBarRenderable   the TextRenderable for rendering the numeric life value
     * @param livesCounter               the Counter object for tracking the player's lives
     * @param gameObjectsCollection      the GameObjectCollection where the NumericLifeBar is added
     */
    public NumericLifeBar(Vector2 topLeftCorner, Vector2 dimensions, TextRenderable numericLifeBarRenderable,
                          Counter livesCounter, GameObjectCollection gameObjectsCollection) {
        super(topLeftCorner, dimensions, numericLifeBarRenderable);
        this.numericLifeBarRenderable = numericLifeBarRenderable;
        this.livesCounter = livesCounter;
        numericLifeBarRenderable.setColor(Color.GREEN);
        gameObjectsCollection.addGameObject(this, Layer.UI);
    }

    /**
     * Updates the NumericLifeBar object.
     * This method is called once per frame and is responsible for updating the numeric life value.
     * @param deltaTime The time elapsed, in seconds, since the last frame.
     */

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        // do not update the life bar if the player has no lives left.
        if (livesCounter.value() < 0) {
            return;
        }
        numericLifeBarRenderable.setString(String.valueOf(livesCounter.value()));
        // change color of the life bar according to the number of lives left.
        switch (livesCounter.value()) {
            case 1:
                numericLifeBarRenderable.setColor(Color.RED);
                break;
            case 2:
                numericLifeBarRenderable.setColor(Color.YELLOW);
                break;
            default:
                numericLifeBarRenderable.setColor(Color.GREEN);
        }
    }
}
