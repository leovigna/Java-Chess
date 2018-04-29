import java.util.Set;
import java.util.TreeSet;

/**
 * Defines the king class which extends PieceInterface
 * 
 * The king is the most important piece in chess. 
 * Even for its movements are simple (1 square each direction) 
 * implementing moves function presents certain edge cases such as. 
 * 
 * The king implementation comes with certain challenges such as
 * determining checkmate, checks (limiting the number of legal moves)
 * and pins (liminiting movement of pieces). Also to account 
 * for are double checks, and castles.
 * 
 * Some edge cases:
 * - Potential pieces blocking the king
 * - Two Kings facing each other controlling squares
 * - King cannot move to squares controlled by the opponent
 * - King's castle & Queen's castle
 * - Check
 * - Checkmate
 * 
 * 
 */

public class King extends PieceInterface {

    //Used for castle
    public boolean moved = true;
    
    King(PieceColor color) {
        super(color);
    }

    @Override
    public Set<Position> controlled(Board board) {
        
        Set<Position> controlled = new TreeSet<Position>();
        
        //Loop around the king
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0  || j != 0) {
                    Position next = this.position.newByIncrementingRowCol(i, j); 
                    if (Board.positionInBounds(next)) {
                        //No piece in next square
                        controlled.add(next);
                    }
                }
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
        
        //Loop around the king
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                //King must move, therefore i and j cannot both be 0.
                if (i != 0  || j != 0) {
                    Position next = this.position.newByIncrementingRowCol(i, j); 
                    PieceInterface nextPiece = board.getPiece(next);
                    if (Board.positionInBounds(next)) {
                        if (nextPiece == null) {
                            //No piece in next square
                            moves.add(next);
                        } 
                        else if (nextPiece.color() != this.color()) {
                            //Opponent piece
                            moves.add(next);
                        }
                    }
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
        
        
        try {    
            //White castle
            Position e = Board.notationToPosition("e1");
            
            //King's castle
            Position f;
            Position g;
            Position h;
            
            //Queen's castle
            Position d;
            Position c;
            Position b;
            Position a;
            
            if (this.isWhite()) {
                e = Board.notationToPosition("e1");
                
                f = Board.notationToPosition("f1");
                g = Board.notationToPosition("g1");
                h = Board.notationToPosition("h1");
                
                d = Board.notationToPosition("d1");
                c = Board.notationToPosition("c1");
                b = Board.notationToPosition("b1");
                a = Board.notationToPosition("a1");
            } else {
                e = Board.notationToPosition("e8");
                
                f = Board.notationToPosition("f8");
                g = Board.notationToPosition("g8");
                h = Board.notationToPosition("h8");
                
                d = Board.notationToPosition("d8");
                c = Board.notationToPosition("c8");
                b = Board.notationToPosition("b8");
                a = Board.notationToPosition("a8");
            }
            
            //King's castle
            PieceInterface fP = board.getPiece(f);
            PieceInterface gP = board.getPiece(g);
            PieceInterface hP = board.getPiece(h);
            
            //Check if king rook
            if (hP instanceof Rook) { 
                Rook kingRook = (Rook) hP;

                //Basic conditions for king's castle
                if (this.position.equals(e) && 
                        !this.moved &&
                        fP == null &&
                        gP == null &&
                        !kingRook.moved) {
                    
                    //Check if king can move to f1, g1  without check
                    Board copy1 = new Board(board);
                    Board copy2 = new Board(board);
                    copy1.move(this.position, f);
                    copy2.move(this.position, g);
                    if (!copy1.isCheck(this.color()) &&
                            !copy2.isCheck(this.color())) {
                        
                        Position castle = g.copy();
                        castle.isCastle = true;
                        moves.add(castle);
                    }
                }
            }
            
            //Queen's castle
            PieceInterface dP = board.getPiece(d);
            PieceInterface cP = board.getPiece(c);
            PieceInterface bP = board.getPiece(b);
            PieceInterface aP = board.getPiece(a);

            //Check if queen rook
            if (aP instanceof Rook) {
                Rook kingRook = (Rook) aP;
                
                //Basic conditions for king's castle
                if (this.position.equals(e) && 
                        !this.moved &&
                        dP == null &&
                        cP == null &&
                        bP == null &&
                        !kingRook.moved) {
                    
                    //Check if king can move to d1, c1  without check
                    Board copy1 = new Board(board);
                    Board copy2 = new Board(board);
                    copy1.move(this.position, d);
                    copy2.move(this.position, c);
                    
                    if (!copy1.isCheck(this.color()) &&
                            !copy2.isCheck(this.color())) {
                        
                        Position castle = c.copy();
                        castle.isCastle = true;
                        moves.add(castle);
                    }
                }
            }
            
        } catch (Board.SquareOutOfBoundsException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return moves;
    }

    @Override
    public PieceType type() {
        return PieceType.King;
    }

    @Override
    public PieceInterface copy() {
        King copy = new King(this.color());
        copy.moved = moved;

        if (position != null) {
            copy.position = position.copy();
        }

        return copy;
    }

}
