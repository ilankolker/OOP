import java.util.Random;
/**
 * class WhateverPlayer represents the player that his every move is randomized.
 */
public class WhateverPlayer implements Player {
    /**
     * An empty constructor for WhateverPlayer
     */
    public WhateverPlayer() {
    }

    /**
     * The playTurn function chooses a row and a column between [0,size - 1] randomly
     and puts the mark of the chosen cell on the bard.
     * @param board - the board
     * @param mark - the mark to put on the board
     */
    public void playTurn(Board board, Mark mark) {
        boolean playedTurn = false;
        while(!playedTurn) {
            Random rand = new Random();
            int randomRow = rand.nextInt(board.getSize());
            int randomCol = rand.nextInt(board.getSize());
            playedTurn = board.putMark(mark, randomRow, randomCol);
        }
    }
}
