package test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import luz.dsexplorer.datastructures.Container;
import luz.dsexplorer.datastructures.ContainerImpl;
import luz.dsexplorer.datastructures.simple.Ascii;
import luz.dsexplorer.datastructures.simple.Byte4;
import luz.dsexplorer.winapi.api.Process;
import luz.dsexplorer.winapi.api.ProcessList;
import luz.dsexplorer.winapi.api.Result;
import luz.dsexplorer.winapi.api.ResultListImpl;
import luz.dsexplorer.winapi.api.WinAPI;
import luz.dsexplorer.winapi.api.WinAPIImpl;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class EveTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long rootAddr=0x00666D38L;
		
		Logger logger = Logger.getRootLogger();
		logger.setLevel(Level.WARN);
		
		WinAPI winApi = WinAPIImpl.getInstance();		
		Process process=null;
		ProcessList pl = winApi.getProcessList();
		for (Process p : pl) {
			if (p.getSzExeFile().endsWith("ExeFile.exe")){
				process = p;
				break;
			}			
		}		
		System.out.println("Found Eve: "+process.getSzExeFile());
		
		Container stringPtr = new ContainerImpl();
		stringPtr.setPointer(true);
		stringPtr.addField(new Ascii());
		
		Container pyTypeObject = new ContainerImpl();
		pyTypeObject.setPointer(true);
		pyTypeObject.addField(new Byte4());
		pyTypeObject.addField(new Byte4());
		pyTypeObject.addField(new Byte4());
		pyTypeObject.addField(stringPtr);		
		
		Container pyObject = new ContainerImpl();
		pyObject.setPointer(true);
		pyObject.addField(pyObject);
		pyObject.addField(pyObject);
		pyObject.addField(new Byte4());
		pyObject.addField(new Byte4());
		pyObject.addField(new Byte4());
		pyObject.addField(pyTypeObject);
		
		ResultListImpl rl = new ResultListImpl(process);
		Result rootResult = new Result(pyObject);
		rl.add(rootResult);
		rootResult.setAddress(rootAddr);
		
		
		
		
		//getTypesFrom(rootResult);
		Long dbrowtTypeAddr=getTypeAddr(rootResult, "blue.DBRow");
		System.out.println(String.format("%08X", dbrowtTypeAddr));

		getRows(rootResult, dbrowtTypeAddr);
		
	}
	
	private static Long getTypeAddr(Result rootResult, String typeString){
		Result r=rootResult;
		Long rootAddr=r.getAddress();
		Result type;
		Long addr;	
		int count=0;		
		do {
			count++;
			r=getNext(r);
			type=getType(r);
			addr=r.getAddress();
			if (typeString.equals(getTypeString(type)))
					return type.getAddress();
		}while (!rootAddr.equals(addr));
		return null;
	}
	
	private static void getRows(Result rootResult, long typeAddr){
		long timer=System.currentTimeMillis();
		Result r=rootResult;
		Long rootAddr=r.getAddress();
		Result type;
		Long addr;	
		int count=0;	
		do {
			r=getNext(r);
			type=getType(r);
			addr=r.getAddress();
			if(type.getAddress().equals(typeAddr)){
				count++;
				System.out.println(count+"\t"+String.format("%08X", addr));				
			}
		}while (!rootAddr.equals(addr));		

		timer=System.currentTimeMillis()-timer;
		System.out.println("loop size: "+count+" timer "+timer+"ms");
	}
	
	private static void getTypesFrom(Result rootResult){
		long timer=System.currentTimeMillis();
		Result r=rootResult;
		Long rootAddr=r.getAddress();
		Result type;
		Long addr;	
		int count=0;
		HashMap<String, Long> types = new HashMap<String, Long>();
		
		do {
			count++;
			r=getNext(r);
			type=getType(r);
			addr=r.getAddress();
			types.put(getTypeString(type), type.getAddress());
			//System.out.println((count++)+"\taddr: "+r.getAddressString()+" type: "+getType(r));
		}while (!rootAddr.equals(addr));
		
		for (Entry<String, Long> entry : types.entrySet())
			System.out.println(String.format("%08X", entry.getValue())+" "+entry.getKey());
		timer=System.currentTimeMillis()-timer;
		System.out.println("loop size: "+count+" timer "+timer+"ms");
	}

	
	
	private static Result getNext(Result r){
		return ((Result)r.getChildAt(0));	
	}
	
	private static Result getType(Result r){
		return ((Result)r.getChildAt(5).getChildAt(3));		
	}
	
	private static String getTypeString(Result r){
		return ((Result)r.getChildAt(0)).getValueString();		
	}
	

	
	
}
