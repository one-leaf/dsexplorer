package luz.dsexplorer.gui;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;
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
import javax.swing.filechooser.FileFilter;

import luz.dsexplorer.datastructures.Result;
import luz.dsexplorer.datastructures.ResultListImpl;
import luz.dsexplorer.datastructures.simple.Byte4;
import luz.dsexplorer.gui.listener.MemorySearchListener;
import luz.dsexplorer.gui.listener.ProcessDialogListener;
import luz.dsexplorer.winapi.api.Process;



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
	private JScrollPane scrTree;
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
	private JSplitPane splMain;
	private ProcessTree tree;
	private JPanel rightPanel;
	private ProcessDialog pd;
	private MemorySearch ms;
	private DSEditor dse;
	private JFileChooser fc;
	private ResultListImpl rl = new ResultListImpl();
	private String version="0.6";
	
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
				ms.setProcess(p);	//FIXME necessairy?
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
		dse.setDataStructures(rl.getDatastructures());
		
		fc = new JFileChooser();
		fc.setFileFilter(new FileFilter() {					
			public boolean accept(File f) {				
				if(f != null && (f.isDirectory() || f.getName().toLowerCase().endsWith(".xml")))		
					return true;			
				else				
					return false;								
			}
			
			public String getDescription() {						
				return "xml DS Explorer savefile";
			}
		});
	}
	
	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			getContentPane().setLayout(thisLayout);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setTitle("DSExplorer v"+version);
			{
				splMain = new JSplitPane();
				splMain.setResizeWeight(1);
				getContentPane().add(splMain, BorderLayout.CENTER);
				{
					scrTree = new JScrollPane();
					splMain.add(scrTree, JSplitPane.LEFT);
					scrTree.setPreferredSize(new Dimension(250, 550));
					scrTree.setMinimumSize(new Dimension(100, 350));
					{
						tree = new ProcessTree();
						scrTree.setViewportView(tree);
						tree.setResultList(rl);
						tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
							public void valueChanged(TreeSelectionEvent evt) {
								treeSelectionEvent(evt);							
							}
						});
					}
				}
				{
					rightPanel = new JPanel();
					splMain.add(rightPanel, JSplitPane.RIGHT);
					BorderLayout jPanel1Layout = new BorderLayout();
					rightPanel.setLayout(jPanel1Layout);
					rightPanel.setPreferredSize(new Dimension(531, 550));
					rightPanel.setMinimumSize(new Dimension(531, 350));
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
						miNew.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								miNewActionPerformed(evt);
							}
						});
					}
					{
						miOpen = new JMenuItem();
						mFile.add(miOpen);
						miOpen.setText("Open...");
						miOpen.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								miOpenActionPerformed(evt);
							}
						});
					}
					{
						miSave = new JMenuItem();
						mFile.add(miSave);
						miSave.setText("Save as...");
						miSave.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								miSaveActionPerformed(evt);
							}
						});
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
						miAdd.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								miAddActionPerformed(evt);
							}
						});
					}
					{
						miDelete = new JMenuItem();
						mEdit.add(miDelete);
						miDelete.setText("Delete");
						miDelete.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								miDeleteActionPerformed(evt);
							}
						});
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
								JOptionPane.showMessageDialog(MainWindow.this, "DSExplorer v"+version, "About", JOptionPane.PLAIN_MESSAGE);
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


	//$hide>>$
	
	private void treeSelectionEvent(TreeSelectionEvent evt) {
		Object node=evt.getPath().getLastPathComponent();
		if (node instanceof ResultListImpl){
			rightPanel.removeAll();
			rightPanel.add(ms, BorderLayout.CENTER);
			rightPanel.repaint();
			rightPanel.validate();			
		}
		if (node instanceof Result){
			rightPanel.removeAll();
			dse.setResult((Result)node);
			rightPanel.add(dse, BorderLayout.CENTER);
			rightPanel.repaint();
			rightPanel.validate();			
		}	
	}

	private void miOpenProcessActionPerformed() {
		pd.refresh();
		pd.setVisible(true);
	}
	
	private void miExitActionPerformed() {
		this.dispose();
	}
		
	private void miNewActionPerformed(ActionEvent evt) {
		tree.reset();

	}
	
	private void miSaveActionPerformed(ActionEvent evt) {
		File f=null;
		while(fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			f = fc.getSelectedFile();
			String path = f.getAbsolutePath();					
			if(!path.toLowerCase().endsWith(".xml"))
				f = new File(path + ".xml");
			if(f.exists()){
				if(JOptionPane.showConfirmDialog(this, "Overwrite "+f.getName()+" ?", "Overwrite", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)
					break;
			}else
				break;				
		}
		if (f!=null){
			tree.saveToFile(f);
		}
	}	
	
	private void miOpenActionPerformed(ActionEvent evt) {
		File f=null;
		while(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			f = fc.getSelectedFile();
			if (f.exists())
				break;
		}
		if (f!=null){
			ResultListImpl rl;
			try {
				rl = ResultListImpl.openFromFile(f);
				tree.setResultList(rl);
				dse.setDataStructures(rl.getDatastructures());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void miDeleteActionPerformed(ActionEvent evt) {
		tree.deleteSelected();
	}
	
	
	private void miAddActionPerformed(ActionEvent evt) {
		Result r = new Result(new Byte4());
		tree.addResult(r);
		//TODO context relative addField or addResult
	}
	
	//$hide<<$
}
