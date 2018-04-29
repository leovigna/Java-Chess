import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

public class KingTest {

    /* ********** King Tests ********** */
    @Test
    public void testKingPrint() throws Board.SquareOutOfBoundsException {
        PieceInterface king1 = new King(PieceColor.White);
        String exp1 = "White King";
        PieceInterface king2 = new King(PieceColor.Black);
        String exp2 = "Black King";
        
        assertEquals(exp1, king1.toString());
        assertEquals(exp2, king2.toString());
    }
    
    @Test
    public void testKingControlled() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
        
        //One king on each end of the board
        PieceInterface king1 = new King(PieceColor.White);
        board.addPiece(king1, "a4");
        Set<Position> controlled1 = Position.makeSet(
                new String[] {"a3","b3", "b4", "a5", "b5"});
        
        PieceInterface king2 = new King(PieceColor.Black);
        board.addPiece(king2, "h5");
        Set<Position> controlled2 = Position.makeSet(
                new String[] {"g6","h6", "g5", "g4", "h4"});

        assertEquals(controlled1, board.getPiece("a4").controlled(board));
        assertEquals(controlled2, board.getPiece("h5").controlled(board));
    }
    
    @Test
    public void testKingRegularMoves() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
        
        //One king on each end of the board
        PieceInterface king = new King(PieceColor.White);
        board.addPiece(king, "b4");
        Set<Position> moves = Position.makeSet(
                new String[] {"a3","b3","c3", "a4", "c4", "a5", "b5", "c5"});
        
        assertEquals(moves, board.getPiece("b4").moves(board));
    }
    
    @Test
    public void testKingBlockedMoves() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
        
        //One king surrounded by 3 white pawns at bottom and 1 black pawn in front 
        PieceInterface king = new King(PieceColor.White);
        PieceInterface whitePawn = new Pawn(PieceColor.White);
        PieceInterface blackPawn = new Pawn(PieceColor.Black);
        board.addPiece(king, "b3");
        
        //White pawns block king
        board.addPiece(whitePawn, "a2");
        board.addPiece(whitePawn, "b2");
        board.addPiece(whitePawn, "c2");
        
        //Black pawn controls squares next to king
        board.addPiece(blackPawn, "b4");
        
        Set<Position> moves = Position.makeSet(
                new String[] {"a4", "b4", "c4"});

        assertEquals(moves, board.getPiece("b3").moves(board));
    }
    
    @Test
    public void testKingsideCastle() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
        board.newGame();
        
        //White & Black prepare for castle.
        board.move("e2", "e3"); //Push pawn
        board.move("e7", "e6");
        board.move("f1", "e2"); //Take out bishop
        board.move("f8", "e7");
        board.move("g1", "f3"); //Take out knight
        board.move("g8", "f6");
        
        Set<Position> moves1 = Position.makeSet(
                new String[] {"f1", "g1"});
        //White King can go to f1 AND castle to g1
        assertEquals(moves1, board.getPiece("e1").moves(board));
        
        //Check is isCastle
        for (Position p : board.getPiece("e1").moves(board)) {
            if (p.toString().equals("f1")) {
                assertFalse(p.isCastle);
            }
            else if (p.toString().equals("g1")) {
                assertTrue(p.isCastle);
                board.move(Board.notationToPosition("e1"), p); //Make the castle move
            }
        }
        
        System.out.println(board);
        //Check if rook has changed position
        assertTrue(board.getPiece("h1") == null);
        assertTrue(board.getPiece("f1") instanceof Rook);
        
        Set<Position> moves2 = Position.makeSet(
                new String[] {"f8", "g8"});
        //Black King can go to f8 AND castle to g8
        assertEquals(moves2, board.getPiece("e8").moves(board));
    }
    
    @Test
    public void testQueensideCastle() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
        board.newGame();

        //White & Black prepare for castle.
        board.move("d2", "d3"); //Push pawn
        board.move("d7", "d6");
        board.move("c1", "e3"); //Take out bishop
        board.move("c8", "e6");
        board.move("d1", "d2"); //Take out queen
        board.move("d8", "d7");
        board.move("b1", "c3"); //Take out knight
        board.move("b8", "c6");
        
        Set<Position> moves1 = Position.makeSet(
                new String[] {"d1", "c1"});
        //White King can go to d1 AND castle to c1
        assertEquals(moves1, board.getPiece("e1").moves(board));

        board.move("a2", "a3"); //Give move to black
        
        Set<Position> moves2 = Position.makeSet(
                new String[] {"d8", "c8"});
        //Black King can go to d8 AND castle to c8
        assertEquals(moves2, board.getPiece("e8").moves(board));
    }
    
    @Test
    public void testNoCastleKingMove() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
        board.newGame();
        
        //White prepares for castle.
        board.move("e2", "e3"); //Push pawn
        board.move("a7", "a6");
        board.move("f1", "e2"); //Take out bishop
        board.move("a6", "a5");
        board.move("g1", "f3"); //Take out knight
        board.move("a5", "a4");
        
        Set<Position> moves1 = Position.makeSet(
                new String[] {"f1", "g1"});
        //White King can go to f1 AND castle to g1
        assertEquals(moves1, board.getPiece("e1").moves(board));

        board.move("e1", "f1"); //Move king
        board.move("b7", "b6");
        board.move("f1", "e1"); //Move king back
        board.move("b6", "b5");
        
        Set<Position> moves2 = Position.makeSet(
                new String[] {"f1"});
        //White King cannot castle as it has moved
        assertEquals(moves2, board.getPiece("e1").moves(board));
        
    }
    
    @Test
    public void testNoCastleRookMove() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
        board.newGame();
        
        //White prepares for castle.
        board.move("e2", "e3"); //Push pawn
        board.move("a7", "a6");
        board.move("f1", "e2"); //Take out bishop
        board.move("a6", "a5");
        board.move("g1", "f3"); //Take out knight
        board.move("a5", "a4");
        
        Set<Position> moves1 = Position.makeSet(
                new String[] {"f1", "g1"});
        //White King can go to f1 AND castle to g1
        assertEquals(moves1, board.getPiece("e1").moves(board));

        board.move("h1", "f1"); //Move rook
        board.move("b7", "b6");
        board.move("f1", "h1"); //Move rook back
        board.move("b6", "b5");
        
        Set<Position> moves2 = Position.makeSet(
                new String[] {"f1"});
        //White King cannot castle as rook has moved
        assertEquals(moves2, board.getPiece("e1").moves(board));
        
    }

}
