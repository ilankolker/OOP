package pepse;

import danogl.collisions.Layer;
import danogl.util.Vector2;

import java.awt.*;

/**
 * Constants class contains various constants used in the game.
 */
public class Constants {
    /** Tag for sky game object. */
    public static final String SKY_TAG = "sky";
    /** Tag for sun game object. */
    public static final String SUN_TAG = "sun";
    /** Tag for sun halo game object. */
    public static final String SUN_HALO_TAG = "sunHalo";
    /** Tag for night game object. */
    public static final String NIGHT_TAG = "night";
    /** Tag for ground game object. */
    public static final String GROUND_TAG = "ground";
    /** Tag for avatar game object. */
    public static final String AVATAR_TAG = "avatar";
    /** Tag for fruit game object. */
    public static final String FRUIT_TAG = "fruit";
    /** The path for the avatar image. */
    public static final String AVATAR_PATH_IMG = "assets/idle_0.png";
    /** Animation tag for idle state. */
    public static final String IDLE = "idle";
    /** Animation tag for run state. */
    public static final String RUN = "run";
    /** Animation tag for jump state. */
    public static final String JUMP = "jump";
    /** Duration of a day in seconds. */
    public static final float DAY_DURATION = 30f;
    /** Duration of a night in seconds. */
    public static final float NIGHT_DURATION = Constants.DAY_DURATION / 2;
    /** Opacity of the sky at midnight. */
    public static final float MIDNIGHT_OPACITY = 0.5f;
    /** Energy gain when idle. */
    public static final float IDLE_ENERGY_GAIN = 1f;
    /** Energy cost for movement. */
    public static final float MOVEMENT_ENERGY_COST = 0.5f;
    /** Energy cost for jumping. */
    public static final float JUMP_ENERGY_COST = 10f;
    /** Time between animation clips. */
    public static final float TIME_BETWEEN_CLIPS = 0.1f;
    /** Number of clips for idle animation. */
    public static final int IDLE_CLIPS = 4;
    /** Number of clips for run animation. */
    public static final int RUN_CLIPS = 6;
    /** Number of clips for jump animation. */
    public static final int JUMP_CLIPS = 4;
    /** Size of tree top. */
    public static final int TREE_TOP_SIZE = 8;
    /**
     * Half of the size of the tree top.
     */
    public static final int MIDDLE_OF_TREE_TOP_SIZE = TREE_TOP_SIZE / 2;
    /** Default color for sky. */
    public static final Color BASIC_SKY_COLOR = Color.decode("#80C6E5");
    /** Base color for ground. */
    public static final Color BASE_GROUND_COLOR = new Color(212, 123,74);
    /** Base color for tree trunk. */
    public static final Color BASE_TRUNK_COLOR = new Color(100, 50, 20);
    /** Base color for tree leaves. */
    public static final Color BASE_LEAF_COLOR = new Color(50, 200, 30);
    /** Base color for fruit. */
    public static final Color BASE_FRUIT_COLOR = new Color(247, 65, 143);
    /** Color for ripe fruit. */
    public static final Color CHANGED_FRUIT_COLOR = new Color(255, 153, 51);
    /**
     * The color of the sun halo, with reduced opacity for a subtle effect.
     */
    public static final Color SUN_HALO_COLOR = new Color(255, 255, 0, 20);
    /** Maximum energy for the avatar. */
    public static final float MAX_ENERGY = 100;
    /** Size of a block. */
    public static final int BLOCK_SIZE = 30;
    /** Size of a leaf. */
    public static final int LEAF_SIZE = 30;
    /** Energy gained from eating fruit. */
    public static final int FRUIT_ENERGY = 10;
    /** Layer for tree trunks. */
    public static final int TRUNK_LAYER = Layer.STATIC_OBJECTS;
    /** Layer for tree fruits. */
    public static final int FRUIT_LAYER = Layer.DEFAULT;
    /** Layer for tree leaves. */
    public static final int LEAF_LAYER = Layer.STATIC_OBJECTS + 50;
    /** X velocity of the avatar. */
    public static final float VELOCITY_X = 400;
    /** Y velocity of the avatar. */
    public static final float VELOCITY_Y = -650;
    /** Gravity constant. */
    public static final float GRAVITY = 600;
    /** Factor used for noise generation in terrain creation. */
    public static final float NOISE_FACTOR = Constants.BLOCK_SIZE * 7;
    /** Dimensions of the energy UI element. */
    public static final Vector2 ENERGY_UI_DIMENSIONS = new Vector2(30f, 30f);
    /** The percentage sign symbol. */
    public static final String PERCENTAGE_SIGN = "%";
    /** Dimensions of the sun object. */
    public static final Vector2 SUN_DIMENSIONS = new Vector2(50, 50);
    /** The ratio of the x-coordinate of the center of
     a cycle relative to the window dimensions */
    public static final float CYCLE_CENTER_X_RATIO = 0.5f;
    /** The ratio of the y-coordinate of the center of
     a cycle relative to the window dimensions */
    public static final float CYCLE_CENTER_Y_RATIO = 2f / 3f;
    /**
     * The probability threshold for tree creation in createInRange method.
     */
    public static final float TREE_CREATION_PROBABILITY = 0.075f;

    /**
     * The minimum height of a tree in createInRange method.
     */
    public static final int MIN_TREE_HEIGHT = 4;

    /**
     * The range of additional height for a tree in createInRange method.
     */
    public static final int TREE_HEIGHT_RANGE = 5;
    /**
     * The maximum delay in seconds for scheduling the createWind method in Leaf.
     */
    public static final float MAX_WIND_DELAY = 3f;

    /**
     * The initial dimensions factor for the leaf's size transition in Leaf.
     */
    public static final float INITIAL_DIMENSIONS_FACTOR = 1f;

    /**
     * The final dimensions factor for the leaf's size transition in Leaf.
     */
    public static final float FINAL_DIMENSIONS_FACTOR = 0.9f;

    /**
     * The duration of the size transition in seconds in Leaf.
     */
    public static final float SIZE_TRANSITION_DURATION = 2f;

    /**
     * The angle range for the wind effect in Leaf.
     */
    public static final float WIND_ANGLE_RANGE = 15f;

    /**
     * The duration of the wind effect in seconds in Leaf.
     */
    public static final float WIND_DURATION = 0.7f;
    /**
     * The default opaqueness value for the fruit after it has been eaten in Fruit.
     */
    public static final int DEFAULT_OPAQUENESS = 100;
    /**
     * The number of blocks to start after the trunk
     when calculating the center y-coordinate of the top of the tree.
     */
    public static final int BLOCKS_AFTER_TRUNK = 2;
    /**
     * The distance from the top of the highest brick in the terrain to the center of the trunk.
     */
    public static final float TRUNK_CENTER_OFFSET = Constants.BLOCK_SIZE / 2.f;
    /**
     * The threshold probability for creating a leaf.
     */
    public static final float LEAF_CREATION_THRESHOLD = 0.6f;

    /**
     * The threshold probability for creating a fruit.
     */
    public static final float FRUIT_CREATION_THRESHOLD = 0.9f;
    /**
     * Initial angle for the leaf animation.
     */
    public static final float LEAF_ANIMATION_START_ANGLE = 0f;

    /**
     * Final angle for the leaf animation.
     */
    public static final float LEAF_ANIMATION_END_ANGLE = 90f;

    /**
     * Duration of the leaf animation.
     */
    public static final float LEAF_ANIMATION_DURATION = 1f;
    /**
     * Factor used to calculate the ground height at x=0 relative to the window height.
     */
    public static final float HEIGHT_AT_X0_FACTOR = 1f / 3f;
    /**
     * The default size of the avatar.
     */
    public static final float AVATAR_SIZE = 50;
    /**
     * The factor used to calculate the dimensions of the Sunhalo,
     * which is 1.5 times the dimensions of the sun.
     */
    public static final float SUN_HALO_SIZE_FACTOR = 1.5f;
    /**
     * The factor by which the x-coordinate of the initial center of
     * the Sunhalo is multiplied to determine its position.
     */
    public static final float SUN_HALO_CENTER_X_FACTOR = 0.5f;
    /**
     * The factor by which the y-coordinate of the initial
     * center of the Sunhalo is multiplied to determine its position.
     */
    public static final float SUN_HALO_CENTER_Y_FACTOR = 1/3f;
    /**
     * The initial value for opacity when creating the night object.
     */
    public static final float NIGHT_INITIAL_OPACITY = 0f;
    /**
     * Initial angle for the sun animation.
     */
    public static final float SUN_ANIMATION_START_ANGLE = 0f;
    /**
     * Final angle for the sun animation.
     */
    public static final float SUN_ANIMATION_END_ANGLE = 360f;
}
