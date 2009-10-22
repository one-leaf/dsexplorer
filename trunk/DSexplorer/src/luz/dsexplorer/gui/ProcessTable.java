package luz.dsexplorer.gui;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import luz.dsexplorer.tools.Process;
import luz.dsexplorer.tools.ProcessList;


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
		this.getColumnModel().getColumn(1).setMinWidth(40);
		this.getColumnModel().getColumn(1).setMaxWidth(40);
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
		private String[] columnNames = {"Pid", "Icon", "Name", "Path"};
		@SuppressWarnings("unchecked")
		private Class[] classes = {Integer.class, ImageIcon.class, String.class, String.class};
		private ProcessList list = new ProcessList();
		
	    public MyTableModel(){

	    }
	    
	    public void refresh() {
			list.refresh();
			fireTableDataChanged();	
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
	    			return list.get(row).getIcon();
	    		case 2: 
	    			return list.get(row).getSzExeFile();
	    		case 3: 
	    			return list.get(row).getModuleFileNameExA();
	    		default: return null;
	    	}
	    }
	    	    
	    public Process getProcessAt(int row) {
	    	return list.get(row);
	    }
	    
		@Override
	    @SuppressWarnings("unchecked")
	    public Class getColumnClass(int c) {
			return classes[c];
	    }
	}
}
