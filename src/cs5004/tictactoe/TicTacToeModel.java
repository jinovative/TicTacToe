package cs5004.tictactoe;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TicTacToeModel implements TicTacToe {
  // add your implementation here
  private final Player[][] board;
  private Player turn;
  private boolean gameOver;
  private Player winner;

  public TicTacToeModel() {
    board = new Player[3][3];
    turn = Player.X;
    gameOver = false;
    winner = null;
  }

  private void switchTurn() {
    turn = (turn == Player.X) ? Player.O : Player.X;
  }

  private void checkGameOver() {
    // Check rows
    for (int i = 0; i < board.length; i++) {
      if (board[i][0] != null && board[i][0] == board[i][1] && board[i][0] == board[i][2]) {
        gameOver = true;
        winner = board[i][0];
        return;
      }
    }

    // Check columns
    for (int j = 0; j < board[0].length; j++) {
      if (board[0][j] != null && board[0][j] == board[1][j] && board[0][j] == board[2][j]) {
        gameOver = true;
        winner = board[0][j];
        return;
      }
    }

    // Check diagonals
    if (board[0][0] != null && board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
      gameOver = true;
      winner = board[0][0];
      return;
    }
    if (board[0][2] != null && board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
      gameOver = true;
      winner = board[0][2];
      return;
    }

    // Check if the board is full
    boolean isFull = true;
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        if (board[i][j] == null) {
          isFull = false;
          break;
        }
      }
      if (!isFull) {
        break;
      }
    }

    if (isFull) {
      gameOver = true;
      winner = null;
    }
  }

  @Override
  public String toString() {
    // Using Java stream API to save code:
    return Arrays.stream(getBoard()).map(
      row -> " " + Arrays.stream(row).map(
        p -> p == null ? " " : p.toString()).collect(Collectors.joining(" | ")))
          .collect(Collectors.joining("\n-----------\n"));
    // This is the equivalent code as above, but using iteration, and still using 
    // the helpful built-in String.join method.

    /**********
    List<String> rows = new ArrayList<>();
    for(Player[] row : getBoard()) {
      List<String> rowStrings = new ArrayList<>();
      for(Player p : row) {
        if(p == null) {
          rowStrings.add(" ");
        } else {
          rowStrings.add(p.toString());
        }
      }
      rows.add(" " + String.join(" | ", rowStrings));
    }
    return String.join("\n-----------\n", rows);
    ************/
  }

  /**
   * Execute a move in the position specified by the given row and column.
   *
   * @param r the row of the intended move
   * @param c the column of the intended move
   * @throws IllegalArgumentException if the space is occupied or the position is otherwise invalid
   * @throws IllegalStateException    if the game is over
   */
  @Override
  public void move(int r, int c) {
    if (gameOver) {
      throw new IllegalStateException("The game is over.");
    }
    if (r < 0 || r >= board.length || c < 0 || c >= board[r].length) {
      throw new IllegalArgumentException("Invalid position.");
    }
    if (board[r][c] != null) {
      throw new IllegalArgumentException("The position is already occupied.");
    }

    board[r][c] = turn;
    checkGameOver();
    switchTurn();
  }

  /**
   * Get the current turn, i.e., the player who will mark on the next call to move().
   *
   * @return the {@link Player} whose turn it is
   */
  @Override
  public Player getTurn() {
    return turn;
  }

  /**
   * Return whether the game is over. The game is over when either the board is full, or
   * one player has won.
   *
   * @return true if the game is over, false otherwise
   */
  @Override
  public boolean isGameOver() {
    return gameOver;
  }

  /**
   * Return the winner of the game, or {@code null} if there is no winner. If the game is not
   * over, returns {@code null}.
   *
   * @return the winner, or null if there is no winner
   */
  @Override
  public Player getWinner() {
    return winner;
  }

  /**
   * Return the current game state, as a 2D array of Player. A {@code null} value in the grid
   * indicates an empty position on the board.
   *
   * @return the current game board
   */
  public Player[][] getBoard() {
    Player[][] board = new Player[3][3]; // Assuming a 3x3 game board

    // Fill the board with the current state of each position
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        board[row][col] = getMarkAt(row, col);
      }
    }

    return board;
  }

  /**
   * Return the current {@link Player} mark at a given row and column, or {@code null} if the
   * position is empty.
   *
   * @param r the row
   * @param c the column
   * @return the player at the given position, or null if it's empty
   */
  @Override
  public Player getMarkAt(int r, int c) {
    if (r < 0 || r >= board.length || c < 0 || c >= board[r].length) {
      throw new IllegalArgumentException("Invalid position.");
    }
    return board[r][c];
  }
}
