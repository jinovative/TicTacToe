package cs5004.tictactoe;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements the TicTacToeController interface.
 */
public class TicTacToeConsoleController implements TicTacToeController {
  private final Readable in;
  private final Appendable out;

  /**
   * Constructs a TicTacToeConsoleController.
   *
   * @param in  the Readable object
   * @param out the Appendable object
   */
  public TicTacToeConsoleController(Readable in, Appendable out) {
    this.in = in;
    this.out = out;
  }

  @Override
  public void playGame(TicTacToe model) {
    Scanner sc = new Scanner(in);

    try {
      out.append(model.toString()).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Append failed.", e);
    }

    while (!model.isGameOver()) {
      try {
        out.append("Enter a move for ").append(model.getTurn().toString()).append(":\n");
      } catch (IOException e) {
        throw new IllegalStateException("Append failed.", e);
      }

      if (!sc.hasNext()) {
        return;
      }
      String token = sc.next().trim();

      if (token.equalsIgnoreCase("q")) {
        try {
          out.append("Game quit! Ending game state:\n").append(model.toString()).append("\n");
        } catch (IOException e) {
          throw new IllegalStateException("Append failed.", e);
        }
        return;
      }

      try {
        int row = Integer.parseInt(token) - 1;
        if (!sc.hasNext()) {
          continue;
        }
        int col = Integer.parseInt(sc.next()) - 1;
        model.move(row, col);
        try {
          out.append(model.toString()).append("\n");
        } catch (IOException e) {
          throw new IllegalStateException("Append failed.", e);
        }
      } catch (NumberFormatException e) {
        if (sc.hasNext()) { // only call next() if there is more input
          sc.next(); // consume the next input, which is expected to be the column number
        }
        continue;
      } catch (IllegalArgumentException e) {
        try {
          out.append("Invalid move. Try again.\n");
        } catch (IOException ex) {
          throw new IllegalStateException("Append failed.", ex);
        }
        continue;
      }
    }

    try {
      out.append("Game is over! ");
      if (model.getWinner() != null) {
        out.append(model.getWinner().toString() + " wins.");
      } else {
        out.append("Tie game.");
      }
    } catch (IOException e) {
      throw new IllegalStateException("Append failed.", e);
    }
  }
}
