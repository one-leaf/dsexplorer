package luz.eveMonitor.datastructure;

import java.util.Date;

public class DBRowMarket {
	private DBRow dbrow;
	
	public DBRowMarket(DBRow dbrow) {
		this.dbrow=dbrow;
	}
	public long	getAddress	  (){return  dbrow.getAddress();}
	
//	public double	getPrice	  (){return (Double)((Long)dbrow.getColumnValue("price")/10000d);}
//	public double	getVolRem	  (){return (Double) dbrow.getColumnValue("volRemaining");}
//	public Date		getIssued	  (){return (Date)   dbrow.getColumnValue("issued");}	
//	public int		getOrderID	  (){return (Integer)dbrow.getColumnValue("orderID");}
//	public int		getVolEnter	  (){return (Integer)dbrow.getColumnValue("volEntered");}
//	public int		getVolMin	  (){return (Integer)dbrow.getColumnValue("minVolume");}
//	public int		getStationID  (){return (Integer)dbrow.getColumnValue("stationID");}
//	public int		getRegionID	  (){return (Integer)dbrow.getColumnValue("regionID");}
//	public int		getSystemID	  (){return (Integer)dbrow.getColumnValue("solarSystemID");}
//	public int		getJumps	  (){return (Integer)dbrow.getColumnValue("jumps");}
//	public short	getType       (){return (Short)  dbrow.getColumnValue("typeID");}
//	public short	getRange      (){return (Short)  dbrow.getColumnValue("range");}
//	public short	getDuration   (){return (Short)  dbrow.getColumnValue("duration");}
//	public byte		getBid        (){return (Byte)   dbrow.getColumnValue("bid");}
		
	public double	getPrice	  (){return (double)dbrow.getLong(8)/10000d;}
	public double	getVolRem	  (){return dbrow.getDouble(16);}
	public Date		getIssued	  (){
		return new Date((dbrow.getLong(24)-116444736000000000L)/10000L);
	}		
	public int		getOrderID	  (){return dbrow.getInt   (32);}
	public int		getVolEnter	  (){return dbrow.getInt   (36);}
	public int		getVolMin	  (){return dbrow.getInt   (40);}
	public int		getStationID  (){return dbrow.getInt   (44);}
	public int		getRegionID	  (){return dbrow.getInt   (48);}
	public int		getSystemID	  (){return dbrow.getInt   (52);}
	public int		getJumps	  (){return dbrow.getInt   (56);}
	public short	getType       (){return dbrow.getShort (60);}
	public short	getRange      (){return dbrow.getShort (62);}
	public short	getDuration   (){return dbrow.getShort (64);}
	public byte	getBid        (){return dbrow.getByte  (66);}
	

}
