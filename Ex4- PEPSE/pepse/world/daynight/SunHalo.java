package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.Constants;

import java.awt.*;

/**
 * SunHalo class represents the halo effect around the sun.
 */
public class SunHalo {

    /**
     * Creates a sun halo GameObject based on the given sun GameObject.
     * @param sun The sun GameObject around which the halo is created.
     * @return The sun halo GameObject.
     */
    public static GameObject create(GameObject sun) {
        Vector2 initialSunHaloCenter = new Vector2(sun.getCenter());
        Color sunHaloColor = Constants.SUN_HALO_COLOR;
        OvalRenderable sunHaloRenderable = new OvalRenderable(sunHaloColor);
        GameObject sunHalo = new GameObject(Vector2.ZERO,
                sun.getDimensions().mult(Constants.SUN_HALO_SIZE_FACTOR), sunHaloRenderable);
        sunHalo.setCenter(initialSunHaloCenter);
        sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sunHalo.setTag(Constants.SUN_HALO_TAG);
        sunHalo.addComponent(deltaTime -> sunHalo.setCenter(sun.getCenter()));
        return sunHalo;
    }
}

