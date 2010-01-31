package test;

import java.util.Iterator;

import luz.eveMonitor.datastructure.PyDict;
import luz.eveMonitor.datastructure.PyDict.PyDictEntry;
import luz.eveMonitor.utils.Reader;

public class EveFindDict {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Reader r = new Reader();
		r.findProcess();

		
		PyDict dict=r.findDict();	
		System.out.println(dict);

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
