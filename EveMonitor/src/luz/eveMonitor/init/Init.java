package luz.eveMonitor.init;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import luz.eveMonitor.datastructure.other.Security;
import luz.eveMonitor.datastructure.other.TransactionSettings;
import luz.eveMonitor.datastructure.other.TypeGroupMap;
import luz.eveMonitor.gui.MainWindow;
import luz.eveMonitor.threads.DictFinder;
import luz.eveMonitor.threads.ProcessFinder;
import luz.eveMonitor.threads.RowFinder;
import luz.eveMonitor.threads.Status;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Init {
	private static final Log log = LogFactory.getLog(Init.class);
	private static MainWindow window;
	private static Status status;
	private static ProcessFinder pf;
	private static DictFinder df;
	private static RowFinder rf;
	private static TypeGroupMap map;
	private static TransactionSettings settings;
	
	
	private static EntityManager emEveDB;
	private static EntityManager emEveMon;
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			//UIManager.put("Table.font", new Font("Lucida Console", Font.PLAIN, 11));
		} catch (ClassNotFoundException e)          { log.warn("getSystemLookAndFeelClassName", e);
		} catch (InstantiationException e)          { log.warn("getSystemLookAndFeelClassName", e);
		} catch (IllegalAccessException e)          { log.warn("getSystemLookAndFeelClassName", e);
		} catch (UnsupportedLookAndFeelException e) { log.warn("getSystemLookAndFeelClassName", e);
		}
		
		emEveMon = Persistence.createEntityManagerFactory("eveMon").createEntityManager();
		emEveDB  = Persistence.createEntityManagerFactory("eveDB" ).createEntityManager();
		
		
		settings = new TransactionSettings(51000000d, 19859.3d, 4, Security.highsec, 20);
		map = new TypeGroupMap(settings);
		status=new Status();
		window=new MainWindow(map, status);
		pf = new ProcessFinder(status);
		df = new DictFinder(status);
		rf = new RowFinder(status, map, emEveMon, emEveDB);
		
		pf.start();
		df.start();
		rf.start();
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				window.addWindowListener(new WindowListener() {
					public void windowOpened(WindowEvent e) {}
					public void windowIconified(WindowEvent e) {}
					public void windowDeiconified(WindowEvent e) {}
					public void windowDeactivated(WindowEvent e) {}
					public void windowClosing(WindowEvent e) {}
					public void windowActivated(WindowEvent e) {}
					
					@Override
					public void windowClosed(WindowEvent e) {
				        pf.stopNow();
				        df.stopNow();
				        rf.stopNow();
					}
				});
				window.setLocationRelativeTo(null);
				window.setVisible(true);
			}
		});

	}


}
