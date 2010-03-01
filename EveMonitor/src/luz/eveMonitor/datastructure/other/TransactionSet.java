package luz.eveMonitor.datastructure.other;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class TransactionSet implements Iterable<Transaction>, TableModel{
	private List<Transaction> transs=new LinkedList<Transaction>();
	private EventListenerList listenerList = new EventListenerList();
	
	public synchronized boolean add(Transaction newTrans) {
		int size=transs.size();
		int row=0;
		if(size==0){
			transs.add(newTrans);
		}else{
			for (row=0; row < size; row++) {		
				if(newTrans.win>transs.get(row).win){
					transs.add(row, newTrans);
					fireTableRowsInserted(row, row);
					return true;
				}else{
					if(transs.get(row).equals(newTrans))
						return false;					
				}				
			}
		}
		transs.add(row, newTrans);
		fireTableRowsInserted(row, row);
		return true;
	}
	

	public void addAll(List<Transaction> newTranss) {
		for (Transaction newT : newTranss)
			add(newT);
	}

	@Override
	public Iterator<Transaction> iterator() {
		return transs.iterator();
	}
	
	
	public void refresh(double money, double volume, int accounting, double security) {
		for (Transaction t : transs)
			t.calcWin(money, volume, accounting, security);
		Collections.sort(transs);
		fireTableDataChanged();	
	}
	
	
	
	
	
	
	
	
	
	
	
	

	// Model stuff////////////////////////////////////
	private Object[][] columns = {
			{"Type",			String.class},
			
			{"Buy Price",		Double.class},
			{"Buy VolRem",		Double.class},
			{"Buy Station",		String.class},
			
			{"Items",			Double.class},
			{"Win",				Double.class},				
			
			{"Sell Price",		Double.class},
			{"Sell VolRem",		Double.class},
			{"Sell Station",	String.class},
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
		return Math.min(20, transs.size());
	}

    @Override
    public Object getValueAt(int row, int col) {
		switch (col){
    		case  0: return transs.get(row).buy.getType().getTypeName();
    		case  1: return transs.get(row).buy.getPrice();
    		case  2: return transs.get(row).buy.getVolRem();
    		case  3: return transs.get(row).buy.getStation().getStationName();
//    		case  4: 
//    			Calendar c1 = Calendar.getInstance();
//    			c1.setTime(transs.get(row).buy.getDuration());
//    			String diff1=
//    				c1.get(Calendar.DAY_OF_YEAR)+"D "+
//    				c1.get(Calendar.HOUR_OF_DAY)+"H "+
//    				c1.get(Calendar.MINUTE)     +"M "+
//    				c1.get(Calendar.SECOND)     +"S ";
//    			return diff1;
    		case  4: return transs.get(row).items;	
    		case  5: return transs.get(row).win;

    		
    		case  6: return transs.get(row).sell.getPrice();
    		case  7: return transs.get(row).sell.getVolRem();
    		case  8: return transs.get(row).sell.getStation().getStationName();
//    		case  9: 
//    			Calendar c2 = Calendar.getInstance();
//    			c2.setTime(transs.get(row).sell.getDuration());
//    			String diff2=
//    				c2.get(Calendar.DAY_OF_YEAR)+"D "+
//    				c2.get(Calendar.HOUR_OF_DAY)+"H "+
//    				c2.get(Calendar.MINUTE)     +"M "+
//    				c2.get(Calendar.SECOND)     +"S ";
//    			return diff2;

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
