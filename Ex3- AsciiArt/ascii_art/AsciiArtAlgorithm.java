package ascii_art;

import image.Image;
import image.ImageManipulator;
import image_char_matching.SubImgCharMatcher;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

/**
 * The AsciiArtAlgorithm class is responsible for converting an Image into ASCII art.
 * It uses a SubImgCharMatcher to match sub-images of the original Image with ASCII characters
 * based on their brightness.
 */
public class AsciiArtAlgorithm {
    private static final SubImgCharMatcher subImgCharMatcher =
            new SubImgCharMatcher(Constants.DEFAULT_CHARSET);
    private static HashSet<Character> previousCharSet = new HashSet<>(Constants.DEFAULT_CHARSET_ARR);
    private static final HashMap<Color[][], Double> subImgBrightnessMap = new HashMap<>();
    private final Image image;
    private final int resolution;
    private static int previousResolution = 0;
    private static Image previousImage = null;
    private static Color[][][][] dividedImage = null;
    private static int maxSubImgColIndex = 0;
    private static int maxSubImgRowIndex = 0;


    /**
     * Constructs an AsciiArtAlgorithm object with the specified Image, resolution, and character set.
     * @param image     The Image object to convert to ASCII art.
     * @param resolution The resolution used to divide the Image into sub-images.
     * @param charSet   The character set used for ASCII representation.
     */
    public AsciiArtAlgorithm (Image image, int resolution, char[] charSet) {
        this.image = image;
        this.resolution = resolution;
        updateCharSet(charSet);
    }

    /**
     * Updates the character set used for ASCII representation.
     * @param charSet The new character set to use.
     */
    private void updateCharSet(char[] charSet) {
        HashSet<Character> tmpCharSetArr = new HashSet<>();

        for (char c : charSet) {
            tmpCharSetArr.add(c);
            if (!previousCharSet.contains(c)) {
                subImgCharMatcher.addChar(c);
            }
        }

        for (char c : previousCharSet) {
            if (!tmpCharSetArr.contains(c)) {
                subImgCharMatcher.removeChar(c);
            }
        }
        previousCharSet = tmpCharSetArr;
    }

    /**
     * Runs the ASCII art conversion algorithm on the Image.
     * @return A 2D array of characters representing the ASCII art version of the Image.
     */
    public char [][] run() {
        if (!isSameImgAndRes()) {
            Color[][] colorArrOfImage = ImageManipulator.padImage(image);
            dividedImage = ImageManipulator.divideImageToSubImages(colorArrOfImage, resolution);
            maxSubImgColIndex = dividedImage[0].length;
            maxSubImgRowIndex = dividedImage.length;
        }
        char [][] dividedImageCharArr = new char[maxSubImgColIndex][maxSubImgRowIndex];
        double subImageBrightness;
        char subImageChar;

        for (int i = 0; i < maxSubImgRowIndex; i++) {
            for (int j = 0; j < maxSubImgColIndex; j++) {
                if (isSameImgAndRes()) {
                    subImageBrightness = subImgBrightnessMap.get(dividedImage[i][j]);
                    subImageChar = subImgCharMatcher.getCharByImageBrightness(subImageBrightness);
                }
                else {
                    subImageBrightness = ImageManipulator.calcImgBrightness(dividedImage[i][j]);
                    subImageChar = subImgCharMatcher.getCharByImageBrightness(subImageBrightness);
                    subImgBrightnessMap.put(dividedImage[i][j], subImageBrightness);
                }
                dividedImageCharArr[i][j] = subImageChar;
            }
        }
        previousImage = image;
        previousResolution = resolution;
        return dividedImageCharArr;
    }

    /**
     * Checks if the current image and resolution are the same as the previous image and resolution.
     * @return true if the current image and resolution are the same as the
     * previous image and resolution, false otherwise.
     */
    private boolean isSameImgAndRes () {
        return image == previousImage && resolution == previousResolution;
    }
}
