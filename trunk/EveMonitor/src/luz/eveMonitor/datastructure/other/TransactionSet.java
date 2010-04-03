package luz.eveMonitor.datastructure.other;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TransactionSet implements Iterable<Transaction>, TableModel{
	private static final Log log = LogFactory.getLog(TransactionSet.class);
	private int number;

	private EventListenerList listenerList = new EventListenerList();
	
	private ConcurrentSkipListSet<Transaction> transs=new ConcurrentSkipListSet<Transaction>();
	private Transaction[] bestOf;
	
	
	
	public TransactionSet(int number) {
		this.number=number;
	}

	public void add(Transaction newTrans) {
		transs.add(newTrans);
		update();
	}
	
	public void addAll(List<Transaction> newTrans) {
		transs.addAll(newTrans);
		update();
	}
	
	public synchronized void refresh(TransactionSettings ts) {
		for (Transaction t : transs)
			t.calcWin(ts);
		this.transs=new ConcurrentSkipListSet<Transaction>(transs);	//sort again
		this.number=ts.getNumber();
		update();		
	}
	
	@Override
	public Iterator<Transaction> iterator() {
		return transs.iterator();
	}
	
	
	private void update(){
		bestOf=new Transaction[this.number];
		int i=0;
		for (Transaction t : transs){
			bestOf[i++]=t;
			if(i==this.number)
				break;
		}
		fireTableDataChanged();
	}
	
	public int size() {
		return transs.size();
	}
	

	public void clear() {
		transs.clear();	
		update();
	}
	
	public void removeType(short typeId) {
		Iterator<Transaction> iter = transs.iterator();
		while (iter.hasNext()){
			Transaction trans=iter.next();
			if(trans.buy.getTypeID()==typeId){
//				log.trace("removing trans "+typeId);
				transs.remove(trans);	
			}
		}
		update();
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
		return Math.min(this.number, transs.size());
	}

    @Override
    public Object getValueAt(int row, int col) {
    	if(bestOf[row]==null) return null;
		switch (col){
    		case  0: return bestOf[row].buy.getType().getTypeName();
    		case  1: return bestOf[row].buy.getPrice();
    		case  2: return bestOf[row].buy.getVolRem();
    		case  3: return bestOf[row].buy.getStation().getStationName();
//    		case  4: 
//    			Calendar c1 = Calendar.getInstance();
//    			c1.setTime(transs.get(row).buy.getDuration());
//    			String diff1=
//    				c1.get(Calendar.DAY_OF_YEAR)+"D "+
//    				c1.get(Calendar.HOUR_OF_DAY)+"H "+
//    				c1.get(Calendar.MINUTE)     +"M "+
//    				c1.get(Calendar.SECOND)     +"S ";
//    			return diff1;
    		case  4: return bestOf[row].items;	
    		case  5: return bestOf[row].win;

    		
    		case  6: return bestOf[row].sell.getPrice();
    		case  7: return bestOf[row].sell.getVolRem();
    		case  8: return bestOf[row].sell.getStation().getStationName();
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

	public Transaction getTransaction(int row) {
		return bestOf[row];
	}













}
