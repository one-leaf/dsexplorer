package test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import luz.eveMonitor.datastructure.PyDict;
import luz.eveMonitor.datastructure.PyDictEntry;
import luz.eveMonitor.utils.Reader;

public class EveFindDict {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Reader r = new Reader();
		r.findProcess();
		List<PyDict> dicts=new LinkedList<PyDict>();
		
		dicts=r.findDict();
		
		//dicts.add(new Dict(0x18BE97E0, r.getProcess()));
		
		for (PyDict dict : dicts) {
			Iterator<PyDictEntry> entries=dict.getDictEntries();
			while(entries.hasNext()){
				PyDictEntry entry=entries.next();
				System.out.println(String.format("%08X %08X %08X", 
						entry.getHash(),
						entry.getKeyPtr(),
						entry.getValuePtr()));			
			}
		}

	}

}
