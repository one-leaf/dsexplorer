package luz.eveMonitor.utils;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import luz.dsexplorer.winapi.api.Process;
import luz.dsexplorer.winapi.api.ProcessList;
import luz.dsexplorer.winapi.api.WinAPI;
import luz.dsexplorer.winapi.api.WinAPIImpl;
import luz.eveMonitor.datastructure.Dict;
import luz.eveMonitor.datastructure.MarketRow;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;

public class Reader {
	static Memory buf = new Memory(100);
	static Memory buf2 = new Memory(32);
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	private static int rootAddr=0x00666D38;
	private WinAPI winApi;
	private int objTypePtr;
	private List<Integer> rowDrescrPtr=new LinkedList<Integer>();
	
	
	//Lazy things
	private Process process;
	
	
	public Reader(){
		Logger logger = Logger.getRootLogger();
		logger.setLevel(Level.WARN);		
		winApi = WinAPIImpl.getInstance();	
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

	
	public void init() throws Exception {
		findProcess();
		System.out.println(process==null?"Eve not found":"Found Eve: "+process.getSzExeFile());

		findTypePtr("blue.DBRow");
		System.out.println("Found type: "+String.format("%08X", objTypePtr));

		rowDrescrPtr=new LinkedList<Integer>();
	}
	

	
	public void findTypePtr(String typeString) throws Exception{
		int addr=rootAddr;
		int objTypePtr;	
		do {		
			//Pointer
			process.ReadProcessMemory(Pointer.createConstant(addr), buf, (int)buf.getSize(), null);
			addr=buf.getInt(0);
			//System.out.println("next "+String.format("%08X", addr));
			//Object
			process.ReadProcessMemory(Pointer.createConstant(addr), buf, (int)buf.getSize(), null);
			objTypePtr=buf.getInt(20);
			//System.out.println("type "+String.format("%08X", typeAddr));
			try{
				if (typeString.equals(getTypeString(objTypePtr))){
					this.objTypePtr=objTypePtr;
					break;
				}
			}catch (Exception e){}
		}while (rootAddr!=addr);
	}
	
//	public void findRowDescrPtr(String rowDescr) throws Exception{
//		int addr=rootAddr;
//		int rowDrescrPtr;	
//		do {		
//			//Pointer
//			process.ReadProcessMemory(Pointer.createConstant(addr), buf, (int)buf.getSize(), null);
//			addr=buf.getInt(0);
//			//System.out.println("next "+String.format("%08X", addr));
//			//Object
//			process.ReadProcessMemory(Pointer.createConstant(addr), buf, (int)buf.getSize(), null);
//			rowDrescrPtr=buf.getInt(24);
//			//System.out.println("type "+String.format("%08X", typeAddr));
//
//			try{
//				if (rowDescr.equals(getRowDescr(process, rowDrescrPtr))){
//					this.rowDrescrPtr=rowDrescrPtr;
//					break;
//				}
//			}catch (Exception e){}
//		}while (rootAddr!=addr);
//	}
	
	
	private String getTypeString(int typeAddr) {
		String type=null;
		try{
			//Pointer
			process.ReadProcessMemory(Pointer.createConstant(typeAddr), buf2, (int)buf2.getSize(), null);
			int nameAddr=buf2.getInt(12);
			//System.out.println("name "+String.format("%08X", nameAddr));
			//Object
			process.ReadProcessMemory(Pointer.createConstant(nameAddr), buf2, (int)buf2.getSize(), null);
			type=buf2.getString(0);
		}catch (Exception e){}
		return type;	
	}
	
	private String getRowDescr(int rowDrescrPtr) throws Exception{
		//Pointer
		process.ReadProcessMemory(Pointer.createConstant(rowDrescrPtr), buf2, (int)buf2.getSize(), null);
		int columnPtr=buf2.getInt(12);

		//Object
		process.ReadProcessMemory(Pointer.createConstant(columnPtr), buf2, (int)buf2.getSize(), null);
		//System.out.println(String.format("%08X", columnPtr)+" "+buf2.getString(4));
		return buf2.getString(4);	
	}
	
	public List<MarketRow> getRows() throws Exception{
		long timer=System.currentTimeMillis();
		int addr=rootAddr;
		int count=0;
		List<MarketRow> list = new LinkedList<MarketRow>();		
		do {
			count++;
			//Pointer
			process.ReadProcessMemory(Pointer.createConstant(addr), buf, (int)buf.getSize(), null);
			addr=buf.getInt(0);
			//System.out.println("next "+String.format("%08X", addr));
			//Object
			MarketRow marketRow = new MarketRow(addr);
			process.ReadProcessMemory(Pointer.createConstant(addr), marketRow, (int)marketRow.getSize(), null);

			if (marketRow.getTypePtr()==objTypePtr)	{		
				if (rowDrescrPtr.contains(marketRow.getRowDescrPtr())){
					list.add(marketRow);
				}else{
					if ("price".equals(getRowDescr(marketRow.getRowDescrPtr()))){
						rowDrescrPtr.add(marketRow.getRowDescrPtr());
						list.add(marketRow);
					}
					
				}
			}
		}while (rootAddr!=addr);
		timer=System.currentTimeMillis()-timer;
		System.out.println("loop size: "+count+" timer "+timer+"ms");
		return list;
	}

	public List<Dict> getDicts() throws Exception{
		long timer=System.currentTimeMillis();
		int addr=rootAddr;
		int count=0;
		List<Dict> list = new LinkedList<Dict>();		
		do {
			count++;
			//Pointer
			process.ReadProcessMemory(Pointer.createConstant(addr), buf, (int)buf.getSize(), null);
			addr=buf.getInt(0);
			//System.out.println("next "+String.format("%08X", addr));
			//Object
			Dict dict= new Dict(addr);
			process.ReadProcessMemory(Pointer.createConstant(addr), dict, (int)dict.getSize(), null);

			int type=dict.getTypePtr();
			if ("dict".equals(getTypeString(type)))	{
				list.add(dict);		
			}
		}while (rootAddr!=addr);
		timer=System.currentTimeMillis()-timer;
		System.out.println("loop size: "+count+" timer "+timer+"ms");
		return list;
	}
	
	
//	public static class DBRow extends Structure{
//		public Pointer	next;
//		public Pointer	prev;
//		public int		u1;
//		public int		u2;
//		public int		refCounter;
//		public Pointer	pyTypeObject;
//		public long	price;
//		public double	volRem;
//		public long	issued;
//		public int		orderID;
//		public int		volEnter;
//		public int		volMin;
//		public int		stationID;
//		public int		regionID;
//		public int		systemID;
//		public int		jumps;
//		public short	type;
//		public short	range;
//		public short	duration;
//		public byte	bid;
//		
//		public DBRow(Pointer p) {
//			super(p);
//		}	
//	}
	
}
