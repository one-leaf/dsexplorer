package luz.eveMonitor.utils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import luz.eveMonitor.datastructure.DBRow;
import luz.eveMonitor.datastructure.PyDict;
import luz.eveMonitor.datastructure.PyInt;
import luz.eveMonitor.datastructure.PyList;
import luz.eveMonitor.datastructure.PyObject;
import luz.eveMonitor.datastructure.PyObjectFactory;
import luz.eveMonitor.datastructure.RowList;
import luz.eveMonitor.datastructure.PyDict.PyDictEntry;
import luz.eveMonitor.entities.eveMon.Order;
import luz.winapi.api.Process;
import luz.winapi.api.ProcessList;
import luz.winapi.api.WinAPI;
import luz.winapi.api.WinAPIImpl;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public class Reader {
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	private EntityManager emEveDB;
	private WinAPI winApi;
	private PyDict dict;

	//Lazy things
	private Process process;
	
	
	public Reader(){	
		winApi = WinAPIImpl.getInstance();	
	}
	
	public void init() throws Exception {
		Logger logger = Logger.getRootLogger();
		logger.setLevel(Level.WARN);	
		findProcess();
		System.out.println(process==null?"Eve not found":"Found Eve: "+process.getSzExeFile());
		//TODO cache dict address with current pid
		dict = findDict();
		System.out.println(dict==null?"orderCache not found":"Found orderCache: "+dict);
	}
	
	public void setProcess(Process process){
		this.process=process;	
	}
	
	public void setDict(PyDict dict) {
		this.dict=dict;
	}
	
	public void setEntityManager(EntityManager emEveDB) {
		this.emEveDB=emEveDB;		
	}
	
	public Process findProcess(){
		ProcessList pl = winApi.getProcessList();
		for (Process p : pl) {
			if (p.getSzExeFile().endsWith("ExeFile.exe")){
				process = p;
				return process;
			}			
		}
		return null;
	}

	public Process getProcess() {
		return process;
	}
	
	
	public List<Order> getRows() throws Exception{
		long timer=System.currentTimeMillis();
		List<Order> rows = new LinkedList<Order>();
		
		Iterator<PyDictEntry> dictIter = dict.getDictEntries();
		PyDictEntry dictEntry;
		while(dictIter.hasNext()){
			dictEntry=dictIter.next();
			PyObject value = dictEntry.getValue();
			if (value instanceof PyList){
				PyList pyList = (PyList)value;
				Iterator<PyObject> listIter = pyList.getIterator();
				PyObject listElement;
				while(listIter.hasNext()){
					listElement=listIter.next();
					if (listElement instanceof RowList){
						RowList rowlist = (RowList)listElement;
						Iterator<DBRow> rowIter = rowlist.getIterator();
						DBRow dbRow;
						while(rowIter.hasNext()){
							dbRow=rowIter.next();
							rows.add(new Order(dbRow, emEveDB));
						}
					}
				}
			}
		}	
		timer=System.currentTimeMillis()-timer;
		System.out.println("timer "+timer+"ms");
		return rows;
	}
	
	private Map<Integer, Integer> stamps=new HashMap<Integer, Integer>();
	public List<Order> getNewRows() {
		long timer=System.currentTimeMillis();
		List<Order> rows = new LinkedList<Order>();
		
		int typeId;
		Iterator<PyDictEntry> dictIter = dict.getDictEntries();
		PyDictEntry dictEntry;
		while(dictIter.hasNext()){
			dictEntry=dictIter.next();
			typeId=dictEntry.getHash();
			PyObject value = dictEntry.getValue();
			if (value instanceof PyList){
				PyList pyList = (PyList)value;
				
				Integer stamp =((PyInt)pyList.getElement(2)).getob_ival();
				Integer stamp2=stamps.get(typeId);
				if(!stamp.equals(stamp2)){
					System.out.println("updated row "+stamp+" "+stamp2);
					stamps.put(typeId, stamp);
					for(int i=0;i<=1;i++){
						RowList rowlist = (RowList)pyList.getElement(i);
						Iterator<DBRow> rowIter = rowlist.getIterator();
						DBRow dbRow;
						while(rowIter.hasNext()){
							dbRow=rowIter.next();
							rows.add(new Order(dbRow, emEveDB));
						}
					}
				}
			}
		}
		timer=System.currentTimeMillis()-timer;
		System.out.println("timer "+timer+"ms. new rows="+rows.size());
		return rows;
	}

	public PyDict findDict(){
		long beginAddr=0;
		long endAddr=0x23000000L;
		int dictHash=(int)pyStringHash("orderCache");

		try {
			PointerListener listener = new PointerListener();
			process.search(beginAddr, endAddr, ""+dictHash, listener);
			List<Long> r = listener.getResults();
			for (int i = 0; i < r.size(); i++){
				long res=r.get(i);
				res=res+2*4;
				IntByReference val=new IntByReference();
				process.ReadProcessMemory(Pointer.createConstant(res), val.getPointer(), 4, null);
				PyObject obj=PyObjectFactory.getObject(val.getValue(), process, false);
				if (obj instanceof PyDict){
					System.out.println(String.format("findDict-result: %08X -> %08X", res, val.getValue()));
					return (PyDict)PyObjectFactory.getObject(val.getValue(),process, false);					
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return null;		
	}
	

	
	public long findRootAddr(long priceAddr){
		long addr=priceAddr-(7*4);
		String staticAddr=null;
		int count=0;
		
		try {
			while(staticAddr==null){
				count++;
				//Pointer
				Memory buf = new Memory(100);
				process.ReadProcessMemory(Pointer.createConstant(addr), buf, (int)buf.getSize(), null);
				addr=buf.getInt(0);
				staticAddr=process.getStatic(addr);
				System.out.println(count+" Addr "+String.format("%08X", addr)+" static "+staticAddr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return addr;
	}
	
	public static long pyStringHash(String string){
		int len = string.length();
		long x = string.charAt(0) << 7;
		for (int index=0; index < len; index++)
			x = (1000003*x) ^ string.charAt(index);
		x ^= len;
		x = x&0xFFFFFFFFL;	//unsigned int
		return x;
	}




	
}
