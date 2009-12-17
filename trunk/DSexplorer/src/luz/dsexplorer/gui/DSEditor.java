package luz.dsexplorer.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import luz.dsexplorer.gui.listener.DSEditorListener;
import luz.dsexplorer.objects.datastructure.DSList;
import luz.dsexplorer.objects.datastructure.DSType;
import luz.dsexplorer.objects.datastructure.Datastructure;
import luz.dsexplorer.winapi.api.Result;


public class DSEditor extends javax.swing.JPanel {
	private static final long serialVersionUID = -6928391243482994782L;
	private JTextField txtValue;
	private JComboBox cbValue;
	private JLabel lblType;
	private JLabel lblValue;
	private JTextField txtAddress;
	private JLabel lblAddress;
	private JTextField txtSize;
	private JLabel lblSize;
	private JLabel lblName;
	private JButton btnAddDS;
	private JLabel lblDSselector;
	private JComboBox cbDSselector;
	private JSeparator jSeparator1;
	private JTextField txtName;
	private JButton btnAddField;
	private enum Action{AddField, AddressChanged, TypeChanged, SizeChanged, NameChanged, DSChanged, PointerChanged}
	private Result result;
	private DSList dsList = new DSList();
	private JTextField txtPointer;
	private JCheckBox chbPointer;
	private Font font = new Font("Lucida Console", Font.PLAIN, 11);

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
				txtValue.setFont(font);
			}
			{
				ComboBoxModel cbValueModel = new DefaultComboBoxModel(DSType.values());
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
				btnAddField = new JButton();
				btnAddField.setText("Add Field");
				btnAddField.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						btnAddFieldActionPerformed();
					}
				});
			}
			{
				txtPointer = new JTextField();
				txtPointer.setFont(font);
				txtPointer.setEditable(false);
			}
			{
				chbPointer = new JCheckBox();
				chbPointer.setText("is Pointer");
				chbPointer.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						chbPointerActionPerformed();
					}
				});
			}
			{
				cbDSselector = new JComboBox();
				cbDSselector.setModel(dsList);
				cbDSselector.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						cbDSselectorActionPerformed();
					}
				});
			}
			{
				lblDSselector = new JLabel();
				lblDSselector.setText("Datastructure");
			}
			{
				btnAddDS = new JButton();
				btnAddDS.setText("Add new");
				btnAddDS.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						btnAddDSActionPerformed();
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
				txtAddress.setFont(font);
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
				    .addComponent(txtName, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
				    .addComponent(lblName, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(txtAddress, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
				    .addComponent(lblAddress, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(chbPointer, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
				    .addComponent(txtPointer, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
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
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(cbDSselector, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(lblDSselector, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(btnAddDS, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(btnAddField, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(57, Short.MAX_VALUE));
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(thisLayout.createParallelGroup()
				    .addGroup(thisLayout.createSequentialGroup()
				        .addGroup(thisLayout.createParallelGroup()
				            .addComponent(lblDSselector, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
				            .addComponent(lblSize, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
				            .addComponent(lblType, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
				            .addComponent(lblValue, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
				            .addComponent(chbPointer, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
				            .addComponent(lblAddress, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
				            .addComponent(lblName, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))
				        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				        .addGroup(thisLayout.createParallelGroup()
				            .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				                .addGroup(thisLayout.createParallelGroup()
				                    .addComponent(cbDSselector, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
				                    .addComponent(txtSize, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
				                    .addComponent(cbValue, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
				                    .addComponent(txtPointer, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
				                    .addComponent(txtAddress, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
				                    .addComponent(txtName, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
				                    .addComponent(btnAddField, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
				                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				                .addComponent(btnAddDS, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
				                .addGap(0, 87, Short.MAX_VALUE))
				            .addComponent(txtValue, GroupLayout.Alignment.LEADING, 0, 295, Short.MAX_VALUE)))
				    .addComponent(jSeparator1, GroupLayout.Alignment.LEADING, 0, 380, Short.MAX_VALUE))
				.addContainerGap());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//$hide>>$
	public void setResult(Result result) {
		this.result=result;
		txtAddress.setText(result.getAddressString());
		txtAddress.setEnabled(!result.isRelative());
		cbValue.setSelectedItem(result.getType());
		
		boolean custom=result.isCustom();
		if (custom) cbDSselector.setSelectedItem(result.getDatastructure());
		if (custom) txtPointer.setText(result.getPointerString());
		
		txtSize.setText(""+result.getByteCount());
		txtSize.setEditable(!result.getType().isFixedSize());
		txtName.setText(result.getName());
		txtValue.setText(result.getValueString());
		txtValue   .setEditable(!custom);
		chbPointer   .setSelected(result.getIsPointer());
		chbPointer   .setVisible(custom);
		lblDSselector.setVisible(custom);
		cbDSselector .setVisible(custom);
		btnAddField  .setVisible(custom);
		btnAddDS     .setVisible(custom);
		chbPointer   .setVisible(custom);		
		txtPointer   .setVisible(custom);
	}
	
	public void setDataStructures(DSList datastructures) {
		this.dsList=datastructures;
		cbDSselector.setModel(dsList);		
	}
	
	private void txtAddressActionPerformed() {
		try{
			result.setAddress(Long.parseLong(txtAddress.getText(),16));
			txtValue.setText(result.getValueString());
			fireActionPerformed(Action.AddressChanged, result);
		}catch(NumberFormatException e){};		
	}
	
	private void cbValueActionPerformed() {
		DSType newType=(DSType)cbValue.getSelectedItem();
		if (!newType.equals(result.getType())){	//Avoid unecessairy changes
			result.setType(newType, (Datastructure)cbDSselector.getItemAt(0));
			boolean custom=result.isCustom();
			fireActionPerformed(Action.TypeChanged, result);

			txtSize.setText(""+result.getByteCount());
			txtSize.setEditable(!result.getType().isFixedSize());
			txtName.setText(result.getName());
			txtValue.setText(result.getValueString());
			txtValue   .setEditable(!custom);
			chbPointer   .setSelected(result.getIsPointer());
			chbPointer   .setVisible(custom);
			lblDSselector.setVisible(custom);
			cbDSselector .setVisible(custom);
			btnAddField  .setVisible(custom);
			btnAddDS     .setVisible(custom);
			txtPointer   .setVisible(custom);
		}
	}
	
	private void cbDSselectorActionPerformed(){
		Datastructure ds = (Datastructure)cbDSselector.getSelectedItem();
		if (result.getDatastructure()!=ds){	//Avoid unecessairy changes
			result.setDatastructure(ds);
			
			txtName.setText(result.getName());
			fireActionPerformed(Action.DSChanged, result);
		}
	}
	
	private void txtSizeActionPerformed() {
		try{
			result.setByteCount(Long.parseLong(txtSize.getText()));
			txtValue.setText(result.getValueString());
			fireActionPerformed(Action.SizeChanged, result);
		}catch(NumberFormatException e){};	
	}
	
	private void txtNameActionPerformed() {
		result.setName(txtName.getText());
		fireActionPerformed(Action.NameChanged, result);
	}
	
	private void btnAddFieldActionPerformed() {
		result.getDatastructure().addElement(DSType.Byte4);
		fireActionPerformed(Action.AddField, result);		
	}
	
	private void btnAddDSActionPerformed(){
		dsList.addElement(new Datastructure("new custom"));
	}	
	
	private void chbPointerActionPerformed() {
		boolean isPointer=chbPointer.isSelected();
		if (result.getIsPointer()!=isPointer){	//Avoid unecessairy changes	
			result.setIsPointer(chbPointer.isSelected());
			txtPointer.setText(isPointer?result.getPointerString():null);
			fireActionPerformed(Action.PointerChanged, result);
		}
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
	            	case AddField:
	            		((DSEditorListener)listeners[i+1]).AddFieldPerformed((Result)o);
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
	            	case DSChanged:
	            		((DSEditorListener)listeners[i+1]).DSChanged((Result)o);
	            		break;
	            	case PointerChanged:
	            		((DSEditorListener)listeners[i+1]).PointerChanged((Result)o);
	            		break;
            	}
            }          
        }
    }


    
    //TODO Datastructure selector

  //$hide<<$
}
