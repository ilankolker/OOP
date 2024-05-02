
public class Game {
    /**
     * These are constants and attributes of the Game class.
     */
    private static final int DEFAULT_WINNING_STREAK = 3;
    private static final int DEFAULT_BOARD_SIZE = 4;
    private static final int MINIMAL_WIN_STREAK = 2;
    private static final int NUMBER_OF_PLAYERS = 2;
    private static final String RIGHT_DIAGONAL = "right";
    private static final String LEFT_DIAGONAL = "left";
    private final int winStreak;
    private final int board_size;
    private final Player playerX;
    private final Player playerO;
    private final Renderer renderer;

    /**
     * This is the default constructor of Game(size and win streak with default values).
     * @param playerX - the player who plays X
     * @param playerO - the player who plays O
     * @param renderer - the type of the renderer - console or none.
     */
    public Game(Player playerX, Player playerO, Renderer renderer) {
        this.winStreak = DEFAULT_WINNING_STREAK;
        this.board_size = DEFAULT_BOARD_SIZE;
        this.playerX = playerX;
        this.playerO = playerO;
        this.renderer = renderer;
    }

    /**
     * This is the constructor of game. It gets the players, the size of the board ,
     the win streak and generates a new game.
     * @param playerX - The player who plays X
     * @param playerO - The player who plays O
     * @param size - The size of the board
     * @param winStreak - The win streak of the game
     * @param renderer - The type of renderer
     */
    public Game(Player playerX, Player playerO, int size, int winStreak, Renderer renderer) {
        this.board_size = size;
        this.playerX = playerX;
        this.playerO = playerO;
        this.renderer = renderer;
        if(winStreak > size || winStreak < MINIMAL_WIN_STREAK) {
            this.winStreak = size;
        } else {
            this.winStreak = winStreak;
        }
    }

    /**
     * A getter function that returns the win streak.
     * @return the win streak.
     */
    public int getWinStreak() {

        return this.winStreak;
    }

    /**
     * A getter function that returns the size of the board.
     * @return the board size.
     */
    public int getBoardSize() {

        return this.board_size;
    }

    /**
     * This method checks if someone won a win streak in some row and returns the mark of the winner.
     * If the game is still played it returns the Blank mark.
     * @param board - the board of the game.
     * @return X, O or Blank
     */
    private Mark checkRow(Board board) {
        int counterX = 0;
        int counterO = 0;
        for (int row = 0; row < this.board_size; row++) {
            for (int col = 0; col < this.board_size; col++) {
                // The following conditions check if the mark on board is X or O
                if(board.getMark(row, col) == Mark.X) {
                    counterX++;
                    counterO = 0;  // the appearance of X breaks the O counter
                } else if(board.getMark(row, col) == Mark.O) {
                    counterO++;
                    counterX = 0;  // the appearance of O breaks the X counter
                } else {     // the case of blank mark
                    counterX = 0;   // the appearance of blank breaks the X counter
                    counterO = 0;   // the appearance of blank breaks the O counter
                }
                // The following conditions check whether some player got a win streak
                if(counterX == this.winStreak) {
                    return Mark.X;
                } else if(counterO == this.winStreak) {
                    return Mark.O;
                }
            }
            counterX = 0;
            counterO = 0;
        }
        return Mark.BLANK;   // it means nobody got a row streak
    }
    /**
     * This method checks if someone won a win streak in some column and returns the winner.
     * If the game is still played it returns the Blank mark.
     * @param board - the board of the game.
     * @return X, O or Blank
     */
    private Mark checkCol(Board board) {
        int counterX = 0;
        int counterO = 0;
        for (int col = 0; col < this.board_size; col++) {
            for (int row = 0; row < this.board_size; row++) {
                // The following conditions check if the mark on board is X or O
                if(board.getMark(row, col) == Mark.X) {
                    counterX++;
                    counterO = 0;  // the appearance of X breaks the O counter
                } else if(board.getMark(row, col) == Mark.O) {
                    counterO++;
                    counterX = 0;  // the appearance of O breaks the X counter
                } else {     // the case of blank mark
                    counterX = 0;   // the appearance of blank breaks the X counter
                    counterO = 0;   // the appearance of blank breaks the O counter
                }
                // The following conditions check whether some player got a win streak
                if(counterX == this.winStreak) {
                    return Mark.X;
                } else if(counterO == this.winStreak) {
                    return Mark.O;
                }
            }
            counterX = 0;
            counterO = 0;
        }
        return Mark.BLANK;   // it means nobody got a column streak
    }
    /**
     * This method checks if someone won a win streak from the diagonal of a specific cell.
     * It returns the mark of the winner or the blank mark in case there's no diagonal of size win streak
     from the specific cell given as an argument.
     * @param board - the board of the game.
     * @param row - the row of a specific cell
     * @param col - the column of a specific cell
     * @param diagonalType - right diagonal or left diagonal
     * @return X, O or Blank
     */
    private Mark checkDiagonalByCoordinate(Board board, int row, int col, String diagonalType) {
        int countX = 0, countO = 0;
        // Checks if row and column are in bounds
        while((row < this.board_size) && (row >= 0) && (col >= 0) && (col < this.board_size)) {
            if(board.getMark(row, col) == Mark.X) {
                countX++;
                countO = 0;   // initialize countO
            } else if(board.getMark(row, col) == Mark.O) {
                countO++;
                countX = 0;   // initialize countX
            } else {      // the case when Mark is blank
                countX = 0;     // X hasn't got a streak yet so the blank mark broke it
                countO = 0;     // O hasn't got a streak yet so the blank mark broke it
            }
            // The following conditions check if X or O got a win streak and won
            if(countX == this.winStreak) {
                return Mark.X;
            } else if(countO == this.winStreak) {
                return Mark.O;
            }
            if(diagonalType.equals(RIGHT_DIAGONAL)) {
                row++;
                col++;
            } else {
                row++;
                col--;
            }
        }
        return Mark.BLANK;
    }


    /**
     * This method checks if someone won a win streak in some diagonal line and returns the winner.
     * If the game is still played it returns the Blank mark.
     * @return X, O or Blank
     */
    private Mark checkDiagonal(Board board) {
        // The following for loop will check the frame of the board
        // It checks the right diagonals of the first column, the right and left diagonals of the first row,
        // and the left diagonals of the last column - covering all the diagonals.
        for (int i = 0; i < this.board_size; i++) {
            Mark firstColResult = checkDiagonalByCoordinate(board, i, 0, RIGHT_DIAGONAL);
            Mark firstRowResultRight = checkDiagonalByCoordinate(board, 0, i, RIGHT_DIAGONAL);
            Mark firstRowResultLeft = checkDiagonalByCoordinate(board, 0, i, LEFT_DIAGONAL);
            Mark lastColResult = checkDiagonalByCoordinate(board, i, board_size - 1, LEFT_DIAGONAL);
            if (firstColResult == Mark.X || firstColResult == Mark.O) {
                return firstColResult;
            } else if (firstRowResultRight == Mark.X || firstRowResultRight == Mark.O) {
                return firstRowResultRight;
            } else if (firstRowResultLeft == Mark.X || firstRowResultLeft == Mark.O) {
                return firstRowResultLeft;
            } else if (lastColResult == Mark.X || lastColResult == Mark.O) {
                return lastColResult;
            }
        }
        return Mark.BLANK;
    }

    /**
     * This method checks if someone wins and returns the winner.
     * If the game is still played it returns the Blank mark.
     * @return X, O or Blank
     */
    private Mark checkBoardResult(Board board) {

        Mark checkRow = checkRow(board);
        Mark checkCol = checkCol(board);
        Mark checkDiagonal = checkDiagonal(board);
        if (checkRow == Mark.X || checkRow == Mark.O) {
            return checkRow;
        } else if (checkCol == Mark.X || checkCol == Mark.O) {
            return checkCol;
        } else if(checkDiagonal == Mark.X || checkDiagonal == Mark.O) {
            return checkDiagonal;
        }

        return Mark.BLANK;  // Meaning no winner was found

    }

    /**
     * This is the run method that responsible for running a game.
     * @return the mark of the winner or tie in case of a tie.
     */

    public Mark run() {
        Board board = new Board(this.board_size);
        Mark[] marks = new Mark[NUMBER_OF_PLAYERS];
        Player[] players = new Player[NUMBER_OF_PLAYERS];
        players[0] = this.playerX;
        players[1] = this.playerO;
        marks[0] = Mark.X;
        marks[1] = Mark.O;
        for (int i = 0; i < this.board_size * this.board_size; i++) {
            players[i % NUMBER_OF_PLAYERS].playTurn(board, marks[i % NUMBER_OF_PLAYERS]);  // plays a turn
            this.renderer.renderBoard(board);   // renders the board
            Mark winner = checkBoardResult(board);
            if(!(winner == Mark.BLANK))   // checks if there's a winner
            {
                return winner;
            }
        }
        return Mark.BLANK;
    }

}
