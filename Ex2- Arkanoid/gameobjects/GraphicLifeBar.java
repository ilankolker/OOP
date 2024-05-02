package bricker.gameobjects;

import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Represents a graphic life bar composed of hearts, each representing a player's life.
 */
public class GraphicLifeBar extends GameObject {
    private final float SPACE_FACTOR = 1.25f;
    private int numOfLives;
    private final Counter livesCounter;
    private final GameObjectCollection gameObjectsCollection;
    private final Renderable renderable;
    private final Vector2 graphicLifeBarTopLeftCorner;

    /**
     * Constructs a GraphicLifeBar object.
     *
     * @param topLeftCorner        the top-left corner position of the GraphicLifeBar
     * @param renderable           the Renderable used to render the hearts in the life bar
     * @param livesCounter         the Counter object tracking the player's lives
     * @param gameObjectsCollection the GameObjectCollection where the hearts are added
     */
    public GraphicLifeBar(Vector2 topLeftCorner, Renderable renderable,
                          Counter livesCounter, GameObjectCollection gameObjectsCollection) {
        super(topLeftCorner, Vector2.ZERO, null);
        this.numOfLives = livesCounter.value();
        this.livesCounter = livesCounter;
        this.gameObjectsCollection = gameObjectsCollection;
        this.renderable = renderable;
        this.graphicLifeBarTopLeftCorner = topLeftCorner;

        // Create hearts based on the initial number of lives
        for (int i = 0; i < numOfLives; i++) {
            Heart heart = new Heart(Vector2.ZERO, Constants.HEART_DIMENSIONS, renderable);
            heart.setCenter(new Vector2(SPACE_FACTOR * Constants.HEART_DIMENSIONS.x() * (i + 1) +
                    graphicLifeBarTopLeftCorner.x(), topLeftCorner.y()));
            heart.setTag(Constants.GRAPHIC_HEART_TAG);
            gameObjectsCollection.addGameObject(heart, Layer.UI);
        }
    }

    /**
     * Updates the state of the GraphicLifeBar, removing hearts if lives have decreased and adding hearts
     * if lives have increased.
     *
     * @param deltaTime the time that has passed since the last update
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        // Remove hearts if lives have decreased
        if (livesCounter.value() < numOfLives) {
            if (numOfLives == 0) {
                return;
            }
            GameObject lastHeart = null;
            for (GameObject obj : gameObjectsCollection) {
                if (obj.getTag().equals(Constants.GRAPHIC_HEART_TAG)) {
                    lastHeart = obj;
                }
            }
            gameObjectsCollection.removeGameObject(lastHeart, Layer.UI);
            numOfLives--;
        }
        addHeartsIfNeeded();
    }

    /**
     * Adds hearts to the GraphicLifeBar if the player gains lives.
     */
    private void addHeartsIfNeeded() {
        // Add hearts if lives have increased
        if (numOfLives < livesCounter.value()) {
            for (int i = numOfLives; i < livesCounter.value(); i++) {
                Heart heart = new Heart(Vector2.ZERO, Constants.HEART_DIMENSIONS, renderable);
                heart.setCenter(new Vector2(Constants.HEART_DIMENSIONS.x() * SPACE_FACTOR * (i + 1) +
                        graphicLifeBarTopLeftCorner.x(), graphicLifeBarTopLeftCorner.y()));
                heart.setTag(Constants.GRAPHIC_HEART_TAG);
                numOfLives++;
                gameObjectsCollection.addGameObject(heart, Layer.UI);
            }
        }
    }
}
