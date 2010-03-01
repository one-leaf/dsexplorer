package luz.eveMonitor.datastructure.other;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import luz.eveMonitor.entities.eveMon.Order;

public class OrderSet implements Iterable<Order>, TableModel{
	private List<Order> orders=new LinkedList<Order>();
	private EventListenerList listenerList = new EventListenerList();
	
	public boolean add(Order newOrder) {
		for (Order order : orders) {
			if(order.getOrderID()==newOrder.getOrderID()){	//TODO update orders? same ID, different volrem
				return false;
			}
		}
		int row=orders.size();
		orders.add(newOrder);
		fireTableRowsInserted(row, row);
		return true;
	}

	@Override
	public Iterator<Order> iterator() {
		return orders.iterator();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	// Model stuff////////////////////////////////////
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
	
	@Override
    @SuppressWarnings("unchecked")
	public Class<?> getColumnClass(int col) {
		return (Class)columns[col][1];
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public String getColumnName(int col) {
        return (String)columns[col][0];
	}

	@Override
	public int getRowCount() {
		return orders.size();
	}

    @Override
    public Object getValueAt(int row, int col) {
		try {
	    	switch (col){
    			case  0: return orders.get(row).getOrderID();
	    		case  1: return orders.get(row).getBid();
	    		case  2: return orders.get(row).getType().getTypeName();
	    		case  3: return orders.get(row).getPrice();
	    		case  4: return orders.get(row).getVolRem();
	    		case  5: return orders.get(row).getVolEnter();
	    		case  6: return orders.get(row).getVolMin();
	    		case  7: return orders.get(row).getStation().getStationName();
	    		case  8: return orders.get(row).getSystem().getSolarSystemName();
	    		case  9: return orders.get(row).getRegion().getRegionName();
	    		case 10: return orders.get(row).getJumps();

	    		case 11:
	    			short range=orders.get(row).getRange();
	    			switch (range) {
						case -1: return "Station";
						case 0: return "Solar System";
						case 32767: return "Region";
						default:return range+" Jumps";
					}
	    		case 12: return sdfIssued.format(orders.get(row).getIssued());
	    		case 13: 
	    		case 14: 
	    			Calendar c = Calendar.getInstance();
	    			c.setTime(orders.get(row).getDuration());
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

	@Override
	public boolean isCellEditable(int paramInt1, int paramInt2) {
		return false;
	}

	@Override
	public void setValueAt(Object paramObject, int paramInt1, int paramInt2) {
		// nada
	}

	@Override
	public void addTableModelListener(TableModelListener paramTableModelListener) {
		this.listenerList.add(TableModelListener.class, paramTableModelListener);
	}

	@Override
	public void removeTableModelListener(TableModelListener paramTableModelListener) {
		this.listenerList.remove(TableModelListener.class, paramTableModelListener);
	}

	public void fireTableDataChanged() {
		fireTableChanged(new TableModelEvent(this));
	}

	public void fireTableStructureChanged() {
		fireTableChanged(new TableModelEvent(this, -1));
	}

	public void fireTableRowsInserted(int paramInt1, int paramInt2) {
		fireTableChanged(new TableModelEvent(this, paramInt1, paramInt2, -1, 1));
	}

	public void fireTableRowsUpdated(int paramInt1, int paramInt2) {
		fireTableChanged(new TableModelEvent(this, paramInt1, paramInt2, -1, 0));
	}

	public void fireTableRowsDeleted(int paramInt1, int paramInt2) {
		fireTableChanged(new TableModelEvent(this, paramInt1, paramInt2, -1, -1));
	}

	public void fireTableCellUpdated(int paramInt1, int paramInt2) {
		fireTableChanged(new TableModelEvent(this, paramInt1, paramInt1, paramInt2));
	}

	public void fireTableChanged(TableModelEvent paramTableModelEvent) {
		Object[] arrayOfObject = this.listenerList.getListenerList();
		for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
			if (arrayOfObject[i] != TableModelListener.class)
				continue;
			((TableModelListener) arrayOfObject[(i + 1)]).tableChanged(paramTableModelEvent);
		}
	}




}
