import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BoardGUI extends JPanel {
        
    public static int width = SquareGUI.width * 8;
    public static int height = SquareGUI.height * 8;    
    
    //Model
    private Board board;
    
    //Map of tiles
    public Map<Position, SquareGUI> squares = new TreeMap<Position, SquareGUI>();
    
    public BoardGUI(Board board) {
        this.board = board;
        
        //UI Layout
        GridLayout boardLayout = new GridLayout(8,8);
        this.setLayout(boardLayout);

        //Add SquareGUI to the BoardGUI
        boolean isWhite = true;
        //Layout top to bottom, left to right
        //Row
        for (int i = 7; i >= 0; i--) {
            //Column
            for (int j = 0; j < 8; j++) {
                Position position = new Position(i, j);
                SquareGUI squarePanel = new SquareGUI(position, isWhite);
                squares.put(position, squarePanel);
                
                squarePanel.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                          clickedSquare(squarePanel);      
                    }
                });
                
                this.add(squarePanel, BorderLayout.SOUTH);
                isWhite = !isWhite;
            }
            isWhite = !isWhite;
        }
       
        refreshBoard();
    }
    
    public void refreshBoard() {
        System.out.println("refreshBoard()");
        //System.out.println(board);
        
        //Remove all pieces
        for (SquareGUI s : squares.values()) {
            s.setPiece(null);
        }
        
        //Add PieceGUI pieces to the SquareGUI
        for (Entry<Position, PieceInterface> entry : board.getEntries()) {
            Position position = entry.getKey();
            PieceInterface piece = entry.getValue();
            SquareGUI square = squares.get(position);
            square.setPiece(piece);
        }              
    }
    
    public void highlightSquare(SquareGUI squarePanel, boolean val) {
        if (squarePanel != null) {
            squarePanel.setIsHighlited(val);
        } else {
            throw new IllegalArgumentException("highlightSquare() null panel !");
        }
    }
    
    public void clickedSquare(SquareGUI square) {    
        if (square == null) {
            throw new IllegalArgumentException("clickedSquare() null SquareGUI !");
        } 
        else if (square.position == null) {
            throw new IllegalArgumentException("clickedSquare() null SquareGUI.position !");
        }
        
        //Unhilight all
        for (SquareGUI s : squares.values()) {
            highlightSquare(s, false);
        }

       PieceInterface pieceAtPos = board.getPiece(square.position);
       System.out.println("Clicked " + pieceAtPos + " at " + square.position + ". Selected: " + board.selectedPiece);
       
       if (board.selectedPiece == null) {
           //No piece had been selected
           if (pieceAtPos == null) {
               //Square empty, do nothing
           } else {
               //Set selected piece and highlight moves
               board.selectedPiece = pieceAtPos;
               Set<Position> moves = pieceAtPos.moves(board);
               for (Position p : moves) {
                   highlightSquare(squares.get(p), true);
               }
           } 
       }
       
       else if (board.selectedPiece != null) {
           //A piece had been selected
           if (board.selectedPiece.equals(pieceAtPos)) {
               //Reclicked same piece
               board.selectedPiece = null;
           }
           else {
               Set<Position> moves = board.selectedPiece.moves(board);
               if (moves.contains(square.position)) {
                   //Clicked a square that is a move, move the piece
                   System.out.println("Move " + board.selectedPiece + " to " + square.position);                   
                   //Move piece
                   if (board.selectedPiece instanceof King) {
                       King king = (King) board.selectedPiece;
                       if (!king.moved) {
                           //Get the potential castle position
                           for (Position p : moves) {
                               if (p.equals(square.position)) {
                                   board.move(board.selectedPiece.position, p);
                                   break;
                               }
                           }
                       }
                       else { 
                         //King moved
                         board.move(board.selectedPiece.position, square.position);
                       }
                   } else {
                       //Not king
                       board.move(board.selectedPiece.position, square.position);
                   }
                   
                   //Deselect piece
                   board.selectedPiece = null;
                   //Update board
                   refreshBoard();
                   
               } 
               else if (pieceAtPos != null) {
                   //Selected a new piece
                   //Set selected piece and highlight moves
                   board.selectedPiece = pieceAtPos;
                   moves = pieceAtPos.moves(board);
                   for (Position p : moves) {
                       highlightSquare(squares.get(p), true);
                   }
               }
               else {
                   //Selected empty square not in moves
                   board.selectedPiece = null;
               }
           }
       }

       repaint();

    }
    
    public void draw(Graphics g) {
       System.out.println("draw() BoardGUI");
       //System.out.println(this.toString());
    }
    
    @Override
    public void paintComponent(Graphics g) {        
        super.paintComponent(g);
        draw(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BoardGUI.width, BoardGUI.height);
    }
    
    @Override
    public String toString() {
        String s = this.getComponentCount() + " components:";
        for (Component c : this.getComponents()) {
            s = s + "\n  - " + c.toString();
        }
        return s;
    }
    
}

