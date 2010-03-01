package luz.eveMonitor.threads;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;

import luz.eveMonitor.datastructure.other.TypeGroupMap;
import luz.eveMonitor.datastructure.python.DBRow;
import luz.eveMonitor.datastructure.python.PyInt;
import luz.eveMonitor.datastructure.python.PyList;
import luz.eveMonitor.datastructure.python.PyObject;
import luz.eveMonitor.datastructure.python.RowList;
import luz.eveMonitor.datastructure.python.PyDict.PyDictEntry;
import luz.eveMonitor.entities.eveMon.Order;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public  class RowFinder extends Thread{
	private static final Log log = LogFactory.getLog(RowFinder.class);
	private final EntityManager emEveDB;
	private final EntityManager emEveMon;
	private final Status status;
	private boolean run=true;
	private TypeGroupMap map;
	
	
	public RowFinder(Status status, TypeGroupMap map, EntityManager emEveMon, EntityManager emEveDB) {
		this.status=status;
		this.map=map;
		this.emEveMon=emEveMon;
		this.emEveDB=emEveDB;
	}	
	
	@Override
	public void run() {
		begin:
		while(run){
			//PHASE 1 - wait for dict ///////////////////////////////////////
			while(status.getDict()==null){
				try {
	                synchronized(status){
	                	status.wait();
	                }
	            } catch (InterruptedException e){
	            	continue begin;
	            }				
			}
			log.debug("Eve dict is ready "+status.getDictAddr());
			
			//PHASE 2 - find Rows ///////////////////////////////////////
			while(status.getDict()!=null){
				getNewRows();

				synchronized (status) {
					try {
						sleep(1*1000);
					} catch (InterruptedException e) {
						continue begin;
					}
				}
			}
		}
	}
	
	public void stopNow(){
		run=false;
		this.interrupt();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	//get rows///////////////////////////////////////////////////////
	
	
	
	
	
	



	public void getNewRows() {
		long timer=System.currentTimeMillis();
		List<Order> rows = new LinkedList<Order>();
		
		int typeId;
		Iterator<PyDictEntry> dictIter = status.getDict().getDictEntries();
		PyDictEntry dictEntry;
		while(dictIter.hasNext()){	//loop over all entries
			dictEntry=dictIter.next();
			typeId=dictEntry.getHash();
			PyObject value = dictEntry.getValue();
			if (value instanceof PyList){	//check if PyList
				PyList pyList = (PyList)value;
				
				PyInt pyStamp = (PyInt)pyList.getElement(2);
				if (pyStamp!=null){
					Integer stamp =pyStamp.getob_ival();		
					if(!stamp.equals(map.getStamp(typeId))){	//Check if stamp has changed
						System.out.println("updating type "+typeId);
						map.setStamp(typeId, stamp);
						
						//Buys
						RowList rowlist = (RowList)pyList.getElement(0);
						Iterator<DBRow> rowIter = rowlist.getIterator();
						while(rowIter.hasNext()){
							Order buy=new Order(rowIter.next(), emEveDB);
							map.addBuy(typeId, buy);
						}
	
						//Sells
						rowlist = (RowList)pyList.getElement(1);
						rowIter = rowlist.getIterator();
						while(rowIter.hasNext()){
							Order sell=new Order(rowIter.next(), emEveDB);
							map.addSell(typeId, sell);
						}
						
						map.createTrans(typeId);
					}
				}
			}
		}
		timer=System.currentTimeMillis()-timer;
		System.out.println("timer "+timer+"ms. new rows="+rows.size());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//return trans///////////////////////////////////////////////////////
	
	
//	double globalMax = Float.MIN_VALUE;
//	private Transaction getBestTransactions(Set<Short> typeIDs){
//		List<Transaction> transactions=new LinkedList<Transaction>();
//		for (Short typeID : typeIDs) {
//			transactions.add(getBestTransaction(typeID));
//		}
//		
//
//		Transaction tmax=null;
//		for (Transaction transaction : transactions) {
//			if (transaction.win>globalMax){
//				globalMax=transaction.win;
//				tmax=transaction;
//			}
//		}
//		if (tmax!=null && tmax.buy!=null){
//			tmax.buy.fill(emEveDB);
//			tmax.sell.fill(emEveDB);
//		}
//		return tmax;
//	}
//	
//	private Transaction getBestTransaction(short typeID){
//		Query qs = emEveMon.createNamedQuery("findOrderByType");
//		qs.setParameter("typeID", typeID);
//		qs.setParameter("bid", (byte)1);
//		List<Order> sells = qs.getResultList();
//		System.err.println("SSSSSSSSSSSSSSSSSS "+sells.size()+" "+typeID);
//		
//		Query qb = emEveMon.createNamedQuery("findOrderByType");
//		qb.setParameter("typeID", typeID);
//		qb.setParameter("bid", (byte)0);
//		List<Order> buys = qb.getResultList();
//		System.err.println("BBBBBBBBBBBBBBBBBB "+sells.size()+" "+typeID);
//		
//		Transaction tmax=new Transaction();
//		for (Order buy : buys) {
//			for (Order sell : sells) {
//				double items=Math.min(buy.getVolRem(), sell.getVolRem());	//TODO limit by cargo space
//																			//TODO limit by security status
//				double win=items*(sell.getPrice()-buy.getPrice());
//				System.out.println(win);
//				if(win>tmax.win){
//					tmax.win=win;
//					tmax.buy=buy;
//					tmax.sell=sell;				
//				}
//			}
//		}
//		return tmax;		
//	}

	

}