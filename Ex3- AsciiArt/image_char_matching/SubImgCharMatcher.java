package image_char_matching;
import java.util.*;

/**
 * Represents a class for matching characters to image brightness.
 */
public class SubImgCharMatcher {
    private static final double MIN_BRIGHTNESS = 0;
    private static final double MAX_BRIGHTNESS = 1;
    private final HashSet<Character> charSet;
    private final HashMap<Character, Double> charBrightnessMap;
    private TreeMap<Double, PriorityQueue<Character>> brightnessCharHeapTree;
    private double maxAbsoluteBrightness = MIN_BRIGHTNESS;
    private double minAbsoluteBrightness = MAX_BRIGHTNESS;

    /**
     * Constructs a new SubImgCharMatcher instance with the given character set.
     * @param charset The character set to use for matching.
     */
    public SubImgCharMatcher(char[] charset) {
        this.charSet = new HashSet<>();
        this.charBrightnessMap = new HashMap<>();
        this.brightnessCharHeapTree = new TreeMap<>();

        double charBrightness;

        for (char character : charset) {
            charBrightness = calcAbsoluteCharBrightness(character);
            charSet.add(character);
            charBrightnessMap.put(character, charBrightness);
            maxAbsoluteBrightness = Math.max(charBrightness, maxAbsoluteBrightness);
            minAbsoluteBrightness = Math.min(charBrightness, minAbsoluteBrightness);
        }
        initializeBrightnessCharHeapTree();
    }

    /**
     * Calculates the absolute brightness of a character.
     * @param character The character for which to calculate the brightness.
     * @return The absolute brightness of the character.
     */
    private static double calcAbsoluteCharBrightness(char character) {
        int numOfPixels = (int) Math.pow(CharConverter.DEFAULT_PIXEL_RESOLUTION, 2);
        double whitePixelCounter = 0;
        boolean[][] charBoolArray = CharConverter.convertToBoolArray(character);
        for (int i = 0; i < CharConverter.DEFAULT_PIXEL_RESOLUTION; i++) {
            for (int j = 0; j < CharConverter.DEFAULT_PIXEL_RESOLUTION; j++) {
                whitePixelCounter += charBoolArray[i][j] ? 1 : 0;
            }
        }
        double charBrightness = whitePixelCounter / numOfPixels;
        return charBrightness;
    }

    /**
     * Converts an array of characters to a HashSet.
     * @param charset The character array to convert.
     * @return A HashSet containing the characters from the array.
     */
    private static HashSet<Character> arrayToHashSet(char[] charset) {
        HashSet<Character> charHashSet = new HashSet<>();
        for (char character : charset) {
            charHashSet.add(character);
        }
        return charHashSet;
    }

    /**
     * Calculates the normalized brightness of a character based on the minimum and maximum
     * brightness values.
     * @param absoluteBrightness The absolute brightness of the character.
     * @return The normalized brightness value.
     */
    private double calcNormalizedBrightness(double absoluteBrightness) {
        return (absoluteBrightness - minAbsoluteBrightness) /
                (maxAbsoluteBrightness - minAbsoluteBrightness);
    }

    /**
     * Initializes the brightness-characters heap tree based on the character brightness map.
     */
    private void initializeBrightnessCharHeapTree() {
        for (Map.Entry<Character, Double> entry : charBrightnessMap.entrySet()) {
            double newCharBrightness = calcNormalizedBrightness(entry.getValue());
            if (!brightnessCharHeapTree.containsKey(newCharBrightness)) {
                addEntryToTree(entry.getKey(), newCharBrightness);
            }
            else {
                PriorityQueue<Character> minHeapOfKey = brightnessCharHeapTree.get(newCharBrightness);
                minHeapOfKey.add(entry.getKey());
            }
        }
    }

    /**
     * Adds a new entry to the brightness-characters heap tree with the specified character
     * and its corresponding brightness value.
     * @param c                 The character to be added.
     * @param newCharBrightness The brightness value of the character.
     */
    private void addEntryToTree(char c, double newCharBrightness) {
        PriorityQueue<Character> newMinHeap = new PriorityQueue<>();
        newMinHeap.add(c);
        brightnessCharHeapTree.put(newCharBrightness, newMinHeap);
    }

    /**
     * Retrieves the character associated with the specified image brightness.
     * If the brightness is outside the range of stored brightness values,
     * it returns the character with the closest brightness.
     * @param brightness The image brightness value.
     * @return The character associated with the specified image brightness.
     */
    public char getCharByImageBrightness(double brightness) {
        double maxRelativeBrightness = brightnessCharHeapTree.lastKey();
        double minRelativeBrightness = brightnessCharHeapTree.firstKey();

        if (brightness >= maxRelativeBrightness) {
            return brightnessCharHeapTree.get(maxRelativeBrightness).peek();
        } else if (brightness <= minRelativeBrightness) {
            return brightnessCharHeapTree.get(minRelativeBrightness).peek();
        }
        double closestBrightnessFromAbove = brightnessCharHeapTree.ceilingKey(brightness);
        double smallestDeltaFromAbove = closestBrightnessFromAbove - brightness;
        double closestBrightnessFromBelow = brightnessCharHeapTree.floorKey(brightness);
        double smallestDeltaFromBelow = brightness - closestBrightnessFromBelow;

        if (smallestDeltaFromAbove < smallestDeltaFromBelow) {
            return brightnessCharHeapTree.get(closestBrightnessFromAbove).peek();
        }
        return brightnessCharHeapTree.get(closestBrightnessFromBelow).peek();
    }

    /**
     * Adds a character to the character set and updates the brightness tree.
     * If the character is already in the character set, it does nothing.
     * @param c The character to add.
     */
    public void addChar(char c) {
        double charAbsoluteBrightness = 0;
        // char is in isCharUsedMap and it is in brightnessCharHeapTree
        if (charSet.contains(c)) {
            return;
        // char is in isCharUsedMap and it's not in brightnessCharHeapTree
        }
        if (charBrightnessMap.containsKey(c)) {
            charAbsoluteBrightness = charBrightnessMap.get(c);
        // char is not in isCharUsedMap
        }
        else {
            charAbsoluteBrightness = calcAbsoluteCharBrightness(c);
            charBrightnessMap.put(c, charAbsoluteBrightness);
        }
        charSet.add(c);
        addToTree(charAbsoluteBrightness, c);
    }

    /**
     * Adds a character to the brightness tree. If the character's brightness is outside the
     * current range, the tree is normalized.
     * @param absoluteBrightness The absolute brightness of the character.
     * @param c                  The character to add.
     */
    private void addToTree(double absoluteBrightness, char c) {
        if (minAbsoluteBrightness <= absoluteBrightness && absoluteBrightness
                <= maxAbsoluteBrightness) {
            double normalizedBrightness = calcNormalizedBrightness(absoluteBrightness);
            if (brightnessCharHeapTree.containsKey(normalizedBrightness)) {
                PriorityQueue<Character> priorityQueueOfChar =
                        brightnessCharHeapTree.get(normalizedBrightness);
                priorityQueueOfChar.add(c);
            } else {
                addEntryToTree(c, normalizedBrightness);
            }
        } else if (absoluteBrightness > maxAbsoluteBrightness) {
            maxAbsoluteBrightness = absoluteBrightness;
            normalizeTree();
            addEntryToTree(c, MAX_BRIGHTNESS);
        } else if (absoluteBrightness < minAbsoluteBrightness) {
            minAbsoluteBrightness = absoluteBrightness;
            normalizeTree();
            addEntryToTree(c, MIN_BRIGHTNESS);
        }
    }

    /**
     * Normalizes the brightness tree by recalculating the normalized brightness values
     * for each character and reconstructing the tree.
     */
    private void normalizeTree() {
        TreeMap<Double, PriorityQueue<Character>> newTreeMap = new TreeMap<>();
        for (Map.Entry<Double, PriorityQueue<Character>> entry : brightnessCharHeapTree.entrySet()) {
            char characterInQueue = entry.getValue().peek();
            double absoluteBrightnessOfEntry = charBrightnessMap.get(characterInQueue);
            double newNormalizedBrightness = calcNormalizedBrightness(absoluteBrightnessOfEntry);
            PriorityQueue<Character> priorityQueue = entry.getValue();
            newTreeMap.put(newNormalizedBrightness, priorityQueue);
        }
        this.brightnessCharHeapTree = newTreeMap;
    }

    /**
     * Removes a character from the character set and the brightness tree.
     * @param c The character to be removed.
     */
    public void removeChar(char c) {
        if (!charSet.contains(c)) {
            return;
        }
        removeFromTree(c);
        charSet.remove(c);
    }

    /**
     * Removes a character from the brightness tree and updates the min and max brightness values if needed.
     * @param c The character to be removed.
     */
    private void removeFromTree(char c) {
        double charBrightness = charBrightnessMap.get(c);
        double normalizedCharBrightness = calcNormalizedBrightness(charBrightness);
        // remove char from minHeap in the Tree entry
        brightnessCharHeapTree.get(normalizedCharBrightness).remove(c);
        // check if the char was the only one in its entry minHeap
        if (brightnessCharHeapTree.get(normalizedCharBrightness).isEmpty()) {
            brightnessCharHeapTree.remove(normalizedCharBrightness);
            if (brightnessCharHeapTree.isEmpty()) {
                maxAbsoluteBrightness = MIN_BRIGHTNESS;
                minAbsoluteBrightness = MAX_BRIGHTNESS;
                return;
            }
            if (charBrightness == minAbsoluteBrightness) {
                char charWithMinBrightness = brightnessCharHeapTree.firstEntry().getValue().peek();
                minAbsoluteBrightness = charBrightnessMap.get(charWithMinBrightness);
                normalizeTree();
            }
            if (charBrightness == maxAbsoluteBrightness) {
                char charWithMaxBrightness = brightnessCharHeapTree.lastEntry().getValue().peek();
                maxAbsoluteBrightness = charBrightnessMap.get(charWithMaxBrightness);
                normalizeTree();
            }
        }
    }
}
