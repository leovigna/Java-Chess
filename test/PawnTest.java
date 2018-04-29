import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

public class PawnTest {

    /* ********** Pawn Tests ********** */
    @Test
    public void testPawnPrint() throws Board.SquareOutOfBoundsException {
        PieceInterface pawn1 = new Pawn(PieceColor.White);
        String exp1 = "White Pawn";
        PieceInterface pawn2 = new Pawn(PieceColor.Black);
        String exp2 = "Black Pawn";
        
        assertEquals(exp1, pawn1.toString());
        assertEquals(exp2, pawn2.toString());
    }
    
    @Test
    public void testPawnControlled() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
        
        //One pawn on each end of the board
        PieceInterface pawn1 = new Pawn(PieceColor.White);
        board.addPiece(pawn1, "b4");
        Set<Position> controlled1 = Position.makeSet(new String[] {"a5", "c5"});
        
        PieceInterface pawn2 = new Pawn(PieceColor.Black);
        board.addPiece(pawn2, "g5");
        Set<Position> controlled2 = Position.makeSet(new String[] {"h4", "f4"});

        assertEquals(controlled1, board.getPiece("b4").controlled(board));
        assertEquals(controlled2, board.getPiece("g5").controlled(board));
    }
    
    @Test
    public void testPawnRegularMoves() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
        
        //One pawn on each end of the board
        PieceInterface pawn1 = new Pawn(PieceColor.White);
        board.addPiece(pawn1, "a4");
        Set<Position> moves1 = Position.makeSet(new String[] {"a5"});

        assertEquals(moves1, board.getPiece("a4").moves(board));
    }
    
    @Test
    public void testPawnBlockedMoves() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
        
        //Both Pawns are facing each other
        PieceInterface pawn1 = new Pawn(PieceColor.White);
        board.addPiece(pawn1, "a4");
        Set<Position> moves1 = Position.makeSet(new String[] {});
        
        PieceInterface pawn2 = new Pawn(PieceColor.Black);
        board.addPiece(pawn2, "a5");

        assertEquals(moves1, board.getPiece("a4").moves(board));
    }
    
    @Test
    public void testPawnTakeMoves() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
        
        //Both Pawns can take each other or move forward on their next turn
        PieceInterface whitePawn = new Pawn(PieceColor.White);
        board.addPiece(whitePawn, "e4");
        Set<Position> moves1 = Position.makeSet(new String[] {"e5", "d5"});

        PieceInterface blackPawn = new Pawn(PieceColor.Black);
        board.addPiece(blackPawn, "d5");
        
        assertEquals(moves1, board.getPiece("e4").moves(board));
    }
    
    @Test
    public void testPawnStartMoves() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
        
        //Both Pawns can take each other or move forward on their next turn
        PieceInterface pawn1 = new Pawn(PieceColor.White);
        board.addPiece(pawn1, "e2");
        Set<Position> moves1 = Position.makeSet(new String[] {"e3", "e4"});

        assertEquals(moves1, board.getPiece("e2").moves(board));
    }
    
    @Test
    public void testPawnCheckMoves() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
        //Black pawn is checking king
        PieceInterface king = new King(PieceColor.White);
        board.addPiece(king, "e4");
        PieceInterface blackPawn = new Pawn(PieceColor.Black);
        board.addPiece(blackPawn, "d5");
        
        //Pawn on c4 can take pawn on d5
        PieceInterface whitePawn = new Pawn(PieceColor.White);
        board.addPiece(whitePawn, "c4");
        Set<Position> moves1 = Position.makeSet(new String[] {"d5"});
        
        assertEquals(moves1, board.getPiece("c4").moves(board));
    }
    
    @Test
    public void testPawnPinnedMoves() throws Board.SquareOutOfBoundsException {
        fail("Unimplemented");
    }
    
    @Test
    public void testPawnPromotion() throws Board.SquareOutOfBoundsException {
        fail("Unimplemented");
    }
    
    @Test
    public void testPawnEnPassantMoves() throws Board.SquareOutOfBoundsException {
        fail("Unimplemented");
    }

}
