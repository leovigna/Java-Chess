import static org.junit.Assert.*;

import java.util.Map.Entry;

import org.junit.Test;


public class BoardTest {

    /* ********** Board Tests ********** */
    @Test
    public void testBoardPrint() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
        
        String expected1 = "There are 0 pieces on the board:";
        assertEquals(expected1, board.toString());
        
        PieceInterface pawn1 = new Pawn(PieceColor.White);
        PieceInterface pawn2 = new Pawn(PieceColor.Black);
        board.addPiece(pawn1, "a1");
        board.addPiece(pawn2, "h8");
                
        String expected2 = "There are 2 pieces on the board:" 
                + "\nWhite Pawn at a1" 
                + "\nBlack Pawn at h8";
        assertEquals(expected2, board.toString());
    }
    
    @Test
    public void testBoardNewGame() {        
        Board board = new Board();        
        board.newGame();
                
        assertEquals(32, board.pieceCount());
        
        for (Entry<Position, PieceInterface> entry : board.getEntries()) {
            Position position = entry.getKey();
            PieceInterface piece = entry.getValue();
            
            if (piece.type() == PieceType.Pawn) {
                if (piece.isWhite()) {
                    assertEquals(1, position.row);
                } else {
                    assertEquals(6, position.row);
                }
            }
            else if (piece.type() == PieceType.Rook) {
                if (piece.isWhite()) {
                    assertTrue(
                            position.toString().equals("a1") || 
                            position.toString().equals("h1"));
                } else {
                    assertTrue(
                            position.toString().equals("a8") || 
                            position.toString().equals("h8"));
                }
            }
            else if (piece.type() == PieceType.Knight) {
                if (piece.isWhite()) {
                    assertTrue(
                            position.toString().equals("b1") || 
                            position.toString().equals("g1"));
                } else {
                    assertTrue(
                            position.toString().equals("b8") || 
                            position.toString().equals("g8"));
                }
            }
            else if (piece.type() == PieceType.Bishop) {
                if (piece.isWhite()) {
                    assertTrue(
                            position.toString().equals("c1") || 
                            position.toString().equals("f1"));
                } else {
                    assertTrue(
                            position.toString().equals("c8") || 
                            position.toString().equals("f8"));
                }
            }
            else if (piece.type() == PieceType.Queen) {
                if (piece.isWhite()) {
                    assertTrue(
                            position.toString().equals("d1"));
                } else {
                    assertTrue(
                            position.toString().equals("d8"));
                }
            }
            else if (piece.type() == PieceType.King) {
                if (piece.isWhite()) {
                    assertTrue(
                            position.toString().equals("e1"));
                } else {
                    assertTrue(
                            position.toString().equals("e8"));
                }
            }
        }
    }
    
    @Test
    public void testBoardMovePiece() throws Board.SquareOutOfBoundsException {   
        Board board = new Board();
        
        board.newGame();
        //Move piece from start to end position
        board.move("e2", "e4");
        //Move piece by reference
        Position e7 = Board.notationToPosition("e7");
        Position e5 = Board.notationToPosition("e5");
        board.move(e7, e5);
                
        assertEquals(32, board.pieceCount());
        
        //Check pawn positions
        for (Entry<Position, PieceInterface> entry : board.getEntries()) {
            Position position = entry.getKey();
            PieceInterface piece = entry.getValue();
            
            if (piece.type() == PieceType.Pawn) {
                if (piece.isWhite()) {
                    if (position.column == 4) {
                        assertEquals(3, position.row);
                    }
                    else {
                        assertEquals(1, position.row);
                    }
                } else {
                    if (position.column == 4) {
                        assertEquals(4, position.row);
                    }
                    else {
                        assertEquals(6, position.row);
                    }
                }
            }
        }
    }
    
    @Test
    public void testBoardMovePieceWrongTurn() {
        Board board = new Board();
        
        board.newGame();
        try {
            //Move black piece first
            board.move("e7", "e5");
            fail("Should throw RuntimeException");
        } catch (Board.SquareOutOfBoundsException e) {
            fail("Should throw RuntimeException");
        } catch (RuntimeException e) {
            // Do nothing
        }
    }
    
    @Test
    public void testBoardNonAliasedAdd() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
        
        Pawn blackPawn = new Pawn(PieceColor.Black);
        board.addPiece(blackPawn, "d7");
        board.addPiece(blackPawn, "e7");
        
        PieceInterface d7 = board.getPiece(Board.notationToPosition("d7"));
        PieceInterface e7 = board.getPiece(Board.notationToPosition("e7"));
        
        assertFalse(d7.equals(e7));
        assertFalse(d7 == e7);
        
        board.addPiece(blackPawn, Board.notationToPosition("f7"));
        board.addPiece(blackPawn, Board.notationToPosition("g7"));
        
        PieceInterface f7 = board.getPiece(Board.notationToPosition("f7"));
        PieceInterface g7 = board.getPiece(Board.notationToPosition("g7"));
        
        assertFalse(f7.equals(g7));
        assertFalse(g7 == f7);
    }
    
    @Test
    public void testBoardCheck() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
        
        King whiteKing = new King(PieceColor.White);
        board.addPiece(whiteKing, "e4");
        
        Pawn blackPawn = new Pawn(PieceColor.Black);
        board.addPiece(blackPawn, "d5");
        board.addPiece(blackPawn, "c7");
        
        //King checked
        assertTrue(board.isCheck(board.turn()));
        //Move king
        board.move("e4", "d5");
        assertFalse(board.isCheck(whiteKing.color()));
        //No black king, so not in check
        assertFalse(board.isCheck(board.turn()));
        //Move pawn to check again        
        board.move("c7", "c6");
        //White king now in check again
        assertTrue(board.isCheck(board.turn()));
    }
    
    @Test
    public void testBoardCheckMate() throws Board.SquareOutOfBoundsException {
        Board board = new Board();
        board.newGame();
        //Quick 2-move mate
        assertFalse(board.isCheckMate());
        board.move("f2", "f3");
        assertFalse(board.isCheckMate());
        board.move("e7", "e6");
        assertFalse(board.isCheckMate());
        board.move("g2", "g4");
        assertFalse(board.isCheckMate());
        //"White is CHECKMATED !" printed twice because 
        //called once on move() and another time in assert
        board.move("d8", "h4");
        assertTrue(board.isCheckMate());
    }

}
