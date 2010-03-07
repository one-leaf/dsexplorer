package luz.eveMonitor.threads;

import java.util.Iterator;

import javax.persistence.EntityManager;

import luz.eveMonitor.datastructure.other.TypeGroupMap;
import luz.eveMonitor.datastructure.python.DBRow;
import luz.eveMonitor.datastructure.python.PyInt;
import luz.eveMonitor.datastructure.python.PyList;
import luz.eveMonitor.datastructure.python.PyObject;
import luz.eveMonitor.datastructure.python.RowList;
import luz.eveMonitor.datastructure.python.PyDict.PyDictEntry;
import luz.eveMonitor.entities.eveMon.Order;
import luz.winapi.api.exception.OpenProcessException;
import luz.winapi.api.exception.ReadProcessMemoryException;

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
			log.debug("Eve dict is ready "+status.getDictAddr()+" "+status.getDict());
			
			//PHASE 2 - find Rows & Wait///////////////////////////////////////
			try {
				getNewRows();
			} catch (ReadProcessMemoryException e1) {
				log.warn("cannot read");
			} catch (OpenProcessException e1) {
				log.warn("process problem");
			}

			synchronized (status) {
				try {
					sleep(1*1000);
				} catch (InterruptedException e) {
					continue begin;
				}
			}
		}
	}
	
	public void stopNow(){
		run=false;
		this.interrupt();
	}
	

	
	//get rows///////////////////////////////////////////////////////

	public void getNewRows() throws ReadProcessMemoryException, OpenProcessException {
		log.trace("getNewRows");
		long timer=System.currentTimeMillis();
		
		int rowCounter=0;
		int typeId;
		Iterator<PyDictEntry> dictIter = status.getDict().getDictEntries();
		while(dictIter.hasNext()){	//loop over all entries
			PyDictEntry dictEntry=dictIter.next();
			log.trace("get next Dict "+dictEntry);
			typeId=dictEntry.getHash();
			PyObject value = dictEntry.getValue();
			if (value instanceof PyList){	//check if PyList
				PyList pyList = (PyList)value;
				log.trace("is PyList");
				
				PyInt pyStamp = (PyInt)pyList.getElement(2);
				if (pyStamp!=null){
					Integer stamp =pyStamp.getob_ival();		
					if(!stamp.equals(map.getStamp(typeId))){	//Check if stamp has changed
						log.debug("updating type "+typeId);
						map.setStamp(typeId, stamp);
						
						//Buys
						RowList rowlist = (RowList)pyList.getElement(0);
						log.trace("get Buys "+rowlist.getElements());
						Iterator<DBRow> rowIter = rowlist.getIterator();
						while(rowIter.hasNext()){
							Order buy=new Order(rowIter.next(), emEveDB);
							map.addBuy(typeId, buy);
							rowCounter++;
						}
	
						//Sells
						rowlist = (RowList)pyList.getElement(1);
						log.trace("get Sells "+rowlist.getElements());
						rowIter = rowlist.getIterator();
						while(rowIter.hasNext()){
							Order sell=new Order(rowIter.next(), emEveDB);
							map.addSell(typeId, sell);
							rowCounter++;
						}
						
						log.trace("create Transs");
						map.createTrans(typeId);
					}
				}
			}
		}
		timer=System.currentTimeMillis()-timer;
		log.debug("timer "+timer+"ms. new rows="+rowCounter);
	}
	
	

	

}