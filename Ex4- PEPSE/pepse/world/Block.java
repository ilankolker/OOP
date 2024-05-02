package pepse.world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.Constants;

/**
 * Block class represents a block in the game, extending GameObject.
 */
public class Block extends GameObject {

    /**
     * Constructs a Block object with the specified top left corner and renderable.
     * @param topLeftCorner The top left corner of the block.
     * @param renderable    The renderable for the block.
     */
    public Block(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner, Vector2.ONES.mult(Constants.BLOCK_SIZE), renderable);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }
}

