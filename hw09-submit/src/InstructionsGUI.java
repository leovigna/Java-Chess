import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class InstructionsGUI extends JPanel {
    
    public InstructionsGUI() {     
        GridLayout grid = new GridLayout(10, 1);
        this.setLayout(grid);
        
        JLabel label1 = new JLabel("    Welcome to CIS120 CHESS ! ");
        label1.setForeground(Color.WHITE);
        JLabel label11 = new JLabel(
                "           This game incorporates most rules of chess including:");
        label11.setForeground(Color.WHITE);
        JLabel label12 = new JLabel(
                "           Legal moves, kingside/queenside castle and checkmate.");
        label12.setForeground(Color.WHITE);
        JLabel label13 = new JLabel(
                "           Make sure to check out the extensive unit "
                + "testing for piece behaviour. ");
        label13.setForeground(Color.WHITE);
        JLabel label14 = new JLabel("   See README.me for more info.");
        label14.setForeground(Color.WHITE);
        JLabel label2 = new JLabel("                        by Leo VIGNA");
        label2.setForeground(Color.WHITE);
        JLabel label3 = new JLabel("            Click to start...");
        label3.setForeground(Color.WHITE);
        
        this.add(label1);
        this.add(label11);
        this.add(label12);
        this.add(label13);
        this.add(label14);
        this.add(label2);
        this.add(label3);
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
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
        String s = "InstructionsGUI " + this.getWidth() + "x" + this.getHeight();
        return s;
    }

}
