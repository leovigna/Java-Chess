import java.util.Set;
import java.util.TreeSet;

/**
 * Defines the pawn class which extends PieceInterface
 * 
 * The pawn is the simplest piece in chess. 
 * Even for this seemingly simple piece, implementing the 
 * moves function is not a simple task as one must take into
 * account edge cases such as. 
 * 
 * Some necessary edge cases to play the game:
 * - Pawns can move two square on their first move
 * - Potential pieces blocking the pawn
 * - Pawns can move one scare diagonal right/left to take 
 *   pieces of the opposing team 
 * 
 * Other edge cases that may also be implemented:
 * - Pawn promotion
 * - Pawn pinned behind king
 * - Pawn required to block/unable to move from a check
 * - En-passant rule
 * 
 * 
 */
public class Pawn extends PieceInterface {
    
    public Pawn(PieceColor color) {
        super(color);
    }
    
    @Override
    public Set<Position> controlled(Board board) {
       
        Set<Position> controlled = new TreeSet<Position>();
        
        int increment;
        if (color().equals(PieceColor.White)) {
            //White pawns controlls up
            increment = 1;
        } else {
            //Black pawns controlls down
            increment = -1;
        }
        
        Position diagLeft = this.position.newByIncrementingRowCol(increment, - increment);
        if (Board.positionInBounds(diagLeft)) {
            controlled.add(diagLeft);
        }
        
        Position diagRight = this.position.newByIncrementingRowCol(increment, increment); 
        if (Board.positionInBounds(diagRight)) {
            controlled.add(diagRight);
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
                
        //Pawns move forward
        int increment;
        boolean isStart = false;
        if (color().equals(PieceColor.White)) {
            //White pawns move up
            increment = 1;
            //White paws start at row 2
            if (this.position.row == 1) { isStart = true; }
        } else {
            //Black pawns move down
            increment = -1;
            //Black paws start at row 7
            if (this.position.row == 6) { isStart = true; }
        }
        
        Position pawnFront = 
                this.position.newByIncrementingRowCol(increment, 0);        
        //Front of pawn empty
        if (board.getPiece(pawnFront) == null && 
                Board.positionInBounds(pawnFront)) {
            moves.add(pawnFront);
            
            Position pawnDoubleFront = 
                    this.position.newByIncrementingRowCol(2 * increment, 0);
            //Because front is empty, check for start position and if square empty
            if (isStart && board.getPiece(pawnDoubleFront) == null) {
                moves.add(pawnDoubleFront);
            }
        }
               
        Position pawnDiagLeft = 
                this.position.newByIncrementingRowCol(increment, - increment);
        //Opponent Piece on Left Diag square
        PieceInterface leftPiece = board.getPiece(pawnDiagLeft);
        if (leftPiece != null) {
            if (leftPiece.color() != this.color() &&
                    Board.positionInBounds(pawnDiagLeft)) {
                moves.add(pawnDiagLeft);
            }
        }
        
        Position pawnDiagRight = 
                this.position.newByIncrementingRowCol(increment, increment);
        //Opponent Piece on Right Diag square
        PieceInterface rightPiece = board.getPiece(pawnDiagRight);
        if (rightPiece != null) {
            if (rightPiece.color() != this.color() &&
                    Board.positionInBounds(pawnDiagRight)) {
                moves.add(pawnDiagRight);
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
        return PieceType.Pawn;
    }

    @Override
    public PieceInterface copy() {
        Pawn copy = new Pawn(this.color());
        if (position != null) {
            copy.position = position.copy();
        }

        return copy;
    }
}
