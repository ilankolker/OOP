package pepse.world.trees;

import danogl.GameObject;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.Constants;
import pepse.PepseGameManager;
import pepse.util.ColorSupplier;

import java.awt.*;
import java.util.HashSet;
import java.util.Random;
import java.util.function.BiConsumer;

/**
 * Tree class represents a tree in the game.
 */
public class Tree {
    /** The set of trunks in the tree. */
    private final HashSet<Trunk> trunkSet;
    /** The set of leaves in the tree. */
    private final HashSet<Leaf> leafSet;
    /** The set of fruits in the tree. */
    private final HashSet<Fruit> fruitSet;

    /**
     * Constructs a Tree object with the specified height, x position, and y position.
     * @param height    The height of the tree.
     * @param xPosition The x position of the tree.
     * @param yPosition The y position of the tree.
     */
    public Tree(int height, float xPosition, float yPosition) {
        trunkSet = new HashSet<>();
        leafSet = new HashSet<>();
        fruitSet = new HashSet<>();
        Random random = new Random();
        float windowHeight = PepseGameManager.getWindowDimensions().y();
        float centerOfTreeTopY = calculateCenterOfTreeTopY(windowHeight, yPosition, height);
        createTrunk(height, xPosition, windowHeight, yPosition);
        createLeavesAndFruits(random, xPosition, centerOfTreeTopY);
    }

    /**
     * Calculates the center y-coordinate of the top of the tree.
     * @param windowHeight The height of the game window.
     * @param yPosition    The y position of the tree.
     * @param height       The height of the tree.
     * @return The center y-coordinate of the top of the tree.
     */
    private float calculateCenterOfTreeTopY(float windowHeight, float yPosition, int height) {
        return windowHeight - yPosition - (height + Constants.BLOCKS_AFTER_TRUNK) * Constants.BLOCK_SIZE;
    }

    /**
     * Creates the trunk of the tree and adds it to the trunk set.
     * @param height       The height of the tree.
     * @param xPosition    The x position of the tree.
     * @param windowHeight The height of the game window.
     * @param yPosition    The y position of the tree.
     */
    private void createTrunk(int height, float xPosition, float windowHeight, float yPosition) {
        for (int i = 1; i < height + 1; i++) {
            Trunk trunk = new Trunk(Vector2.ONES.mult(Constants.BLOCK_SIZE),
                    new Vector2(xPosition, windowHeight - yPosition +
                            (Constants.TRUNK_CENTER_OFFSET) - i * Constants.BLOCK_SIZE),
                    new RectangleRenderable(ColorSupplier.approximateColor(Constants.BASE_TRUNK_COLOR)));
            trunkSet.add(trunk);
        }
    }

    /**
     * Creates the leaves and fruits for the tree based on random generation.
     * @param random           The Random object used for random generation.
     * @param xPosition        The x position of the tree.
     * @param centerOfTreeTopY The y position of the center of the tree top.
     */
    private void createLeavesAndFruits(Random random, float xPosition, float centerOfTreeTopY) {
        OvalRenderable fruitRenderable = new OvalRenderable(Constants.BASE_FRUIT_COLOR);

        for (int i = -Constants.MIDDLE_OF_TREE_TOP_SIZE; i <= Constants.MIDDLE_OF_TREE_TOP_SIZE; i++) {
            for (int j = -Constants.MIDDLE_OF_TREE_TOP_SIZE; j <= Constants.MIDDLE_OF_TREE_TOP_SIZE; j++) {
                if (random.nextFloat() >= Constants.LEAF_CREATION_THRESHOLD) {
                    createLeaf(xPosition, centerOfTreeTopY, i, j);
                } else if (random.nextFloat() >= Constants.FRUIT_CREATION_THRESHOLD) {
                    createFruit(xPosition, centerOfTreeTopY, fruitRenderable, i, j);
                }
            }
        }
    }

    /**
     * Creates a leaf at the specified position and adds it to the leaf set.
     * @param xPosition        The x position of the leaf.
     * @param centerOfTreeTopY The y position of the center of the tree top.
     * @param i                The vertical offset index for positioning the leaf.
     * @param j                The horizontal offset index for positioning the leaf.
     */
    private void createLeaf(float xPosition, float centerOfTreeTopY, int i, int j) {
        Leaf leaf = new Leaf(Vector2.ONES.mult(Constants.LEAF_SIZE),
                new Vector2(xPosition + j * Constants.LEAF_SIZE,
                        centerOfTreeTopY + i * Constants.LEAF_SIZE),
                new RectangleRenderable
                        (ColorSupplier.approximateColor(Constants.BASE_LEAF_COLOR)));
        leafSet.add(leaf);
    }

    /**
     * Creates a fruit at the specified position and adds it to the fruit set.
     * @param xPosition        The x position of the fruit.
     * @param centerOfTreeTopY The y position of the center of the tree top.
     * @param fruitRenderable  The renderable for the fruit.
     * @param i                The vertical offset index for positioning the fruit.
     * @param j                The horizontal offset index for positioning the fruit.
     */
    private void createFruit(float xPosition, float centerOfTreeTopY,
                             OvalRenderable fruitRenderable, int i, int j) {
        Fruit fruit = new Fruit(Vector2.ONES.mult(Constants.LEAF_SIZE),
                new Vector2(xPosition + j * Constants.LEAF_SIZE,
                        centerOfTreeTopY + i * Constants.LEAF_SIZE),
                fruitRenderable);
        fruitSet.add(fruit);
    }

    /**
     * Updates the tree, including its leaves, trunks, and fruits.
     */
    public void update() {
        updateLeafs();
        updateTrunk();
        updateFruits();
    }

    /**
     * Updates the state of the fruits in the tree.
     */
    private void updateFruits() {
        for (Fruit fruit : fruitSet) {
            if (fruit.getColor() == Constants.BASE_FRUIT_COLOR) {
                fruit.renderer().setRenderable(new OvalRenderable(ColorSupplier.approximateColor
                        (Constants.CHANGED_FRUIT_COLOR)));
                fruit.setColor(Constants.CHANGED_FRUIT_COLOR);
            } else {
                fruit.renderer().setRenderable(new OvalRenderable(ColorSupplier.approximateColor
                        (Constants.BASE_FRUIT_COLOR)));
                fruit.setColor(Constants.BASE_FRUIT_COLOR);
            }
        }
    }

    /**
     * Updates the state of the leaves in the tree.
     */
    private void updateLeafs() {
        for (Leaf leaf : leafSet) {
            new Transition<>(
                    leaf,
                    angle -> leaf.renderer().setRenderableAngle(angle),
                    Constants.LEAF_ANIMATION_START_ANGLE,
                    Constants.LEAF_ANIMATION_END_ANGLE,
                    Transition.LINEAR_INTERPOLATOR_FLOAT,
                    Constants.LEAF_ANIMATION_DURATION,
                    Transition.TransitionType.TRANSITION_ONCE,
                    null
            );
        }
    }

    /**
     * Updates the state of the trunks in the tree.
     */
    private void updateTrunk() {
        for (Trunk trunk : trunkSet) {
            trunk.renderer().setRenderable(new RectangleRenderable(ColorSupplier.approximateColor
                    (Constants.BASE_TRUNK_COLOR)));
        }
    }

    /**
     * Gets the set of trunks in the tree.
     * @return The set of trunks.
     */
    public HashSet<Trunk> getTrunkSet() {
        return trunkSet;
    }

    /**
     * Gets the set of leaves in the tree.
     * @return The set of leaves.
     */
    public HashSet<Leaf> getLeafSet() {
        return leafSet;
    }

    /**
     * Gets the set of fruits in the tree.
     * @return The set of fruits.
     */
    public HashSet<Fruit> getFruitSet() {
        return fruitSet;
    }
}
