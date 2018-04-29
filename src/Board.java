import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Defines the class of a chess board
 * 
 */
public class Board {
    // Set of pieces on the board
    private Map<Position, PieceInterface> pieces = new TreeMap<Position, PieceInterface>();
    // Turn of next player
    private PieceColor turn;
    private BoardDelegate delegate;
    
    public PieceInterface selectedPiece;
    
    /**
     * Initialize board
     * 
     */
    public Board() {      
        //White moves first
        turn = PieceColor.White;
    }
    
    /**
     * Initialize board with delegate
     * 
     * @param The delegate for the board
     */
    public Board(BoardDelegate delegate) {      
        //White moves first
        turn = PieceColor.White;
        //Set delegate
        this.delegate = delegate;
    }
    
    /**
     * Initialize board from board, unaliased
     * 
     * @param The board to copy
     */
    public Board(Board board) {      
        this.turn = board.turn;
        if (board.selectedPiece != null) { this.selectedPiece = board.selectedPiece.copy(); }
        
        for (Entry<Position, PieceInterface> entry : board.getEntries()) {
            Position position = entry.getKey().copy();
            PieceInterface piece = entry.getValue().copy();   
            this.pieces.put(position, piece);
        } 
    }
    
    
    /**
     * Resets board to new game position
     */
    public void newGame() {
        try {
            //Clear board
            pieces = new TreeMap<Position, PieceInterface>();
            turn = PieceColor.White;
            selectedPiece = null;
                   
            //Add pawns
            String[] columns = {"a", "b", "c", "d", "e", "f", "g", "h"};
            for (String col : columns) {
                //Add white pawn
                Pawn whitePawn = new Pawn(PieceColor.White);
                addPiece(whitePawn, col + "2");
                //Add black pawn
                Pawn blackPawn = new Pawn(PieceColor.Black);
                addPiece(blackPawn, col + "7");
            }
            
            //Add Rooks
            Rook whiteRook = new Rook(PieceColor.White);
            whiteRook.moved = false;
            addPiece(whiteRook, "a1");
            addPiece(whiteRook, "h1");
            Rook blackRook = new Rook(PieceColor.Black);
            blackRook.moved = false;
            addPiece(blackRook, "a8");
            addPiece(blackRook, "h8");
            //Add Knights
            Knight whiteKnight = new Knight(PieceColor.White);
            addPiece(whiteKnight, "b1");
            addPiece(whiteKnight, "g1");
            Knight blackKnight = new Knight(PieceColor.Black);
            addPiece(blackKnight, "b8");
            addPiece(blackKnight, "g8");
            //Add Bishops
            Bishop whiteBishop = new Bishop(PieceColor.White);
            addPiece(whiteBishop, "c1");
            addPiece(whiteBishop, "f1");
            Bishop blackBishop = new Bishop(PieceColor.Black);
            addPiece(blackBishop, "c8");
            addPiece(blackBishop, "f8");
            //Add Queens
            Queen whiteQueen = new Queen(PieceColor.White);
            addPiece(whiteQueen, "d1");
            Queen blackQueen = new Queen(PieceColor.Black);
            addPiece(blackQueen, "d8");
            //Add Kings
            King whiteKing = new King(PieceColor.White);
            whiteKing.moved = false;
            addPiece(whiteKing, "e1");
            King blackKing = new King(PieceColor.Black);
            blackKing.moved = false;
            addPiece(blackKing, "e8");
            
        } catch (SquareOutOfBoundsException e) {
            throw new RuntimeException("Error reseting Board !");
        } 
        
        //White moves first
        turn = PieceColor.White;
    }
    
    /* ********** Static Functions ********** */
    /**
     * A special purpose exception class to indicate errors when reading the input for a
     * square on the board.
     */
    public static class SquareOutOfBoundsException extends Exception {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        public SquareOutOfBoundsException(String msg) {
            super(msg);
        }
    }
    
    /**
     * A helper method to convert row/col coordinate to an integer
     * @param The row integer
     * @param The colummn integer 
     * @return An integer representing the square number
     *
     */
    public static int coordToInt(int row, int column) {
        return row * 8 + column;
    }
    
    /**
     * A helper method to get the row of a square on the board
     * @param The square integer 
     * @return An integer representing row of the square
     *
     */
    public static int rowFromInt(int n) {
        return n / 8;
    }
    
    /**
     * A helper method to get the column of a square on the board
     * @param The square integer 
     * @return An integer representing column of the square
     *
     */
    public static int columnFromInt(int n) {
        return n - (n / 8) * 8;
    }
    
    /**
     * Returns whether postion is in bounds
     * @param The position 
     * @return True if row within board bounds
     *
     */
    public static boolean positionInBounds(Position pos) {
        return 0 <= pos.row && pos.row <= 7 && 
                0 <= pos.column && pos.column <= 7;
    }
    
    /**
     * Converts chess notation (eg. "a1") to square int ("0")
     * @throws SquareOutOfBoundsException 
     * @return Int representing square 0-63
     *
     */
    public static Position notationToPosition(String notation) throws SquareOutOfBoundsException {
        if (notation == null) {
            throw new IllegalArgumentException("Notation is null !");
        }
        else if (notation.length() < 2) {
            throw new IllegalArgumentException("Notation too short !");
        }
        
        //Number 1-8 convert to 0-7
        int row = notation.charAt(1) - '1';
        //Letter a-h convert to 0-7
        int column = notation.charAt(0) - 'a';
        Position pos = new Position(row, column);
        
        if (!Board.positionInBounds(pos)) {
            //Throw unbound square
            throw new SquareOutOfBoundsException("Notation out of bounds !");
        }
        
        return pos;
    }
    
    /**
     * Converts chess notation (eg. "a1") to square int ("0")
     * @throws SquareOutOfBoundsException 
     * @return Position representing square 0-63
     *
     */ 
    public static String positionToNotation(Position position) throws SquareOutOfBoundsException {        
        if (!Board.positionInBounds(position)) {
            //Throw unbound square
            throw new SquareOutOfBoundsException("Square out of bounds !");
        }
        
        //Convert second number 0-7
        char number = (char) (position.row + '1');
        //Convert first letter
        char letter = (char) (position.column + 'a');
        
        return "" + letter + number;
    }
    
    /* ********** Board Functions ********** */
    /**
     * Moves the piece to the position, castle moves must use this method
     * @param Piece to move
     * @param A new position
     */ 
    public void move(Position oldPos, Position newPos) {
        //Get old position
        PieceInterface piece = getPiece(oldPos);
        
        if (piece == null) {
            throw new RuntimeException("Error: No piece at position !");
        }
        else if (!piece.isTurn(this)) {
            throw new RuntimeException("Error: Not " + piece.color() + " pieces turn !");
        }
        
        if (piece instanceof King) {
            King king = (King) piece;
            king.moved = true;
        } else if (piece instanceof Rook) {
            Rook rook = (Rook) piece;
            rook.moved = true;
        }

        //Remove from previous position
        this.removePiece(oldPos);
        //Add to new position
        this.addPiece(piece, newPos);
        
        //Is castle, move the rook
        boolean castled = false;
        try {
            if (newPos.isCastle) {
                if (newPos.toString().equals("g1")) {
                    //Kingside White
                    System.out.println("Moving h1 -> f1");
                    this.move("h1", "f1");
                    castled = true;
                } else if (newPos.toString().equals("c1")) {
                    //Kingside Black
                    this.move("h8", "f8");
                    castled = true;
                } else if (newPos.toString().equals("g1")) {
                    //Queenside White
                    this.move("a1", "d1");
                    castled = true;
                } else if (newPos.toString().equals("g1")) {
                    //Queenside Black
                    this.move("a8", "d8");
                    castled = true;
                }
            }
        } catch (SquareOutOfBoundsException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        //Change turn, if castled, turn has already been changed.
        if (turn == PieceColor.White && !castled) {
            turn = PieceColor.Black;
        } else if (!castled) {
            turn = PieceColor.White;
        }
        
        //Do not notify while castling, notified after rook move
        if (!newPos.isCastle) {
            if (delegate != null) {
                delegate.didMove(this);
            }
        }
        
    } 
    
    /**
     * Moves the piece from one position to another
     * @param A new position
     * @throws SquareOutOfBoundsException 
     */ 
    public void move(String pos1, String pos2) throws SquareOutOfBoundsException {
        Position oldPos = Board.notationToPosition(pos1);
        Position newPos = Board.notationToPosition(pos2);
        move(oldPos, newPos);
    } 
    
    /**
     * Adds a piece to the game board using notation
     *
     */
    public void addPiece(PieceInterface piece, Position position) {
        if (piece == null) {
            throw new IllegalArgumentException("Adding null piece !");
        } else if (position == null) {
            throw new IllegalArgumentException("Adding null position !");
        }
        PieceInterface copy = piece.copy();
        
        pieces.put(position, copy);
        copy.position = position;

    }
    
    /**
     * Adds a piece to the game board using notation
     * @throws SquareOutOfBoundsException 
     *
     */
    public void addPiece(PieceInterface piece, String notation) 
            throws SquareOutOfBoundsException {
        if (notation == null) {
            throw new IllegalArgumentException("Adding null position !");
        }
        Position position = Board.notationToPosition(notation);
        addPiece(piece, position);
    }
    
    /**
     * Removes a piece from the game board
     *
     */
    public void removePiece(Position position) {
        if (position == null) {
            throw new IllegalArgumentException("Removing null position !");
        }

        pieces.remove(position);
    }
    
    /**
     * Gets a piece from the game board
     *
     * @return The piece at the position
     */
    public PieceInterface getPiece(Position position) {
        if (position == null) {
            throw new IllegalArgumentException("Get piece at null position !");
        }
        return pieces.get(position);
    }
    
    /**
     * Gets a piece from the game board using notation
     * @return The piece at the position
     * @throws SquareOutOfBoundsException 
     *
     */
    public PieceInterface getPiece(String notation)  throws SquareOutOfBoundsException {
        if (notation == null) {
            throw new IllegalArgumentException("Adding null position !");
        }
        Position position = Board.notationToPosition(notation);
        return getPiece(position);
    }

    /**
     * @return an set of entries in the Map
     */
    public Set<Entry<Position, PieceInterface>> getEntries() {
        return pieces.entrySet();
    }
    
    /**
     * @return the number of pieces on the board
     */
    public int pieceCount() {
        return pieces.size();
    }

    /**
     * Returns whether the board is a check,
     * only works is color is the turn
     *
     * @return Board check or not
     */
    public boolean isCheck(PieceColor color) {
        Position kingPosition = null;
        
        Set<Position> oppControlled = new TreeSet<Position>();
        
        for (Entry<Position, PieceInterface> entry : this.getEntries()) {
            Position position = entry.getKey();
            PieceInterface piece = entry.getValue();
            
            if (piece.color() == color && piece.type() == PieceType.King) {
                kingPosition = position;
            }
            
            if (piece.color() != color) {
                oppControlled.addAll(piece.controlled(this));
            }
        }
        
        if (kingPosition == null) {
            return false;
        }
        
        return oppControlled.contains(kingPosition);
    }
    
    /**
     * Returns whether there are legal moves
     *
     * @return True if no legal moves
     */
    public boolean hasNoMoves() {
        //Check if moves exists for pieces of player who has turn            
        for (PieceInterface piece : this.pieces.values()) {
            if (piece.color() == turn && piece.moves(this).size() > 0) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Returns whether the board is in checkmate, 
     * that is the king of the player who has its turn is in check,
     * and that player has no moves.
     *
     * @return Board checkmate or not
     */
    public boolean isCheckMate() {
        boolean isCheckMate = isCheck(turn) && hasNoMoves();
        if (isCheckMate) {
            System.out.println(turn + " is CHECKMATED !");
        }
        return isCheckMate;      
    }
    
    /**
     * Returns whether the board is in draw
     *
     * @return Board checkmate or not
     */
    public boolean isDraw(PieceColor color) {
        throw new UnsupportedOperationException("Unimplemented");
    }
    
    /**
     * Get which team's turn it is
     * 
     * @return Turn of the next player
     */
    public PieceColor turn() {
        return turn;
    }
    
    @Override
    public String toString() {
        String s = "There are " + pieceCount() + " pieces on the board:";

        for (Entry<Position, PieceInterface> entry : this.getEntries()) {
            Position position = entry.getKey();
            PieceInterface piece = entry.getValue();
            
            s = s  + "\n" + piece.toString() + " at " + position.toString();
        }
        
        return s;
    }

}