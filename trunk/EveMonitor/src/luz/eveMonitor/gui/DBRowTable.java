package luz.eveMonitor.gui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import luz.eveMonitor.entities.eveMon.Order;


public class DBRowTable extends JTable{
	private static final long serialVersionUID = -5848750370811800958L;
	private final MyTableModel model;

	
	public DBRowTable(){
		super();
		model=new MyTableModel();

		this.setAutoCreateRowSorter(true);
		
		this.setModel(model);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		int row=0;
		this.getColumnModel().getColumn(row++).setPreferredWidth(30);	//OrderID
		this.getColumnModel().getColumn(row++).setPreferredWidth(5);	//Bid	
		this.getColumnModel().getColumn(row++).setPreferredWidth(10);	//type
		this.getColumnModel().getColumn(row++).setPreferredWidth(40);	//Price
		this.getColumnModel().getColumn(row++).setPreferredWidth(10);	//VolRem
		this.getColumnModel().getColumn(row++).setPreferredWidth(10);	//VolEnter
		this.getColumnModel().getColumn(row++).setPreferredWidth(10);	//VolMin
		this.getColumnModel().getColumn(row++).setPreferredWidth(30);	//Station
		this.getColumnModel().getColumn(row++).setPreferredWidth(30);	//System
		this.getColumnModel().getColumn(row++).setPreferredWidth(30);	//Region
		this.getColumnModel().getColumn(row++).setPreferredWidth(10);	//Jumps
		this.getColumnModel().getColumn(row++).setPreferredWidth(50);	//range
		this.getColumnModel().getColumn(row++).setPreferredWidth(80);	//Issued
		this.getColumnModel().getColumn(row++).setPreferredWidth(50);	//dur	
	}
	
	public void refresh() {
		model.refresh();		
	}
	
	public Order getSelectedItem(){		
		int rowSorted = this.getSelectedRow();
		if (rowSorted!=-1){
			int rowReal =getRowSorter().convertRowIndexToModel(rowSorted);
			return model.getItemAt(rowReal);
		}else
			return null;
	}

	private static class MyTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 7780019195084742274L;
		private SimpleDateFormat sdfIssued = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		private Object[][] columns = {
				{"OrderID",		Integer.class},
				{"Bid",			Byte.class}	,
				{"Type",		String.class},
				{"Price",		Double.class},
				{"VolRem",		Double.class},
				{"VolEnter",	Integer.class},
				{"VolMin",		Integer.class},
				{"Station",		String.class},
				{"System",		String.class},
				{"Region",		String.class},
				{"Jumps",		Integer.class},
				{"Range",		String.class},
				{"Issued",		String.class},
				{"Duration",	String.class}		
		};

		private List<Order> list=new LinkedList<Order>();
	    public MyTableModel(){

	    }
	    
	    public Order getItemAt(int index) {
			return list.get(index);
		}

		public void refresh() {

			fireTableDataChanged();	
		}

		@Override
	    public int getColumnCount() {
	        return columns.length;
	    }
	    
	    @Override
	    public int getRowCount() {
	        return list.size();
	    }
	    
	    @Override
	    public String getColumnName(int col) {
	        return (String)columns[col][0];
	    }
	    
	    @Override
	    public Object getValueAt(int row, int col) {
			try {
		    	switch (col){
	    			case  0: return list.get(row).getOrderID();
		    		case  1: return list.get(row).getBid();
		    		case  2: return list.get(row).getType().getTypeName();
		    		case  3: return list.get(row).getPrice();
		    		case  4: return list.get(row).getVolRem();
		    		case  5: return list.get(row).getVolEnter();
		    		case  6: return list.get(row).getVolMin();
		    		case  7: return list.get(row).getStation().getStationName();
		    		case  8: return list.get(row).getSystem().getSolarSystemName();
		    		case  9: return list.get(row).getRegion().getRegionName();
		    		case 10: return list.get(row).getJumps();

		    		case 11:
		    			short range=list.get(row).getRange();
		    			switch (range) {
							case -1: return "Station";
							case 0: return "Solar System";
							case 32767: return "Region";
							default:return range+" Jumps";
						}
		    		case 12: return sdfIssued.format(list.get(row).getIssued());
		    		case 13: 
		    		case 14: 
		    			Calendar c = Calendar.getInstance();
		    			c.setTime(list.get(row).getDuration());
		    			String diff=
		    				c.get(Calendar.DAY_OF_YEAR)+"D "+
		    				c.get(Calendar.HOUR_OF_DAY)+"H "+
		    				c.get(Calendar.MINUTE)     +"M "+
		    				c.get(Calendar.SECOND)     +"S ";
		    			return diff;

		    	}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
	    }

		public void addItem(Order row) {
			if(row!=null){
				this.list.add(row);
				fireTableDataChanged();
			}
		}

		public void addItems(List<Order> list) {
			if (list!=null && list.size()>0){
				this.list.addAll(list);
				fireTableDataChanged();
			}
		}
		
		public void setItems(List<Order> list) {
			this.list=list;
			fireTableDataChanged();			
		}
	    	    
   
		@Override
	    @SuppressWarnings("unchecked")
	    public Class getColumnClass(int col) {
			return (Class)columns[col][1];
	    }


	}

	public void addItem(Order row) {
		model.addItem(row);
		
	}

	public void setItems(List<Order> list) {
		model.setItems(list);		
	}

	public void addItems(List<Order> list) {
		model.addItems(list);		
	}
}
