public class PlayerFactory {

    /**
     * Constants that will be used for the switch case.
     */
    private static final String HUMAN_PLAYER = "human";
    private static final String WHATEVER_PLAYER = "whatever";
    private static final String CLEVER_PLAYER = "clever";
    private static final String GENIUS_PLAYER = "genius";

    /**
     * Empty Constructor
     */
    public PlayerFactory() {

    }

    /**
     * This method gets the type of player and generates it.
     * In case the type is not one of the known types - it returns null.
     * @param type - the type of the player.
     * @return the Player type that was generated or null.
     */
    public Player buildPlayer(String type) {
        Player player;
        switch (type.toLowerCase()) {
            case HUMAN_PLAYER:
                player = new HumanPlayer();
                break;
            case WHATEVER_PLAYER:
                player = new WhateverPlayer();
                break;
            case CLEVER_PLAYER:
                player = new CleverPlayer();
                break;
            case GENIUS_PLAYER:
                player = new GeniusPlayer();
                break;
            default:
                return null;
        }
        return player;
    }
}
