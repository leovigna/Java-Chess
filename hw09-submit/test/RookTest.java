import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

public class RookTest {

    /* ********** Rook Tests ********** */
    @Test
    public void testRookPrint() throws Board.SquareOutOfBoundsException {
        PieceInterface rook1 = new Rook(PieceColor.White);
        String exp1 = "White Rook";
        PieceInterface rook2 = new Rook(PieceColor.Black);
        String exp2 = "Black Rook";
        
        assertEquals(exp1, rook1.toString());
        assertEquals(exp2, rook2.toString());
    }
    
    @Test
    public void testRookControlled() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
        
        PieceInterface rook1 = new Rook(PieceColor.White);
        board.addPiece(rook1, "b4");
        Set<Position> controlled1 = Position.makeSet(
                new String[] {
                        "b3", "b2", "b1", "b5", "b6", "b7", "b8",
                        "a4", "c4", "d4", "e4", "f4", "g4", "h4"});

        assertEquals(controlled1, board.getPiece("b4").controlled(board));
    }
    
    @Test
    public void testRookRegularMoves() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
                
        PieceInterface rook1 = new Rook(PieceColor.White);
        board.addPiece(rook1, "b4");
        Set<Position> moves1 = Position.makeSet(
                new String[] {
                        "b3", "b2", "b1", "b5", "b6", "b7", "b8",
                        "a4", "c4", "d4", "e4", "f4", "g4", "h4"});

        assertEquals(moves1, board.getPiece("b4").moves(board));
    }
    
    @Test
    public void testRookBlockedMoves() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
        
        PieceInterface rook1 = new Rook(PieceColor.White);
        board.addPiece(rook1, "b4");
        PieceInterface pawn1 = new Pawn(PieceColor.White);
        board.addPiece(pawn1, "b3");
        board.addPiece(pawn1, "d4");
        
        Set<Position> moves1 = Position.makeSet(
                new String[] {
                        "b5", "b6", "b7", "b8",
                        "a4", "c4"});

        assertEquals(moves1, board.getPiece("b4").moves(board));
    }
    
    @Test
    public void testRookTakeMoves() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
        
        PieceInterface rook1 = new Rook(PieceColor.White);
        board.addPiece(rook1, "b4");
        PieceInterface pawn1 = new Pawn(PieceColor.Black);
        board.addPiece(pawn1, "b3");
        board.addPiece(pawn1, "d4");
        
        Set<Position> moves1 = Position.makeSet(
                new String[] {
                        "b3", "b5", "b6", "b7", "b8",
                        "a4", "c4", "d4"});

        assertEquals(moves1, board.getPiece("b4").moves(board));
    }
    
    @Test
    public void testRookCheckMoves() throws Board.SquareOutOfBoundsException {
        Board board = new Board();

        //Black pawn is checking king
        PieceInterface king = new King(PieceColor.White);
        board.addPiece(king, "e4");
        PieceInterface blackPawn = new Pawn(PieceColor.Black);
        board.addPiece(blackPawn, "d5");
        
        //Rook must take pawn
        PieceInterface rook1 = new Rook(PieceColor.White);
        board.addPiece(rook1, "b5");
        Set<Position> moves1 = Position.makeSet(
                new String[] {"d5"});
        
        assertEquals(moves1, board.getPiece("b5").moves(board));
    }
    
    @Test
    public void testRookPinnedMoves() throws Board.SquareOutOfBoundsException {
        Board board = new Board();

        //Black pawn is checking king
        PieceInterface king = new King(PieceColor.White);
        board.addPiece(king, "e1");
        PieceInterface blackRook = new Rook(PieceColor.Black);
        board.addPiece(blackRook, "e8");
        
        //Rook is pinned, can only move along one column
        PieceInterface whiteRook = new Rook(PieceColor.White);
        board.addPiece(whiteRook, "e5");
        Set<Position> moves1 = Position.makeSet(
                new String[] {"e2", "e3", "e4", "e6", "e7", "e8"});
        
        assertEquals(moves1, board.getPiece("e5").moves(board));
    }
    
}
