/**
 * Creates an enum Player with two options: X or O.
 */
public enum Player {
    X,
    O;

    /**
     * Returns a string for each enum value.
     *
     * @return a string for the enum value.
     */
    @Override
    public String toString(){
        switch (this) {
            case X:
                return "X";
            case O:
                return "O";
            default:
                return null;
        }
    }
}
