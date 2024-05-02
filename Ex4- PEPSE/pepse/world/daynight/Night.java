package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.Constants;

import java.awt.*;

/**
 * Night class represents the night in the game world.
 */
public class Night {

    /**
     * Creates a night GameObject with the specified window dimensions and cycle length.
     * @param windowDimensions The dimensions of the game window.
     * @param cycleLength      The length of the night's cycle.
     * @return The night GameObject.
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength) {
        RectangleRenderable nightRenderable = new RectangleRenderable(Color.BLACK);
        GameObject night = new GameObject(Vector2.ZERO, windowDimensions, nightRenderable);
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        night.setTag(Constants.NIGHT_TAG);
        new Transition<Float>(
                night,
                night.renderer()::setOpaqueness,
                Constants.NIGHT_INITIAL_OPACITY,
                Constants.MIDNIGHT_OPACITY,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                cycleLength,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null
        );// nothing further to execute upon reaching final value

        return night;

    }
}
