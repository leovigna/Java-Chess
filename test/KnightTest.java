import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

public class KnightTest {

    /* ********** Knight Tests ********** */
    @Test
    public void testKnightPrint() throws Board.SquareOutOfBoundsException {
        PieceInterface knight1 = new Knight(PieceColor.White);
        String exp1 = "White Knight";
        PieceInterface knight2 = new Knight(PieceColor.Black);
        String exp2 = "Black Knight";
        
        assertEquals(exp1, knight1.toString());
        assertEquals(exp2, knight2.toString());
    }
    
    @Test
    public void testKnightControlled() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
        
        PieceInterface knight1 = new Knight(PieceColor.White);
        board.addPiece(knight1, "b4");
        Set<Position> controlled1 = Position.makeSet(
                new String[] {
                        "a6", "c6", "a2", "c2",
                        "d5", "d3"});

        assertEquals(controlled1, board.getPiece("b4").controlled(board));
    }
    
    @Test
    public void testKnightRegularMoves() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
                
        PieceInterface knight1 = new Knight(PieceColor.White);
        board.addPiece(knight1, "b4");
        Set<Position> moves1 = Position.makeSet(
                new String[] {
                        "a6", "c6", "a2", "c2",
                        "d5", "d3"});

        assertEquals(moves1, board.getPiece("b4").moves(board));
    }
    
    @Test
    public void testKnightBlockedMoves() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
        
        PieceInterface knight1 = new Knight(PieceColor.White);
        board.addPiece(knight1, "b4");
        PieceInterface pawn1 = new Pawn(PieceColor.White);
        board.addPiece(pawn1, "a6");
        board.addPiece(pawn1, "c6");
        
        Set<Position> moves1 = Position.makeSet(
                new String[] {
                        "a2", "c2",
                        "d5", "d3"});

        assertEquals(moves1, board.getPiece("b4").moves(board));
    }
    
    @Test
    public void testKnightTakeMoves() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
        
        PieceInterface knight1 = new Knight(PieceColor.White);
        board.addPiece(knight1, "b4");
        PieceInterface pawn1 = new Pawn(PieceColor.Black);
        board.addPiece(pawn1, "a6");
        board.addPiece(pawn1, "c6");
        
        Set<Position> moves1 = Position.makeSet(
                new String[] {
                        "a6", "c6", "a2", "c2",
                        "d5", "d3"});

        assertEquals(moves1, board.getPiece("b4").moves(board));
    }
    
    @Test
    public void testKnightCheckMoves() throws Board.SquareOutOfBoundsException {
        Board board = new Board();

        //Black pawn is checking king
        PieceInterface king = new King(PieceColor.White);
        board.addPiece(king, "e4");
        PieceInterface blackPawn = new Pawn(PieceColor.Black);
        board.addPiece(blackPawn, "d5");
        
        //Knight must take pawn
        PieceInterface knight1 = new Knight(PieceColor.White);
        board.addPiece(knight1, "c3");
        Set<Position> moves1 = Position.makeSet(
                new String[] {"d5"});
        
        assertEquals(moves1, board.getPiece("c3").moves(board));
    }
    
    @Test
    public void testKnightPinnedMoves() throws Board.SquareOutOfBoundsException {
        Board board = new Board();

        //Black pawn is checking king
        PieceInterface king = new King(PieceColor.White);
        board.addPiece(king, "e1");
        PieceInterface blackRook = new Rook(PieceColor.Black);
        board.addPiece(blackRook, "e8");
        
        //Knight is pinned, cannot move
        PieceInterface whiteKnight = new Knight(PieceColor.White);
        board.addPiece(whiteKnight, "e5");
        Set<Position> moves1 = Position.makeSet(
                new String[] {});
        
        assertEquals(moves1, board.getPiece("e5").moves(board));
    }
    
}
