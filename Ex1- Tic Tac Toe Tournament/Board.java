public class Board {
    private final int board_size;
    private static final int DEFAULT_BOARD_SIZE = 4;
    private final Mark[][] board;

    /**
     * A default constructor of Board. It initializes a new board with default size.
     */
    public Board() {
        this.board = new Mark[DEFAULT_BOARD_SIZE][DEFAULT_BOARD_SIZE];
        this.board_size = DEFAULT_BOARD_SIZE;
        initializeBoard();
    }

    /**
     * This function fills the board with Blank marks.
     */
    private void initializeBoard(){
        for (int i = 0; i < this.board_size; i++) {
            for (int j = 0; j < this.board_size; j++) {
                this.board[i][j] = Mark.BLANK;
            }
        }
    }

    /**
     * A constructor that gets the size of the board and initializes a new board of size x size.
     * @param size - the size of the board.
     */
    public Board(int size) {
        this.board = new Mark[size][size];
        this.board_size = size;
        initializeBoard();
    }

    /**
     * This function returns the size of the board.
     * @return the size of the board.
     */
    public int getSize() {
        return this.board_size;
    }

    /**
     * This function gets mark, row, column and tries to put the mark into the specified
      row and column. If the cell is blank - meaning it successfully put the mark, it returns true,
     otherwise - returns false.
     * @param mark - X or O
     * @param row - a number between 0 and board_size - 1
     * @param col - a number between 0 and board_size - 1
     * @return true in success, false otherwise
     */
    public boolean putMark(Mark mark, int row, int col) {
        if((0 <= row && row < this.board_size) && (0 <= col && col < this.board_size)) {
            if (this.board[row][col] == Mark.BLANK) {
                this.board[row][col] = mark;
                return true;
            }
        }
        return false;
    }

    /**
     * This function gets a specified row and column and returns the mark of the corresponding cell.
     * @param row - a row in the board
     * @param col - a column in the board
     * @return X, O or Blank of the corresponding cell.
     */
    public Mark getMark(int row, int col) {
        if((0 <= row && row < this.board_size) && (0 <= col && col < this.board_size)) {
            return this.board[row][col];
        }
        return Mark.BLANK;
    }

}
