package luz.eveMonitor.gui;

import java.awt.Dimension;
import java.text.NumberFormat;

import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import luz.eveMonitor.datastructure.other.Transaction;
import luz.eveMonitor.datastructure.other.TransactionSettings;

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
public class TransInfo extends javax.swing.JPanel {
	private static final long serialVersionUID = -4375884411896017055L;
	private NumberFormat nf= NumberFormat.getInstance();
	private NumberFormat pf= NumberFormat.getPercentInstance();
	private JLabel lbl1;
	private JLabel lblWin;
	private JLabel lbl2;
	private JLabel lblPercent;
	private JLabel lbl3;
	private JLabel lblType;
	private JLabel lbl4;
	private JLabel lblGroup;
	private JLabel lbl5;
	private JLabel lblVolume;
	private JLabel lbl6;
	private JLabel lblItems;
	private JLabel lbl7;
	private JLabel lblCargo;
	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new TransInfo());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public TransInfo() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			GroupLayout thisLayout = new GroupLayout((JComponent)this);
			this.setLayout(thisLayout);
			setPreferredSize(new Dimension(400, 300));
			{
				lbl1 = new JLabel();
				lbl1.setText("Win:");
			}
			{
				lblWin = new JLabel();
			}
			{
				lbl2 = new JLabel();
				lbl2.setText("Percent:");
			}
			{
				lblPercent = new JLabel();
			}
			{
				lbl3 = new JLabel();
				lbl3.setText("Type:");
			}
			{
				lblType = new JLabel();
			}
			{
				lbl4 = new JLabel();
				lbl4.setText("Group:");
			}
			{
				lblGroup = new JLabel();
			}	
			{
				lbl5 = new JLabel();
				lbl5.setText("Volume:");
			}
			{
				lblVolume = new JLabel();
			}
			{
				lbl6 = new JLabel();
				lbl6.setText("Items:");
			}
			{
				lblItems = new JLabel();
			}
			{
				lbl7 = new JLabel();
				lbl7.setText("Cargo:");
			}
			{
				lblCargo = new JLabel();
			}
			
				thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
					.addGroup(thisLayout.createParallelGroup()
					    .addComponent(lbl1      , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					    .addComponent(lblWin  , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addGroup(thisLayout.createParallelGroup()
					    .addComponent(lbl2      , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					    .addComponent(lblPercent , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addGroup(thisLayout.createParallelGroup()
					    .addComponent(lbl3      , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					    .addComponent(lblType, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addGroup(thisLayout.createParallelGroup()
					    .addComponent(lbl4      , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					    .addComponent(lblGroup , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addGroup(thisLayout.createParallelGroup()
					    .addComponent(lbl5      , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					    .addComponent(lblVolume , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addGroup(thisLayout.createParallelGroup()
					    .addComponent(lbl6      , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					    .addComponent(lblItems  , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addGroup(thisLayout.createParallelGroup()
					    .addComponent(lbl7      , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					    .addComponent(lblCargo  , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))

					    .addContainerGap());
				thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(thisLayout.createParallelGroup()
					    .addComponent(lbl1 , GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					    .addComponent(lbl2 , GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					    .addComponent(lbl3 , GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					    .addComponent(lbl4 , GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					    .addComponent(lbl5 , GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					    .addComponent(lbl6 , GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					    .addComponent(lbl7 , GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
						)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(thisLayout.createParallelGroup()
					    .addComponent(lblWin    , 0, 300, Short.MAX_VALUE)
					    .addComponent(lblPercent, 0, 300, Short.MAX_VALUE)
					    .addComponent(lblType   , 0, 300, Short.MAX_VALUE)
					    .addComponent(lblGroup  , 0, 300, Short.MAX_VALUE)
					    .addComponent(lblVolume , 0, 300, Short.MAX_VALUE)
					    .addComponent(lblItems  , 0, 300, Short.MAX_VALUE)
					    .addComponent(lblCargo  , 0, 300, Short.MAX_VALUE)
					));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void setTransaction(Transaction trans, TransactionSettings settings){
		if(trans!=null){


			lblWin    .setText(nf.format(trans.calcWin(settings)));
			lblPercent.setText(pf.format(trans.calcPercent(settings)));
			lblType   .setText(trans.buy.getType().getTypeName());
			lblGroup  .setText(trans.buy.getType().getGroupID().getGroupName());
			lblVolume .setText(nf.format(trans.buy.getType().getVolume()));
			lblItems  .setText(nf.format(trans.getMaxItemNumber(settings)));
			lblCargo  .setText(nf.format(trans.getMaxCargo(settings)));


		}else{
			lblWin  .setText("");
			lblPercent .setText("");
			lblType.setText("");
			lblGroup .setText("");
			lblVolume .setText("");
			lblItems  .setText("");

		}
		
		
	}

}
