package luz.eveMonitor.datastructure;

import java.util.Calendar;
import java.util.Date;

import com.sun.jna.Memory;

public class MarketRow extends Memory {
	private int address;

	public MarketRow(int addr) {
		super(100);
		this.address=addr;
	}
	public int		getAddress    (){return address;}
	
	public int		getRefCount	  (){return super.getInt   (16);}	
	public int		getTypePtr	  (){return super.getInt   (20);}
	public int		getRowDescrPtr(){return super.getInt   (24);}
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