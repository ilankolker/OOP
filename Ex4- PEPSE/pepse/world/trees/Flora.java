package pepse.world.trees;
import danogl.GameObject;
import pepse.Constants;
import pepse.world.Terrain;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Flora class represents the vegetation in the game.
 */
public class Flora {
    private Function<Float, Float> groundHeightAt;

    /**
     * Constructs a Flora object with the specified groundHeightAt function.
     * @param groundHeightAt The function to get the ground height at a specific x-coordinate.
     */
    public Flora(Function<Float, Float> groundHeightAt) {
        this.groundHeightAt = groundHeightAt;
    }

    /**
     * Creates trees in a specified range based on the ground height function.
     * @param minX The minimum x-coordinate.
     * @param maxX The maximum x-coordinate.
     * @return A HashSet of trees created in the specified range.
     */
    public HashSet<Tree> createInRange(int minX, int maxX) {
        HashSet<Tree> treeSet = new HashSet<>();
        Random random = new Random();

        int minBlockX = minX / Constants.BLOCK_SIZE * Constants.BLOCK_SIZE;
        int maxBlockX = (int) Math.ceil((double) maxX / Constants.BLOCK_SIZE) * Constants.BLOCK_SIZE;

        for (int i = minBlockX; i < maxBlockX; i += Constants.BLOCK_SIZE) {
            if (random.nextFloat() < Constants.TREE_CREATION_PROBABILITY) {
                float xPosition = i;
                int yPosition = (int) (Math.floor(groundHeightAt.apply((float) i)
                        / Constants.BLOCK_SIZE) + 1) * Constants.BLOCK_SIZE;
                treeSet.add(new Tree(random.nextInt(Constants.TREE_HEIGHT_RANGE) +
                        Constants.MIN_TREE_HEIGHT, xPosition, yPosition));
            }
        }
        return treeSet;
    }
}

