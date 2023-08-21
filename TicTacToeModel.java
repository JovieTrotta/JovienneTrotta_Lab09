// Import statements.
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

// Tic Tac Toe Model class.
public class TicTacToeModel implements TicTacToe {

  // Creates a Tic Tac Toe game board as a 3x3 2d array.
  private final Player[][] gameBoard = new Player[3][3];

  // Player X always starts in Tic Tac Toe, so the default setting for the current player is X.
  Player currentPlayer = Player.X;

  // Creates an array list of Points for both X's and O's.
  // This prevents a player from being able to change the game board directly.
  ArrayList<Point> xArray = new ArrayList<Point>();
  ArrayList<Point> oArray = new ArrayList<Point>();

  // Non parameterized constructor for the Tic Tac Toe model.
  public TicTacToeModel() {}

  @Override
  public void move(int r, int c) {
    // If the row or column variables are outside the confines of the 2D array, throw an exception.
    if (r < 0 || r > 2 || c < 0 || c > 2) {throw new IllegalArgumentException("Invalid position: outside of game board.");}

    // If the game is not over, accept a player move.
    if (!isGameOver()) {

      // If the space is not empty, do not allow the player to move there and throw an exception.
      if (getMarkAt(r, c) != null) {throw new IllegalArgumentException("Invalid move: space is occupied.");}

      else {
        // If it's Player X's turn...
        if (getTurn() == Player.X) {
          // Add their Point to the X array.
          xArray.add(new Point(r, c));
          // Switch the current player to Player O.
          currentPlayer = Player.O;
          // Update the board.
          getBoard();
          // If it's Player O's turn....
        } else {
          // Add their point to the O array.
          oArray.add(new Point(r, c));
          // Switch the current player to Player X.
          currentPlayer = Player.X;
          // Update the board.
          getBoard();
        }
      }

      // If the game is over, do not accept a player move and throw an exception.
    } else {throw new IllegalStateException("Game is over.");}
  }

  @Override
  public Player getTurn() {return currentPlayer;}

  @Override
  public boolean isGameOver() {
    // There cannot be more than 9 moves in a Tic Tac Toe game, so end the game.
    // OR end the game once there's a winner.
    if (xArray.size() + oArray.size() >= 9 || getWinner() != null) {return true;}
    else {return false;}
  }

  @Override
  public Player getWinner() {
    // For loop iterates through the 2D array.
    for (int i = 0; i < 3; i++) {

      // Iterate through the scenarios where someone can win three in a row.
      if (gameBoard[i][0]!= null && gameBoard[i][0].equals(gameBoard[i][1]) && gameBoard[i][1].equals(gameBoard[i][2])) {
        return getMarkAt(i,0); // If yes, return that Player as the winner.
      }

      // Iterate through the scenarios where someone can win three in a column.
      if (gameBoard[0][i] != null && gameBoard[0][i].equals(gameBoard[1][i]) && gameBoard[1][i].equals(gameBoard[2][i])) {
        return getMarkAt(0,i); // If yes, return that Player as the winner.
      }

    }
    // Check both diagonal win scenarios.
    if(gameBoard[1][1] != null&& (gameBoard[0][0] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][2] ||
            gameBoard[2][0] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[0][2])) {
      return getMarkAt(1,1); // If yes, return that Player as the winner.
    }

    // Otherwise, return nothing, there is no winner.
    return null;
  }

  @Override
  public Player[][] getBoard() {
    // Go through each Point in the X array and add it to the game board.
    for (Point point : xArray) {gameBoard[point.x][point.y] = Player.X;}

    // Go through each Point in the O array and add it to the game board.
    for (Point point : oArray) {gameBoard[point.x][point.y] = Player.O;}

    // Return updated game board.
    return gameBoard;
  }

  @Override
  public Player getMarkAt(int r, int c) {
    // If the row or column variables are outside the confines of the 2D array, throw an exception.
    if (r < 0 || r > 2 || c < 0 || c > 2) {throw new IllegalArgumentException("Position is out of bounds.");}

    // If that Point is in the X array return X.
    else if (xArray.contains(new Point(r,c))) {return Player.X;}
    // If that Point is in the O array return O.
    else if (oArray.contains(new Point(r,c))) {return Player.O;}
    // Otherwise return nothing.
    else {return null;}
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
}