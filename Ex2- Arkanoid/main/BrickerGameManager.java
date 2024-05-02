package bricker.main;

import bricker.brick_strategies.*;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.*;
import danogl.util.Counter;
import danogl.util.Vector2;
import danogl.gui.UserInputListener;

import java.awt.event.KeyEvent;
import java.util.Random;


/**
 * Manages the game logic for a brick-breaking game.
 */
public class BrickerGameManager extends GameManager {
    private final int brickPerRow;
    private final int brickPerCol;
    private Vector2 windowDimensions;
    private Ball ball;
    private Counter livesCounter;
    private Counter bricksCounter;
    private UserInputListener inputListener;
    private WindowController windowController;
    private ImageReader imageReader;
    private SoundReader soundReader;

    /**
     * Constructs a new BrickerGameManager with the specified parameters.
     *
     * @param windowTitle      the title of the game window
     * @param windowDimensions the dimensions of the game window
     * @param brickPerRow      the number of bricks per row
     * @param brickPerCol      the number of bricks per column
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int brickPerRow,
                              int brickPerCol) {
        super(windowTitle, windowDimensions);
        this.brickPerRow = brickPerRow;
        this.brickPerCol = brickPerCol;
        this.windowDimensions = windowDimensions;
    }

    /**
     * Constructs a new BrickerGameManager with the specified parameters.
     *
     * @param windowTitle      the title of the game window
     * @param windowDimensions the dimensions of the game window
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
        this.brickPerRow = Constants.DEFAULT_BRICKS_PER_ROW;
        this.brickPerCol = Constants.DEFAULT_BRICKS_PER_COL;
        this.windowDimensions = windowDimensions;
    }

    /**
     * Initializes the game with the specified resources.
     *
     * @param imageReader      the image reader for loading images
     * @param soundReader      the sound reader for loading sounds
     * @param inputListener    the input listener for user input events
     * @param windowController the window controller for managing the game window
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.bricksCounter = new Counter(brickPerRow * brickPerCol);
        this.soundReader = soundReader;
        this.imageReader = imageReader;
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        createBall();
        createPaddle();
        createSideBorders();
        createUpperBorder();
        createBricks();
        createBackground();
        createLives();

    }

    /**
     * Creates the visual representation of the player's remaining lives.
     */
    private void createLives() {
        this.livesCounter = new Counter(Constants.DEFAULT_NUM_OF_LIVES);   // initialized to 3 lives

        Renderable heartImg = imageReader.readImage(Constants.HEART_IMAGE_PATH, true);
        GraphicLifeBar graphicLifeBar = new GraphicLifeBar(Constants.GRAPHIC_LIFE_BAR_TOP_LEFT_CORNER,
                heartImg, livesCounter, gameObjects());
        gameObjects().addGameObject(graphicLifeBar, Layer.UI);

        TextRenderable numericLifeBarText = new TextRenderable(String.valueOf(livesCounter.value()));

        new NumericLifeBar(Constants.NUMERIC_LIFE_BAR_TOP_LEFT_CORNER,
                Constants.NUMERIC_LIFE_BAR_DIMENSIONS, numericLifeBarText,livesCounter, gameObjects());
    }

    /**
     * Updates the game state based on the elapsed time.
     *
     * @param deltaTime the time elapsed since the last update
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkPucks();
        checkFinitePaddle();
        checkCamera();
        checkAdditionalHeart();
        checkGameStatus();
    }

    /**
     * Checks if an object is out of bounds of the game window.
     *
     * @param gameObject the game object to check
     * @return true if the object is out of bounds, false otherwise
     */
    private boolean isObjectOutOfBounds(GameObject gameObject) {
        return gameObject.getCenter().y() > windowDimensions.y();
    }

    /**
     * Checks for pucks that are out of bounds and removes them from the game.
     */
    private void checkPucks() {
        for (GameObject gameObject : gameObjects()) {
            if (gameObject.getTag().equals(Constants.PUCK_TAG) && isObjectOutOfBounds(gameObject)) {
                gameObjects().removeGameObject(gameObject);
            }
        }
    }

    /**
     * Checks if the camera needs to be reset based on the number of lives.
     */
    private void checkCamera() {
        if (ball.getCollisionCounter() >= Constants.CAMERA_LIVES) {
            ball.resetCounter();
            this.setCamera(null);
        }
    }

    /**
     * Checks for finite paddles that need to be removed from the game.
     */
    private void checkFinitePaddle() {
        for (GameObject gameObject : gameObjects()) {
            if (gameObject.getTag().equals(Constants.FINITE_PADDLE_TAG) &&
                    ((FinitePaddle)gameObject).needsToBeRemoved()) {
                gameObjects().removeGameObject(gameObject);
            }
        }
    }

    /**
     * Checks for additional heart game objects, removes them if out of bounds,
     * and increments the player's lives counter if needed.
     */
    private void checkAdditionalHeart() {
        for (GameObject gameObject : gameObjects()) {
            if (gameObject.getTag().equals(Constants.HEART_TAG)) {
                if (isObjectOutOfBounds(gameObject)) {
                    gameObjects().removeGameObject(gameObject);
                } else if (((Heart) gameObject).needsToBeRemoved()) {
                    gameObjects().removeGameObject(gameObject);
                    if (livesCounter.value() < Constants.MAX_NUMBER_OF_LIVES) {
                        livesCounter.increment();
                    }

                }
            }
        }
    }

    /**
     * Checks the game status to determine if the player has won, lost, or if the game should continue.
     * Displays a dialog box with the appropriate message and resets the game if needed.
     */
    private void checkGameStatus() {
        double ballHeight = ball.getCenter().y();
        String prompt = "";
        // check losing case
        if (ballHeight > windowDimensions.y()) {
            livesCounter.decrement();
            this.ball.initBallSpeed(new Vector2(Constants.BALL_SPEED_X, Constants.BALL_SPEED_Y));
            ball.setCenter(Constants.MIDDLE_OF_WINDOW);
            if (livesCounter.value() <= 0) {
                prompt = Constants.LOSE_MESSAGE;
            }
        }
        //check winning case
        if (bricksCounter.value() <= 0 || inputListener.isKeyPressed(KeyEvent.VK_W)) {
            prompt = Constants.VICTORY_MESSAGE;
            windowController.resetGame();
        }

        if (!prompt.isEmpty()) {
            if (windowController.openYesNoDialog(prompt)) {
                windowController.resetGame();
            }
            else {
                windowController.closeWindow();
            }
        }
    }

    /**
     * Creates the ball GameObject and adds it to the game.
     */
    private void createBall() {
        Renderable ballImg =
                imageReader.readImage(Constants.BALL_IMAGE_PATH, true);
        Sound collisionSound = soundReader.readSound(Constants.BALL_SOUND_PATH);
        Ball ball = new Ball(Vector2.ZERO, Constants.BALL_DIMENSIONS, ballImg, collisionSound);
        gameObjects().addGameObject(ball);
        this.ball = ball;
        this.ball.initBallSpeed(new Vector2(Constants.BALL_SPEED_X, Constants.BALL_SPEED_Y));
        ball.setTag(Constants.BALL_TAG);
        ball.setCenter(Constants.MIDDLE_OF_WINDOW);
    }

    /**
     * Creates the background GameObject and adds it to the game.
     */
    private void createBackground() {
        ImageRenderable backgroundImg =
                imageReader.readImage(Constants.BACKGROUND_IMAGE_PATH, true);
        GameObject background = new GameObject(Vector2.ZERO, new Vector2(windowDimensions.x(),
                windowDimensions.y()), backgroundImg);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
    }

    /**
     * Creates the upper border GameObject and adds it to the game.
     */
    private void createUpperBorder() {
        GameObject upperBorder =
                new GameObject(Vector2.ZERO, Constants.UPPER_BORDER_DIMENSIONS, null);
        upperBorder.setCenter(Constants.UPPER_BORDER_CENTER);
        gameObjects().addGameObject(upperBorder, Layer.DEFAULT);
    }

    /**
     * Creates the side border GameObjects and adds them to the game.
     */
    private void createSideBorders() {
        // create left border
        GameObject leftBorder =
                new GameObject(Vector2.ZERO, new Vector2(Constants.DEFAULT_BORDER_WIDTH,
                        Constants.WINDOW_DIMENSIONS_Y), null);
        leftBorder.setCenter(Constants.LEFT_BORDER_CENTER);
        gameObjects().addGameObject(leftBorder, Layer.DEFAULT);
        // create right border
        GameObject rightBorder =
                new GameObject(Vector2.ZERO, new Vector2(Constants.DEFAULT_BORDER_WIDTH,
                        windowDimensions.y()), null);
        rightBorder.setCenter(Constants.RIGHT_BORDER_CENTER);
        gameObjects().addGameObject(rightBorder, Layer.DEFAULT);

    }

    /**
     * Creates the brick GameObjects and adds them to the game.
     */
    private void createBricks() {
        for (int rowIndex = 0; rowIndex < brickPerRow; rowIndex++) {
            for (int colIndex = 0; colIndex < brickPerCol; colIndex++) {
                createBrick(rowIndex, colIndex);
            }
        }
    }

    /**
     * Creates a single brick GameObject at the specified row and column indices.
     *
     * @param rowIndex the row index of the brick
     * @param colIndex the column index of the brick
     */
    private void createBrick(int rowIndex, int colIndex) {
        Renderable brickImg = imageReader.readImage(Constants.BRICK_IMAGE_PATH, false);
        CollisionStrategyFactory collisionStrategyFactory = new CollisionStrategyFactory(gameObjects(),
                imageReader,soundReader, inputListener, windowDimensions,
                this, ball, bricksCounter);
        Random rnd = new Random();
        int strategyNumber = rnd.nextInt(collisionStrategyFactory.NUM_OF_STRATEGIES) + 1;
        CollisionStrategy strategy =
                collisionStrategyFactory.createStrategy(strategyNumber);

        float windowXWithoutBorder = windowDimensions.x() - Constants.NUM_OF_SIDE_BORDERS *
                Constants.DEFAULT_BORDER_WIDTH - Constants.SPACE_BETWEEN_COLS * brickPerRow;
        float brickWidth = windowXWithoutBorder / (brickPerRow + 1);
        GameObject brick =
                new Brick(Vector2.ZERO, new Vector2(brickWidth,Constants.BRICK_HEIGHT),
                        brickImg,
                        strategy);

        brick.setCenter(new Vector2(Constants.SPACE_BETWEEN_COLS + brickWidth +
                Constants.DEFAULT_BORDER_WIDTH + (brickWidth + Constants.SPACE_BETWEEN_COLS) * rowIndex,
                (Constants.BRICK_HEIGHT + Constants.SPACE_BETWEEN_ROWS) * colIndex +
                        Constants.BRICK_HEIGHT + Constants.DEFAULT_BORDER_WIDTH));
        gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
    }

    /**
     * Creates the paddle GameObject and adds it to the game.
     */
    private void createPaddle() {
        Renderable paddleImg =
                imageReader.readImage(Constants.PADDLE_IMAGE_PATH, true);
        GameObject paddle = new Paddle(Vector2.ZERO, Constants.PADDLE_DIMENSIONS, paddleImg, inputListener);
        paddle.setCenter(Constants.PADDLE_CENTER);
        paddle.setTag(Constants.PADDLE_TAG);
        gameObjects().addGameObject(paddle, Layer.DEFAULT);
    }

    /**
     * The main method of the game. Initializes the game manager and starts the game loop.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        int brickPerRow = Constants.DEFAULT_BRICKS_PER_ROW;
        int brickPerCol = Constants.DEFAULT_BRICKS_PER_COL;
        if (args.length > 0) {
            brickPerRow = Integer.parseInt(args[0]);
            brickPerCol = Integer.parseInt(args[1]);
        }
        BrickerGameManager brickerGameManager = new BrickerGameManager(Constants.WINDOW_TITLE,
                new Vector2(Constants.WINDOW_DIMENSIONS_X, Constants.WINDOW_DIMENSIONS_Y), brickPerRow,
                brickPerCol);
        brickerGameManager.run();
    }
}
