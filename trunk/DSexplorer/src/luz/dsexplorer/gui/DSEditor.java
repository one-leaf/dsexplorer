package luz.dsexplorer.gui;

import java.awt.Color;
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
import org.fife.ui.hex.swing.HexEditor;

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
	private HexEditor hexEditor;
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
			setPreferredSize(new Dimension(532, 440));
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
				hexEditor = new HexEditor();
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
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				    .addComponent(txtName,       GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
				    .addComponent(lblName,       GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				    .addComponent(chbPointer,    GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
				    .addComponent(txtPointer,    GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
				    .addComponent(txtAddress,    GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
				    .addComponent(lblAddress,    GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				    .addComponent(cbDSselector,  GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
				    .addComponent(lblDSselector, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
				    .addComponent(btnAddDS,      GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
				    .addComponent(cbValue,       GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
				    .addComponent(lblType,       GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				    .addComponent(txtSize,       GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
				    .addComponent(lblSize,       GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
				    .addComponent(btnAddField,   GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				    .addComponent(txtValue,      GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
				    .addComponent(lblValue,      GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(jSeparator1,       GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(hexEditor, 0,      GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
				.addContainerGap());
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(thisLayout.createParallelGroup()
				    .addGroup(thisLayout.createSequentialGroup()
				        .addGroup(thisLayout.createParallelGroup()
				        	.addComponent(lblName,              GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
				            .addComponent(lblAddress,           GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
				            .addComponent(lblType,              GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
				            .addComponent(lblSize,              GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
				            .addComponent(lblValue,             GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))
				        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				        .addGroup(thisLayout.createParallelGroup()
				            .addComponent(txtValue,             GroupLayout.Alignment.LEADING, 0, 431, Short.MAX_VALUE)
				            .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				                .addGroup(thisLayout.createParallelGroup()
				                	.addComponent(txtName,       GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
				                	.addComponent(txtAddress,    GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
				                	.addComponent(cbValue,       GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
				                	.addComponent(txtSize,       GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
				                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				                .addGroup(thisLayout.createParallelGroup()
				                    .addComponent(lblDSselector, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
				                    .addComponent(chbPointer,    GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))
				                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				                .addGroup(thisLayout.createParallelGroup()
				                    .addComponent(btnAddField,   GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
				                    .addComponent(cbDSselector,  GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
				                    .addComponent(txtPointer,    GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
				                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				                .addComponent(btnAddDS,          GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))))
				    .addComponent(jSeparator1,                   GroupLayout.Alignment.LEADING, 0, 510, Short.MAX_VALUE)
				    .addComponent(hexEditor,                     GroupLayout.Alignment.LEADING, 0, 510, Short.MAX_VALUE))
				.addContainerGap());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//$hide>>$
	public void setResult(Result result) {
		this.result=result;
		Datastructure ds = result.getDatastructure();
		
		Long address=result.getAddress();
		if (address!=null){
			hexEditor.open(result.getMemoryBytes(-512, 512), result.getAddress()-512);
			hexEditor.setHighlightHexPos(512);
			hexEditor.setHighlightHexSize(result.getDatastructure().getByteCount());
		}
		
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
			long address = Long.parseLong(txtAddress.getText(),16);
			result.setAddress(address);
			txtValue.setText(result.getValueString());

			hexEditor.open(result.getMemoryBytes(-512, 512), address-512);
			hexEditor.setHighlightHexPos(512);
			hexEditor.setHighlightHexSize(result.getDatastructure().getByteCount());
		
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

			hexEditor.setHighlightHexSize(ds.getByteCount());
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
			
			hexEditor.setHighlightHexSize(ds.getByteCount());
			txtSize.setText(""+ds.getByteCount());
			txtName.setText(ds.getName());
			boolean container=ds.isContainer();
			if (container){
				Container c = (Container)ds;				
				chbPointer.setSelected(c.isPointer());
			}
		}
	}
	
	private void txtSizeActionPerformed() {
		try{
			result.getDatastructure().setByteCount(Integer.parseInt(txtSize.getText()));
			txtValue.setText(result.getValueString());
			hexEditor.setHighlightHexSize(result.getDatastructure().getByteCount());
		}catch(NumberFormatException e){};	
	}
	
	private void txtNameActionPerformed() {
		result.getDatastructure().setName(txtName.getText());
	}
	
	private void btnAddFieldActionPerformed() {
		Datastructure ds = result.getDatastructure();
		((Container)ds).addField(new Byte4());
		txtSize.setText(""+ds.getByteCount());
		hexEditor.setHighlightHexSize(result.getDatastructure().getByteCount());
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
			hexEditor.setHighlightHexSize(result.getDatastructure().getByteCount());
		}
	}

  //$hide<<$
}
