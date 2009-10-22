package luz.dsexplorer.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;

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
public class ProcessDialog extends javax.swing.JDialog {
	private static final long serialVersionUID = -9170956341779668601L;
	private JButton btnOpen;
	private JButton btnCancel;
	private JScrollPane jScrollPane1;
	private JButton btnRefresh;
	private ProcessTable tblProcess;

	/**
	* Auto-generated main method to display this JDialog
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				ProcessDialog inst = new ProcessDialog(frame);
				inst.setVisible(true);
			}
		});
	}
	
	public ProcessDialog(JFrame frame) {
		super(frame);
		initGUI();
	}
	
	private void initGUI() {
		try {
			GroupLayout thisLayout = new GroupLayout((JComponent)getContentPane());
			getContentPane().setLayout(thisLayout);
			this.setTitle("Open Process");
			this.setModal(true);
			this.setMinimumSize(new Dimension(250, 300));
			{
				btnOpen = new JButton();
				btnOpen.setText("Open");
				btnOpen.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						openSelectedProcess();
					}
				});
			}
			{
				btnCancel = new JButton();
				btnCancel.setText("Cancel");
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						ProcessDialog.this.dispose();
					}
				});
			}
			{
				jScrollPane1 = new JScrollPane();
				{
					tblProcess = new ProcessTable();
					jScrollPane1.setViewportView(tblProcess);
					tblProcess.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							tblProcessMouseClicked(evt);
						}
					});
				}
			}
			{
				btnRefresh = new JButton();
				btnRefresh.setText("Refresh");
				btnRefresh.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						refresh();
					}
				});
			}
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addContainerGap()
				.addComponent(jScrollPane1, 0, 193, Short.MAX_VALUE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(btnRefresh, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(btnOpen, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(btnCancel, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addContainerGap());
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(thisLayout.createParallelGroup()
				    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				        .addComponent(btnOpen, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
				        .addGap(0, 122, Short.MAX_VALUE)
				        .addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))
				    .addComponent(jScrollPane1, GroupLayout.Alignment.LEADING, 0, 272, Short.MAX_VALUE)
				    .addComponent(btnRefresh, GroupLayout.Alignment.LEADING, 0, 272, Short.MAX_VALUE))
				.addContainerGap());
			pack();
			this.setSize(350, 400);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void openSelectedProcess() {
		System.out.println("Open Process "+tblProcess.getSelectedProcess());
		//TODO send Event to main window		
		this.dispose();
	}

	public void refresh() {
		tblProcess.refresh();
	}
	
	private void tblProcessMouseClicked(MouseEvent evt) {
		if (evt.getClickCount()>=2)
			openSelectedProcess();
	}

}
