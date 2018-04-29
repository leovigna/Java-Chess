import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * BoardGUI class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable, BoardDelegate {
    final InfoGUI infoPanel = new InfoGUI("");;
    
    public void run() { 
        final JFrame frame = new JFrame("Chess");
        frame.setResizable(true);
        frame.setLocation(300, 300);
        
        BorderLayout boarderLayout = new BorderLayout();
        frame.setLayout(boarderLayout);
        
        //Info Panel
        frame.add(infoPanel, BorderLayout.PAGE_START);

        //Game panel
        Board board = new Board(this);
        board.newGame();
        final BoardGUI boardPanel = new BoardGUI(board);
        final InstructionsGUI instructionsPanel = new InstructionsGUI();
        
        frame.add(instructionsPanel, BorderLayout.CENTER);
        instructionsPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.out.println("Start game");
                frame.remove(instructionsPanel);
                frame.add(boardPanel, BorderLayout.CENTER);
                infoPanel.setMessage("White to move.");
                frame.repaint();
          }
        });
                
        //Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
    } 

    public void didMove(Board board) {
        
        if (board.turn() == PieceColor.White) {
            if (board.isCheckMate()) {
                infoPanel.setMessage("White is checkmate. Black wins!");
                
            } else {
                infoPanel.setMessage("White to move.");
            }
        } else {
            if (board.isCheckMate()) {
                infoPanel.setMessage("Black is checkmate. White wins!");
            } else {
                infoPanel.setMessage("Black to move.");
            }
        }
    }
    
    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
    
}