package test;

import java.util.Iterator;

import luz.dsexplorer.datastructures.DSType;
import luz.dsexplorer.winapi.api.Process;
import luz.dsexplorer.winapi.api.Result;
import luz.dsexplorer.winapi.api.ResultList;
import luz.eveMonitor.datastructure.DBRow;
import luz.eveMonitor.datastructure.PyDict;
import luz.eveMonitor.datastructure.PyList;
import luz.eveMonitor.datastructure.PyObject;
import luz.eveMonitor.datastructure.PyObjectFactory;
import luz.eveMonitor.datastructure.RowList;
import luz.eveMonitor.datastructure.PyDict.PyDictEntry;
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
		Reader r = new Reader();
		r.findProcess();
		process = r.getProcess();
		long timer=System.currentTimeMillis();
		
//		Integer dictAddr =stage0Hash(dictHash);
//		System.out.println(String.format("%08X", dictAddr));
		Integer dictAddr=0x138C8B80;

		
		
		PyDict dict=(PyDict)PyObjectFactory.getObject(dictAddr, process, false);
		System.out.println(dict);
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
				System.out.println(list);
				
				System.out.println(list.getElements());
				Iterator<PyObject> iter2 = list.getIterator();
				PyObject element;
				while(iter2.hasNext()){
					element=iter2.next();
					if (element instanceof RowList){
						RowList rowlist = (RowList)element;
						System.out.println("\t"+rowlist);
						
						System.out.println("\t"+rowlist.getElements());
						Iterator<DBRow> iter3 = rowlist.getIterator();
						DBRow row;
						while(iter3.hasNext()){
							row=iter3.next();
							System.out.println("\t\t"+row);				
							System.out.println("\t\t\tprice         "+row.getColumnValue("price"));
							System.out.println("\t\t\tvolRemaining  "+row.getColumnValue("volRemaining"));
							System.out.println("\t\t\ttypeID        "+row.getColumnValue("typeID"));
							System.out.println("\t\t\trange         "+row.getColumnValue("range"));
							System.out.println("\t\t\torderID       "+row.getColumnValue("orderID"));
							System.out.println("\t\t\tvolEntered    "+row.getColumnValue("volEntered"));
							System.out.println("\t\t\tminVolume     "+row.getColumnValue("minVolume"));
							System.out.println("\t\t\tbid           "+row.getColumnValue("bid"));
							System.out.println("\t\t\tissued        "+row.getColumnValue("issued"));
							System.out.println("\t\t\tduration      "+row.getColumnValue("duration"));
							System.out.println("\t\t\tstationID     "+row.getColumnValue("stationID"));
							System.out.println("\t\t\tregionID      "+row.getColumnValue("regionID"));
							System.out.println("\t\t\tsolarSystemID "+row.getColumnValue("solarSystemID"));
							System.out.println("\t\t\tjumps         "+row.getColumnValue("jumps"));
							
//							RowDescr rowDescr=row.getRowDescr();
//							System.out.println("\t\t\t"+rowDescr);
//							
//							System.out.println("\t\t\t"+rowDescr.getColums());
//							Iterator<Column> iter4 = rowDescr.getIterator();
//							Column col;							
//							while(iter4.hasNext()){
//								col=iter4.next();
//								System.out.println("\t\t\t\t"+col.getName()+"\t"+col.getType()+"\t"+col.getBytes()+"\t"+col.getId());
//							}
							
						}
					}
				}
			}			
		}
		
		timer=System.currentTimeMillis()-timer;
		System.out.println("Timer "+timer+"ms");
	}
	
	
	public static Integer stage0Hash(int hash){
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
					System.out.println(String.format("Stage0-Hash-result: %08X -> %08X", res, val.getValue()));
					return val.getValue();
					
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return null;		
	}

}
