package test;

import java.util.LinkedList;
import java.util.List;

import luz.dsexplorer.datastructures.DSType;
import luz.dsexplorer.winapi.api.Process;
import luz.dsexplorer.winapi.api.Result;
import luz.dsexplorer.winapi.api.ResultList;
import luz.eveMonitor.datastructure.PyList;
import luz.eveMonitor.datastructure.PyObject;
import luz.eveMonitor.utils.Reader;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;

public class EveFindDictReverse {
	private static Process process;
	private static long beginAddr=0;
	private static long endAddr=0x23000000L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long timer=System.currentTimeMillis();
		Reader r = new Reader();
		r.findProcess();
		process = r.getProcess();
		

		List<Long> s0= stage0Price(252499500L);
		List<Long> s1= stage1DBRowNormalPtr(s0);
		List<Long> s2= stage2DBRowArray(s1);
		List<Long> s3= stage3DBRowList(s2);
		List<Long> s4= stage4DBRowListArray(s3);
		List<Long> s5= stage5PyList(s4);
		List<Long> s6= stage6Dict(s5);
		List<Long> s7= stage7DictPtr(s6);
		List<Long> s8= stage8DictofDict(s7);
		List<Long> s9= stage9DictPtrofDict(s8);
		List<Long> s10=stage10Instance(s9);
		
//		List<Long> test=new LinkedList<Long>();
//		test.add(0x18758254L);
//		stage10Instance(test);
		
		
		timer=System.currentTimeMillis()-timer;
		System.out.println("Timer "+timer+"ms");

	}
	
	
	public static List<Long> stage0Price(long price){
		List<Long> list = new LinkedList<Long>();
		
		System.out.println("Stage0-Price-taget: "+price);
		
		try {
			ResultList r = process.search(beginAddr, endAddr, ""+price, DSType.Byte4);
			for (int i = 0; i < r.getChildCount(); i++){
				long res=((Result)r.getChildAt(i)).getAddress();
				PyObject obj=new PyObject(res-(8*4), process);
				if (obj.getRefCount()==2){ //check refCount=2 too strict?
					list.add(res-(4*4));
					System.out.println(String.format("Stage0-Price-result: %08X", res-(4*4)));
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return list;		
	}
	
	public static List<Long> stage1DBRowNormalPtr(List<Long> addrs){
		List<Long> list = new LinkedList<Long>();
		for (Long addr : addrs) {
			System.out.println(String.format("Stage1-DBRow-taget: %08X", addr));
			
			try {
				ResultList r = process.search(beginAddr, endAddr, ""+addr, DSType.Byte4);
				for (int i = 0; i < r.getChildCount(); i++){
					long res=((Result)r.getChildAt(i)).getAddress();
					list.add(res);
					System.out.println(String.format("Stage1-DBRow-result: %08X", res));
				}			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;		
	}
	
	public static List<Long> stage2DBRowArray(List<Long> addrs){
		List<Long> list = new LinkedList<Long>();
		int area=512;
		for (Long addr : addrs) {
			//get first element of array. it follows normally a value of 0x40 = 64.
			try {
				Memory buffer=new Memory(area+4);
				process.ReadProcessMemory(Pointer.createConstant(addr-area), buffer, area, null);
				long target=0;		
				for (long i = area-4; i > 0; i=i-4) {
					int value=buffer.getInt(i);
					if (0<=value && value<0x400000){
						target=addr-area +(i+4); //previous
						break;
					}
				}
				if (target!=0){
					System.out.println(String.format("Stage2-DBRowArray-taget: %08X", target));
					ResultList r = process.search(beginAddr, endAddr, ""+target, DSType.Byte4);
					for (int i = 0; i < r.getChildCount(); i++){
						long res=((Result)r.getChildAt(i)).getAddress();
						list.add(res);
						System.out.println(String.format("Stage2-DBRowArray-result: %08X", res));
					}					
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return list;		
	}
	
	public static List<Long> stage3DBRowList(List<Long> addrs){
		List<Long> list = new LinkedList<Long>();
		
		for (Long addr : addrs) {
			PyObject obj=new PyObject(addr-(7*4), process);
			if (obj.getU1()==-3){
				addr=addr-(3*4);
				System.out.println(String.format("Stage3-DBRowList-taget: %08X", addr));
				try {
					ResultList r = process.search(beginAddr, endAddr, ""+addr, DSType.Byte4);
					for (int i = 0; i < r.getChildCount(); i++){
						long res=((Result)r.getChildAt(i)).getAddress();
						list.add(res);
						System.out.println(String.format("Stage3-DBRowList-result: %08X", res));
					}				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return list;		
	}
	
	public static List<Long> stage4DBRowListArray(List<Long> addrs){
		List<Long> list = new LinkedList<Long>();
		
		for (Long addr : addrs) {
			for (int j=0;j<2;j++){
				long target=addr-j*4;
				//seach for 1 previous addr too (target is not necessarily the first array element)
				System.out.println(String.format("Stage4-DBRowListArray-taget: %08X", target));
				
				try {
					ResultList r = process.search(beginAddr, endAddr, ""+target, DSType.Byte4);
					for (int i = 0; i < r.getChildCount(); i++){
						long res=((Result)r.getChildAt(i)).getAddress();
						list.add(res);
						System.out.println(String.format("Stage4-DBRowListArray-result: %08X", res));
					}				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return list;		
	}
	
	public static List<Long> stage5PyList(List<Long> addrs){
		List<Long> list = new LinkedList<Long>();
		
		for (Long addr : addrs) {
			PyList obj=new PyList(addr-(7*4), process);
			if (obj.getU1()==-3 && obj.getElements()==3){//check elements = 3 too strict?
				addr=addr-(3*4);
				System.out.println(String.format("Stage5-PyList-taget: %08X", addr));
				try {
					ResultList r = process.search(beginAddr, endAddr, ""+addr, DSType.Byte4);
					for (int i = 0; i < r.getChildCount(); i++){
						long res=((Result)r.getChildAt(i)).getAddress();
						list.add(res);
						System.out.println(String.format("Stage5-PyList-result: %08X", res));
					}				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return list;		
	}
	
	public static List<Long> stage6Dict(List<Long> addrs){
		List<Long> list = new LinkedList<Long>();
		int area=512;
		for (Long addr : addrs) {
			System.out.println(String.format("Stage6-Dict-taget: %08X", addr));
			try {
				Memory buffer=new Memory(area);
				process.ReadProcessMemory(Pointer.createConstant(addr-area), buffer, area, null);
				long target=0;
				for (long i = area-4; i > 0; i=i-4) {
					if (buffer.getInt(i)==-3){
						target=addr-area+i;
						break;
					}
				}
				if (target>0){
					list.add(target+(2*4));
					System.out.println(String.format("Stage6-Dict-result: %08X", target+(2*4)));	
				}
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		return list;		
	}
	
	public static List<Long> stage7DictPtr(List<Long> addrs){
		List<Long> list = new LinkedList<Long>();
		
		for (Long addr : addrs) {
			System.out.println(String.format("Stage7-DictPtr-taget: %08X", addr));
			
			try {
				ResultList r = process.search(beginAddr, endAddr, ""+addr, DSType.Byte4);
				for (int i = 0; i < r.getChildCount(); i++){
					long res=((Result)r.getChildAt(i)).getAddress();
					list.add(res);
					System.out.println(String.format("Stage7-DictPtr-result: %08X", res));
				}				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;		
	}

	public static List<Long> stage8DictofDict(List<Long> addrs){
		List<Long> list = new LinkedList<Long>();
		for (Long addr : addrs) {
			addr=addr-2*4;	//ptr, hash
			int results=0;
			Memory buffer=new Memory(4);
			do{
				System.out.println(String.format("Stage8-DictofDict-taget: %08X", addr));
				try {
					process.ReadProcessMemory(Pointer.createConstant(addr), buffer, (int)buffer.getSize(), null);
					if (buffer.getInt(0)!=0){	//Hash != 0			
						ResultList r = process.search(beginAddr, endAddr, ""+addr, DSType.Byte4);
						results=r.getChildCount();
						for (int i = 0; i < r.getChildCount(); i++){
							long res=((Result)r.getChildAt(i)).getAddress();
							list.add(res-5*4);	//refcount, Type, ma_fill, ma_used, ma_mask
							System.out.println(String.format("Stage8-DictofDict-result: %08X", res));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				addr=addr-3*4;	//DictEntry
			}while(results==0);
		}		
		return list;		
	}
	
	public static List<Long> stage9DictPtrofDict(List<Long> addrs){
		List<Long> list = new LinkedList<Long>();
		
		for (Long addr : addrs) {
			System.out.println(String.format("Stage9-DictPtrofDict-taget: %08X", addr));
			
			try {
				ResultList r = process.search(beginAddr, endAddr, ""+addr, DSType.Byte4);
				for (int i = 0; i < r.getChildCount(); i++){
					long res=((Result)r.getChildAt(i)).getAddress();
					list.add(res-3*4);
					System.out.println(String.format("Stage9-DictPtrofDict-result: %08X", res));
				}				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public static List<Long> stage10Instance(List<Long> addrs){
		List<Long> list = new LinkedList<Long>();
		
		for (Long addr : addrs) {
			System.out.println(String.format("Stage10-Instance-taget: %08X", addr));
			
			try {
				ResultList r = process.search(beginAddr, endAddr, ""+addr, DSType.Byte4);
				for (int i = 0; i < r.getChildCount(); i++){
					long res=((Result)r.getChildAt(i)).getAddress();
					list.add(res);
					System.out.println(String.format("Stage10-Instance-result: %08X", res));
				}				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	
//	public static List<Long> stage1DBRowDebugPtr(List<Long> addrs){
//		List<Long> list = new LinkedList<Long>();
//		for (Long addr : addrs)
//			list.add(addr+(4*4));
//		return stage1DBRowNormalPtr(list);	
//	}
//	
//	public static List<Long> stage2DBRowArray2(List<Long> addrs){
//		List<Long> list = new LinkedList<Long>();
//		
//		for (Long addr : addrs) {
//			//seach for previous addr too (target is not necessarily the first array element)
//			//TODO get first element of array. it follows normally a value of 0x40 = 64.
//			for (int j=0;j<6;j++){
//				long target=addr-j*4;
//				System.out.println(String.format("Stage2-DBRowArray-taget: %08X", target));
//				try {
//					ResultList r = process.search(beginAddr, endAddr, ""+target, DSType.Byte4);
//					for (int i = 0; i < r.getChildCount(); i++){
//						long res=((Result)r.getChildAt(i)).getAddress();
//						list.add(res);
//						System.out.println(String.format("Stage2-DBRowArray-result: %08X", res));
//					}				
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}			
//		}
//		return list;		
//	}
	

}
