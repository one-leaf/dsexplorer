package test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import luz.eveMonitor.datastructure.Dict;
import luz.eveMonitor.datastructure.PyDictEntry;
import luz.eveMonitor.datastructure.PyObject;
import luz.eveMonitor.utils.Reader;

public class EveFindDict {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Reader r = new Reader();
		r.findProcess();
		List<Dict> dicts=new LinkedList<Dict>();
		
		dicts=r.findDict();
		
		//dicts.add(new Dict(0x18BE97E0, r.getProcess()));
		
		for (Dict dict : dicts) {
			Iterator<PyDictEntry> entries=dict.getDictEntries(10);
			while(entries.hasNext()){
				PyDictEntry entry=entries.next();
				PyObject value=entry.getValue();
				System.out.println(String.format("%08X %08X %08X", 
						entry.getHash(),
						entry.getKeyPtr(),
						entry.getValuePtr())+" "
						+(value==null?"null":value.getTypeString()));
				if (value!=null)
					break;
				
			}
		}

	}

}
