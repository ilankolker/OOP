package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.Constants;

import java.util.Random;

/**
 * Leaf class represents a leaf in the game, extending GameObject.
 */
public class Leaf extends GameObject {

    /**
     * Constructs a Leaf object with the specified dimensions, center, and renderable.
     * @param dimensions  The dimensions of the leaf.
     * @param center      The center of the leaf.
     * @param renderable  The renderable for the leaf.
     */
    public Leaf(Vector2 dimensions, Vector2 center, Renderable renderable) {
        super(Vector2.ZERO, dimensions, renderable);
        setCenter(center);
        Random random = new Random();

        // Schedule the createWind method to be called with a random
        // delay between 0 and 3 seconds, repeatedly
        new ScheduledTask(this, random.nextFloat() * Constants.MAX_WIND_DELAY,
                false, this::createWind);
        // Change the leaf's size back and forth over a period of 2 seconds
        new Transition<>(
                this,
                (Float dimensionsFactor) -> this.setDimensions(dimensions.mult(dimensionsFactor)),
                Constants.INITIAL_DIMENSIONS_FACTOR,
                Constants.FINAL_DIMENSIONS_FACTOR,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                Constants.SIZE_TRANSITION_DURATION ,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
    }

    /**
     * Creates a wind effect on the leaf, making it move back and forth.
     */
    private void createWind() {
        // Move the leaf in the wind by changing its angle back and forth from 0 to 90 degrees over 2 seconds
        new Transition<>(
                this,
                (Float angle) -> this.renderer().setRenderableAngle(angle),
                -Constants.WIND_ANGLE_RANGE,
                Constants.WIND_ANGLE_RANGE ,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                Constants.WIND_DURATION,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
    }
}
