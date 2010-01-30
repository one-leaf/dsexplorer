package test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import luz.dsexplorer.datastructures.DSType;
import luz.dsexplorer.winapi.api.Process;
import luz.dsexplorer.winapi.api.Result;
import luz.dsexplorer.winapi.api.ResultList;
import luz.eveMonitor.datastructure.PyDict;
import luz.eveMonitor.datastructure.PyDictEntry;
import luz.eveMonitor.datastructure.PyList;
import luz.eveMonitor.datastructure.PyObject;
import luz.eveMonitor.datastructure.PyObjectFactory;
import luz.eveMonitor.utils.Reader;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public class EveFindDictHash {
	private static Process process;
	private static long beginAddr=0;
	private static long endAddr=0x23000000L;
	private static int dictHash=0x8FDE2CCC;
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		long timer=System.currentTimeMillis();
		Reader r = new Reader();
		r.findProcess();
		process = r.getProcess();
		
		List<Long> s0=stage0Hash(dictHash);
		
		
		PyDict dict=(PyDict)PyObjectFactory.getObject(s0.get(0), process, false);
		Iterator<PyDictEntry> iter = dict.getDictEntries();
		PyDictEntry entry;
		while(iter.hasNext()){
			entry=iter.next();
			System.out.println(String.format("%08X %08X %s %08X %s"
				,entry.getHash()
				,entry.getKeyPtr()
				,entry.getKey()
				,entry.getValuePtr()
				,entry.getValue()));
			
			PyObject value = entry.getValue();
			if (value instanceof PyList){
				PyList list = (PyList)value;
				System.out.println(list.getElements());
				Iterator<PyObject> iter2 = list.getDictEntries();
				PyObject element;
				while(iter2.hasNext()){
					element=iter2.next();
					System.out.println(element);

				}
			}
			
		}
		
		timer=System.currentTimeMillis()-timer;
		System.out.println("Timer "+timer+"ms");
	}
	
	
	public static List<Long> stage0Hash(int hash){
		List<Long> list = new LinkedList<Long>();
		
		System.out.println("Stage0-Hash-taget: "+hash);
		
		try {
			ResultList r = process.search(beginAddr, endAddr, ""+hash, DSType.Byte4);
			for (int i = 0; i < r.getChildCount(); i++){
				long res=((Result)r.getChildAt(i)).getAddress();
				res=res+2*4;
				IntByReference val=new IntByReference();
				process.ReadProcessMemory(Pointer.createConstant(res), val.getPointer(), 4, null);
				PyObject obj=PyObjectFactory.getObject(val.getValue(), process, false);
				if (obj instanceof PyDict){
					list.add((long)val.getValue());
					System.out.println(String.format("Stage0-Hash-result: %08X -> %08X", res, val.getValue()));
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return list;		
	}

}
