package test;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class TextQualityDemo {
    private static Object[] antialiasHintValues = {
        RenderingHints.VALUE_TEXT_ANTIALIAS_OFF,
        RenderingHints.VALUE_TEXT_ANTIALIAS_GASP,    // new to 1.6
        RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HBGR,// new to 1.6
        RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB,// new to 1.6
        RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_VBGR,// new to 1.6
        RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_VRGB,// new to 1.6
        RenderingHints.VALUE_TEXT_ANTIALIAS_ON
    };
    private static void constructGUI() {

        JPanel cp = new JPanel();
        for (int i = 0; i < antialiasHintValues.length; i++) {
            MyPanel p = new MyPanel(antialiasHintValues[i]);
            cp.add(p);
        }

        JFrame frame = new JFrame("LCD Text Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(630, 460));
        frame.setContentPane(cp);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                constructGUI();
            }
        });
    }
    
    private static class MyPanel extends JPanel {
        private Object hintValue;

        public MyPanel(Object hintValue) {
            this.hintValue = hintValue;
            this.setPreferredSize(new Dimension(300, 100));
            String title = hintValue.toString();
            Border border = new TitledBorder(title);
            this.setBorder(border);
        }

        public void paintComponent(Graphics g) {
            Dimension d = this.getSize();

            // Create buffer where we will draw boxes and text
            BufferedImage backBuffer = (BufferedImage)
                    this.createImage(d.width, d.height);
            Graphics2D g2 = backBuffer.createGraphics();

            // Fill with white
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, d.width, d.height);

            // Set pen color and antialiasing type
            g2.setColor(Color.BLACK);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    hintValue);

            // Draw black border
            g2.drawRect(0, 0, d.width - 1, d.height - 1);

            // Draw strings
            g2.drawString("abcdefghijklmnopqrstuvwxyz", 20, 40);
            g2.drawString("ABCDEFGHIJKLMNOPQRSTUVWXYZ", 20, 60);
            g2.drawString("1234567890-=!@#$%^&*()_+,./<>?", 20, 80);

            // Draw image
            g.drawImage(backBuffer, 0, 0, this);
        }
    }
}
