import java.util.Set;
import java.util.TreeSet;

/**
 * Defines the knight class which extends PieceInterface
 * 
 * The knight moves across in an L shaped matter, it
 * is NOT blocked by pieces which it can jump over.
 * 
 * 
 */
public class Knight extends PieceInterface {

    public Knight(PieceColor color) {
        super(color);
    }

    @Override
    public Set<Position> controlled(Board board) {
        Set<Position> controlled = new TreeSet<Position>();
        
        Position[] positions = new Position[8];
        positions[0] = this.position.newByIncrementingRowCol(-1, 2);
        positions[1] = this.position.newByIncrementingRowCol(1, 2);
        positions[2] = this.position.newByIncrementingRowCol(-1, -2);
        positions[3] = this.position.newByIncrementingRowCol(1, -2);
        positions[4] = this.position.newByIncrementingRowCol(-2, 1);
        positions[5] = this.position.newByIncrementingRowCol(-2, -1);
        positions[6] = this.position.newByIncrementingRowCol(2, 1);
        positions[7] = this.position.newByIncrementingRowCol(2, -1);
        
        for (Position p : positions) {
            if (Board.positionInBounds(p)) {
                controlled.add(p);
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
        
        Position[] positions = new Position[8];
        positions[0] = this.position.newByIncrementingRowCol(-1, 2);
        positions[1] = this.position.newByIncrementingRowCol(1, 2);
        positions[2] = this.position.newByIncrementingRowCol(-1, -2);
        positions[3] = this.position.newByIncrementingRowCol(1, -2);
        positions[4] = this.position.newByIncrementingRowCol(-2, 1);
        positions[5] = this.position.newByIncrementingRowCol(-2, -1);
        positions[6] = this.position.newByIncrementingRowCol(2, 1);
        positions[7] = this.position.newByIncrementingRowCol(2, -1);
        
        for (Position p : positions) {
            if (Board.positionInBounds(p)) {
                PieceInterface piece = board.getPiece(p);
                if (piece == null) {
                    moves.add(p);
                }
                else if (piece.color() != this.color()) {
                    moves.add(p);
                }
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
        return PieceType.Knight;
    }
    
    @Override
    public PieceInterface copy() {
        Knight copy = new Knight(this.color());
        if (position != null) {
            copy.position = position.copy();
        }

        return copy;
    }

}
