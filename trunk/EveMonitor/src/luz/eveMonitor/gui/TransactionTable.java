package luz.eveMonitor.gui;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;

import luz.eveMonitor.datastructure.other.Transaction;
import luz.eveMonitor.datastructure.other.TransactionSet;


public class TransactionTable extends JTable{
	private static final long serialVersionUID = -5848750370811800958L;


	
	public TransactionTable(){
		super();

//		this.setAutoCreateRowSorter(true);

		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


	}
	
	@Override
	public void setModel(TableModel paramTableModel) {
		super.setModel(paramTableModel);

		if(paramTableModel instanceof TransactionSet){
			int row=0;
			this.getColumnModel().getColumn(row++).setPreferredWidth(50);	//type
			this.getColumnModel().getColumn(row++).setPreferredWidth(30);	//Price
			this.getColumnModel().getColumn(row++).setPreferredWidth(30);	//VolRem
			this.getColumnModel().getColumn(row++).setPreferredWidth(200);	//Station
			
			this.getColumnModel().getColumn(row++).setPreferredWidth(30);	//items
			this.getColumnModel().getColumn(row++).setPreferredWidth(50);	//Win
			
			this.getColumnModel().getColumn(row++).setPreferredWidth(30);	//Price
			this.getColumnModel().getColumn(row++).setPreferredWidth(30);	//VolRem
			this.getColumnModel().getColumn(row++).setPreferredWidth(200);	//Station
		}
	}

	public Transaction getTransaction(int row) {
		return ((TransactionSet)getModel()).getTransaction(row);
		
	}
	
}
