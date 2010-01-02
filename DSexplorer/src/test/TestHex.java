package test;

import java.awt.event.WindowAdapter;
import java.io.ByteArrayOutputStream;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import org.fife.ui.hex.swing.HexEditor;

public class TestHex extends WindowAdapter {

    public static void main(String arg[]) throws Exception{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        for (int i = 0; i < 1000; i++)
			bos.write((byte)(Math.random()*255));
        byte[] ar=bos.toByteArray();
        
        

        HexEditor hex =new HexEditor();
        hex.open(ar, 42L);
        hex.setHighlightSelectionInAsciiDumpColor(UIManager.getColor("Table.selectionBackground"));

       
        
        
        JFrame win=new JFrame();
        win.getContentPane().add(hex);
        win.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        win.pack();
        win.setVisible(true);
    }
}
