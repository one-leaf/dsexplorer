package luz.eveMonitor.gui;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

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
public class StatusBar extends javax.swing.JPanel {
	private static final long serialVersionUID = 1505143385322711167L;
	private JLabel lbl;
	private JLabel lblTrans;
	private JLabel lbl2;
	private JLabel lbl1;
	private static Status status;
	
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
				lbl1 = new JLabel();
				this.add(lbl1);
				lbl1.setText("Process:");
				lbl1.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			}
			{
				lbl2 = new JLabel();
				this.add(lbl2);
				lbl2.setText("Dict:");
				lbl2.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			}
			{
				lbl = new JLabel();
				this.add(lbl);
				lbl.setText("Rows:");
				lbl.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			}
			{
				lblTrans = new JLabel();
				this.add(lblTrans);
				lblTrans.setText("Transactions:");
				lblTrans.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setStatus(Status status) {
		this.status=status;
		
	}

}
