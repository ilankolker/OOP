package bricker.main;

import danogl.util.Vector2;

/**
 * This class contains all the constants used in the game.
 */

public class Constants {
    /** Dimensions of the paddle. */
    public static Vector2 PADDLE_DIMENSIONS = new Vector2(200, 20);

    /** Dimensions of the ball. */
    public static Vector2 BALL_DIMENSIONS = new Vector2(50, 50);

    /** Width of the game window. */
    public static float WINDOW_DIMENSIONS_X = 700;

    /** Height of the game window. */
    public static float WINDOW_DIMENSIONS_Y = 500;

    /** Tag for identifying balls. */
    public static String BALL_TAG = "ball";

    /** Tag for identifying paddles. */
    public static String PADDLE_TAG = "paddle";

    /** Tag for identifying pucks. */
    public static String PUCK_TAG = "puck";

    /** Tag for identifying finite paddles. */
    public static String FINITE_PADDLE_TAG = "finitePaddle";

    /** Tag for identifying hearts. */
    public static String HEART_TAG = "heart";

    /** Tag for identifying graphic hearts. */
    public static String GRAPHIC_HEART_TAG = "graphicHeartTag";

    /** Maximum number of behaviors. */
    public static final int MAX_NUM_OF_BEHAVIORS = 3;

    /** Number of lives for a finite paddle. */
    public static final int FINITE_PADDLE_LIVES = 4;

    /** Number of lives for a camera. */
    public static final int CAMERA_LIVES = 4;

    /** Initial speed of the ball along the X axis. */
    public static final float BALL_SPEED_X = 250;

    /** Initial speed of the ball along the Y axis. */
    public static final float BALL_SPEED_Y = 250;

    /** Speed at which the paddle moves. */
    public static final float PADDLE_MOVEMENT_SPEED = 300;

    /** Speed of the heart. */
    public static final float HEART_SPEED = 100;

    /** Size of the heart. */
    public static final float HEART_SIZE = WINDOW_DIMENSIONS_X / 15;

    /** Dimensions of the heart. */
    public static final Vector2 HEART_DIMENSIONS = new Vector2(Constants.HEART_SIZE, Constants.HEART_SIZE);

    /** Top-left corner of the graphic life bar. */
    public static final Vector2 GRAPHIC_LIFE_BAR_TOP_LEFT_CORNER = new Vector2(55,
            WINDOW_DIMENSIONS_Y - 50);

    /** Top-left corner of the numeric life bar. */
    public static final Vector2 NUMERIC_LIFE_BAR_TOP_LEFT_CORNER = new Vector2(40,
            WINDOW_DIMENSIONS_Y - 70);

    /** Dimensions of the numeric life bar. */
    public static final Vector2 NUMERIC_LIFE_BAR_DIMENSIONS = new Vector2(100, 40);

    /** Middle of the game window. */
    public static final Vector2 MIDDLE_OF_WINDOW = new Vector2(WINDOW_DIMENSIONS_X * 0.5f,
            WINDOW_DIMENSIONS_Y * 0.5f);

    /** Default number of bricks per row. */
    public static final int DEFAULT_BRICKS_PER_ROW = 8;

    /** Default number of bricks per column. */
    public static final int DEFAULT_BRICKS_PER_COL = 7;

    /** Default number of lives. */
    public static final int DEFAULT_NUM_OF_LIVES = 3;

    /** Default width of the border. */
    public static final int DEFAULT_BORDER_WIDTH = 15;

    /** Maximum number of lives. */
    public static final int MAX_NUMBER_OF_LIVES = 4;

    /** Title of the game window. */
    public static final String WINDOW_TITLE = "Arkanoid";

    /** Path to the puck image asset. */
    public static final String PUCK_IMAGE_PATH = "assets/mockBall.png";

    /** Path to the puck sound asset. */
    public static final String PUCK_SOUND_PATH = "assets/blop.wav";

    /** Path to the paddle image asset. */
    public static final String PADDLE_IMAGE_PATH = "assets/paddle.png";

    /** Path to the brick image asset. */
    public static final String BRICK_IMAGE_PATH = "assets/brick.png";

    /** Path to the background image asset. */
    public static final String BACKGROUND_IMAGE_PATH = "assets/DARK_BG2_small.jpeg";

    /** Path to the heart image asset. */
    public static final String HEART_IMAGE_PATH = "assets/heart.png";

    /** Path to the ball image asset. */
    public static final String BALL_IMAGE_PATH = "assets/ball.png";

    /** Path to the ball sound asset. */
    public static final String BALL_SOUND_PATH = "assets/blop.wav";

    /** Message displayed when the player loses. */
    public static final String LOSE_MESSAGE = "You Lose! Play again?";

    /** Message displayed when the player wins. */
    public static final String VICTORY_MESSAGE = "You Win! Play again?";

    /** Center of the upper border. */
    public static Vector2 UPPER_BORDER_CENTER = new Vector2(WINDOW_DIMENSIONS_X / 2,
            DEFAULT_BORDER_WIDTH / 2.f);

    /** Dimensions of the upper border. */
    public static Vector2 UPPER_BORDER_DIMENSIONS = new Vector2(WINDOW_DIMENSIONS_X,
            Constants.DEFAULT_BORDER_WIDTH);

    /** Center of the left border. */
    public static Vector2 LEFT_BORDER_CENTER = new Vector2(DEFAULT_BORDER_WIDTH / 2.f,
            WINDOW_DIMENSIONS_Y / 2.f);

    /** Center of the right border. */
    public static Vector2 RIGHT_BORDER_CENTER = new Vector2(WINDOW_DIMENSIONS_X -
            (DEFAULT_BORDER_WIDTH / 2.f), WINDOW_DIMENSIONS_Y / 2.f);

    /** Space between rows of bricks. */
    public static float SPACE_BETWEEN_ROWS = 7;

    /** Space between columns of bricks. */
    public static float SPACE_BETWEEN_COLS = 1;

    /** Height of a brick. */
    public static float BRICK_HEIGHT = 15;

    /** Height of the paddle. */
    public static int PADDLE_HEIGHT = (int) WINDOW_DIMENSIONS_Y - 30;

    /** Center of the paddle. */
    public static Vector2 PADDLE_CENTER = new Vector2(WINDOW_DIMENSIONS_X / 2, PADDLE_HEIGHT);

    /** Number of side borders. */
    public static int NUM_OF_SIDE_BORDERS = 2;

    /**
     * This is the constructor for the Constants class.
     */
    public Constants() {

    }
}

