public class RendererFactory {
    /**
     * Constants that will be used for the switch case.
     */
    private static final String CONSOLE_RENDERER = "console";
    private static final String NONE_RENDERER = "none";
    /**
     * Empty constructor
     */
    public RendererFactory(){

    }

    /**
     * This method generated the Renderer and returns it
     * @param type It gets the type of the renderer to generate
     * @param size It gets the size of the board to generate
     * @return It returns the Renderer that was generated
     */
    public Renderer buildRenderer(String type, int size) {
        if (type.equals(CONSOLE_RENDERER)) {
            return new ConsoleRenderer(size);
        }
        else if(type.equals(NONE_RENDERER)) {
            return new VoidRenderer();
        }
        else {
            return null;
        }
    }

}
