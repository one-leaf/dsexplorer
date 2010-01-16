package luz.eveMonitor.gui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import luz.eveMonitor.datastructure.MarketRow;


public class DBRowTable extends JTable{
	private static final long serialVersionUID = -5848750370811800958L;
	private final MyTableModel model;

	
	public DBRowTable(){
		super();
		model=new MyTableModel();

		this.setAutoCreateRowSorter(true);

		this.setModel(model);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		this.getColumnModel().getColumn( 0).setPreferredWidth(20);
		this.getColumnModel().getColumn( 1).setPreferredWidth(20);
		this.getColumnModel().getColumn( 2).setPreferredWidth(80);	//Issued
		this.getColumnModel().getColumn( 3).setPreferredWidth(30);
		this.getColumnModel().getColumn( 4).setPreferredWidth(20);
		this.getColumnModel().getColumn( 5).setPreferredWidth(10);	//VolMin
		this.getColumnModel().getColumn( 6).setPreferredWidth(30);
		this.getColumnModel().getColumn( 7).setPreferredWidth(30);
		this.getColumnModel().getColumn( 8).setPreferredWidth(30);
		this.getColumnModel().getColumn( 9).setPreferredWidth(10);	//Jumps
		this.getColumnModel().getColumn(10).setPreferredWidth(20);
		this.getColumnModel().getColumn(11).setPreferredWidth(50);
		this.getColumnModel().getColumn(12).setPreferredWidth(20);
		this.getColumnModel().getColumn(13).setPreferredWidth(10);	//Bid
		
	}
	
	public void refresh() {
		model.refresh();		
	}
	
	public MarketRow getSelectedItem(){		
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
		private String[] columnNames = {"Price", "VolRem", "Issued", "OrderID",
		"VolEnter", "VolMin", "StationID", "RegionID", "SystemID","Jumps",
		"Type", "Range", "Duration","Bid"};
		
//		@SuppressWarnings("unchecked")
//		private Class[] classes = {Double.class, Double.class, Date.class, Integer.class};
		private List<MarketRow> list=new LinkedList<MarketRow>();
	    public MyTableModel(){

	    }
	    
	    public MarketRow getItemAt(int index) {
			return list.get(index);
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
		    		case 0: return list.get(row).getPrice();
		    		case 1: return list.get(row).getVolRem();
		    		case 2: return sdfIssued.format(list.get(row).getIssued());
		    		case 3: return list.get(row).getOrderID();
		    		case 4: return list.get(row).getVolEnter();
		    		case 5: return list.get(row).getVolMin();
		    		case 6: return list.get(row).getStationID();
		    		case 7: return list.get(row).getRegionID();
		    		case 8: return list.get(row).getSystemID();
		    		case 9: return list.get(row).getJumps();
		    		case 10: return list.get(row).getType();
		    		case 11:
		    			short range=list.get(row).getRange();
		    			switch (range) {
							case -1: return "Station";
							case 0: return "Solar System";
							case 32767: return "Region";
							default:return range+" Jumps";
						}
		    		case 12: 
		    			short days=list.get(row).getDuration();
		    			long issued=list.get(row).getIssued().getTime();
		    			Calendar c = Calendar.getInstance();
		    			c.setTimeInMillis(issued);
		    			c.add(Calendar.DAY_OF_YEAR, days-1);	//TODO why -1
		    			long expires=c.getTimeInMillis();
		    			
		    			long now=System.currentTimeMillis();
		    			c.setTimeInMillis(expires-now);
		    			c.setTimeZone(TimeZone.getTimeZone("GMT"));
		    			String diff=
		    				c.get(Calendar.DAY_OF_YEAR)+"D "+
		    				c.get(Calendar.HOUR_OF_DAY)+"H "+
		    				c.get(Calendar.MINUTE)+"M "+
		    				c.get(Calendar.SECOND)+"S ";
		    			return diff;
		    		case 13: return list.get(row).getBid();
		    	}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
	    }

		public void addItem(MarketRow row) {
			list.add(row);
			fireTableDataChanged();	
		}

		public void setItems(List<MarketRow> list) {
			this.list=list;
			fireTableDataChanged();			
		}
	    	    
   
//		@Override
//	    @SuppressWarnings("unchecked")
//	    public Class getColumnClass(int c) {
//			return classes[c];
//	    }
	}

	public void addItem(MarketRow row) {
		model.addItem(row);
		
	}

	public void setItems(List<MarketRow> list) {
		model.setItems(list);
		
	}
}
