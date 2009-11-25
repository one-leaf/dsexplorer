package luz.dsexplorer.gui;

import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import luz.dsexplorer.gui.listener.DSEditorListener;
import luz.dsexplorer.objects.Result;
import luz.dsexplorer.objects.Result.Type;


public class DSEditor extends javax.swing.JPanel {
	private static final long serialVersionUID = -6928391243482994782L;
	private JTextField txtValue;
	private JComboBox cbValue;
	private JLabel lblType;
	private JLabel lblValue;
	private JTextField txtAddress;
	private JLabel lblAddress;
	private Result result;
	private JTextField txtSize;
	private JLabel lblSize;
	private JLabel lblName;
	private JSeparator jSeparator1;
	private JTextField txtName;
	private JButton btnAddChild;
	private enum Action{AddChild, AddressChanged, TypeChanged, SizeChanged, NameChanged}
	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new DSEditor());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public DSEditor() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			GroupLayout thisLayout = new GroupLayout((JComponent)this);
			this.setLayout(thisLayout);
			setPreferredSize(new Dimension(400, 300));
			{
				txtValue = new JTextField();
				txtValue.setFont(new Font("Lucida Console", Font.PLAIN, 11));
			}
			{
				ComboBoxModel cbValueModel = new DefaultComboBoxModel(Type.values());
				cbValue = new JComboBox();
				cbValue.setModel(cbValueModel);
				cbValue.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						cbValueActionPerformed();
					}
				});
			}
			{
				lblValue = new JLabel();
				lblValue.setText("Value");
			}
			{
				lblAddress = new JLabel();
				lblAddress.setText("Address");
			}
			{
				btnAddChild = new JButton();
				btnAddChild.setText("Add Field");
				btnAddChild.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						btnAddChildActionPerformed();
					}
				});
			}
			{
				jSeparator1 = new JSeparator();
			}
			{
				txtName = new JTextField();
				txtName.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						txtNameActionPerformed();
					}
				});
			}
			{
				lblSize = new JLabel();
				lblSize.setText("Size");
			}
			{
				txtSize = new JTextField();
				txtSize.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						txtSizeActionPerformed();
					}
				});
			}
			{
				txtAddress = new JTextField();
				txtAddress.setFont(new Font("Lucida Console", Font.PLAIN, 11));
				txtAddress.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						txtAddressActionPerformed();
					}
				});
			}
			{
				lblName = new JLabel();
				lblName.setText("Name");
			}
			{
				lblType = new JLabel();
				lblType.setText("Value Type");
			}
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(txtAddress, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
				    .addComponent(lblAddress, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(txtValue, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
				    .addComponent(lblValue, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(cbValue, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(lblType, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(txtSize, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
				    .addComponent(lblSize, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 0, GroupLayout.PREFERRED_SIZE)
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(txtName, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
				    .addComponent(lblName, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(btnAddChild, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(114, Short.MAX_VALUE));
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(thisLayout.createParallelGroup()
				    .addGroup(thisLayout.createSequentialGroup()
				        .addGroup(thisLayout.createParallelGroup()
				            .addComponent(lblName, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
				            .addComponent(lblSize, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
				            .addComponent(lblType, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
				            .addComponent(lblValue, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
				            .addComponent(lblAddress, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))
				        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				        .addGroup(thisLayout.createParallelGroup()
				            .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				                .addComponent(txtName, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
				                .addGap(0, 194, Short.MAX_VALUE))
				            .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				                .addComponent(txtSize, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
				                .addGap(0, 194, Short.MAX_VALUE))
				            .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				                .addComponent(cbValue, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
				                .addGap(0, 194, Short.MAX_VALUE))
				            .addComponent(txtValue, GroupLayout.Alignment.LEADING, 0, 295, Short.MAX_VALUE)
				            .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				                .addComponent(txtAddress, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
				                .addGap(0, 194, Short.MAX_VALUE))
				            .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				                .addComponent(btnAddChild, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
				                .addGap(0, 194, Short.MAX_VALUE))))
				    .addComponent(jSeparator1, GroupLayout.Alignment.LEADING, 0, 380, Short.MAX_VALUE))
				.addContainerGap());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void setResult(Result result) {
		this.result=result;
		
		txtValue.setText(result.getValueString());
		txtAddress.setText(result.getPointerString());
		txtAddress.setEnabled(!result.isRelative());
		cbValue.setSelectedItem(result.getType());
		txtSize.setText(""+result.getSize());
		txtSize.setEnabled(!result.getType().isFixedSize());
		txtName.setText(result.getName());
		txtName.setVisible(result.isCustom());
		lblName.setVisible(result.isCustom());
		btnAddChild.setVisible(result.isCustom());
	}
	
	private void txtAddressActionPerformed() {
		try{
			result.setPointer(Long.parseLong(txtAddress.getText(),16));
			txtValue.setText(result.getValueString());
			fireActionPerformed(Action.AddressChanged, result);
		}catch(NumberFormatException e){};		
	}
	
	private void cbValueActionPerformed() {
		Type newType=(Type)cbValue.getSelectedItem();
		if (!newType.equals(result.getType())){	//Avoid unecessairy changes
			result.setType(newType);
			fireActionPerformed(Action.TypeChanged, result);
			
			txtValue.setText(result.getValueString());
			txtSize.setText(""+result.getSize());
			txtSize.setEnabled(!result.getType().isFixedSize());
			txtName.setText(result.getName());
			txtName.setVisible(result.isCustom());
			lblName.setVisible(result.isCustom());
			btnAddChild.setVisible(result.isCustom());
		}
	}
	
	private void txtSizeActionPerformed() {
		try{
			result.setSize(Integer.parseInt(txtSize.getText()));
			txtValue.setText(result.getValueString());
			fireActionPerformed(Action.SizeChanged, result);
		}catch(NumberFormatException e){};	
	}
	
	private void txtNameActionPerformed() {
		result.setName(txtName.getText());
		fireActionPerformed(Action.NameChanged, result);
	}
	
	private void btnAddChildActionPerformed() {
		Result r = result.addCustomEntry(Type.Byte4);
		fireActionPerformed(Action.AddChild, r);		
	}
	
	///////////////////////////////////////////////////////////
	
	public void addListener(DSEditorListener l) {
        listenerList.add(DSEditorListener.class, l);
    }
    
    public void removeListener(DSEditorListener l) {
	    listenerList.remove(DSEditorListener.class, l);
    }
    
    public DSEditorListener[] getListeners() {
        return (DSEditorListener[])(listenerList.getListeners(DSEditorListener.class));
    }
    
    protected void fireActionPerformed(Action action, Object o) {
        Object[] listeners = listenerList.getListenerList(); // Guaranteed to return a non-null array
        // Process the listeners last to first, notifying those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==DSEditorListener.class) {
            	switch (action){
	            	case AddChild:
	            		((DSEditorListener)listeners[i+1]).AddChildPerformed((Result)o);
	            		break;
	            	case AddressChanged:
	            		((DSEditorListener)listeners[i+1]).AddessChanged((Result)o);
	            		break;
	            	case TypeChanged:
	            		((DSEditorListener)listeners[i+1]).TypeChanged((Result)o);
	            		break;
	            	case SizeChanged:
	            		((DSEditorListener)listeners[i+1]).SizeChanged((Result)o);
	            		break;
	            	case NameChanged:
	            		((DSEditorListener)listeners[i+1]).NameChanged((Result)o);
	            		break;
            	}
            }          
        }
    }
    
    //TODO Datastructure selector


}
