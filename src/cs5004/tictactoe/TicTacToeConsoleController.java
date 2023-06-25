package cs5004.tictactoe;

import java.io.IOException;
import java.util.Scanner;

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
    String line;

    while (!model.isGameOver()) {
      try {
        out.append(model.toString()).append("\n");
        out.append("Enter a move for " + model.getTurn().toString() + ":\n");
      } catch (IOException e) {
        throw new IllegalStateException("Append failed.", e);
      }

      if (!sc.hasNext()) {
        return; // no more input
      }
      line = sc.nextLine().trim();

      if (line.equalsIgnoreCase("q")) {
        try {
          out.append("Game quit! Ending game state:\n").append(model.toString()).append("\n");
        } catch (IOException e) {
          throw new IllegalStateException("Append failed.", e);
        }
        return;
      }

      String[] parts = line.split(" ");
      if (parts.length != 2) {
        continue;
      }

      try {
        int row = Integer.parseInt(parts[0]) - 1;
        int col = Integer.parseInt(parts[1]) - 1;
        model.move(row, col);
      } catch (NumberFormatException e) {
        // Handle non-integer input
        continue;
      } catch (IllegalArgumentException e) {
        // Handle invalid move
        continue;
      }
    }

    try {
      out.append(model.toString()).append("\n");
      out.append("Game is over!\n");
      if (model.getWinner() != null) {
        out.append(model.getWinner().toString() + " wins.\n");
      } else {
        out.append("Tie game.\n");
      }
    } catch (IOException e) {
      throw new IllegalStateException("Append failed.", e);
    }
  }
}
