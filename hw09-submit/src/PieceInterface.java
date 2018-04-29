import java.util.Set;

/**
 * Defines the interface of a chess piece
 * 
 * The most important function in the chess piece class
 * is the moves(Board) function which returns the legal
 * moves of the piece within the context of the chess board
 * when a user clicks a piece on the GUI, they will be presented 
 * with highlights of their possible legal moves.
 * 
 */
public abstract class PieceInterface {

    public Position position;
    private final PieceColor color;
    
    PieceInterface(PieceColor color) {
        this.color = color;
    }
    
    /**
     * Gets the piece color which is an enum and not 
     * a boolean for purposes of ever extending the game 
     * to 3 player chess
     *
     * @return Piece color corresponding to player
     */ 
    public PieceColor color() {
        return color;
    }
    
    /**
     * Returns whether piece is white for easy if statements
     *
     * @return True if piece color is white
     */ 
    public boolean isWhite() {
        return color == PieceColor.White;
    }   
    
    /**
     * Returns whether piece can move on the board
     *
     * @return True if piece color equals board turn
     */ 
    public boolean isTurn(Board board) {
        return color == board.turn();
    }   
    
    /**
     * Gets the squares controlled by the piece
     * The controlled squares take into account all other pieces on the board
     * @param A chess board to take into consideration all the other pieces
     * @return A set of positions the piece controls
     */ 
    public abstract Set<Position> controlled(Board board);
    
    /**
     * Gets the possible squares the piece can legally move to
     * The moves take into account all other pieces on the board
     * @param A chess board to take into consideration all the other pieces
     * @return A set of positions for moves of the piece
     */ 
    public abstract Set<Position> moves(Board board);
    
    /**
     * Gets the piece type which is an enum
     *
     * @return Piece type corresponding to subclass
     */ 
    public abstract PieceType type();
    
    /**
     * Gets an unaliased copy of the piece
     *
     * @return A copy of the piece
     */ 
    public abstract PieceInterface copy();
    
    /**
     * Gets a string representation of the chess piece
     *
     * @return String representation "(color) (type) at (position)"
     */ 
    @Override
    public String toString() {
        return color + " " + type();
    }

    /**
     * Compares the color, type of the current Piece
     * with another to check if they are the same
     * (and thus whether the two Pieces are the same)
     * Note that same pieces at different position return
     * true.
     *
     * @param piece The piece being compared with this piece
     * @return True if pieces are the same
     */
    public boolean equals(PieceInterface piece) {
        if (piece == null) {
            return false;
        }
        
        boolean colorEqual = this.color().equals(piece.color());
        boolean typeEqual = this.type().equals(piece.type()) ;
        boolean positionEqual = false;
        if (this.position == null && piece.position == null) {
            positionEqual = true;
        } else if (this.position == null || piece.position == null) {
            positionEqual = false;
        } else {
            positionEqual = this.position.equals(piece.position);
        }
        
        return colorEqual && typeEqual && positionEqual;
    }
    
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other instanceof PieceInterface) {
            PieceInterface p = (PieceInterface) other;
            return this.equals(p);
        }
        return false;
    }
    
}

enum PieceType {
    Pawn, Knight, Bishop, Rook, Queen, King;
    
    /**
     * Returns value of a piece
     * For sorting purposes, Knight is 2 & King 15
     * 
     * @return Integer representing value
     */ 
    public int value() {
        switch (this) {
        case Pawn: 
            return 1;
        case Knight: 
            return 2;
        case Bishop: 
            return 3;
        case Rook: 
            return 5;
        case Queen: 
            return 9;
        case King: 
            return 15;
        default:
            //Invalid
            return -1;
        }
    }
}

enum PieceColor {
    White, Black
}

