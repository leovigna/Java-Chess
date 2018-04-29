import java.util.Set;
import java.util.TreeSet;

/**
 * Defines the rook class which extends PieceInterface
 * 
 * The rook moves across rows and columns, it
 * is blocked by its own pieces.
 * 
 * 
 */
public class Rook extends PieceInterface {

    //Used for castle
    public boolean moved = true;
    
    public Rook(PieceColor color) {
        super(color);
    }

    @Override
    public Set<Position> controlled(Board board) {
        Set<Position> controlled = new TreeSet<Position>();
        
        //Right Row
        for (int i = 1; i < 8; i++) {
            Position p = this.position.newByIncrementingRowCol(i, 0);
            PieceInterface piece = board.getPiece(p);
            
            if (!Board.positionInBounds(p)) {
                //Reached bounds
                break;
            } else if (piece == null) {
                //Empty square
                controlled.add(p);
            } else {
                //Piece controlled
                controlled.add(p);
                break;
            }
        }
        
        //Left Row
        for (int i = 1; i < 8; i++) {
            Position p = this.position.newByIncrementingRowCol(-i, 0);
            PieceInterface piece = board.getPiece(p);
            
            if (!Board.positionInBounds(p)) {
                //Reached bounds
                break;
            } else if (piece == null) {
                //Empty square
                controlled.add(p);
            } else {
                //Piece controlled
                controlled.add(p);
                break;
            }
        }
        
        //Top Column
        for (int i = 1; i < 8; i++) {
            Position p = this.position.newByIncrementingRowCol(0, i);
            PieceInterface piece = board.getPiece(p);
            
            if (!Board.positionInBounds(p)) {
                //Reached bounds
                break;
            } else if (piece == null) {
                //Empty square
                controlled.add(p);
            } else {
                //Piece controlled
                controlled.add(p);
                break;
            }
        }
        
        //Bottom Column
        for (int i = 1; i < 8; i++) {
            Position p = this.position.newByIncrementingRowCol(0, -i);
            PieceInterface piece = board.getPiece(p);
            
            if (!Board.positionInBounds(p)) {
                //Reached bounds
                break;
            } else if (piece == null) {
                //Empty square
                controlled.add(p);
            } else {
                //Piece controlled
                controlled.add(p);
                break;
            }
        }
                
        return controlled;
    }

    @Override
    public Set<Position> moves(Board board) {
        Set<Position> moves = new TreeSet<Position>();
        //No moves if not turn
        if (!isTurn(board)) {
            return moves;
        }
                
        //Right Row
        for (int i = 1; i < 8; i++) {
            Position p = this.position.newByIncrementingRowCol(i, 0);
            PieceInterface piece = board.getPiece(p);
            
            if (!Board.positionInBounds(p)) {
                //Reached bounds
                break;
            } else if (piece == null) {
                //Empty square
                moves.add(p);
            } else if (piece.color() != this.color()) {
                //Piece controlled
                moves.add(p);
                break;
            } else {
                //Team piece
                break;
            }
        }
        
        //Left Row
        for (int i = 1; i < 8; i++) {
            Position p = this.position.newByIncrementingRowCol(-i, 0);
            PieceInterface piece = board.getPiece(p);
            
            if (!Board.positionInBounds(p)) {
                //Reached bounds
                break;
            } else if (piece == null) {
                //Empty square
                moves.add(p);
            } else if (piece.color() != this.color()) {
                //Piece controlled
                moves.add(p);
                break;
            } else {
                //Team piece
                break;
            }
        }
        
        //Top Column
        for (int i = 1; i < 8; i++) {
            Position p = this.position.newByIncrementingRowCol(0, i);
            PieceInterface piece = board.getPiece(p);
            
            if (!Board.positionInBounds(p)) {
                //Reached bounds
                break;
            } else if (piece == null) {
                //Empty square
                moves.add(p);
            } else if (piece.color() != this.color()) {
                //Piece controlled
                moves.add(p);
                break;
            } else {
                //Team piece
                break;
            }
        }
        
        //Bottom Column
        for (int i = 1; i < 8; i++) {
            Position p = this.position.newByIncrementingRowCol(0, -i);
            PieceInterface piece = board.getPiece(p);
            
            if (!Board.positionInBounds(p)) {
                //Reached bounds
                break;
            } else if (piece == null) {
                //Empty square
                moves.add(p);
            } else if (piece.color() != this.color()) {
                //Piece controlled
                moves.add(p);
                break;
            } else {
                //Team piece
                break;
            }
        }
        
        Set<Position> illegalMoves = new TreeSet<Position>();
        //To get illegal moves we test each move and check if the king ends up in check
        for (Position p : moves) {
            Board copy = new Board(board);

            copy.move(this.position, p);
            if (copy.isCheck(this.color())) {
                illegalMoves.add(p);
            }
        }
                
        moves.removeAll(illegalMoves);
        
        return moves;
    }

    @Override
    public PieceType type() {
        return PieceType.Rook;
    }
    
    @Override
    public PieceInterface copy() {
        Rook copy = new Rook(this.color());
        copy.moved = moved;
        
        if (position != null) {
            copy.position = position.copy();
        }

        return copy;
    }

}
