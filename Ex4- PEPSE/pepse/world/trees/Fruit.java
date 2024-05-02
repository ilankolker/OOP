package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.Constants;
import java.awt.*;
import java.util.function.Consumer;

/**
 * Fruit class represents a fruit in the game, extending GameObject.
 */
public class Fruit extends GameObject {
    private Consumer<Float> addEnergyToAvatar;
    private Color color = Constants.BASE_FRUIT_COLOR;
    private boolean isEaten = false;

    /**
     * Constructs a Fruit object with the specified dimensions, center, and renderable.
     * @param dimensions  The dimensions of the fruit.
     * @param center      The center of the fruit.
     * @param renderable  The renderable for the fruit.
     */
    public Fruit(Vector2 dimensions, Vector2 center, Renderable renderable) {
        super(Vector2.ZERO, dimensions, renderable);
        this.addEnergyToAvatar = null;
        setCenter(center);
        setTag(Constants.FRUIT_TAG);
    }

    /**
     * Resets the fruit after it has been eaten, making it visible again.
     */
    private void addFruitAfterCollision() {
        isEaten = false;
        renderer().setOpaqueness(Constants.DEFAULT_OPAQUENESS);
    }

    /**
     * Sets the consumer for adding energy to the avatar.
     * @param addEnergyToAvatar  The consumer for adding energy to the avatar.
     */
    public void setAddEnergyToAvatar(Consumer<Float> addEnergyToAvatar) {
        this.addEnergyToAvatar = addEnergyToAvatar;
    }

    /**
     * Handles collision events for the fruit.
     * @param other      The other GameObject involved in the collision.
     * @param collision  The Collision object containing information about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (!isEaten && other.getTag().equals(Constants.AVATAR_TAG)) {
            isEaten = true;
            renderer().setOpaqueness(0);
            addEnergyToAvatar.accept((float) Constants.FRUIT_ENERGY);
        }
        new ScheduledTask(other, Constants.DAY_DURATION, false, this::addFruitAfterCollision);
    }

    /**
     * Gets the color of the fruit.
     * @return The color of the fruit.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color of the fruit.
     * @param changedColor  The new color of the fruit.
     */
    public void setColor(Color changedColor) {
        color = changedColor;
    }

}
