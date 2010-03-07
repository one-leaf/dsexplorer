package luz.eveMonitor.gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
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
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import luz.eveMonitor.datastructure.other.Security;
import luz.eveMonitor.datastructure.other.Transaction;
import luz.eveMonitor.datastructure.other.TransactionSettings;
import luz.eveMonitor.datastructure.other.TypeGroupMap;
import luz.eveMonitor.threads.Status;

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
	private JScrollPane spTransTable;
	private JLabel lblMaxMoney;
	private JTextField txtMaxVolume;
	private TransactionTable tblTransactions;
	private JTextField txtMaxMoney;
	private JSpinner spAccounting;
	private JLabel lblAccounting;
	private JButton btnRefresh;
	private JTextField txtNumber;
	private JLabel lblNumber;
	private JSpinner spSecurity;
	private JLabel lblSecurity;
	private StatusBar stStatusBar;
	private OrderInfo oiSell;
	private OrderInfo oiBuy;
	private JLabel lblMaxVol;
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
	private Status status;
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
				SpinnerListModel spAccountingModel = 
					new SpinnerListModel( new Integer[] { 0,1,2,3,4,5 });
				spAccounting = new JSpinner();
				spAccounting.setModel(spAccountingModel);
			}
			{
				lblAccounting = new JLabel();
				lblAccounting.setText("Accouting Level:");
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
				lblMaxVol = new JLabel();
				lblMaxVol.setText("max Volume:");
			}
			{
				txtMaxVolume = new JTextField();
			}
			{
				lblMaxMoney = new JLabel();
				lblMaxMoney.setText("max Money:");
			}
			{
				spTransTable = new JScrollPane();
				{
					tblTransactions = new TransactionTable();
					spTransTable.setViewportView(tblTransactions);
					tblTransactions.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
						@Override
						public void valueChanged(ListSelectionEvent evt) {
							tblTransactionsSelected(evt);							
						}
					});
				}
			}
			{
				stStatusBar = new StatusBar();
			}
			{
				txtNumber = new JTextField();
			}
			{
				lblNumber = new JLabel();
				lblNumber.setText("Number:");
			}
			{
				SpinnerListModel spSecurityModel =  new SpinnerListModel(Security.values());
				spSecurity = new JSpinner();
				spSecurity.setModel(spSecurityModel);
			}
			{
				lblSecurity = new JLabel();
				lblSecurity.setText("Security:");
			}
			{
				oiBuy = new OrderInfo();
				oiBuy.setBorder(BorderFactory.createTitledBorder(null, "Buy Order", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION));
			}
			{
				oiSell = new OrderInfo();
				oiSell.setBorder(BorderFactory.createTitledBorder(null, "Sell Order", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION));
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
				    .addComponent(lblMaxMoney, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(lblMaxVol, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(lblAccounting, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(spAccounting, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
				    .addComponent(lblSecurity, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(spSecurity, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
				    .addComponent(lblNumber, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(txtNumber, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(spTransTable, 0, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(thisLayout.createParallelGroup()
				    .addComponent(oiBuy, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
				    .addComponent(oiSell, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(stStatusBar, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE));
			thisLayout.setHorizontalGroup(thisLayout.createParallelGroup()
				.addComponent(stStatusBar, GroupLayout.Alignment.LEADING, 0, 1016, Short.MAX_VALUE)
				.addGroup(thisLayout.createSequentialGroup()
				    .addPreferredGap(stStatusBar, oiBuy, LayoutStyle.ComponentPlacement.INDENT)
				    .addGroup(thisLayout.createParallelGroup()
				        .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				            .addGroup(thisLayout.createParallelGroup()
				                .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				                    .addComponent(lblMaxMoney, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
				                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				                    .addComponent(txtMaxMoney, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
				                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				                    .addComponent(lblMaxVol, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
				                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				                    .addComponent(txtMaxVolume, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
				                .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				                    .addComponent(oiBuy, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
				                    .addGap(50)))
				            .addGap(10)
				            .addComponent(lblAccounting, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
				            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				            .addComponent(spAccounting, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
				            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				            .addComponent(lblSecurity, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
				            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				            .addComponent(spSecurity, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
				            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				            .addComponent(lblNumber, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
				            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				            .addComponent(txtNumber, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
				            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				            .addGroup(thisLayout.createParallelGroup()
				                .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				                    .addComponent(btnRefresh, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				                    .addGap(0, 0, Short.MAX_VALUE))
				                .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				                    .addGap(0, 0, Short.MAX_VALUE)
				                    .addComponent(oiSell, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))))
				        .addComponent(spTransTable, GroupLayout.Alignment.LEADING, 0, 996, Short.MAX_VALUE))
				    .addContainerGap()));

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
			this.setSize(1024, 660);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	//$hide>>$
	public MainWindow(TypeGroupMap map, Status status) {
		this();
		this.map=map;
		this.status=status;
		tblTransactions.setModel(map.getTransactions());
		stStatusBar.setStatus(status);
		stStatusBar.setMap(map);
		
//		tblOrders.setModel(map.getOrders());
		TransactionSettings ts = map.getTransactionSettings();
		txtMaxMoney.setText(Double.toString(ts.getMaxMoney()));
		txtMaxVolume.setText(""+ts.getMaxVolume());
		spAccounting.setValue(ts.getAccounting());
		spSecurity.setValue(ts.getSecurity());
		txtNumber.setText(""+ts.getNumber());
		
	}
	
	private void btnRefreshActionPerformed(ActionEvent evt) {
		map.refresh(Double.parseDouble(txtMaxMoney.getText()), 
				Double.parseDouble(txtMaxVolume.getText()), 
				(Integer)spAccounting.getValue(), 
				((Security)spSecurity.getValue()),
				Integer.parseInt(txtNumber.getText()));
	}
	
	private void tblTransactionsSelected(ListSelectionEvent evt) {
		int row= tblTransactions.getSelectedRow();
		if(row!=-1){
			Transaction t = tblTransactions.getTransaction(row);
			oiBuy.setOrder(t.buy);
			oiSell.setOrder(t.sell);
		}
	}
	
	
	//$hide<<$
}
