public class GeniusPlayer implements Player{
    /**
     * The default constructor of the GeniusPlayer class.
     */
    public GeniusPlayer() {

    }

    /**
     * This function gets a board and a mark and plays a GeniusPlayer turn.
     * The GeniusPlayer strategy is to start from the first cell of the second column (column 1), and
      it tries to fill in a column by column if possible.
     * @param board - the board
     * @param mark - the mark to be put on the board
     */
    public void playTurn(Board board, Mark mark) {
        boolean playedTurn = false;
        int cleverRow = 0, cleverCol = 1;  // starting filling the first column
        while(!playedTurn) {
            // constantly try playing a turn as long as the position is taken.
            playedTurn = board.putMark(mark, cleverRow, cleverCol);
            cleverRow++;
            if (cleverRow == board.getSize()) {
                cleverCol++;
                cleverRow = 0;
            }
            if(cleverCol == board.getSize()) {
                cleverCol = 0;
            }
        }
    }
}
