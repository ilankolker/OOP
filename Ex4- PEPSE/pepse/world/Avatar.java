package pepse.world;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.Constants;
import pepse.ui.EnergyUI;
import pepse.world.trees.Tree;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * Avatar class represents the player's avatar in the game, extending GameObject.
 */
public class Avatar extends GameObject {
    /** The color of the avatar. */
    private static final Color AVATAR_COLOR = Color.DARK_GRAY;
    /** The image reader for loading images. */
    private final ImageReader imageReader;
    /** The set of trees observing the avatar. */
    private final Set<Tree> observers;
    /** The energy level of the avatar. */
    private float energy = Constants.MAX_ENERGY;
    /** The animation for idle state facing right. */
    private AnimationRenderable idleRightAnimation;
    /** The animation for run state facing right. */
    private AnimationRenderable runRightAnimation;
    /** The animation for jump state facing right. */
    private AnimationRenderable jumpRightAnimation;
    /** The input listener for user input. */
    private UserInputListener inputListener;

    /**
     * Constructs an Avatar object with the specified position, input listener, image reader, and observers.
     * @param pos         The position of the avatar.
     * @param inputListener The input listener for user input.
     * @param imageReader The image reader for loading images.
     * @param observers   The set of trees observing the avatar.
     */
    public Avatar(Vector2 pos, UserInputListener inputListener, ImageReader imageReader,
                  HashSet<Tree> observers) {
        super(pos, Vector2.ONES.mult(Constants.AVATAR_SIZE), null);
        this.imageReader = imageReader;
        this.observers = observers;
        Renderable avatarIdleImg = imageReader.readImage(Constants.AVATAR_PATH_IMG,
                true);
        renderer().setRenderable(avatarIdleImg);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(Constants.GRAVITY);
        setTag(Constants.AVATAR_TAG);
        idleRightAnimation = createAnimationRenderable(Constants.IDLE, Constants.IDLE_CLIPS);
        runRightAnimation = createAnimationRenderable(Constants.RUN, Constants.RUN_CLIPS);
        jumpRightAnimation = createAnimationRenderable(Constants.JUMP, Constants.JUMP_CLIPS);
        this.inputListener = inputListener;
    }

    /**
     * Updates the avatar's state based on user input and current energy level.
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float xVel = updateXVelocity();
        setAvatarMovement(xVel);
        setJumpState();
        setIdleState();
        EnergyUI.update();
    }

    /**
     * Calculates the avatar's horizontal velocity based on user input.
     * @return The horizontal velocity.
     */
    private float updateXVelocity() {
        float xVel = 0;
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT) && energy >= Constants.MOVEMENT_ENERGY_COST) {
            xVel += Constants.VELOCITY_X;
            renderer().setIsFlippedHorizontally(false);
            renderer().setRenderable(runRightAnimation);
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT) && energy >= Constants.MOVEMENT_ENERGY_COST) {
            xVel -= Constants.VELOCITY_X;
            renderer().setRenderable(runRightAnimation);
            renderer().setIsFlippedHorizontally(true);
        }
        return xVel;
    }

    /**
     * Sets the avatar's movement state based on the calculated horizontal velocity.
     * @param xVel The horizontal velocity.
     */
    private void setAvatarMovement(float xVel) {
        transform().setVelocityX(xVel);
        if (xVel != 0) {
            energy -= Constants.MOVEMENT_ENERGY_COST;
        }
    }

    /**
     * Sets the avatar's jump state based on user input and energy level.
     */
    private void setJumpState() {
        if(inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0 &&
                energy >= Constants.JUMP_ENERGY_COST) {
            transform().setVelocityY(Constants.VELOCITY_Y);
            energy -= Constants.JUMP_ENERGY_COST;
            renderer().setRenderable(jumpRightAnimation);
            updateObservers();
        }
    }

    /**
     * Sets the avatar's idle state if it's not moving.
     */
    private void setIdleState() {
        if (getVelocity().x() == 0 && getVelocity().y() == 0) {
            addEnergy(Constants.IDLE_ENERGY_GAIN);
            renderer().setRenderable(idleRightAnimation);
        }
    }

    /**
     * Updates all observer trees.
     */
    private void updateObservers() {
        for (Tree tree : observers) {
            tree.update();
        }
    }

    /**
     * Creates an animation renderable for the specified command and number of clips.
     * @param command   The command for the animation (e.g., "idle", "run", "jump").
     * @param numOfClips The number of clips in the animation.
     * @return The animation renderable.
     */
    private AnimationRenderable createAnimationRenderable(String command, int numOfClips) {
        Renderable[] clips = new Renderable[numOfClips];
        for (int i = 0; i < clips.length; i++) {
            clips[i] = imageReader.readImage("assets/" + command  + "_" + i + ".png",
                    true);
        }
        return new AnimationRenderable(clips, Constants.TIME_BETWEEN_CLIPS);
    }

    /**
     * Adds energy to the avatar, up to the maximum energy level.
     * @param energyToAdd The amount of energy to add.
     */
    public void addEnergy(float energyToAdd) {
        energy = Math.min(energy + energyToAdd, Constants.MAX_ENERGY);
    }

    /**
     * Returns the current energy level of the avatar.
     * @return The current energy level.
     */
    public float getEnergy() {
        return energy;
    }

    /**
     * Handles collision events with other game objects.
     * @param other     The other game object involved in the collision.
     * @param collision The collision information.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
    }
}
