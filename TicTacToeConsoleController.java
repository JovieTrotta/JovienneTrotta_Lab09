// Import statements.
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

// Tic Tac Toe Controller class.
public class TicTacToeConsoleController implements TicTacToeController {
    private Readable readable;
    private Appendable appendable;

    // Parameterized constructor takes both Readable (basically input) and Appendable (basically output) objects.
    public TicTacToeConsoleController(Readable readable, Appendable appendable) {
        this.readable = readable;
        this.appendable = appendable;
    }

    /**
     * Appends a message to display to the user and handles the IO exception.
     * @param string which is the message to display to the user.
     */
    public void catchIOException(String string) {
        try {appendable.append(string);}
        catch(IOException ioe) {throw new IllegalStateException("IO Exception.\n");}
    }

    @Override
    public void playGame(TicTacToe m) {

        // Creates a scanner object to read in the input to the readable object.
        Scanner scanner = new Scanner(readable);

        // Creates an array list that will store the player's moves.
        ArrayList<Integer> moves = new ArrayList<>();

        // This variable will hold the input even if it turns out not to be a number.
        String buffer = null;

        // Prints the board to the user to begin.
        catchIOException(m + "\n");

        // Begins the game loops.
        while (!m.isGameOver()) {

            // Displays the player turn and asks the user for input.
            catchIOException("Enter a move for " + m.getTurn().toString() + ":\n");

            // Tries to...
            // ...Set the buffer equal to the next input.
            // ...Make the buffer into an integer and sets that equal to the rowInput variable.
            // ...Add the rowInput to the moves list.
            try {
                buffer = scanner.next();
                Integer rowInput = Integer.valueOf(buffer);
                moves.add(rowInput);
            }

            // If the buffer isn't a number and throws an error...
            catch (NumberFormatException nfe) {
                // ...first check if the string is q or Q and then ends the game.
                if (Objects.equals(buffer, "q") || Objects.equals(buffer, "Q")) {
                    catchIOException("Game quit! Ending game state:\n" + m + "\n");
                    return;
                    // Otherwise, print an error message that the input type is incorrect.
                } else {catchIOException("Non-integer value entered for row.\n" + m + "\n");}
                // Go back to the start of the loop.
                continue;
            }

            // Checks that the list is still less than two, otherwise you don't need anymore input to make a move.
            if (moves.size() < 2) {

                // Tries to...
                // ...Set the buffer equal to the next input.
                // ...Make the buffer into an integer and sets that equal to the columnInput variable.
                // ...Add the columnInput to the moves list.
                try {
                    buffer = scanner.next();
                    Integer columnInput = Integer.valueOf(buffer);
                    moves.add(columnInput);
                }

                // If the buffer isn't a number and throws an error...
                catch (NumberFormatException nfe) {
                    // ...first check if the string is q or Q and then ends the game.
                    if (Objects.equals(buffer, "q") || Objects.equals(buffer, "Q")) {
                        catchIOException("Game quit! Ending game state:\n" + m + "\n");
                        return;
                        // Otherwise, print an error message that the input type is incorrect.
                    } else {catchIOException("Non-integer value entered for column.\n" + m + "\n");}
                }
            }

            // If you have enough input in the list to make a move...
            if (moves.size() >= 2) {

                try {
                    // ...make a move.
                    // ...reset the moves list.
                    // ...print the board.
                    m.move(moves.get(0), moves.get(1));
                    moves.remove(0);
                    moves.remove(0);
                    catchIOException(m + "\n");
                }

                // If the move is invalid (out of bounds or space taken), print the error message, reset the moves list, and print the board.
                catch (IllegalArgumentException iae ) {
                    catchIOException(iae.getMessage() + "\n");
                    moves.remove(0);
                    moves.remove(0);
                    catchIOException(m + "\n");
                }
            }
        }

        // When the game is over...
        // ... if there is no winner, print tie.
        if (m.getWinner() == null) {
            catchIOException("Game is over! Tie game.\n");
            // ... if X wins, print that.
        } else if (m.getWinner() == Player.X) {
            catchIOException("Game is over! X wins.\n");
            // ... if O wins, print that.
        } else {catchIOException("Game is over! O wins.\n");}
    }
}