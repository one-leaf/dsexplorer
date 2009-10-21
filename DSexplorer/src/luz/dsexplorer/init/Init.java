package luz.dsexplorer.init;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import luz.dsexplorer.gui.MainWindow;

public class Init {
	private static final Log log = LogFactory.getLog(Init.class);

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e)          { log.warn("getSystemLookAndFeelClassName", e);
		} catch (InstantiationException e)          { log.warn("getSystemLookAndFeelClassName", e);
		} catch (IllegalAccessException e)          { log.warn("getSystemLookAndFeelClassName", e);
		} catch (UnsupportedLookAndFeelException e) { log.warn("getSystemLookAndFeelClassName", e);
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainWindow inst = new MainWindow();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});

	}

}
