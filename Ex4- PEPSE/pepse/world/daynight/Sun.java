package pepse.world.daynight;
import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.Constants;

import java.awt.*;

/**
 * Sun class represents the sun in the game world.
 */
public class Sun {

    /**
     * Creates a sun GameObject with the specified window dimensions and cycle length.
     * @param windowDimensions The dimensions of the game window.
     * @param cycleLength      The length of the sun's cycle.
     * @return The sun GameObject.
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength) {
        Vector2 initialSunCenter = new Vector2(Constants.SUN_HALO_CENTER_X_FACTOR,
                Constants.SUN_HALO_CENTER_Y_FACTOR);
        OvalRenderable sunRenderable = new OvalRenderable(Color.YELLOW);
        GameObject sun = new GameObject(Vector2.ZERO, Constants.SUN_DIMENSIONS, sunRenderable);
        sun.setCenter(initialSunCenter);
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag(Constants.SUN_TAG);
        Vector2 cycleCenter = new Vector2(windowDimensions.x() * Constants.CYCLE_CENTER_X_RATIO,
                windowDimensions.y() * Constants.CYCLE_CENTER_Y_RATIO);

        new Transition<>(
                sun,
                (Float angle) -> sun.setCenter
                        (initialSunCenter.subtract(cycleCenter)
                                .rotated(angle)
                                .add(cycleCenter)),
                Constants.SUN_ANIMATION_START_ANGLE,
                Constants.SUN_ANIMATION_END_ANGLE,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                cycleLength,
                Transition.TransitionType.TRANSITION_LOOP,
                null
        );
        return sun;
    }
}
