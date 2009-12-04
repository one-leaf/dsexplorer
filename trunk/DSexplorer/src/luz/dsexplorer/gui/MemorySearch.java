package luz.dsexplorer.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import luz.dsexplorer.gui.listener.MemorySearchListener;
import luz.dsexplorer.objects.datastructure.DSType;
import luz.dsexplorer.winapi.ResultList;
import luz.dsexplorer.winapi.objects.Process;
import luz.dsexplorer.winapi.objects.Result;


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
public class MemorySearch extends javax.swing.JPanel {
	private static final long serialVersionUID = 2361607017186276542L;
	private JTextField txtSearch;
	private JComboBox cbType;
	private JLabel jLabel2;
	private JButton btnAdd;
	private JTextField txtTo;
	private JLabel lblTo;
	private JTextField txtFrom;
	private JLabel lblFrom;
	private JComboBox cbValue;
	private JLabel jLabel1;
	private JLabel lblValue;
	private JScrollPane jScrollPane1;
	private ResultTable tblResults;
	private JButton btnNext;
	private JButton btnFirst;
	private Process process;
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
				btnFirst.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						btnFirstActionPerformed();
					}
				});
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
				btnNext.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						btnNextActionPerformed();
					}
				});
			}
			{
				jScrollPane1 = new JScrollPane();
				{
					tblResults = new ResultTable();
					jScrollPane1.setViewportView(tblResults);
					tblResults.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							if (evt.getClickCount()>1)
								fireActionPerformed(Action.Add, null);
						}
					});
				}
			}
			{
				btnAdd = new JButton();
				btnAdd.setText("<- Add");
				btnAdd.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						fireActionPerformed(Action.Add, null);
					}
				});
			}
			{
				lblFrom = new JLabel();
				lblFrom.setText("From");
			}
			{
				txtFrom = new JTextField();
				txtFrom.setText("00400000");
			}
			{
				lblTo = new JLabel();
				lblTo.setText("To");
			}
			{
				txtTo = new JTextField();
				txtTo.setText("7FFF0000");	//7FFFFFFF
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
				
				ComboBoxModel cbValueModel = new DefaultComboBoxModel(DSType.values());
				cbValue = new JComboBox();
				cbValue.setModel(cbValueModel);
				cbValue.setSelectedIndex(2);
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
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(txtSearch, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(lblValue, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(cbType, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(jLabel1, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(cbValue, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(jLabel2, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(txtFrom, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(lblFrom, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(lblTo, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(txtTo, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(jScrollPane1, 0, 112, Short.MAX_VALUE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				.addContainerGap());
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(thisLayout.createParallelGroup()
				    .addComponent(jScrollPane1, GroupLayout.Alignment.LEADING, 0, 294, Short.MAX_VALUE)
				    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				        .addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
				        .addGap(0, 219, Short.MAX_VALUE))
				    .addGroup(thisLayout.createSequentialGroup()
				        .addGroup(thisLayout.createParallelGroup()
				            .addComponent(lblFrom, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
				            .addComponent(jLabel2, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
				            .addComponent(jLabel1, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
				            .addComponent(lblValue, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
				            .addComponent(btnFirst, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE))
				        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				        .addGroup(thisLayout.createParallelGroup()
				            .addComponent(txtSearch, GroupLayout.Alignment.LEADING, 0, 225, Short.MAX_VALUE)
				            .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				                .addGroup(thisLayout.createParallelGroup()
				                    .addComponent(txtFrom, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
				                    .addComponent(cbValue, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
				                    .addComponent(cbType, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
				                    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				                        .addComponent(btnNext, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
				                        .addGap(40)))
				                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				                .addComponent(lblTo, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
				                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				                .addComponent(txtTo, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
				                .addGap(0, 0, Short.MAX_VALUE)))))
				.addContainerGap());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void btnFirstActionPerformed(){
		try{
			int from=Integer.parseInt(txtFrom.getText(),16);
			int to  =Integer.parseInt(txtTo  .getText(),16);
			ResultList results=process.search(from, to, txtSearch.getText(), (DSType)cbValue.getSelectedItem());
			tblResults.setResults(results);
		}catch(NumberFormatException e){
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void btnNextActionPerformed(){
		
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
	            		try{
	            			((MemorySearchListener)listeners[i+1]).FirstSearchPerformed(Integer.parseInt(txtFrom.getText()), Integer.parseInt(txtTo.getText()));
	            		}catch(NumberFormatException e){}
	            		break;
	            	case NextSearch:
	            		((MemorySearchListener)listeners[i+1]).NextSearchPerformed();
	            		break;
	            	case Add:
	            		//TODO table getSelected
	            		((MemorySearchListener)listeners[i+1]).AddPerformed(tblResults.getSelectedResults());
	            		break;
            	}
            }          
        }
    }

	public void setProcess(Process process) {
		this.process=process;
	}


}
