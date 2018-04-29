import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

public class QueenTest {

    /* ********** Queen Tests ********** */
    @Test
    public void testQueenPrint() throws Board.SquareOutOfBoundsException {
        PieceInterface queen1 = new Queen(PieceColor.White);
        String exp1 = "White Queen";
        PieceInterface queen2 = new Queen(PieceColor.Black);
        String exp2 = "Black Queen";
        
        assertEquals(exp1, queen1.toString());
        assertEquals(exp2, queen2.toString());
    }
    
    @Test
    public void testQueenControlled() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
        
        PieceInterface queen1 = new Queen(PieceColor.White);
        board.addPiece(queen1, "b4");
        Set<Position> controlled1 = Position.makeSet(
                new String[] {
                        //Rook controlled
                        "b3", "b2", "b1", "b5", "b6", "b7", "b8",
                        "a4", "c4", "d4", "e4", "f4", "g4", "h4",
                        //Bishop controlled
                        "a3", "c5", "d6", "e7", "f8",
                        "a5", "c3", "d2", "e1"});

        assertEquals(controlled1, board.getPiece("b4").controlled(board));
    }
    
    @Test
    public void testQueenRegularMoves() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
                
        PieceInterface queen1 = new Queen(PieceColor.White);
        board.addPiece(queen1, "b4");
        Set<Position> moves1 = Position.makeSet(
                new String[] {
                        //Rook moves
                        "b3", "b2", "b1", "b5", "b6", "b7", "b8",
                        "a4", "c4", "d4", "e4", "f4", "g4", "h4",
                        //Bishop moves
                        "a3", "c5", "d6", "e7", "f8",
                        "a5", "c3", "d2", "e1"});

        assertEquals(moves1, board.getPiece("b4").moves(board));
    }
    
    @Test
    public void testQueenBlockedMoves() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
        
        PieceInterface queen1 = new Queen(PieceColor.White);
        board.addPiece(queen1, "b4");
        PieceInterface pawn1 = new Pawn(PieceColor.White);
        //Block rook moves
        board.addPiece(pawn1, "b3");
        //Block bishop moves
        board.addPiece(pawn1, "c3");
        
        Set<Position> moves1 = Position.makeSet(
                new String[] {
                        //Rook moves
                        "b5", "b6", "b7", "b8",
                        "a4", "c4", "d4", "e4", "f4", "g4", "h4",
                        //Bishop moves
                        "a3", "c5", "d6", "e7", "f8",
                        "a5"});

        assertEquals(moves1, board.getPiece("b4").moves(board));
    }
    
    @Test
    public void testQueenTakeMoves() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
        
        PieceInterface queen1 = new Queen(PieceColor.White);
        board.addPiece(queen1, "b4");
        PieceInterface pawn1 = new Pawn(PieceColor.Black);
        //Block rook moves
        board.addPiece(pawn1, "b3");
        //Block bishop moves
        board.addPiece(pawn1, "c3");
        
        Set<Position> moves1 = Position.makeSet(
                new String[] {
                        //Rook moves
                        "b3", "b5", "b6", "b7", "b8",
                        "a4", "c4", "d4", "e4", "f4", "g4", "h4",
                        //Bishop moves
                        "a3", "c5", "d6", "e7", "f8",
                        "a5", "c3"});

        assertEquals(moves1, board.getPiece("b4").moves(board));
    }
    
    @Test
    public void testQueenCheckMoves() throws Board.SquareOutOfBoundsException {
        Board board = new Board();

        //Black pawn is checking king
        PieceInterface king = new King(PieceColor.White);
        board.addPiece(king, "e4");
        PieceInterface blackPawn = new Pawn(PieceColor.Black);
        board.addPiece(blackPawn, "d5");
        
        //Queen must take pawn
        PieceInterface queen1 = new Queen(PieceColor.White);
        board.addPiece(queen1, "b5");
        Set<Position> moves1 = Position.makeSet(
                new String[] {"d5"});
        
        assertEquals(moves1, board.getPiece("b5").moves(board));
    }
    
    @Test
    public void testQueenPinnedMoves() throws Board.SquareOutOfBoundsException {
        Board board = new Board();

        //Black pawn is checking king
        PieceInterface king = new King(PieceColor.White);
        board.addPiece(king, "e1");
        PieceInterface blackQueen = new Queen(PieceColor.Black);
        board.addPiece(blackQueen, "e8");
        
        //Queen must pinned, can only move along a column
        PieceInterface whiteQueen = new Queen(PieceColor.White);
        board.addPiece(whiteQueen, "e5");
        Set<Position> moves1 = Position.makeSet(
                new String[] {"e2", "e3", "e4", "e6", "e7", "e8"});
        
        assertEquals(moves1, board.getPiece("e5").moves(board));
    }
    
}
