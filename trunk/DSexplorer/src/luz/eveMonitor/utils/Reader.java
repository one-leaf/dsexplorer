package luz.eveMonitor.utils;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import luz.dsexplorer.datastructures.DSType;
import luz.dsexplorer.winapi.api.Process;
import luz.dsexplorer.winapi.api.ProcessList;
import luz.dsexplorer.winapi.api.Result;
import luz.dsexplorer.winapi.api.ResultList;
import luz.dsexplorer.winapi.api.WinAPI;
import luz.dsexplorer.winapi.api.WinAPIImpl;
import luz.eveMonitor.datastructure.DBRow;
import luz.eveMonitor.datastructure.DBRowMarket;
import luz.eveMonitor.datastructure.PyDict;
import luz.eveMonitor.datastructure.PyList;
import luz.eveMonitor.datastructure.PyObject;
import luz.eveMonitor.datastructure.PyObjectFactory;
import luz.eveMonitor.datastructure.RowList;
import luz.eveMonitor.datastructure.PyDict.PyDictEntry;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public class Reader {
	static Memory buf = new Memory(100);
	static Memory buf2 = new Memory(32);
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	private WinAPI winApi;
	private PyDict dict;
	
	//Lazy things
	private Process process;
	
	
	public Reader(){
		Logger logger = Logger.getRootLogger();
		logger.setLevel(Level.WARN);		
		winApi = WinAPIImpl.getInstance();	
	}
	
	public void init() throws Exception {
		findProcess();
		System.out.println(process==null?"Eve not found":"Found Eve: "+process.getSzExeFile());
		dict = findDict();
		System.out.println(dict==null?"orderCache not found":"Found orderCache: "+dict);
	}
	
	public void findProcess(){
		ProcessList pl = winApi.getProcessList();
		for (Process p : pl) {
			if (p.getSzExeFile().endsWith("ExeFile.exe")){
				process = p;
				break;
			}			
		}	
	}

	public Process getProcess() {
		return process;
	}
	
	public List<DBRowMarket> getRows() throws Exception{
		long timer=System.currentTimeMillis();
		List<DBRowMarket> rows = new LinkedList<DBRowMarket>();
		
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
							rows.add(new DBRowMarket(dbRow));
						}
					}
				}
			}
		}	
		timer=System.currentTimeMillis()-timer;
		System.out.println("timer "+timer+"ms");
		return rows;
	}

	public PyDict findDict(){
		long beginAddr=0;
		long endAddr=0x23000000L;
		int dictHash=(int)pyStringHash("orderCache");

		try {
			ResultList r = process.search(beginAddr, endAddr, ""+dictHash, DSType.Byte4);
			for (int i = 0; i < r.getChildCount(); i++){
				long res=((Result)r.getChildAt(i)).getAddress();
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
		x = x&0xFFFFFFFFL;	//unsinged int
		return x;
	}
	
}
