package image;

import ascii_art.Constants;

import java.awt.*;

/**
 * Provides methods for manipulating images.
 */
public class ImageManipulator {
    /**
     * Default constructor for ImageManipulator class.
     */
    public ImageManipulator(){}

    /**
     * Finds the closest power of two to a given number.
     * @param num The input number.
     * @return The closest power of two to the input number.
     */
    public static int findClosestPowerOfTwo(int num) {
        if (num <= 0) {
            return 1;
        }
        int power = 1;
        while (power < num) {
            power *= 2;
        }
        return power;
    }

    /**
     * Pads an image to the closest power of two dimensions.
     * @param image The input image.
     * @return The padded image as a 2D Color array.
     */
    public static Color[][] padImage(Image image) {
        int newHeight = findClosestPowerOfTwo(image.getHeight());
        int newWidth = findClosestPowerOfTwo(image.getWidth());
        int heightPadding = newHeight - image.getHeight();
        int widthPadding = newWidth - image.getWidth();
        Color[][] paddedImg = new Color[newHeight][newWidth];
        // initialize paddedImg with white pixels
        for (int i = 0; i < newHeight; i++) {
            for (int j = 0; j < newWidth; j++) {
                paddedImg[i][j] = Color.WHITE;
            }
        }
        // copy the previous image to the current one
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                paddedImg[i + (heightPadding / 2)][j + (widthPadding / 2)] = image.getPixel(i,j);
            }
        }
        return paddedImg;
    }
    /**
     * Divides an image into sub-images based on a specified resolution.
     * @param colorArr The input image as a 2D Color array.
     * @param resolution The resolution for dividing the image.
     * @return The divided image as a 4D Color array.
     */
    public static Color[][][][] divideImageToSubImages(Color[][] colorArr, int resolution) {
        int imgHeight = colorArr.length;
        int imgWidth = colorArr[0].length;
        int subImageSize = imgWidth / resolution;
        int maxSubImgColIndex = resolution;
        int maxSubImgRowIndex = imgHeight / subImageSize;

        Color[][][][] res = new Color[maxSubImgColIndex][maxSubImgRowIndex][subImageSize][subImageSize];

        for (int subImgRowIndex = 0; subImgRowIndex < maxSubImgRowIndex; subImgRowIndex++) {
            for (int subImgColIndex = 0; subImgColIndex < maxSubImgColIndex; subImgColIndex++) {
                // create subImg
                res[subImgRowIndex][subImgColIndex] =
                        fillSubImg(colorArr, subImageSize, subImgRowIndex, subImgColIndex);
            }
        }
        return res;
    }

    /**
     * Fills a sub-image with pixels from a larger image.
     * @param colorArr The input image as a 2D Color array.
     * @param subImageSize The size of the sub-image.
     * @param subImgRowIndex The row index of the sub-image.
     * @param subImgColIndex The column index of the sub-image.
     * @return The filled sub-image as a 2D Color array.
     */
    private static Color[][] fillSubImg(Color[][] colorArr, int subImageSize,
                                        int subImgRowIndex, int subImgColIndex) {
        Color[][] subImg = new Color[subImageSize][subImageSize];
        for (int rowIndex = 0; rowIndex < subImageSize; rowIndex++) {
            for (int colIndex = 0; colIndex < subImageSize; colIndex++) {
                subImg[rowIndex][colIndex] =
                        colorArr[rowIndex + subImgRowIndex * subImageSize]
                                [colIndex + subImgColIndex * subImageSize];

            }
        }
        return subImg;
    }

    /**
     * Calculates the brightness of an image.
     * @param colorArr The input image as a 2D Color array.
     * @return The brightness value of the image.
     */
    public static double calcImgBrightness(Color[][] colorArr) {
        int imgHeight = colorArr.length;
        int imgWidth = colorArr[0].length;
        double greyColorCounter = 0;
        for (int i = 0; i < imgHeight; i++) {
            for (int j = 0; j < imgWidth; j++) {
                greyColorCounter += colorArr[i][j].getRed() * Constants.RED_FACTOR +
                        colorArr[i][j].getGreen() * Constants.GREEN_FACTOR +
                        colorArr[i][j].getBlue() * Constants.BLUE_FACTOR;
            }
        }
        return greyColorCounter / (Constants.MAX_RGB_RATE * imgHeight * imgWidth);
    }
}
