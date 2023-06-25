package cs5004.tictactoe;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * This class represents a single game of Tic Tac Toe.
 */
public class TicTacToeModel implements TicTacToe {
  // add your implementation here
  // fill in the fields and remaining method definitions
  private final Player[][] board;
  private Player turn;
  private boolean gameOver;
  private Player winner;

  /**
   * Construct of TicTacToeModel.
   */
  //a single public constructor that takes no arguments
  public TicTacToeModel() {
    board = new Player[3][3];
    turn = Player.X;
    gameOver = false;
    winner = null;
  }

  @Override
  public String toString() {
    // Using Java stream API to save code:
    return Arrays.stream(getBoard()).map(
      row -> " " + Arrays.stream(row).map(
        p -> p == null ? " " : p.toString()).collect(Collectors.joining(" | ")))
          .collect(Collectors.joining("\n-----------\n"));
  }

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

  @Override
  public Player getTurn() {
    return turn;
  }

  @Override
  public boolean isGameOver() {
    return gameOver;
  }

  @Override
  public Player getWinner() {
    return winner;
  }

  @Override
  public Player[][] getBoard() {
    // Assuming a 3x3 game board
    Player[][] board = new Player[3][3];

    // Fill the board with the current state of each position
    for (int row = 0; row < board.length; row++) {
      for (int col = 0; col < board.length; col++) {
        board[row][col] = getMarkAt(row, col);
      }
    }

    return board;
  }

  @Override
  public Player getMarkAt(int r, int c) {
    if (r < 0 || r >= board.length || c < 0 || c >= board[r].length) {
      throw new IllegalArgumentException("Invalid position.");
    }
    return board[r][c];
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
}
