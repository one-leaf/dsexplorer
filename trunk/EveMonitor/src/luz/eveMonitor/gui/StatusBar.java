package luz.eveMonitor.gui;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import luz.eveMonitor.datastructure.other.TypeGroupMap;
import luz.eveMonitor.threads.Status;
import luz.eveMonitor.threads.StatusListener;

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
public class StatusBar extends javax.swing.JPanel {
	private static final long serialVersionUID = 1505143385322711167L;
	private JLabel lblOrders;
	private JLabel lblTrans;
	private JLabel lblTypes;
	private JLabel lblDict;
	private JLabel lblProcess;
	private Status status;
	private TypeGroupMap map;
	
	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new StatusBar());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public StatusBar() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout(1, 10);
			thisLayout.setHgap(1);
			thisLayout.setColumns(10);
			this.setLayout(thisLayout);
			{
				lblProcess = new JLabel();
				this.add(lblProcess);
				lblProcess.setText("Process id: ");
				lblProcess.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			}
			{
				lblDict = new JLabel();
				this.add(lblDict);
				lblDict.setText("Dict Addr: ");
				lblDict.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			}
			{
				lblTypes = new JLabel();
				this.add(lblTypes);
				lblTypes.setText("Types: ");
				lblTypes.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			}			
			{
				lblOrders = new JLabel();
				this.add(lblOrders);
				lblOrders.setText("Orders: ");
				lblOrders.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			}
			{
				lblTrans = new JLabel();
				this.add(lblTrans);
				lblTrans.setText("Transactions: ");
				lblTrans.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setStatus(Status status) {
		this.status=status;
		this.status.addStatusListener(new StatusListener() {
			
			@Override
			public void statusChanged() {
				//TODO output serious data
				lblProcess.setText("Process id: "+StatusBar.this.status.getPid());
				lblDict.setText("Dict Addr: "+String.format("%08X", StatusBar.this.status.getDictAddr()));
		
			}
		});
	}
	
	public void setMap(TypeGroupMap map) {
		this.map=map;
		map.getTransactions().addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent paramTableModelEvent) {
				lblTypes.setText("Types: "+StatusBar.this.map.getTypeCount());
				lblOrders.setText("Orders: "+StatusBar.this.map.getOrderCount());
				lblTrans.setText("Transactions: "+StatusBar.this.map.getTransactions().size());		
			}
		});
	}
	

}
