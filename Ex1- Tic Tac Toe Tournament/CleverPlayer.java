import java.util.Random;

public class CleverPlayer implements Player{
    /**
     * A default constructor for the CleverPlayer class.
     */
    public CleverPlayer() {

    }

    /**
     * This function gets a board and a mark and plays a CleverPlayer turn.
     * The CleverPlayer's strategy is to fill rows in order, starting from the row 0.
     * When a cell is taken it tries to put the mark on the next cell to its right.
     * @param board - the board
     * @param mark - the mark to be put on the board
     */
    public void playTurn(Board board, Mark mark) {
        boolean playedTurn = false;
        int cleverRow = 0, cleverCol = 0;
        // constantly try playing a turn as long as the position is taken.
        while(!playedTurn) {
            playedTurn = board.putMark(mark, cleverRow, cleverCol);
            cleverCol++;
            if (cleverCol == board.getSize()) {
                cleverRow++;
                cleverCol = 0;
            }
        }
    }
}
