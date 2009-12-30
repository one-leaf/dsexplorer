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

import luz.dsexplorer.datastructures.Container;
import luz.dsexplorer.datastructures.ContainerImpl;
import luz.dsexplorer.datastructures.DSList;
import luz.dsexplorer.datastructures.DSType;
import luz.dsexplorer.datastructures.Datastructure;
import luz.dsexplorer.datastructures.simple.Byte4;
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
	private Result result;
	private DSList dsList;
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
		Datastructure ds = result.getDatastructure();
		
		
		txtAddress.setText(result.getAddressString());
		txtAddress.setEditable(result.isSimpleResult());
		cbValue.setSelectedItem(ds.getType());
		

		boolean container=ds.isContainer();
		if (container){
			Container c = (Container)ds;
			cbDSselector.setSelectedItem(ds);
			txtPointer.setText(result.getPointerString());
			chbPointer.setSelected(c.isPointer());
		}

		txtSize.setText(""+ds.getByteCount());
		txtSize.setEditable(!ds.isByteCountFix());
		txtName.setText(ds.getName());
		txtValue.setText(result.getValueString());
		txtValue   .setEditable(!container);

		chbPointer   .setVisible(container);
		lblDSselector.setVisible(container);
		cbDSselector .setVisible(container);
		btnAddField  .setVisible(container);
		btnAddDS     .setVisible(container);
		chbPointer   .setVisible(container);		
		txtPointer   .setVisible(container);
	}
	
	public void setDataStructures(DSList dsList) {
		this.dsList=dsList;
		cbDSselector.setModel(this.dsList);		
	}
	
	private void txtAddressActionPerformed() {
		try{
			result.setAddress(Long.parseLong(txtAddress.getText(),16));
			txtValue.setText(result.getValueString());
		}catch(NumberFormatException e){};		
	}
	
	private void cbValueActionPerformed() {
		DSType newType=(DSType)cbValue.getSelectedItem();
		Datastructure ds = result.getDatastructure();
		
		if (!newType.equals(ds.getType())){	//Avoid unecessairy changes
			
			if (newType.equals(DSType.Container)){
				Datastructure item=(Datastructure)cbDSselector.getItemAt(0);
				if (item==null)
					dsList.addElement(new ContainerImpl());
				
				result.setDatastructure(item);
				cbDSselector.setSelectedItem(item);
			}else{
				result.setDatastructure(newType.getInstance());
			}
			
			ds = result.getDatastructure();
			boolean container=ds.isContainer();
			if (container){
				Container c = (Container)ds;				
				chbPointer.setSelected(c.isPointer());
			}


			txtSize.setText(""+ds.getByteCount());
			txtSize.setEditable(!ds.isByteCountFix());
			txtName.setText(ds.getName());
			txtValue.setText(result.getValueString());
			txtValue   .setEditable(!container);
			chbPointer   .setVisible(container);
			lblDSselector.setVisible(container);
			cbDSselector .setVisible(container);
			btnAddField  .setVisible(container);
			btnAddDS     .setVisible(container);
			txtPointer   .setVisible(container);
		}
	}
	
	private void cbDSselectorActionPerformed(){
		Datastructure ds = (Datastructure)cbDSselector.getSelectedItem();
		if (result.getDatastructure()!=ds){	//Avoid unecessairy changes
			result.setDatastructure(ds);
			
			txtName.setText(result.getDatastructure().getName());
		}
	}
	
	private void txtSizeActionPerformed() {
		try{
			result.getDatastructure().setByteCount(Integer.parseInt(txtSize.getText()));
			txtValue.setText(result.getValueString());
		}catch(NumberFormatException e){};	
	}
	
	private void txtNameActionPerformed() {
		result.getDatastructure().setName(txtName.getText());
	}
	
	private void btnAddFieldActionPerformed() {
		Datastructure ds = result.getDatastructure();
		((Container)ds).addField(new Byte4());
		txtSize.setText(""+ds.getByteCount());

	}
	
	private void btnAddDSActionPerformed(){
		dsList.addElement(new ContainerImpl());
	}	
	
	private void chbPointerActionPerformed() {
		boolean isPointer=chbPointer.isSelected();
		Container c = (Container)result.getDatastructure();
		if (c.isPointer()!=isPointer){	//Avoid unecessairy changes	
			c.setPointer(chbPointer.isSelected());
			txtPointer.setText(isPointer?result.getPointerString():null);
			txtSize.setText(""+c.getByteCount());
		}
	}

  //$hide<<$
}
