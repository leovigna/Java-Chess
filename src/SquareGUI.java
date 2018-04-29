import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SquareGUI extends JPanel {
    public final Position position;
    public final boolean isWhite;
    
    private boolean isHighlited = false;
    private PieceInterface piece;
    
    public static int width = 70;
    public static int height = 70;
    
    //Square Images
    private static BufferedImage whiteImg;
    private static BufferedImage blackImg;
    private static BufferedImage highlightImg;
    
    //Piece Images
    private static BufferedImage whitePawn;
    private static BufferedImage whiteKnight;
    private static BufferedImage whiteBishop;
    private static BufferedImage whiteRook;
    private static BufferedImage whiteKing;
    private static BufferedImage whiteQueen;
    
    private static BufferedImage blackPawn;
    private static BufferedImage blackKnight;
    private static BufferedImage blackBishop;
    private static BufferedImage blackRook;
    private static BufferedImage blackKing;
    private static BufferedImage blackQueen;
        
    public SquareGUI(Position position, boolean isWhite) {
        this.position = position;
        this.isWhite = isWhite;
        
        //Load image
        try {
            //Load static images
            //Square images
            if (whiteImg == null) {
                whiteImg = ImageIO.read(new File("files/" + "whiteTile.png"));
            }
            if (blackImg == null) {
                blackImg = ImageIO.read(new File("files/" + "blackTile.png"));
            }
            if (highlightImg == null) {
                highlightImg = ImageIO.read(new File("files/" + "choice.png"));
            }
            //Piece images
            if (whitePawn == null) { 
                whitePawn = ImageIO.read(new File("files/" + "whitePawn.png"));
            }
            if (whiteKnight == null) { 
                whiteKnight = ImageIO.read(new File("files/" + "whiteKnight.png"));
            }
            if (whiteBishop == null) { 
                whiteBishop = ImageIO.read(new File("files/" + "whiteBishop.png"));
            }
            if (whiteRook == null) { 
                whiteRook = ImageIO.read(new File("files/" + "whiteRook.png"));
            }
            if (whiteKing == null) { 
                whiteKing = ImageIO.read(new File("files/" + "whiteKing.png"));
            }
            if (whiteQueen == null) { 
                whiteQueen = ImageIO.read(new File("files/" + "whiteQueen.png"));
            }
            
            if (blackPawn == null) { 
                blackPawn = ImageIO.read(new File("files/" + "blackPawn.png"));
            }
            if (blackKnight == null) { 
                blackKnight = ImageIO.read(new File("files/" + "blackKnight.png"));
            }
            if (blackBishop == null) { 
                blackBishop = ImageIO.read(new File("files/" + "blackBishop.png"));
            }
            if (blackRook == null) { 
                blackRook = ImageIO.read(new File("files/" + "blackRook.png"));
            }
            if (blackKing == null) { 
                blackKing = ImageIO.read(new File("files/" + "blackKing.png"));
            }
            if (blackQueen == null) { 
                blackQueen = ImageIO.read(new File("files/" + "blackQueen.png"));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }
    
    public void setIsHighlited(boolean val) {
        this.isHighlited = val;
    }
    
    public void setPiece(PieceInterface val) {
        this.piece = val;
    }
    
    public void draw(Graphics g) {
        //System.out.println("draw() SquareGUI " + this.toString());
        
        //Draw Square images
        if (isWhite) {
            g.drawImage(whiteImg, 0, 0, SquareGUI.width, SquareGUI.height, null);
        }
        else {
            g.drawImage(blackImg, 0, 0, SquareGUI.width, SquareGUI.height, null);
        }
        
        //Draw Piece images
        BufferedImage pieceImage = null;
        if (piece != null) {
            //White Pieces
            if (piece.isWhite()) {
                switch (piece.type()) {
                case Pawn: 
                    pieceImage = whitePawn;
                    break;
                case Knight: 
                    pieceImage = whiteKnight;
                    break;
                case Bishop: 
                    pieceImage = whiteBishop;
                    break;
                case Rook: 
                    pieceImage = whiteRook;
                    break;
                case Queen: 
                    pieceImage = whiteQueen;
                    break;
                case King: 
                    pieceImage = whiteKing;
                    break;
                default:
                    break;
                }
            }
            //Black Pieces
            else {
                switch (piece.type()) {
                case Pawn: 
                    pieceImage = blackPawn;
                    break;
                case Knight: 
                    pieceImage = blackKnight;
                    break;
                case Bishop: 
                    pieceImage = blackBishop;
                    break;
                case Rook: 
                    pieceImage = blackRook;
                    break;
                case Queen: 
                    pieceImage = blackQueen;
                    break;
                case King: 
                    pieceImage = blackKing;
                    break;
                default:
                    break;
                }
            }
        }
        
        //Draw if piece image not null
        if (pieceImage != null) {
            int centerX = SquareGUI.width / 2 - pieceImage.getWidth() / 2;
            int centerY = SquareGUI.height / 2 - pieceImage.getHeight() / 2;
            g.drawImage(pieceImage, centerX, centerY, 
                    pieceImage.getWidth(), pieceImage.getHeight(), null);
        }
        
        //Draw Highlight
        if (isHighlited) {
            g.drawImage(highlightImg, 0, 0, SquareGUI.width, SquareGUI.height, null);
        }
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(SquareGUI.width, SquareGUI.height);
    }
    
    @Override
    public String toString() {
        String s = position + ": " + piece;
        return s;
    }
    
}
