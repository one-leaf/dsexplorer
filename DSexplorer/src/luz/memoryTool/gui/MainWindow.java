package luz.memoryTool.gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class MainWindow extends javax.swing.JFrame {
	private static final long serialVersionUID = 8472126583792212590L;
	private JMenuBar jMenuBar;
	private JMenu mFile;
	private JMenuItem miNew;
	private JSeparator jSeparator2;
	private JSeparator jSeparator1;
	private JMenuItem miSettings;
	private JMenuItem miOpen;	
	private JMenuItem miSave;
	private JMenuItem miExit;
	private JMenu mEdit;
	private JMenuItem miCopy;
	private JMenuItem miPaste;	
	private JMenu mProcess;
	private JMenuItem miOpenProcess;
	private JMenu mHelp;
	private JMenuItem miHelp;
	private JMenuItem miAbout;
	private JSplitPane jSplitPane1;
	private ProcessTree jTree1;
	private JPanel jPanel1;
	
	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainWindow inst = new MainWindow();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public MainWindow() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			getContentPane().setLayout(thisLayout);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setTitle("Memory Tool");
			{
				jSplitPane1 = new JSplitPane();
				getContentPane().add(jSplitPane1, BorderLayout.CENTER);
				{
					jTree1 = new ProcessTree();
					jSplitPane1.add(jTree1, JSplitPane.LEFT);
					jTree1.setPreferredSize(new Dimension(179, 381));
				}
				{
					jPanel1 = new JPanel();
					jSplitPane1.add(jPanel1, JSplitPane.RIGHT);
					jPanel1.setPreferredSize(new Dimension(236, 381));
				}
			}
			{
				jMenuBar = new JMenuBar();
				setJMenuBar(jMenuBar);
				{
					mFile = new JMenu();
					jMenuBar.add(mFile);
					mFile.setText("File");
					{
						miNew = new JMenuItem();
						mFile.add(miNew);
						miNew.setText("New");
					}
					{
						miOpen = new JMenuItem();
						mFile.add(miOpen);
						miOpen.setText("Open...");
					}
					{
						miSave = new JMenuItem();
						mFile.add(miSave);
						miSave.setText("Save as...");
					}
					{
						jSeparator1 = new JSeparator();
						mFile.add(jSeparator1);
					}
					{
						miExit = new JMenuItem();
						mFile.add(miExit);
						miExit.setText("Exit");
						miExit.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								miExitActionPerformed(evt);
							}
						});
					}
				}
				{
					mEdit = new JMenu();
					jMenuBar.add(mEdit);
					mEdit.setText("Edit");
					{
						miCopy = new JMenuItem();
						mEdit.add(miCopy);
						miCopy.setText("Copy");
					}
					{
						miPaste = new JMenuItem();
						mEdit.add(miPaste);
						miPaste.setText("Paste");
					}
					{
						jSeparator2 = new JSeparator();
						mEdit.add(jSeparator2);
					}
					{
						miSettings = new JMenuItem();
						mEdit.add(miSettings);
						miSettings.setText("Settings...");
					}
				}
				{
					mProcess = new JMenu();
					jMenuBar.add(mProcess);
					mProcess.setText("Process");
					{
						miOpenProcess = new JMenuItem();
						mProcess.add(miOpenProcess);
						miOpenProcess.setText("Open Process...");
						miOpenProcess.setBounds(-60, 19, 78, 19);
						miOpenProcess.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								miOpenProcessActionPerformed(evt);
							}
						});
					}
				}
				{
					mHelp = new JMenu();
					jMenuBar.add(mHelp);
					mHelp.setText("Help");
					{
						miHelp = new JMenuItem();
						mHelp.add(miHelp);
						miHelp.setText("Help");
					}
					{
						miAbout = new JMenuItem();
						mHelp.add(miAbout);
						miAbout.setText("About");
					}
				}
			}
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void miOpenProcessActionPerformed(ActionEvent evt) {
		ProcessDialog pd = new ProcessDialog(this);
		pd.setLocationRelativeTo(this);
		pd.refresh();
		pd.setVisible(true);
	}
	
	private void miExitActionPerformed(ActionEvent evt) {
		this.dispose();
	}

}
