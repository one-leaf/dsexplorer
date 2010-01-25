package luz.eveMonitor.datastructure;

import java.util.Calendar;
import java.util.Date;

import luz.dsexplorer.winapi.api.Process;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;

public class MarketRow extends PyObject {

	public MarketRow(long addr, Process process) {
		super(addr, process);
	}

	public int		getRowDescrPtr(){return super.getInt   (24);}
	public String getRowDescr(){
		Memory buf2 = new Memory(32);
		//Pointer
		try {
			process.ReadProcessMemory(Pointer.createConstant(getRowDescrPtr()), buf2, (int)buf2.getSize(), null);
			int columnPtr=buf2.getInt(12);

			//Object
			process.ReadProcessMemory(Pointer.createConstant(columnPtr), buf2, (int)buf2.getSize(), null);
			//System.out.println(String.format("%08X", columnPtr)+" "+buf2.getString(4));
			return buf2.getString(4);	
		} catch (Exception e) {
			System.out.println("cannot get RowDescr");
			return null;
		}	
	}
	
	
	
	public double	getPrice	  (){
		long p = super.getLong  (32);
		return (double)p/10000d;
	}
	public double	getVolRem	  (){return super.getDouble(40);}
	public Date		getIssued	  (){
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis((super.getLong(48)-116444736000000000L)/10000L);
		return c.getTime();
	}		
	public int		getOrderID	  (){return super.getInt   (56);}
	public int		getVolEnter	  (){return super.getInt   (60);}
	public int		getVolMin	  (){return super.getInt   (64);}
	public int		getStationID  (){return super.getInt   (68);}
	public int		getRegionID	  (){return super.getInt   (72);}
	public int		getSystemID	  (){return super.getInt   (76);}
	public int		getJumps	  (){return super.getInt   (80);}
	public short	getType       (){return super.getShort (84);}
	public short	getRange      (){return super.getShort (86);}
	public short	getDuration   (){return super.getShort (88);}
	public byte	getBid        (){return super.getByte  (90);}

}