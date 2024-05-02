package pepse.world;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.Constants;

import java.awt.Color;

/**
 * Sky class represents the sky in the game.
 */
public class Sky {
    /**
     * Creates a sky game object with the specified window dimensions.
     * @param windowDimensions The dimensions of the game window.
     * @return The sky game object.
     */
    public static GameObject create(Vector2 windowDimensions){
        GameObject sky = new GameObject(
                Vector2.ZERO, windowDimensions,
                new RectangleRenderable(Constants.BASIC_SKY_COLOR));
        sky.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sky.setTag(Constants.SKY_TAG);
        return sky;
    }
}
