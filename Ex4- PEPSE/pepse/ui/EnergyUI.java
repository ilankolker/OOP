package pepse.ui;

import danogl.GameObject;
import danogl.components.RendererComponent;
import danogl.gui.rendering.Camera;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import pepse.Constants;

import java.util.function.Supplier;

/**
 * The EnergyUI class provides functionality to create and update an energy UI element in the game.
 */
public class EnergyUI {
    /**
     * The energy UI GameObject instance.
     */
    public static GameObject energyUI = null;

    /**
     * The supplier function to get the current energy value.
     */
    private static Supplier<Float> energyGetter;

    /**
     * Creates the energy UI GameObject and sets up the energy getter function.
     * @param energyGetter The function to get the current energy value.
     * @return The created energy UI GameObject.
     */
    public static GameObject create(Supplier<Float> energyGetter) {
        EnergyUI.energyGetter = energyGetter;
        energyUI = new GameObject(Vector2.ZERO, Constants.ENERGY_UI_DIMENSIONS, null);
        return energyUI;
    }

    /**
     * Updates the energy UI text with the current energy value.
     */
    public static void update() {
        TextRenderable energyUIText = new TextRenderable(energyGetter.get() +
                Constants.PERCENTAGE_SIGN);
        energyUI.renderer().setRenderable(energyUIText);
    }
}

