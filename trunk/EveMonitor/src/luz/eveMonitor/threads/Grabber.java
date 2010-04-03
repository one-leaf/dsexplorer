package luz.eveMonitor.threads;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import luz.eveMonitor.datastructure.other.TypeGroupMap;
import luz.eveMonitor.datastructure.python.DBRow;
import luz.eveMonitor.datastructure.python.PyDict;
import luz.eveMonitor.datastructure.python.PyInt;
import luz.eveMonitor.datastructure.python.PyList;
import luz.eveMonitor.datastructure.python.PyObject;
import luz.eveMonitor.datastructure.python.PyObjectFactory;
import luz.eveMonitor.datastructure.python.RowList;
import luz.eveMonitor.datastructure.python.PyDict.PyDictEntry;
import luz.eveMonitor.datastructure.python.exception.PythonObjectException;
import luz.eveMonitor.entities.eveMon.Order;
import luz.eveMonitor.utils.PointerListener;
import luz.winapi.api.Process;
import luz.winapi.api.ProcessList;
import luz.winapi.api.WinAPI;
import luz.winapi.api.WinAPIImpl;
import luz.winapi.api.exception.Kernel32Exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public  class Grabber extends Thread{
	private static final Log log = LogFactory.getLog(Grabber.class);
	private final EntityManager emEveDB;
	private final EntityManager emEveMon;
	private final Status status;
	private boolean run=true;
	private TypeGroupMap map;
	private WinAPI winApi= WinAPIImpl.getInstance();	
	
	public Grabber(Status status, TypeGroupMap map, EntityManager emEveMon, EntityManager emEveDB) {
		this.status=status;
		this.map=map;
		this.emEveMon=emEveMon;
		this.emEveDB=emEveDB;
	}
	
	
	@Override
	public void run() {
		begin:
		while(run){
			
			
			findProcess();
			if(status.getProcess()==null){
				log.debug("checking eve process: "+status.getPid());
				try {
	                sleep(1000);
	            } catch (InterruptedException e){
	            	continue begin;
	            }
			}else{
				

				while(true){
					try {
						findDict();
					} catch (Exception e){	//really catch everthing, this thread sould not die
						log.warn("findDict: fail ", e);
						continue begin;
					}
					if(status.getDict()==null){
						try {
			                sleep(1000);
			            } catch (InterruptedException e){
			            	continue begin;
			            }	
					}else{
						
						
						while(true){
							try {
								findRows();
								sleep(1000);
								refreshDict();
				            } catch (Exception e){	//really catch everthing, this thread sould not die
								log.warn("findRows: fail ", e);
								continue begin;
							}
						}
					}
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void stopNow(){
		run=false;
		this.interrupt();
	}
	
	
	public void findProcess(){
		log.trace("findProcess");
		ProcessList pl = winApi.getProcessList();
		for (Process p : pl) {
			if (p.getSzExeFile().endsWith("ExeFile.exe")){
				status.setProcess(p);
				return;
			}			
		}
		status.setProcess(null);
	}
	
	public void findDict() throws Kernel32Exception{
		log.trace("findDict");
		long beginAddr=0x00400000L;
		long endAddr  =0x23000000L;
		int dictHash=(int)PyObjectFactory.pyStringHash("orderCache");

		long timer=System.currentTimeMillis();

		PointerListener listener = new PointerListener();
		status.getProcess().search(beginAddr, endAddr, ""+dictHash, listener);
		List<Long> r = listener.getResults();
		for (int i = 0; i < r.size(); i++){
			long res=r.get(i);
			res=res+2*4;
			IntByReference val=new IntByReference();
			status.getProcess().ReadProcessMemory(Pointer.createConstant(res), val.getPointer(), 4, null);
			
			try {
				PyObject obj = PyObjectFactory.getObject(val.getValue(), status.getProcess(), false);
				if (obj instanceof PyDict){
					log.info(String.format("findDict-result: %08X -> %08X", res, val.getValue()));
					status.setDict((PyDict)obj);
					
					timer=System.currentTimeMillis()-timer;
					log.debug("Dict Search time: "+timer+" ms");
					return;				
				}			
			} catch (PythonObjectException e) {
				// do nothing, try 
			}
		}
		status.setDict(null);
	}
	
	public void refreshDict() throws PythonObjectException {
		PyObject obj=PyObjectFactory.getObject(status.getDictAddr(), status.getProcess(), true);
		if(obj instanceof PyDict){
			status.setDict((PyDict)obj);
		}else{
			status.setDict(null);
		}
	}
	
	public void findRows() throws PythonObjectException {
		log.trace("findRows");	
		int rowCounter=0;
		short typeId;
		Iterator<PyDictEntry> dictIter = status.getDict().getDictEntries();
		while(dictIter.hasNext()){	//loop over all entries
			PyDictEntry dictEntry=dictIter.next();
			log.trace("get next Dict "+dictEntry);
			typeId=(short)dictEntry.getHash();
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
	}

}