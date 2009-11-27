package luz.dsexplorer.gui;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import luz.dsexplorer.gui.listener.DSEditorListener;
import luz.dsexplorer.gui.listener.MemorySearchListener;
import luz.dsexplorer.gui.listener.ProcessDialogListener;
import luz.dsexplorer.objects.Process;
import luz.dsexplorer.objects.Result;
import luz.dsexplorer.objects.ResultList;
import luz.dsexplorer.objects.datastructure.Datastructure;



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
	private JScrollPane jScrollPane1;
	private JSeparator jSeparator1;
	private JSeparator jSeparator2;
	private JSeparator jSeparator3;
	private JMenuItem miSettings;
	private JMenuItem miOpen;	
	private JMenuItem miSave;
	private JMenuItem miExit;
	private JMenu mEdit;
	private JMenuItem miAdd;
	private JMenuItem miDelete;
	private JMenuItem miCopy;
	private JMenuItem miPaste;	
	private JMenu mProcess;
	private JMenuItem miOpenProcess;
	private JMenu mHelp;
	private JMenuItem miHelp;
	private JMenuItem miAbout;
	private JSplitPane jSplitPane1;
	private ProcessTree tree;
	private JPanel jPanel1;
	private ProcessDialog pd;
	private MemorySearch ms;
	private DSEditor dse;
	
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
		
		pd = new ProcessDialog(this);
		pd.setLocationRelativeTo(this);
		pd.addListener(new ProcessDialogListener(){
			@Override
			public void okPerformed(Process p) {
				tree.setProcess(p);
				ms.setProcess(p);
			}
			
			@Override
			public void cancelPerformed() {
				// TODO Auto-generated method stub
			}
		});
		
		ms=new MemorySearch();
		ms.addListener(new MemorySearchListener() {
			@Override
			public void FirstSearchPerformed(int from, int to) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void NextSearchPerformed() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void AddPerformed(List<Result> results) {			
				tree.addResults(results);				
			}
		});
		
		dse=new DSEditor();
		dse.addListener(new DSEditorListener() {
			@Override
			public void AddFieldPerformed(Datastructure Result) {
				tree.refresh();	//TODO more precise refresh			
			}

			@Override
			public void AddessChanged(Result result) {
				tree.refresh();	//TODO more precise refresh			
			}

			@Override
			public void SizeChanged(Result o) {
				tree.refresh();	//TODO more precise refresh
			}

			@Override
			public void TypeChanged(Result o) {
				tree.refresh();	//TODO more precise refresh
			}

			@Override
			public void NameChanged(Result o) {
				tree.refresh();	//TODO more precise refresh
			}

			@Override
			public void DSChanged(Result o) {
				tree.refresh();	//TODO more precise refresh			
			}

			@Override
			public void PointerChanged(Result o) {
				tree.refresh();	//TODO more precise refresh				
			}
		});

	}
	
	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			getContentPane().setLayout(thisLayout);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setTitle("DSExplorer v0.2");
			{
				jSplitPane1 = new JSplitPane();
				getContentPane().add(jSplitPane1, BorderLayout.CENTER);
				{
					jScrollPane1 = new JScrollPane();
					jSplitPane1.add(jScrollPane1, JSplitPane.LEFT);
					jScrollPane1.setPreferredSize(new Dimension(250, 550));
					jScrollPane1.setMinimumSize(new Dimension(100, 350));
					{
						tree = new ProcessTree();
						jScrollPane1.setViewportView(tree);
						tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
							public void valueChanged(TreeSelectionEvent evt) {
								treeSelectionEvent(evt);							
							}
						});
					}
				}
				{
					jPanel1 = new JPanel();
					jSplitPane1.add(jPanel1, JSplitPane.RIGHT);
					BorderLayout jPanel1Layout = new BorderLayout();
					jPanel1.setLayout(jPanel1Layout);
					jPanel1.setPreferredSize(new Dimension(350, 550));
					jPanel1.setMinimumSize(new Dimension(250, 350));
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
								miExitActionPerformed();
							}
						});
					}
				}
				{
					mEdit = new JMenu();
					jMenuBar.add(mEdit);
					mEdit.setText("Edit");
					{
						miAdd = new JMenuItem();
						mEdit.add(miAdd);
						miAdd.setText("Add");
					}
					{
						miDelete = new JMenuItem();
						mEdit.add(miDelete);
						miDelete.setText("Delete");
					}
					{
						jSeparator3 = new JSeparator();
						mEdit.add(jSeparator3);
					}
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
						miOpenProcess.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								miOpenProcessActionPerformed();
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
						miAbout.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								JOptionPane.showMessageDialog(MainWindow.this, "DSExplorer v0.2", "About", JOptionPane.PLAIN_MESSAGE);
							}
						});
						
					}
				}
			}
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void treeSelectionEvent(TreeSelectionEvent evt) {
		Object node=evt.getPath().getLastPathComponent();
		if (node instanceof ResultList){
			jPanel1.removeAll();
			jPanel1.add(ms, BorderLayout.CENTER);
			jPanel1.repaint();
			jPanel1.validate();			
		}
		if (node instanceof Result){
			jPanel1.removeAll();
			dse.setResult((Result)node);
			jPanel1.add(dse, BorderLayout.CENTER);
			jPanel1.repaint();
			jPanel1.validate();			
		}	
	}

	private void miOpenProcessActionPerformed() {
		pd.refresh();
		pd.setVisible(true);
	}
	
	private void miExitActionPerformed() {
		this.dispose();
	}

}
