package bricker.brick_strategies;

import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

import java.util.Random;

/**
 * Represents a collision strategy that combines two other collision strategies randomly.
 */
public class DoubleBehaviorCollisionStrategy implements CollisionStrategy {
    private final BasicCollisionStrategy basicCollisionStrategy;
    private CollisionStrategy[] strategiesArr;
    private final int TWO_STRATEGIES_TO_GENERATE = 2;

    /**
     * Constructs a DoubleBehaviorCollisionStrategy object.
     *
     * @param gameObjectCollection      the GameObjectCollection to apply the collision strategies to
     * @param collisionStrategyFactory  the CollisionStrategyFactory used to create collision strategies
     * @param bricksCounter             the counter for the number of bricks remaining in the game
     */
    public DoubleBehaviorCollisionStrategy(GameObjectCollection gameObjectCollection,
                                           CollisionStrategyFactory collisionStrategyFactory,
                                           Counter bricksCounter) {

        this.strategiesArr = new CollisionStrategy[Constants.MAX_NUM_OF_BEHAVIORS];
        this.basicCollisionStrategy = new BasicCollisionStrategy(gameObjectCollection, bricksCounter);
        // This loop initializes the strategiesArr array with null values.
        for (int i = 0; i < Constants.MAX_NUM_OF_BEHAVIORS; i++) {
            this.strategiesArr[i] = null;
        }

        int i = 0;
        int numOfStrategiesToGenerate = TWO_STRATEGIES_TO_GENERATE;
        // This loop generates a random number of collision strategies, up to a
        // maximum of Constants.MAX_NUM_OF_BEHAVIORS.
        while (numOfStrategiesToGenerate > 0 && i < Constants.MAX_NUM_OF_BEHAVIORS) {
            Random rnd = new Random();
            int strategy = rnd.nextInt(collisionStrategyFactory.NUM_OF_STRATEGIES) + 1;
            if (strategy != collisionStrategyFactory.DOUBLE_BEHAVIOR_COLLISION_STRATEGY) {
                strategiesArr[i] = collisionStrategyFactory.createStrategy(strategy);
                i++;
            } else {
                numOfStrategiesToGenerate += TWO_STRATEGIES_TO_GENERATE;
            }
        }
    }

    /**
     * Handles the collision between two GameObjects by invoking the collision strategies
     * in the strategiesArr array.
     *
     * @param gameObject1   the first GameObject involved in the collision
     * @param gameObject2   the second GameObject involved in the collision
     */
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        basicCollisionStrategy.onCollision(gameObject1, gameObject2);
        for (CollisionStrategy strategy : strategiesArr) {
            if (strategy != null) {
                strategy.onCollision(gameObject1, gameObject2);
            }
        }
    }
}
