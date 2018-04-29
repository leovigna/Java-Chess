import java.util.Set;
import java.util.TreeSet;

/**
 * A wrapper with row/column values for squares on the chess board
 * Most importantly, this class throws SquareOutOfBoundsException
 * if the square is not within the board bounds.
 * 
 */
public class Position implements Comparable<Position> {
    public final int row;
    public final int column;
    
    //Used for castle
    public boolean isCastle = false;
    
    /* ********** Initializer methods ********** */
    Position(int row, int column) {
        
        this.row = row;
        this.column = column;
    }
    
    /* ********** Factory methods ********** */
    /**
     * Get a set of positions from an array of notations
     * 
     * @throws SquareOutOfBoundsException if one of the notations 
     *   is out of board bounds
     * @return A set of positions
     */
    public static Set<Position> makeSet(String[] strings) 
            throws Board.SquareOutOfBoundsException {
        Set<Position> mySet = new TreeSet<Position>();
        for (String s : strings) {
            mySet.add(Board.notationToPosition(s));
        }

        return mySet;
    }
    
    /**
     * Create a new position from an existing one
     * 
     * @return A new position
     */
    public Position copy() {
        return new Position(this.row, this.column);
    }
    
    /**
     * Create a new position from an existing one
     * 
     * @throws SquareOutOfBoundsException incremented position 
     *   is out of board bounds
     * @return A new position
     */
    public Position newByIncrementingRowCol(int row, int column) {
        return new Position(this.row + row, this.column + column);
    }

    /* ********** Object superclass Overrides ********** */
    @Override
    public String toString() {
        String notation;
        try {
            notation = Board.positionToNotation(this);
        } catch (Board.SquareOutOfBoundsException e) {
            notation = "(outOfBounds)";
        }
        
        return notation;
    }

    /**
     * Compares the RGB values of the current Pixel with another to check if
     * they are the same (and thus whether the two Pixels equal each other)
     *
     * @param px The pixel being compared with this
     */
    public boolean equals(Position pos) {
        if (pos == null) {
            return false;
        }
        
        return this.row == pos.row && this.column == pos.column;
    }

    
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other instanceof Position) {
            Position pos = (Position) other;
            return this.equals(pos);
        }
        return false;
    }
    
    @Override
    public int compareTo(Position o) {
        return Board.coordToInt(row, column) - Board.coordToInt(o.row, o.column);
    }
    
}
