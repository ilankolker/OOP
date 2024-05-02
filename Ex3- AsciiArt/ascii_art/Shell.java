package ascii_art;

import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import exceptions.EmptyQueueException;
import exceptions.InvalidInputException;
import image.Image;
import image.ImageManipulator;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

/**
 * The Shell class Provides a command-line interface for interacting with the application.
 * Manages user input, commands execution, and exception handling.
 */
public class Shell {
    private static PriorityQueue<Character> charQueue;
    private static int res;
    private static Image image;
    private static AsciiOutput asciiOutput;

    /**
     * Constructs a new Shell instance.
     * Initializes the list of valid commands, default resolution, ASCII output,
     * image, and character queue. Initializes the character queue with characters '0' to '9'.
     *
     * @throws IOException in case the image failed to be created.
     */
    public Shell() throws IOException {
        Constants.COMMANDS.addAll(Arrays.asList(Constants.ADD_COMMAND, Constants.CHARS_COMMAND,
                Constants.REMOVE_COMMAND, Constants.RES_COMMAND, Constants.IMAGE_COMMAND,
                Constants.OUTPUT_COMMAND, Constants.ASCII_ART_COMMAND, Constants.EXIT_COMMAND));
        res = Constants.DEFAULT_RES;
        asciiOutput = new ConsoleAsciiOutput();
        image = new Image(Constants.CAT_IMAGE_PATH);
        charQueue = new PriorityQueue<>();
        // initialize char queue to 0 - 9
        initCharQueue();
    }

    /**
     * Pads the provided image and returns the padded image as a 2D Color array.
     * @param image The image to pad.
     * @return The padded image as a 2D Color array.
     */
    private static Color[][] padImage(Image image) {
        return ImageManipulator.padImage(image);
    }

    /**
     * Initializes the character queue with characters '1' to '9'.
     */
    private static void initCharQueue() {
        for (int i = Constants.ZERO_ASCII_NUM; i <= Constants.NINE_ASCII_NUM; i++) {
            charQueue.add((char) i);
        }
    }

    /**
     * The main method of the program.
     * Initializes a new Shell instance and runs the shell.
     * Catches and handles IOException by printing an error message.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Shell shell = new Shell();
            shell.run();
        } catch (IOException e) {
            System.out.println(Constants.INCORRECT_IMAGE_COMMAND);
        }
    }

    /**
     * Runs the shell, processing user commands until the exit command is entered.
     * Reads user input, validates commands, and executes corresponding actions.
     * Handles various exceptions by printing error messages.
     */
    public void run() {
        String userInput;
        String userCommand;
        String[] userCommandArgs;

        do {
            System.out.print(Constants.INPUT_PREFIX + " ");
            userInput = KeyboardInput.readLine();
            userCommandArgs = userInput.split(" ");
            userCommand = userCommandArgs[0];

            try {
                validateUserCommand(userCommand);
                switch (userCommand) {
                    case Constants.CHARS_COMMAND: {
                        chars();
                        break;
                    }
                    case Constants.ADD_COMMAND: {
                        add(userCommandArgs);
                        break;
                    }
                    case Constants.REMOVE_COMMAND: {
                        remove(userCommandArgs);
                        break;
                    }
                    case Constants.RES_COMMAND: {
                        res(userCommandArgs);
                        break;
                    }
                    case Constants.IMAGE_COMMAND: {
                        try {
                            image(userCommandArgs);
                        } catch (IOException e) {
                            System.out.println(Constants.INCORRECT_IMAGE_COMMAND);
                        }
                        break;
                    }
                    case Constants.OUTPUT_COMMAND: {
                        output(userCommandArgs);
                    }
                    case Constants.ASCII_ART_COMMAND: {
                        try {
                            runAsciiArt();
                        } catch (EmptyQueueException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    }
                }
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        } while (!userCommand.equals(Constants.EXIT_COMMAND));
    }

    /**
     * Validates the user command against the list of valid commands.
     *
     * @param userCommand the user-provided command
     * @throws InvalidInputException if the user command is not valid
     */
    private static void validateUserCommand(String userCommand)
            throws InvalidInputException {
        if (!Constants.COMMANDS.contains(userCommand)) {
            throw new InvalidInputException(Constants.INCORRECT_COMMAND);
        }
    }

    /**
     * Prints the characters in the character queue in ascending order.
     */
    private static void chars() {
        List<Character> sortedList = new ArrayList<>(charQueue);
        Collections.sort(sortedList);
        for (char c : sortedList) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    /**
     * Processes the add or remove operation based on the user input.
     *
     * @param userCommandArgs the user-provided command arguments
     * @param charProcessor   the functional interface for processing characters
     * @throws InvalidInputException if the user input is invalid
     */
    private static void addOrRemove(String[] userCommandArgs, Consumer<Character> charProcessor)
            throws InvalidInputException {
        String userArg = userCommandArgs[1];
        if (userArg.length() == 1) {
            char charToProcess = userArg.charAt(0);
            charProcessor.accept(charToProcess);

        } else if (userArg.equals(Constants.ALL)) {
            ProcessAllChars(charProcessor);

        } else if (userArg.equals(Constants.SPACE)) {
            charProcessor.accept(' ');

        } else if (userArg.length() == 3 && userArg.charAt(1) == '-') {
            int firstCharAsciiVal = userArg.charAt(0);
            int lastCharAsciiVal = userArg.charAt(2);
            ProcessRangeOfChars(firstCharAsciiVal, lastCharAsciiVal, charProcessor);
        } else {
            // throws an exception with no specific message so that the callers method
            // could deal with the exception
            throw new InvalidInputException("");
        }
    }

    /**
     * Adds characters based on the user input.
     *
     * @param userCommandArgs the user-provided command arguments
     * @throws InvalidInputException if the user input is invalid
     */
    private static void add(String[] userCommandArgs) throws InvalidInputException {
        try {
            addOrRemove(userCommandArgs, Shell::addSingleChar);
        } catch (InvalidInputException e) {
            e.setMessage(Constants.INCORRECT_ADD_COMMAND);
            throw e;
        }
    }

    /**
     * Removes characters based on the user input.
     *
     * @param userCommandArgs the user-provided command arguments
     * @throws InvalidInputException if the user input is invalid
     */
    private static void remove(String[] userCommandArgs) throws InvalidInputException {
        try {
            addOrRemove(userCommandArgs, Shell::removeSingleChar);
        } catch (InvalidInputException e) {
            e.setMessage(Constants.INCORRECT_REMOVE_COMMAND);
            throw e;
        }
    }

    /**
     * Changes the resolution based on the user input.
     * The change will eventually cause the resolution to increase or decrease by two times.
     *
     * @param userCommandArgs the user-provided command arguments
     * @throws InvalidInputException if the user input is invalid
     */
    private static void res(String[] userCommandArgs) throws InvalidInputException {
        String userArg = userCommandArgs[1];
        int paddedImgWidth = ImageManipulator.findClosestPowerOfTwo(image.getWidth());
        int paddedImgHeight = ImageManipulator.findClosestPowerOfTwo(image.getHeight());
        if (userArg.equals(Constants.RES_UP)) {
            if (res * Constants.RES_FACTOR > paddedImgWidth) {
                System.out.println(Constants.BOUNDARIES_ERR_RES_COMMAND);
                return;
            }
            res *= Constants.RES_FACTOR;
            System.out.println(String.format(Constants.CHANGED_RES_MSG, res));
        } else if (userArg.equals(Constants.RES_DOWN)) {
            int minCharsInRow = Math.max(1, paddedImgWidth / paddedImgHeight);
            if (res / Constants.RES_FACTOR < minCharsInRow) {
                System.out.println(Constants.BOUNDARIES_ERR_RES_COMMAND);
                return;
            }
            res /= Constants.RES_FACTOR;
            System.out.println(String.format(Constants.CHANGED_RES_MSG, res));
        } else {
            throw new InvalidInputException(Constants.INCORRECT_RES_COMMAND);
        }
    }

    /**
     * Changes the image based on the user input.
     *
     * @param userCommandArgs the user-provided command arguments
     * @throws IOException if there is an error reading the image file
     */
    private static void image(String[] userCommandArgs) throws IOException {
        String userArg = userCommandArgs[1];
        image = new Image(userArg);
    }

    /**
     * Changes the output mode based on the user input.
     *
     * @param userCommandArgs the user-provided command arguments
     * @throws InvalidInputException if the user input is incorrect
     */
    private void output(String[] userCommandArgs) throws InvalidInputException {
        String userArg = userCommandArgs[1];
        if (userArg.equals(Constants.CONSOLE_OUTPUT)) {
            asciiOutput = new ConsoleAsciiOutput();
        } else if (userArg.equals(Constants.HTML_OUTPUT)) {
            asciiOutput = new HtmlAsciiOutput(Constants.OUTPUT_FILENAME, Constants.OUTPUT_FONT);
        } else {
            throw new InvalidInputException(Constants.INCORRECT_OUTPUT_COMMAND);
        }
    }

    /**
     * Runs the ASCII art algorithm using the current charQueue.
     *
     * @throws EmptyQueueException if the charQueue is empty
     */
    private void runAsciiArt() throws EmptyQueueException {
        if (charQueue.isEmpty()) {
            throw new EmptyQueueException(Constants.EMPTY_CHARSET_MSG);
        }
        char[] charArr = QueueOfCharsToCharArr();
        AsciiArtAlgorithm asciiArtAlgorithm = new AsciiArtAlgorithm(image, res, charArr);
        asciiOutput.out(asciiArtAlgorithm.run());
    }

    /**
     * Converts the charQueue to a char array.
     *
     * @return the char array containing the characters from the charQueue
     */
    private static char[] QueueOfCharsToCharArr() {
        char[] charArr = new char[charQueue.size()];
        int i = 0;
        for (char character : charQueue) {
            charArr[i++] = character;
        }
        return charArr;
    }

    /**
     * Adds a single character to the character queue if it is not already present.
     *
     * @param charToAdd the character to add to the queue
     */
    private static void addSingleChar(char charToAdd) {
        if (!charQueue.contains(charToAdd)) {
            charQueue.add(charToAdd);
        }
    }

    /**
     * Removes a single character from the character queue.
     *
     * @param charToAdd the character to remove from the queue
     */
    private static void removeSingleChar(char charToAdd) {
        charQueue.remove(charToAdd);
    }

    /**
     * Processes a range of characters specified by their ASCII values.
     * The charProcessor consumer is applied to each character in the range.
     *
     * @param firstCharAsciiVal the ASCII value of the first character in the range
     * @param lastCharAsciiVal  the ASCII value of the last character in the range
     * @param charProcessor     the consumer to apply to each character in the range
     */
    private static void ProcessRangeOfChars(int firstCharAsciiVal, int lastCharAsciiVal,
                                            Consumer<Character> charProcessor) {
        if (firstCharAsciiVal > lastCharAsciiVal) {
            int temp = firstCharAsciiVal;
            firstCharAsciiVal = lastCharAsciiVal;
            lastCharAsciiVal = temp;
        }
        for (int i = firstCharAsciiVal; i <= lastCharAsciiVal; i++) {
            charProcessor.accept((char) i);
        }
    }

    /**
     * Processes all characters within the ASCII range defined by Constants.MIN_CHAR_ASCII_NUM
     * and Constants.MAX_CHAR_ASCII_NUM.
     * The charProcessor consumer is applied to each character in the range.
     *
     * @param charProcessor the consumer to apply to each character in the range
     */
    private static void ProcessAllChars(Consumer<Character> charProcessor) {
        for (int i = Constants.MIN_CHAR_ASCII_NUM; i <= Constants.MAX_CHAR_ASCII_NUM; i++) {
            charProcessor.accept((char) i);
        }
    }

}

