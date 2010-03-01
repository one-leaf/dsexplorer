package luz.eveMonitor.gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerListModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import luz.eveMonitor.datastructure.other.TransactionSettings;
import luz.eveMonitor.datastructure.other.TypeGroupMap;


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

	private static final long serialVersionUID = 6529006528289001505L;
	private JMenuItem helpMenuItem;
	private JMenu jMenu5;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JLabel jLabel1;
	private JTextField txtMaxVolume;
	private TransactionTable tblTransactions;
	private JTextField txtMaxMoney;
	private DBRowTable tblOrders;
	private JSpinner spAccounting;
	private JLabel jLabel3;
	private JButton btnRefresh;
	private JLabel jLabel2;
	private JMenuItem deleteMenuItem;
	private JSeparator jSeparator1;
	private JMenuItem pasteMenuItem;
	private JMenuItem copyMenuItem;
	private JMenuItem cutMenuItem;
	private JMenu jMenu4;
	private JMenuItem exitMenuItem;
	private JSeparator jSeparator2;
	private JMenuItem closeFileMenuItem;
	private JMenuItem saveAsMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem openFileMenuItem;
	private JMenuItem newFileMenuItem;
	private JMenu jMenu3;
	private JMenuBar jMenuBar1;
	private TypeGroupMap map;
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
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			GroupLayout thisLayout = new GroupLayout((JComponent)getContentPane());
			getContentPane().setLayout(thisLayout);
			{
				jScrollPane1 = new JScrollPane();
				{
					tblOrders = new DBRowTable();
					jScrollPane1.setViewportView(tblOrders);
				}
			}
			{
				SpinnerListModel spAccountingModel = 
					new SpinnerListModel( new Integer[] { 0,1,2,3,4,5 });
				spAccounting = new JSpinner();
				spAccounting.setModel(spAccountingModel);
			}
			{
				jLabel3 = new JLabel();
				jLabel3.setText("Accouting Level:");
			}
			{
				btnRefresh = new JButton();
				btnRefresh.setText("Refresh");
				btnRefresh.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						btnRefreshActionPerformed(evt);
					}
				});
			}
			{
				jLabel2 = new JLabel();
				jLabel2.setText("max Volume:");
			}
			{
				txtMaxVolume = new JTextField();
			}
			{
				jLabel1 = new JLabel();
				jLabel1.setText("max Money:");
			}
			{
				jScrollPane2 = new JScrollPane();
				{
					tblTransactions = new TransactionTable();
					jScrollPane2.setViewportView(tblTransactions);
				}
			}
			{
				txtMaxMoney = new JTextField();
			}
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(btnRefresh, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(txtMaxMoney, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(txtMaxVolume, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(jLabel1, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(jLabel2, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(jLabel3, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(spAccounting, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(jScrollPane2, 0, 184, Short.MAX_VALUE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
				.addContainerGap());
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(thisLayout.createParallelGroup()
				    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				        .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
				        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				        .addComponent(txtMaxMoney, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
				        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				        .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
				        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				        .addComponent(txtMaxVolume, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
				        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				        .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
				        .addComponent(spAccounting, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
				        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				        .addComponent(btnRefresh, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
				        .addGap(0, 555, Short.MAX_VALUE))
				    .addComponent(jScrollPane2, GroupLayout.Alignment.LEADING, 0, 1094, Short.MAX_VALUE)
				    .addComponent(jScrollPane1, GroupLayout.Alignment.LEADING, 0, 1094, Short.MAX_VALUE))
				.addContainerGap());
			{
				jMenuBar1 = new JMenuBar();
				setJMenuBar(jMenuBar1);
				{
					jMenu3 = new JMenu();
					jMenuBar1.add(jMenu3);
					jMenu3.setText("File");
					{
						newFileMenuItem = new JMenuItem();
						jMenu3.add(newFileMenuItem);
						newFileMenuItem.setText("New");
					}
					{
						openFileMenuItem = new JMenuItem();
						jMenu3.add(openFileMenuItem);
						openFileMenuItem.setText("Open");
					}
					{
						saveMenuItem = new JMenuItem();
						jMenu3.add(saveMenuItem);
						saveMenuItem.setText("Save");
					}
					{
						saveAsMenuItem = new JMenuItem();
						jMenu3.add(saveAsMenuItem);
						saveAsMenuItem.setText("Save As ...");
					}
					{
						closeFileMenuItem = new JMenuItem();
						jMenu3.add(closeFileMenuItem);
						closeFileMenuItem.setText("Close");
					}
					{
						jSeparator2 = new JSeparator();
						jMenu3.add(jSeparator2);
					}
					{
						exitMenuItem = new JMenuItem();
						jMenu3.add(exitMenuItem);
						exitMenuItem.setText("Exit");
					}
				}
				{
					jMenu4 = new JMenu();
					jMenuBar1.add(jMenu4);
					jMenu4.setText("Edit");
					{
						cutMenuItem = new JMenuItem();
						jMenu4.add(cutMenuItem);
						cutMenuItem.setText("Cut");
					}
					{
						copyMenuItem = new JMenuItem();
						jMenu4.add(copyMenuItem);
						copyMenuItem.setText("Copy");
					}
					{
						pasteMenuItem = new JMenuItem();
						jMenu4.add(pasteMenuItem);
						pasteMenuItem.setText("Paste");
					}
					{
						jSeparator1 = new JSeparator();
						jMenu4.add(jSeparator1);
					}
					{
						deleteMenuItem = new JMenuItem();
						jMenu4.add(deleteMenuItem);
						deleteMenuItem.setText("Delete");
					}
				}
				{
					jMenu5 = new JMenu();
					jMenuBar1.add(jMenu5);
					jMenu5.setText("Help");
					{
						helpMenuItem = new JMenuItem();
						jMenu5.add(helpMenuItem);
						helpMenuItem.setText("Help");
					}
				}
			}
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	


	//$hide>>$
	public MainWindow(TypeGroupMap map) {
		this();
		this.map=map;
		tblTransactions.setModel(map.getTransactions());


		
//		tblOrders.setModel(map.getOrders());
		TransactionSettings ts = map.getTransactionSettings();
		txtMaxMoney.setText(Double.toString(ts.getMaxMoney()));
		txtMaxVolume.setText(""+ts.getMaxVolume());
		spAccounting.setValue(ts.getAccounting());
		
	}
	
	private void btnRefreshActionPerformed(ActionEvent evt) {
		map.getTransactions().refresh(Double.parseDouble(txtMaxMoney.getText()), Double.parseDouble(txtMaxVolume.getText()), (Integer)spAccounting.getValue(), 0.5d);
	}


//	public void setRows(List<Order> list) {
//		tblOrders.setItems(list);
//	}
//	
//
//	public void addRows(List<Order> list) {
//		tblOrders.addItems(list);
//	}
//	
//	public void setTransaction(Transaction t) {
//		if(t!=null){
//			txtWin.setText(""+t.win);
//			if(t.buy!=null){
//				tblTransactions.addItem(t);
//			}
//		}
//	}
//
//	public void setTransactions(List<Transaction> transactions) {
//		tblTransactions.setItems(transactions);		
//	}

	
	
	//$hide<<$
}
