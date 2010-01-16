package test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import luz.dsexplorer.winapi.api.Process;
import luz.dsexplorer.winapi.api.ProcessList;
import luz.dsexplorer.winapi.api.WinAPI;
import luz.dsexplorer.winapi.api.WinAPIImpl;
import luz.eveMonitor.datastructure.MarketRow;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;

public class EveTest2 {
	static Memory buf = new Memory(100);
	static Memory buf2 = new Memory(32);
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		int rootAddr=0x00666D38;
		
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
		if(process==null){
			System.out.println("Eve not found");
			return;
		}
		System.out.println("Found Eve: "+process.getSzExeFile());
		

		
		

		Integer dbrowtTypeAddr=getTypeAddr(process, rootAddr, "blue.DBRow");
		System.out.println("Found type: "+String.format("%08X", dbrowtTypeAddr));

		Integer dbrowDescrPtr=getRowDescrAddr(process, rootAddr, "price");
		System.out.println("Found RowDescr: "+String.format("%08X", dbrowDescrPtr));
		
		getRows(process, rootAddr, dbrowtTypeAddr, dbrowDescrPtr);
		
	}
	

	
	private static Integer getTypeAddr(Process process, int rootAddr, String typeString) throws Exception{
		int addr=rootAddr;
		int typeAddr;	
		do {		
			//Pointer
			process.ReadProcessMemory(Pointer.createConstant(addr), buf, (int)buf.getSize(), null);
			addr=buf.getInt(0);
			//System.out.println("next "+String.format("%08X", addr));
			//Object
			process.ReadProcessMemory(Pointer.createConstant(addr), buf, (int)buf.getSize(), null);
			typeAddr=buf.getInt(20);
			//System.out.println("type "+String.format("%08X", typeAddr));

			if (typeString.equals(getTypeString(process, typeAddr)))
					return typeAddr;
		}while (rootAddr!=addr);
		return null;
	}
	
	private static Integer getRowDescrAddr(Process process, int rootAddr, String rowDescr) throws Exception{
		int addr=rootAddr;
		int rowDrescrPtr;	
		do {		
			//Pointer
			process.ReadProcessMemory(Pointer.createConstant(addr), buf, (int)buf.getSize(), null);
			addr=buf.getInt(0);
			//System.out.println("next "+String.format("%08X", addr));
			//Object
			process.ReadProcessMemory(Pointer.createConstant(addr), buf, (int)buf.getSize(), null);
			rowDrescrPtr=buf.getInt(24);
			//System.out.println("type "+String.format("%08X", typeAddr));

			try{
				if (rowDescr.equals(getRowDescr(process, rowDrescrPtr)))
						return rowDrescrPtr;
			}catch (Exception e){}
		}while (rootAddr!=addr);
		return null;
	}
	
	
	private static void getRows(Process process, int rootAddr, int targetTypeAddr, int dbrowDescrPtr) throws Exception{
		long timer=System.currentTimeMillis();
		int addr=rootAddr;
		int typeAddr;	
		int count=0;
		int rows=0;
		MarketRow marketRow = new MarketRow();
		StringBuilder sb=new StringBuilder();
		do {
			count++;
			//Pointer
			process.ReadProcessMemory(Pointer.createConstant(addr), buf, (int)buf.getSize(), null);
			addr=buf.getInt(0);
			//System.out.println("next "+String.format("%08X", addr));
			//Object
			process.ReadProcessMemory(Pointer.createConstant(addr), marketRow, (int)marketRow.getSize(), null);
		
			
			typeAddr=marketRow.getTypePtr();
			//System.out.println("type "+String.format("%08X", typeAddr));

			if (targetTypeAddr==typeAddr){				
				if (marketRow.getRowDescrPtr()==dbrowDescrPtr){	
					rows++;
					sb.setLength(0);
					sb.append(rows)								.append('\t');
					sb.append(String.format("%08X", addr))		.append('\t');//sb.append("price     ");
					sb.append(marketRow.getPrice())				.append('\t');//sb.append("vol rem   ");
					sb.append(marketRow.getVolRem())			.append('\t');//sb.append("issued    ");
					sb.append(sdf.format(marketRow.getIssued())).append('\t');//sb.append("orderID   ");
					sb.append(marketRow.getOrderID())			.append('\t');//sb.append("volEnter  ");
					sb.append(marketRow.getVolEnter())			.append('\t');//sb.append("volMin    ");
					sb.append(marketRow.getVolMin())			.append('\t');//sb.append("stationID ");
					sb.append(marketRow.getStationID())			.append('\t');//sb.append("regionID  ");
					sb.append(marketRow.getRegionID())			.append('\t');//sb.append("systemID  ");
					sb.append(marketRow.getSystemID())			.append('\t');//sb.append("jumps     ");
					sb.append(marketRow.getJumps())				.append('\t');//sb.append("type      ");
					sb.append(marketRow.getType())				.append('\t');//sb.append("range     ");
					sb.append(marketRow.getRange())				.append('\t');//sb.append("duration  ");
					sb.append(marketRow.getDuration())			.append('\t');//sb.append("bid       ");
					sb.append(marketRow.getBid())				.append('\t');
					System.out.println(sb.toString());
				}
			}
		}while (rootAddr!=addr);
	

		timer=System.currentTimeMillis()-timer;
		System.out.println("loop size: "+count+" timer "+timer+"ms");
	}
	
	
	private static String getTypeString(Process process, int typeAddr) throws Exception{
		//Pointer
		process.ReadProcessMemory(Pointer.createConstant(typeAddr), buf2, (int)buf2.getSize(), null);
		int nameAddr=buf2.getInt(12);
		//System.out.println("name "+String.format("%08X", nameAddr));
		//Object
		process.ReadProcessMemory(Pointer.createConstant(nameAddr), buf2, (int)buf2.getSize(), null);
		return buf2.getString(0);	
	}
	
	private static String getRowDescr(Process process, int rowDrescrPtr) throws Exception{
		//Pointer
		process.ReadProcessMemory(Pointer.createConstant(rowDrescrPtr), buf2, (int)buf2.getSize(), null);
		int columnPtr=buf2.getInt(12);

		//Object
		process.ReadProcessMemory(Pointer.createConstant(columnPtr), buf2, (int)buf2.getSize(), null);
		//System.out.println(String.format("%08X", columnPtr)+" "+buf2.getString(4));
		return buf2.getString(4);	
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
