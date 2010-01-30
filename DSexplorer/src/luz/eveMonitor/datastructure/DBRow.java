package luz.eveMonitor.datastructure;

import java.util.Calendar;
import java.util.Date;

import luz.dsexplorer.winapi.api.Process;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;

public class DBRow extends PyObject {

	public DBRow(PyObject_VAR_HEAD head, Process process) {
		super(head, 67, process);
	}

	public int		getRowDescrPtr(){return super.getInt   (0);}
	public String getRowDescr(){
		//TODO add rowdescrition object
		
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
	
	
	
	public double	getPrice	  (){return (double)super.getLong(8)/10000d;}
	public double	getVolRem	  (){return super.getDouble(16);}
	public Date		getIssued	  (){
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis((super.getLong(24)-116444736000000000L)/10000L);
		return c.getTime();
	}		
	public int		getOrderID	  (){return super.getInt   (32);}
	public int		getVolEnter	  (){return super.getInt   (36);}
	public int		getVolMin	  (){return super.getInt   (40);}
	public int		getStationID  (){return super.getInt   (44);}
	public int		getRegionID	  (){return super.getInt   (48);}
	public int		getSystemID	  (){return super.getInt   (52);}
	public int		getJumps	  (){return super.getInt   (56);}
	public short	getType       (){return super.getShort (60);}
	public short	getRange      (){return super.getShort (62);}
	public short	getDuration   (){return super.getShort (64);}
	public byte	getBid        (){return super.getByte  (66);}

}