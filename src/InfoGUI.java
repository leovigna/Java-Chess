import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class InfoGUI extends JPanel {
        private JLabel label;
    
    public InfoGUI(String message) {        
        label = new JLabel(message);
        label.setForeground(Color.WHITE);
        
        this.add(label);
    }

    public void setMessage(String message) {
        label.setText(message);
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
        return new Dimension(BoardGUI.width, 30);
    }
    
    @Override
    public String toString() {
        String s = "InfoGUI " + this.getWidth() + "x" + this.getHeight();
        return s;
    }

}
