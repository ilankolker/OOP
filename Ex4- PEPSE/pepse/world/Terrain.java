package pepse.world;
import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.Constants;
import pepse.util.ColorSupplier;
import pepse.util.NoiseGenerator;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Terrain class represents the terrain of the game.
 */
public class Terrain {
    /** The dimensions of the game window. */
    private final Vector2 windowDimensions;
    /** The ground height at x=0. */
    private final float groundHeightAtX0;
    /** The noise generator for terrain generation. */
    private NoiseGenerator noiseGenerator;

    /**
     * Constructs a Terrain object with the specified window dimensions and seed.
     * @param windowDimensions The dimensions of the game window.
     */
    public Terrain(Vector2 windowDimensions) {
        this.windowDimensions = windowDimensions;
        groundHeightAtX0 = windowDimensions.y() * Constants.HEIGHT_AT_X0_FACTOR;
        noiseGenerator = new NoiseGenerator(new Random().nextDouble(), (int)groundHeightAtX0);
    }

    /**
     * Returns the ground height at the specified x-coordinate.
     * @param x The x-coordinate.
     * @return The ground height at the specified x-coordinate.
     */
    public float groundHeightAt(float x) {
        float noise = (float) noiseGenerator.noise(x, Constants.NOISE_FACTOR);
        return groundHeightAtX0 + noise;
    }

    /**
     * Creates a set of blocks representing the terrain within the specified range.
     * @param minX The minimum x-coordinate.
     * @param maxX The maximum x-coordinate.
     * @return A set of blocks representing the terrain within the specified range.
     */
    public Set<Block> createInRange(int minX, int maxX) {
        Set<Block> blockSet = new HashSet<>();
        int minBlockX = minX / Constants.BLOCK_SIZE * Constants.BLOCK_SIZE;
        int maxBlockX = (int) Math.ceil((double) maxX / Constants.BLOCK_SIZE) * Constants.BLOCK_SIZE;
        createBlockSet(minBlockX, maxBlockX, blockSet);
        return blockSet;
    }

    /**
     * Creates a set of blocks representing the terrain within the specified x-coordinate range,
     * up to the ground height at each x-coordinate.
     * @param minBlockX The minimum x-coordinate of the block range.
     * @param maxBlockX The maximum x-coordinate of the block range.
     * @param blockSet  The set to which the created blocks will be added.
     */
    private void createBlockSet(int minBlockX, int maxBlockX, Set<Block> blockSet) {
        int maxBlockY;
        for (int i = minBlockX; i < maxBlockX; i += Constants.BLOCK_SIZE) {
            maxBlockY = (int)(Math.floor(groundHeightAt(i) / Constants.BLOCK_SIZE) + 1 );
            for (int j = 0; j <= maxBlockY ; j++) {
                RectangleRenderable blockRenderable =
                        new RectangleRenderable(ColorSupplier.approximateColor(Constants.BASE_GROUND_COLOR));
                Block block = new Block(
                        new Vector2(i,
                                windowDimensions.y() - j * Constants.BLOCK_SIZE),
                        blockRenderable);
                block.setTag(Constants.GROUND_TAG);
                blockSet.add(block);
            }
        }
    }
}

