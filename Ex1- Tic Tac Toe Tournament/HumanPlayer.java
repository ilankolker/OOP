
public class HumanPlayer implements Player {
    /**
     * The HumanPlayer constants
     */
    private static final int DIVIDER = 10;
    public HumanPlayer() {
    }

    /**
     * This function gets the board and a mark and asks the user for a coordinate
     in order to put the mark to the user's choice coordinate in the board.
     * @param board - the board
     * @param mark - X or O
     */
    public void playTurn(Board board, Mark mark) {
        switch (mark) {
            case X: {
                System.out.println(Constants.playerRequestInputString("X"));
                break;
            }
            case O: {
                System.out.println(Constants.playerRequestInputString("O"));
                break;
            }
        }
        int user_input = KeyboardInput.readInt();
        int row = user_input / DIVIDER;  // Get the row after casting to int
        int col = user_input % DIVIDER;  // Get the column after casting to int
        while(!board.putMark(mark, row, col)) {
            // Checks if user's position is not in boundaries.
            if(!((0 <= row && row < board.getSize()) && (0 <= col && col < board.getSize()))) {
                System.out.println(Constants.INVALID_COORDINATE);
            }
            // user's position is in boundaries but the location is already occupied.
            else {
                System.out.println(Constants.OCCUPIED_COORDINATE);
            }
            user_input = KeyboardInput.readInt();
            row = user_input / DIVIDER;
            col = user_input % DIVIDER;
        }
    }
}
