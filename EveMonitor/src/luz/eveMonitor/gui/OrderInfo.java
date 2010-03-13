package luz.eveMonitor.gui;

import java.awt.Dimension;
import java.util.Calendar;

import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import luz.eveMonitor.entities.eveMon.Order;

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
public class OrderInfo extends javax.swing.JPanel {
	private static final long serialVersionUID = -4375884411896017055L;
	private JLabel lbl1;
	private JLabel lblPrice;
	private JLabel lbl2;
	private JLabel lblVolRem;
	private JLabel lbl3;
	private JLabel lblStation;
	private JLabel lbl4;
	private JLabel lblSystem;
	private JLabel lbl5;
	private JLabel lblRegion;
	private JLabel lbl6;
	private JLabel lblJumps;
	private JLabel lbl7;
	private JLabel lblRange;
	private JLabel lbl8;
	private JLabel lblExpires;
	private JLabel lbl9;
	private JLabel lblType;
	private JLabel lbl10;
	private JLabel lblGroup;
//	private JLabel lbl11;
//	private JLabel lblMGroup;
	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new OrderInfo());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public OrderInfo() {
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
				lbl1.setText("Price:");
			}
			{
				lblPrice = new JLabel();
			}
			{
				lbl2 = new JLabel();
				lbl2.setText("Quantity:");
			}
			{
				lblVolRem = new JLabel();
			}
			{
				lbl3 = new JLabel();
				lbl3.setText("Station:");
			}
			{
				lblStation = new JLabel();
			}
			{
				lbl4 = new JLabel();
				lbl4.setText("System:");
			}
			{
				lblSystem = new JLabel();
			}	
			{
				lbl5 = new JLabel();
				lbl5.setText("Region:");
			}
			{
				lblRegion = new JLabel();
			}
			{
				lbl6 = new JLabel();
				lbl6.setText("Jumps:");
			}
			{
				lblJumps = new JLabel();
			}
			{
				lbl7 = new JLabel();
				lbl7.setText("Range:");
			}
			{
				lblRange = new JLabel();
			}			
			{
				lbl8 = new JLabel();
				lbl8.setText("Expires in:");
			}
			{
				lblExpires = new JLabel();
			}
			{
				lbl9 = new JLabel();
				lbl9.setText("Type:");
			}
			{
				lblType = new JLabel();
			}
			{
				lbl10 = new JLabel();
				lbl10.setText("Group:");
			}
			{
				lblGroup = new JLabel();
			}
//			{
//				lbl11 = new JLabel();
//				lbl11.setText("MGroup:");
//			}
//			{
//				lblMGroup = new JLabel();
//			}


				thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
					.addGroup(thisLayout.createParallelGroup()
					    .addComponent(lbl1      , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					    .addComponent(lblPrice  , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addGroup(thisLayout.createParallelGroup()
					    .addComponent(lbl2      , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					    .addComponent(lblVolRem , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addGroup(thisLayout.createParallelGroup()
					    .addComponent(lbl3      , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					    .addComponent(lblStation, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addGroup(thisLayout.createParallelGroup()
					    .addComponent(lbl4      , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					    .addComponent(lblSystem , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addGroup(thisLayout.createParallelGroup()
					    .addComponent(lbl5      , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					    .addComponent(lblRegion , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addGroup(thisLayout.createParallelGroup()
					    .addComponent(lbl6      , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					    .addComponent(lblJumps  , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addGroup(thisLayout.createParallelGroup()
					    .addComponent(lbl7      , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					    .addComponent(lblRange  , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addGroup(thisLayout.createParallelGroup()
					    .addComponent(lbl8      , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					    .addComponent(lblExpires, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addGroup(thisLayout.createParallelGroup()
					    .addComponent(lbl9      , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					    .addComponent(lblType   , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addGroup(thisLayout.createParallelGroup()
					    .addComponent(lbl10     , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					    .addComponent(lblGroup  , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
//					.addGroup(thisLayout.createParallelGroup()
//					    .addComponent(lbl11     , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
//					    .addComponent(lblMGroup  , GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))

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
					    .addComponent(lbl8 , GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					    .addComponent(lbl9 , GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					    .addComponent(lbl10, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
//					    .addComponent(lbl11, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					    )
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(thisLayout.createParallelGroup()
					    .addComponent(lblPrice  , 0, 300, Short.MAX_VALUE)
					    .addComponent(lblVolRem , 0, 300, Short.MAX_VALUE)
					    .addComponent(lblStation, 0, 300, Short.MAX_VALUE)
					    .addComponent(lblSystem , 0, 300, Short.MAX_VALUE)
					    .addComponent(lblRegion , 0, 300, Short.MAX_VALUE)
					    .addComponent(lblJumps  , 0, 300, Short.MAX_VALUE)
					    .addComponent(lblRange  , 0, 300, Short.MAX_VALUE)
					    .addComponent(lblExpires, 0, 300, Short.MAX_VALUE)
					    .addComponent(lblType   , 0, 300, Short.MAX_VALUE)
					    .addComponent(lblGroup  , 0, 300, Short.MAX_VALUE)
//					    .addComponent(lblMGroup , 0, 300, Short.MAX_VALUE)
					));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void setOrder(Order order){
		if(order!=null){
			lblPrice  .setText(""+order.getPrice());
			lblVolRem .setText(""+order.getVolRem());
			lblStation.setText(""+order.getStation().getStationName());
			lblSystem .setText(""+order.getSystem().getSolarSystemName());
			lblRegion .setText(""+order.getRegion().getRegionName());			
			lblJumps  .setText(""+order.getJumps());
			
			String rangeT;
			short range=order.getRange();
			switch (range) {
				case -1:	rangeT= "Station"; 		break;
				case 0:		rangeT= "Solar System";	break;
				case 32767:	rangeT= "Region";		break;
				default:	rangeT= range+" Jumps";	break;
			}
			lblRange  .setText(rangeT);
			
			Calendar c = Calendar.getInstance();
			c.setTime(order.getDuration());
			String expires=
				c.get(Calendar.DAY_OF_YEAR)+"D "+
				c.get(Calendar.HOUR_OF_DAY)+"H "+
				c.get(Calendar.MINUTE)     +"M "+
				c.get(Calendar.SECOND)     +"S ";
			lblExpires.setText(expires);
			lblType   .setText(order.getType().getTypeName());
			lblGroup  .setText(order.getType().getGroupID().getGroupName());
//			lblGroup  .setText(order.getType().getMarketGroupID().getMarketGroupName());
		}else{
			lblPrice  .setText("");
			lblVolRem .setText("");
			lblStation.setText("");
			lblSystem .setText("");
			lblRegion .setText("");
			lblJumps  .setText("");
			lblRange  .setText("");
			lblExpires.setText("");
			lblType   .setText("");
			lblGroup  .setText("");
//			lblMGroup .setText("");
		}
		
		
	}

}
