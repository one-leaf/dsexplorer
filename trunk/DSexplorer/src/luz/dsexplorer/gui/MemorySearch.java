package luz.dsexplorer.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import luz.dsexplorer.gui.listener.MemorySearchListener;

public class MemorySearch extends javax.swing.JPanel {
	private static final long serialVersionUID = 2361607017186276542L;
	private JTextField txtSearch;
	private JComboBox cbType;
	private JLabel jLabel2;
	private JButton btnAdd;
	private JComboBox cbValue;
	private JLabel jLabel1;
	private JLabel lblValue;
	private JScrollPane jScrollPane1;
	private JTable tblResults;
	private JButton btnNext;
	private JButton btnFirst;
	private enum Action{FirstSeach, NextSearch, Add}

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new MemorySearch());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public MemorySearch() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			GroupLayout thisLayout = new GroupLayout((JComponent)this);
			this.setLayout(thisLayout);
			setPreferredSize(new Dimension(400, 300));
			{
				txtSearch = new JTextField();
			}
			{
				btnFirst = new JButton();
				btnFirst.setText("First Scan");
				btnFirst.setMargin(new java.awt.Insets(2, 2, 2, 2));
			}
			{
				ComboBoxModel cbTypeModel = 
					new DefaultComboBoxModel(
							new String[] { "Exact Search", "Pattern Search" });
				cbType = new JComboBox();
				cbType.setModel(cbTypeModel);
			}
			{
				btnNext = new JButton();
				btnNext.setText("Next Scan");
				btnNext.setMargin(new java.awt.Insets(2, 2, 2, 2));
			}
			{
				jScrollPane1 = new JScrollPane();
				{
					TableModel tblResultsModel = 
						new DefaultTableModel(
								new String[][] { { "0x0001", "1" }, { "0x0002", "2" } },
								new String[] { "Address", "Value" });
					tblResults = new JTable();
					jScrollPane1.setViewportView(tblResults);
					tblResults.setModel(tblResultsModel);
				}
			}
			{
				btnAdd = new JButton();
				btnAdd.setText("<- Add");
				btnAdd.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						//TODO add value
						fireActionPerformed(Action.Add, null);
					}
				});
			}
			{
				lblValue = new JLabel();
				lblValue.setText("Value");
			}
			{
				jLabel1 = new JLabel();
				jLabel1.setText("Scan Tye");
			}
			{
				ComboBoxModel cbValueModel = 
					new DefaultComboBoxModel(
							new String[] { "2 Bytes", "4 Bytes" });
				cbValue = new JComboBox();
				cbValue.setModel(cbValueModel);
			}
			{
				jLabel2 = new JLabel();
				jLabel2.setText("Value Type");
			}
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(btnNext, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(btnFirst, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(txtSearch, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
				    .addComponent(lblValue, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(cbType, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(jLabel1, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(thisLayout.createParallelGroup()
				    .addComponent(cbValue, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				        .addGap(8)
				        .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(jScrollPane1, 0, 121, Short.MAX_VALUE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				.addContainerGap());
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(thisLayout.createParallelGroup()
				    .addGroup(thisLayout.createSequentialGroup()
				        .addGroup(thisLayout.createParallelGroup()
				            .addComponent(jLabel2, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
				            .addComponent(jLabel1, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
				            .addComponent(lblValue, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
				            .addComponent(btnFirst, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
				            .addComponent(btnAdd, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))
				        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				        .addGroup(thisLayout.createParallelGroup()
				            .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				                .addComponent(cbValue, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
				                .addGap(0, 37, Short.MAX_VALUE))
				            .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				                .addComponent(cbType, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
				                .addGap(0, 37, Short.MAX_VALUE))
				            .addComponent(txtSearch, GroupLayout.Alignment.LEADING, 0, 138, Short.MAX_VALUE)
				            .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				                .addComponent(btnNext, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
				                .addGap(0, 63, Short.MAX_VALUE))))
				    .addComponent(jScrollPane1, GroupLayout.Alignment.LEADING, 0, 223, Short.MAX_VALUE))
				.addContainerGap());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	///////////////////////////////////////////////////////////
	
	public void addListener(MemorySearchListener l) {
        listenerList.add(MemorySearchListener.class, l);
    }
    
    public void removeListener(MemorySearchListener l) {
	    listenerList.remove(MemorySearchListener.class, l);
    }
    
    public MemorySearchListener[] getListeners() {
        return (MemorySearchListener[])(listenerList.getListeners(MemorySearchListener.class));
    }
    
    protected void fireActionPerformed(Action action, Object o) {
        Object[] listeners = listenerList.getListenerList(); // Guaranteed to return a non-null array
        // Process the listeners last to first, notifying those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==MemorySearchListener.class) {
            	switch (action){
	            	case FirstSeach:
	            		((MemorySearchListener)listeners[i+1]).FirstSearchPerformed();
	            		break;
	            	case NextSearch:
	            		((MemorySearchListener)listeners[i+1]).NextSearchPerformed();
	            		break;
	            	case Add:
	            		//TODO table getSelected
	            		((MemorySearchListener)listeners[i+1]).AddPerformed(0L);
	            		break;
            	}
            }          
        }
    }

}
