package luz.dsexplorer.gui;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import luz.dsexplorer.tools.Kernel32Tools;
import luz.dsexplorer.tools.Process;


public class ProcessTable extends JTable{
	private static final long serialVersionUID = -5848750370811800958L;
	private final MyTableModel model;
	
	public ProcessTable(){
		super();
		model=new MyTableModel();

		this.setAutoCreateRowSorter(true);

		this.setModel(model);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		this.getColumnModel().getColumn(0).setMinWidth(50);
		this.getColumnModel().getColumn(0).setMaxWidth(50);
	}
	
	public void refresh() {
		model.refresh();		
	}
	
	public Process getSelectedProcess(){
		int row = this.getSelectedRow();
		if (row!=-1)
			return model.getProcessAt(row);
		else
			return null;
	}

	private static class MyTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 7780019195084742274L;
		private String[] columnNames = {"Pid", "Name", "Path"};
		private Kernel32Tools k32 = Kernel32Tools.getInstance(); 
		private List<Process> list = new LinkedList<Process>();
		
	    public MyTableModel(){

	    }
	    
	    public void refresh() {
	    	try {
				list=k32.getProcessList();
				fireTableDataChanged();
			} catch (Exception e) { e.printStackTrace();
			}
			
		}

		@Override
	    public int getColumnCount() {
	        return columnNames.length;
	    }
	    
	    @Override
	    public int getRowCount() {
	        return list.size();
	    }
	    
	    @Override
	    public String getColumnName(int col) {
	        return columnNames[col];
	    }
	    
	    @Override
	    public Object getValueAt(int row, int col) {
	    	switch (col){
	    		case 0:
	    			return list.get(row).getPid();
	    		case 1: 
	    			return list.get(row).getSzExeFile();
	    		case 2: 
	    			return list.get(row).getProcessImageFileName();
	    		default: return null;
	    	}
	    }
	    	    
	    public Process getProcessAt(int row) {
	    	return list.get(row);
	    }
	    
		@Override
	    @SuppressWarnings("unchecked")
	    public Class getColumnClass(int c) {
	    	Object r = getValueAt(0, c);
	        return r!=null?r.getClass():Object.class;
	    }
	}
}
