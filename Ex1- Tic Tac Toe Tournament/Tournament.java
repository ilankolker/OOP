

public class Tournament {
    private static final String HUMAN_PLAYER = "human";
    private static final String WHATEVER_PLAYER = "whatever";
    private static final String CLEVER_PLAYER = "clever";
    private static final String GENIUS_PLAYER = "genius";
    private static final String CONSOLE_RENDERER = "console";
    private static final String NONE_RENDERER = "none";

    private static final int ROUNDS_NUMBER_IND = 0;
    private static final int BOARD_SIZE_IND = 1;
    private static final int WIN_STREAK_IND = 2;
    private static final int RENDERER_NAME_IND = 3;
    private static final int FIRST_PLAYER_IND = 4;
    private static final int SECOND_PLAYER_IND = 5;
    private static final int TWO_PLAYERS = 2;
    private final int rounds;
    private final Renderer renderer;
    private final Player firstPlayer;
    private final Player secondPlayer;

    /**
     * The constructor of the Tournament class.
     * @param rounds - A positive number of the rounds to play.
     * @param renderer - The type of renderer to be used: [none, console].
     * @param player1 - The first Player.
     * @param player2 - The second Player.
     */
    public Tournament(int rounds, Renderer renderer, Player player1, Player player2) {
        this.rounds = rounds;
        this.renderer = renderer;
        this.firstPlayer = player1;
        this.secondPlayer = player2;
    }

    /**
     * This static function checks if the players' names are legal, which means part of:
     [human, clever, whatever, genius].
     * @param firstPlayerName - A string of the first player's name.
     * @param secondPlayerName - A string of the second player's name.
     * @return true if both are legal, false otherwise.
     */
    private static boolean checkPlayerNames(String firstPlayerName, String secondPlayerName) {
        String[] legalNames = new String[]{HUMAN_PLAYER, CLEVER_PLAYER, WHATEVER_PLAYER, GENIUS_PLAYER};
        boolean isFirstLegal = false, isSecondLegal = false;
        // go through the legal players' names array and check if the given names appears.

        for(String name: legalNames) {
            if (name.equals(firstPlayerName.toLowerCase())) {
                isFirstLegal = true;
                break;
            }
        }
        if (!isFirstLegal) {
            return false;
        }
        for(String name: legalNames) {
            if (name.equals(secondPlayerName.toLowerCase())) {
                isSecondLegal = true;
                break;
            }
        }
        return isSecondLegal;
    }

    /**
     * This function is responsible for making the tournament and prints the results of it.
     * @param size - the size of the board.
     * @param winStreak - the win streak .
     * @param playerName1 - The name of the first player.
     * @param playerName2 - the name of the second player.
     */

    public void playTournament(int size, int winStreak, String playerName1, String playerName2) {
        int[] resultsWinCounters = new int[TWO_PLAYERS];  // counts the players' victories
        int tieCounter = 0;
        Player[] players = new Player[TWO_PLAYERS];
        players[0] = this.firstPlayer;
        players[1] = this.secondPlayer;
        for (int i = 0; i < this.rounds; i++) {
            // generates a new game with two players who play X and O alternately
            Game game = new Game(players[i % TWO_PLAYERS], players[(i + 1) % TWO_PLAYERS]
                    , size, winStreak, this.renderer);
            // launch a new game
            Mark gameResult = game.run();
            // checks who won and adds the victories counter array or tie counter in case of a tie
            if(gameResult.equals(Mark.X)) {
                (resultsWinCounters[i % TWO_PLAYERS])++;
            } else if(gameResult.equals(Mark.O)) {
                    (resultsWinCounters[(i + 1) % TWO_PLAYERS])++;
            } else {
                    tieCounter++;
                }
        }
        // prints the results of the tournament
        System.out.println("######### Results #########");
        System.out.println("Player 1, " + playerName1 + " won: " + resultsWinCounters[0] + " rounds");
        System.out.println("Player 2, " + playerName2 + " won: " + resultsWinCounters[1] + " rounds");
        System.out.println("Ties: " + tieCounter);
    }

    /**
     * This is the main method. Its purpose is to call playerFactory and rendererFactory that
     build the players and renderer, and then it makes a tournament.
     In case it gets non-valid names of players or non-valid renderer, it alerts with an informative
     error message and stops the program.
     * @param args - the arguments of the main method
     */

    public static void main(String[] args) {
        int rounds = Integer.parseInt(args[ROUNDS_NUMBER_IND]);
        int boardSize = Integer.parseInt(args[BOARD_SIZE_IND]);
        int winStreak = Integer.parseInt(args[WIN_STREAK_IND]);
        String rendererType = args[RENDERER_NAME_IND];
        String firstPlayerName = args[FIRST_PLAYER_IND];
        String secondPlayerName = args[SECOND_PLAYER_IND];
        Player[] players = new Player[TWO_PLAYERS];
        // checks if renderer type is legal
        if(!rendererType.equalsIgnoreCase(CONSOLE_RENDERER) &&
                !rendererType.equalsIgnoreCase(NONE_RENDERER)) {
            System.out.println(Constants.UNKNOWN_RENDERER_NAME);
            return;
        }
        boolean areLegalNames = checkPlayerNames(firstPlayerName, secondPlayerName);
        if(!areLegalNames) {  // end of the program without a tournament in case names are illegal
            System.out.println(Constants.UNKNOWN_PLAYER_NAME);
            return;
        }
        RendererFactory rendererFactory = new RendererFactory();
        Renderer renderer = rendererFactory.buildRenderer(rendererType.toLowerCase(), boardSize);
        players[0] = new PlayerFactory().buildPlayer(firstPlayerName.toLowerCase());
        players[1] = new PlayerFactory().buildPlayer(secondPlayerName.toLowerCase());
        Tournament tournament = new Tournament(rounds, renderer, players[0], players[1]);
        tournament.playTournament(boardSize, winStreak, firstPlayerName.toLowerCase()
                , secondPlayerName.toLowerCase());
    }
}
