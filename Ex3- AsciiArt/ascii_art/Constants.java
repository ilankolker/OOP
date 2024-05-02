package ascii_art;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Constants class containing various constants used in the ASCII art application.
 */
public class Constants {
    /** Command string for generating ASCII art. */
    public static final String ASCII_ART_COMMAND = "asciiArt";

    /** Message indicating that the charset is empty. */
    public static final String EMPTY_CHARSET_MSG = "Did not execute. Charset is empty.";

    /** Prefix for user input. */
    public static final String INPUT_PREFIX = ">>>";

    /** Number of commands supported by the application. */
    public static final int NUM_OF_COMMANDS = 8;

    /** Default resolution value. */
    public static final int DEFAULT_RES = 128;

    /** Default character set. */
    public static final char[] DEFAULT_CHARSET = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /** Default character set as an ArrayList. */
    public static final ArrayList<Character> DEFAULT_CHARSET_ARR = new ArrayList<>(
            Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));

    /** Minimum ASCII value for a character. */
    public static final int MIN_CHAR_ASCII_NUM = 32;

    /** Maximum ASCII value for a character. */
    public static final int MAX_CHAR_ASCII_NUM = 126;

    /** ASCII value for '0'. */
    public static final int ZERO_ASCII_NUM = 48;

    /** ASCII value for '9'. */
    public static final int NINE_ASCII_NUM = 57;

    /** Factor by which to multiply the resolution for operations. */
    public static final int RES_FACTOR = 2;

    /** List of supported commands. */
    public static final ArrayList<String> COMMANDS = new ArrayList<>(NUM_OF_COMMANDS);

    /** Message indicating an incorrect command format. */
    public static final String INCORRECT_COMMAND = "Did not execute due to incorrect command.";

    /** Message indicating an incorrect format for adding a character. */
    public static final String INCORRECT_ADD_COMMAND = "Did not add due to incorrect format.";

    /** Message indicating an incorrect format for removing a character. */
    public static final String INCORRECT_REMOVE_COMMAND = "Did not remove due to incorrect format.";

    /** Message indicating that resolution did not change due to exceeding boundaries. */
    public static final String BOUNDARIES_ERR_RES_COMMAND = "Did not change resolution" +
            " due to exceeding boundaries.";

    /** Message indicating that resolution did not change due to incorrect format. */
    public static final String INCORRECT_RES_COMMAND = "Did not change resolution due to incorrect format.";

    /** Message indicating a problem with the image file. */
    public static final String INCORRECT_IMAGE_COMMAND = "Did not execute due to problem with image file.";

    /** Message indicating that the output method did not change due to incorrect format. */
    public static final String INCORRECT_OUTPUT_COMMAND = "Did not change output method due to" +
            " incorrect format.";

    /** Message indicating a change in resolution. */
    public static final String CHANGED_RES_MSG = "Resolution set to %d.";

    /** Command to exit the application. */
    public static final String EXIT_COMMAND = "exit";

    /** Command to display the character set. */
    public static final String CHARS_COMMAND = "chars";

    /** Command to add a character to the character set. */
    public static final String ADD_COMMAND = "add";

    /** Command to remove a character from the character set. */
    public static final String REMOVE_COMMAND = "remove";

    /** Command to change the resolution. */
    public static final String RES_COMMAND = "res";

    /** Command to change the image file. */
    public static final String IMAGE_COMMAND = "image";

    /** Command to change the output method. */
    public static final String OUTPUT_COMMAND = "output";

    /** Default font for the output. */
    public static final String OUTPUT_FONT = "Courier New";

    /** Default filename for the output. */
    public static final String OUTPUT_FILENAME = "out.html";

    /** Special keyword to indicate all characters. */
    public static final String ALL = "all";

    /** Special keyword to indicate space character. */
    public static final String SPACE = "space";

    /** Special keyword to increase resolution. */
    public static final String RES_UP = "up";

    /** Special keyword to decrease resolution. */
    public static final String RES_DOWN = "down";

    /** Output method for console. */
    public static final String CONSOLE_OUTPUT = "console";

    /** Output method for HTML. */
    public static final String HTML_OUTPUT = "html";

    /** Path to the cat default image. */
    public static final String CAT_IMAGE_PATH = "cat.jpeg";
    /** The factor for the red component in RGB to grayscale conversion. */
    public static double RED_FACTOR = 0.2126;

    /** The factor for the green component in RGB to grayscale conversion. */
    public static double GREEN_FACTOR = 0.7152;

    /** The factor for the blue component in RGB to grayscale conversion. */
    public static double BLUE_FACTOR = 0.0722;

    /** The maximum value for a RGB component. */
    public static int MAX_RGB_RATE = 255;
}
