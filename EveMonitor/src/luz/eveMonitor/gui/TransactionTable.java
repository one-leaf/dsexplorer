package luz.eveMonitor.gui;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import luz.eveMonitor.datastructure.other.TransactionSet;


public class TransactionTable extends JTable{
	private static final long serialVersionUID = -5848750370811800958L;
//	private final MyTableModel model;

	
	public TransactionTable(){
		super();
//		model=new MyTableModel();
		
//		this.setAutoCreateRowSorter(true);
		System.out.println(getColumnModel());
		this.setModel(new TransactionSet());
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		System.out.println(getColumnModel());
		int row=0;

		this.getColumnModel().getColumn(row++).setMaxWidth(10);	//type
		this.getColumnModel().getColumn(row++).setPreferredWidth(40);	//Price
		this.getColumnModel().getColumn(row++).setPreferredWidth(10);	//VolRem
		this.getColumnModel().getColumn(row++).setPreferredWidth(30);	//Station
		
		this.getColumnModel().getColumn(row++).setPreferredWidth(50);	//items
		this.getColumnModel().getColumn(row++).setPreferredWidth(40);	//Win
		
		this.getColumnModel().getColumn(row++).setPreferredWidth(40);	//Price
		this.getColumnModel().getColumn(row++).setPreferredWidth(10);	//VolRem
		this.getColumnModel().getColumn(row++).setPreferredWidth(30);	//Station
	
	}
	
//	public void refresh() {
//		model.refresh();		
//	}
//	
//	public Transaction getSelectedItem(){		
//		int rowSorted = this.getSelectedRow();
//		if (rowSorted!=-1){
//			int rowReal =getRowSorter().convertRowIndexToModel(rowSorted);
//			return model.getItemAt(rowReal);
//		}else
//			return null;
//	}

//	private static class MyTableModel extends AbstractTableModel {
//		private static final long serialVersionUID = 7780019195084742274L;
//		private Object[][] columns = {
//				{"Type",			String.class},
//				
//				{"Buy Price",		Double.class},
//				{"Buy VolRem",		Double.class},
//				{"Buy Station",		String.class},
//				{"Buy Duration",	String.class},
//				
//				{"Win",				Double.class},				
//				
//				{"Sell Price",		Double.class},
//				{"Sell VolRem",		Double.class},
//				{"Sell Station",	String.class},
//				{"Sell Duration",	String.class},
//				
//		};
//
//		private List<Transaction> list=new LinkedList<Transaction>();
//	    public MyTableModel(){
//
//	    }
//	    
//	    public Transaction getItemAt(int index) {
//			return list.get(index);
//		}
//
//		public void refresh() {
//			fireTableDataChanged();	
//		}
//
//		@Override
//	    public int getColumnCount() {
//	        return columns.length;
//	    }
//	    
//	    @Override
//	    public int getRowCount() {
//	        return list.size();
//	    }
//	    
//	    @Override
//	    public String getColumnName(int col) {
//	        return (String)columns[col][0];
//	    }
//	    
//	    @Override
//	    public Object getValueAt(int row, int col) {
//			try {
//		    	switch (col){
//		    		case  0: return list.get(row).buy.getType().getTypeName();
//		    		case  1: return list.get(row).buy.getPrice();
//		    		case  2: return list.get(row).buy.getVolRem();
//		    		case  3: return list.get(row).buy.getStation().getStationName();
//		    		case  4: 
//		    			Calendar c1 = Calendar.getInstance();
//		    			c1.setTime(list.get(row).buy.getDuration());
//		    			String diff1=
//		    				c1.get(Calendar.DAY_OF_YEAR)+"D "+
//		    				c1.get(Calendar.HOUR_OF_DAY)+"H "+
//		    				c1.get(Calendar.MINUTE)     +"M "+
//		    				c1.get(Calendar.SECOND)     +"S ";
//		    			return diff1;
//		    			
//		    		case  5: return list.get(row).win;
//		    		
//		    		
//		    		case  6: return list.get(row).sell.getPrice();
//		    		case  7: return list.get(row).sell.getVolRem();
//		    		case  8: return list.get(row).sell.getStation().getStationName();
//		    		case  9: 
//		    			Calendar c2 = Calendar.getInstance();
//		    			c2.setTime(list.get(row).sell.getDuration());
//		    			String diff2=
//		    				c2.get(Calendar.DAY_OF_YEAR)+"D "+
//		    				c2.get(Calendar.HOUR_OF_DAY)+"H "+
//		    				c2.get(Calendar.MINUTE)     +"M "+
//		    				c2.get(Calendar.SECOND)     +"S ";
//		    			return diff2;
//
//		    	}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return null;
//	    }
//
//		public void addItem(Transaction row) {
//			if(row!=null){
//				this.list.add(row);
//				fireTableDataChanged();
//			}
//		}
//
//		public void addItems(List<Transaction> list) {
//			if (list!=null && list.size()>0){
//				this.list.addAll(list);
//				fireTableDataChanged();
//			}
//		}
//		
//		public void setItems(List<Transaction> list) {
//			this.list=list;
//			fireTableDataChanged();			
//		}
//	    	    
//   
//		@Override
//	    @SuppressWarnings("unchecked")
//	    public Class getColumnClass(int col) {
//			return (Class)columns[col][1];
//	    }
//
//
//	}
//
//	public void addItem(Transaction row) {
//		model.addItem(row);
//		
//	}
//
//	public void setItems(List<Transaction> list) {
//		model.setItems(list);		
//	}
//
//	public void addItems(List<Transaction> list) {
//		model.addItems(list);		
//	}
}
