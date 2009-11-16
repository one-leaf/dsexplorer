package luz.dsexplorer.gui;

import java.awt.Component;
import java.awt.Font;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import luz.dsexplorer.objects.Result;


public class ResultTable extends JTable{
	private static final long serialVersionUID = -5848750370811800958L;
	private final MyTableModel model;
	
	public ResultTable(){
		super();
		model=new MyTableModel();
		this.setModel(model);
		this.setAutoCreateRowSorter(true);
		this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		this.getColumnModel().getColumn(0).setCellRenderer(new MyCellRenderer());	//TODO suboptimal font change
	}
	
	public void refresh() {
		model.refresh();		
	}
	

	public List<Result> getSelectedResults() {
		int[] rows = this.getSelectedRows();
		if (rows.length==0)
			return null;
		
		List<Result> list = new LinkedList<Result>();
		for (int row : rows)
			list.add(model.getResultAt(row));
		
		return list;
	}
	
	
	
	
	private static class MyCellRenderer extends DefaultTableCellRenderer{
		private static final long serialVersionUID = -7760559280746695139L;
		Font font=new Font("Lucida Console", Font.PLAIN, 11);

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JLabel l = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			l.setFont(font);
			return l;			
		};
	}
	

	private static class MyTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 7780019195084742274L;
		private String[] columnNames = {"Address", "Value"};
		@SuppressWarnings("unchecked")
		private Class[] classes = {String.class, Integer.class};
		private List<Result> list = new LinkedList<Result>();
		
	    public Result getResultAt(int row) {
			return list.get(row);
		}

		public void refresh() {
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
			try {
		    	switch (col){
		    		case 0:
		    			return list.get(row).getPointerString();
		    		case 1: 
		    			return list.get(row).getValue();
		    	}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
	    }
	    
		@Override
	    @SuppressWarnings("unchecked")
	    public Class getColumnClass(int c) {
			return classes[c];
	    }

		public void setResults(List<Result> search) {
			list = search;
			fireTableDataChanged();
		}
	}

	public void setResults(List<Result> search) {
		model.setResults(search);		
	}


}
