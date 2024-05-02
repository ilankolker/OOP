/**
 * The interface Player.
 */
public interface Player {
    /**
     * The playTurn method gets a board and a mark and plays a turn, meaning -
     puts the mark in some cell on the board.
     * @param board - the board
     * @param mark - the mark to put on the board
     */
    void playTurn(Board board, Mark mark);
}
