import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

public class BishopTest {

    /* ********** Bishop Tests ********** */
    @Test
    public void testBishopPrint() throws Board.SquareOutOfBoundsException {
        PieceInterface bishop1 = new Bishop(PieceColor.White);
        String exp1 = "White Bishop";
        PieceInterface bishop2 = new Bishop(PieceColor.Black);
        String exp2 = "Black Bishop";
        
        assertEquals(exp1, bishop1.toString());
        assertEquals(exp2, bishop2.toString());
    }
    
    @Test
    public void testBishopControlled() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
        
        PieceInterface bishop1 = new Bishop(PieceColor.White);
        board.addPiece(bishop1, "b4");
        Set<Position> controlled1 = Position.makeSet(
                new String[] {
                        "a3", "c5", "d6", "e7", "f8",
                        "a5", "c3", "d2", "e1"});

        assertEquals(controlled1, board.getPiece("b4").controlled(board));
    }
    
    @Test
    public void testBishopRegularMoves() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
                
        PieceInterface bishop1 = new Bishop(PieceColor.White);
        board.addPiece(bishop1, "b4");
        Set<Position> moves1 = Position.makeSet(
                new String[] {
                        "a3", "c5", "d6", "e7", "f8",
                        "a5", "c3", "d2", "e1"});

        assertEquals(moves1, board.getPiece("b4").moves(board));
    }
    
    @Test
    public void testBishopBlockedMoves() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
        
        PieceInterface bishop1 = new Bishop(PieceColor.White);
        board.addPiece(bishop1, "b4");
        PieceInterface pawn1 = new Pawn(PieceColor.White);
        board.addPiece(pawn1, "d6");
        board.addPiece(pawn1, "c3");
        
        Set<Position> moves1 = Position.makeSet(
                new String[] {
                        "a3", "c5",
                        "a5"});

        assertEquals(moves1, board.getPiece("b4").moves(board));
    }
    
    @Test
    public void testBishopTakeMoves() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
        
        PieceInterface bishop1 = new Bishop(PieceColor.White);
        board.addPiece(bishop1, "b4");
        PieceInterface pawn1 = new Pawn(PieceColor.Black);
        board.addPiece(pawn1, "d6");
        board.addPiece(pawn1, "c3");
        
        Set<Position> moves1 = Position.makeSet(
                new String[] {
                        "a3", "c5", "d6",
                        "a5", "c3"});

        assertEquals(moves1, board.getPiece("b4").moves(board));
    }
    
    @Test
    public void testBishopCheckMoves() throws Board.SquareOutOfBoundsException {
        Board board = new Board();

        //Black pawn is checking king
        PieceInterface king = new King(PieceColor.White);
        board.addPiece(king, "e4");
        PieceInterface blackPawn = new Pawn(PieceColor.Black);
        board.addPiece(blackPawn, "d5");
        
        //Bishop must take pawn
        PieceInterface bishop1 = new Bishop(PieceColor.White);
        board.addPiece(bishop1, "b3");
        Set<Position> moves1 = Position.makeSet(
                new String[] {"d5"});
        
        assertEquals(moves1, board.getPiece("b3").moves(board));
    }
    
    @Test
    public void testBishopPinnedMoves() throws Board.SquareOutOfBoundsException {
        Board board = new Board();

        //Black pawn is checking king
        PieceInterface king = new King(PieceColor.White);
        board.addPiece(king, "a1");
        PieceInterface blackBishop = new Bishop(PieceColor.Black);
        board.addPiece(blackBishop, "h8");
        
        //Bishop is pinned, can only move along one diagonal
        PieceInterface whiteBishop = new Bishop(PieceColor.White);
        board.addPiece(whiteBishop, "d4");
        Set<Position> moves1 = Position.makeSet(
                new String[] {"b2", "c3", "e5", "f6", "g7", "h8"});
        
        assertEquals(moves1, board.getPiece("d4").moves(board));
    }
    
}
