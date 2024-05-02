package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import pepse.ui.EnergyUI;
import pepse.world.Avatar;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.*;
import java.util.HashSet;

/**
 * PepseGameManager extends GameManager to initialize and run a game.
 */
public class PepseGameManager extends GameManager {
    private static WindowController windowController;

    /**
     * Initializes the game with the specified resources and window controller.
     * @param imageReader   The image reader for loading images.
     * @param soundReader   The sound reader for loading sounds.
     * @param inputListener The input listener for user input.
     * @param windowController The window controller for managing the game window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        PepseGameManager.windowController = windowController;
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        createSky(windowController);
        Terrain terrain = createTerrain(windowController);
        createNight(windowController);
        GameObject sun = createSun(windowController);
        createSunHalo(sun);
        HashSet<Tree> trees = createFloraAndTrees(windowController, terrain);
        Avatar avatar = createAvatar(imageReader, inputListener, trees);
        initializeFruitAddEnergy(trees, avatar);
        createEnergyUi(avatar);
    }

    /**
     * Creates the sky background.
     * @param windowController The window controller for managing the game window.
     */
    private void createSky(WindowController windowController) {
        GameObject sky = Sky.create(windowController.getWindowDimensions());
        gameObjects().addGameObject(sky, Layer.BACKGROUND);
    }

    /**
     * Creates the terrain for the game.
     * @param windowController The window controller for managing the game window.
     * @return The created Terrain object.
     */
    private Terrain createTerrain(WindowController windowController) {
        Terrain terrain = new Terrain(windowController.getWindowDimensions());
        for (GameObject block : terrain.createInRange(0 ,(int) windowController.getWindowDimensions().x())) {
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }
        return terrain;
    }

    /**
     * Creates the night background.
     * @param windowController The window controller for managing the game window.
     */
    private void createNight(WindowController windowController) {
        GameObject night = Night.create(windowController.getWindowDimensions(), Constants.NIGHT_DURATION);
        gameObjects().addGameObject(night, Layer.FOREGROUND);
    }

    /**
     * Creates the sun object.
     * @param windowController The window controller for managing the game window.
     * @return The created sun GameObject.
     */
    private GameObject createSun(WindowController windowController) {
        GameObject sun = Sun.create(windowController.getWindowDimensions(), Constants.DAY_DURATION);
        gameObjects().addGameObject(sun, Layer.BACKGROUND);
        return sun;
    }

    /**
     * Creates the sun halo around the sun.
     * @param sun The sun GameObject.
     */
    private void createSunHalo(GameObject sun) {
        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo, Layer.BACKGROUND);
    }

    /**
     * Creates the flora and trees in the game.
     * @param windowController The window controller for managing the game window.
     * @param terrain The Terrain object for the game.
     * @return A HashSet of Tree objects created in the game.
     */
    private HashSet<Tree> createFloraAndTrees(WindowController windowController, Terrain terrain) {
        Flora flora = new Flora(terrain::groundHeightAt);
        HashSet<Tree> trees = flora.createInRange(0, (int) windowController.getWindowDimensions().x());
        for (Tree tree : trees) {
            for (Trunk trunk : tree.getTrunkSet()) {
                gameObjects().addGameObject(trunk, Constants.TRUNK_LAYER);
            }
            for (Leaf leaf : tree.getLeafSet()) {
                gameObjects().addGameObject(leaf, Constants.LEAF_LAYER);
            }
            for (Fruit fruit : tree.getFruitSet()) {
                gameObjects().addGameObject(fruit, Constants.FRUIT_LAYER);
            }
        }
        return trees;
    }

    /**
     * Creates the avatar for the game.
     * @param imageReader The image reader for loading images.
     * @param inputListener The input listener for user input.
     * @param trees The HashSet of Tree objects in the game.
     * @return The created Avatar object.
     */
    private Avatar createAvatar(ImageReader imageReader,
                                UserInputListener inputListener, HashSet<Tree> trees) {
        Avatar avatar= new Avatar(Vector2.ZERO, inputListener, imageReader, trees);
        gameObjects().addGameObject(avatar);
        return avatar;
    }

    /**
     * Initializes the fruit objects to add energy to the avatar when eaten.
     * @param trees The HashSet of Tree objects in the game.
     * @param avatar The Avatar object in the game.
     */
    private static void initializeFruitAddEnergy(HashSet<Tree> trees, Avatar avatar) {
        for (Tree tree : trees) {
            for (Fruit fruit : tree.getFruitSet()) {
                fruit.setAddEnergyToAvatar(avatar::addEnergy);
            }
        }
    }

    /**
     * Creates the energy UI element in the game.
     * @param avatar The Avatar object in the game.
     */
    private void createEnergyUi(Avatar avatar) {
        GameObject energyUi = EnergyUI.create(avatar::getEnergy);
        gameObjects().addGameObject(energyUi, Layer.UI);
    }

    /**
     * Returns the window dimensions.
     * @return The window dimensions as a Vector2.
     */
    public static Vector2 getWindowDimensions() {
        return windowController.getWindowDimensions();
    }

    /**
     * Main method to create and run an instance of PepseGameManager.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}

