package pepse.world.trees;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Trunk class represents a trunk of a tree in the game, extending GameObject.
 */
public class Trunk extends GameObject {

    /**
     * Constructs a Trunk object with the specified dimensions, trunk center, and renderable.
     * @param dimensions   The dimensions of the trunk.
     * @param trunkCenter  The center of the trunk.
     * @param renderable   The renderable for the trunk.
     */
    public Trunk(Vector2 dimensions, Vector2 trunkCenter, Renderable renderable) {
        super(Vector2.ZERO, dimensions, renderable);
        setCenter(trunkCenter);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }
}
