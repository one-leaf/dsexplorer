package luz.eveMonitor.datastructure;

import java.util.Date;

public class DBRowMarket {
	private DBRow dbrow;
	
	public DBRowMarket(DBRow dbrow) {
		this.dbrow=dbrow;
	}
	public long	getAddress	  (){return  dbrow.getAddress();}
	
	public double	getPrice	  (){return (Double)((Long)dbrow.getColumnValue("price")/10000d);}
	public double	getVolRem	  (){return (Double) dbrow.getColumnValue("volRemaining");}
	public Date		getIssued	  (){return (Date)   dbrow.getColumnValue("issued");}	
	public int		getOrderID	  (){return (Integer)dbrow.getColumnValue("orderID");}
	public int		getVolEnter	  (){return (Integer)dbrow.getColumnValue("volEntered");}
	public int		getVolMin	  (){return (Integer)dbrow.getColumnValue("minVolume");}
	public int		getStationID  (){return (Integer)dbrow.getColumnValue("stationID");}
	public int		getRegionID	  (){return (Integer)dbrow.getColumnValue("regionID");}
	public int		getSystemID	  (){return (Integer)dbrow.getColumnValue("solarSystemID");}
	public int		getJumps	  (){return (Integer)dbrow.getColumnValue("jumps");}
	public short	getType       (){return (Short)  dbrow.getColumnValue("typeID");}
	public short	getRange      (){return (Short)  dbrow.getColumnValue("range");}
	public short	getDuration   (){return (Short)  dbrow.getColumnValue("duration");}
	public byte	getBid        (){return (Byte)   dbrow.getColumnValue("bid");}

}
