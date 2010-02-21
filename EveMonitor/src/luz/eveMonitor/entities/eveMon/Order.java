package luz.eveMonitor.entities.eveMon;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import luz.eveMonitor.datastructure.DBRow;
import luz.eveMonitor.entities.eveDB.inv.InvType;
import luz.eveMonitor.entities.eveDB.map.MapRegion;
import luz.eveMonitor.entities.eveDB.map.MapSolarSystem;
import luz.eveMonitor.entities.eveDB.sta.StaStation;

@Entity(name="Orders")
public class Order {
	@Id
	private int orderID;
	private double price;
	private double volRem;
	@Temporal(TemporalType.TIMESTAMP)
	private Date issued;
	private int volEnter;
	private int volMin;
	private int stationID;
	private int regionID;
	private int systemID;
	private int jumps;
	private short typeID;
	private short range;
	private short duration;
	private byte bid;
	
	private StaStation station;
	private MapRegion region;
	private MapSolarSystem system;
	private InvType type;
	
	protected Order(){
		
	}
	
	
	public Order(DBRow dbrow, EntityManager emEveDB) {
		this.price		=(Double)((Long)dbrow.getColumnValue("price")/10000d);
		this.volRem		=(Double) dbrow.getColumnValue("volRemaining");
		this.issued		=(Date)   dbrow.getColumnValue("issued");
		this.orderID	=(Integer)dbrow.getColumnValue("orderID");
		this.volEnter	=(Integer)dbrow.getColumnValue("volEntered");
		this.volMin		=(Integer)dbrow.getColumnValue("minVolume");
		this.stationID	=(Integer)dbrow.getColumnValue("stationID");
		this.regionID	=(Integer)dbrow.getColumnValue("regionID");
		this.systemID	=(Integer)dbrow.getColumnValue("solarSystemID");
		this.jumps		=(Integer)dbrow.getColumnValue("jumps");
		this.typeID		=(Short)  dbrow.getColumnValue("typeID");
		this.range		=(Short)  dbrow.getColumnValue("range");
		this.duration	=(Short)  dbrow.getColumnValue("duration");
		this.bid		=(Byte)   dbrow.getColumnValue("bid");
		
		this.station	=emEveDB.find(StaStation.class		, stationID);
		this.region		=emEveDB.find(MapRegion.class		, regionID);
		this.system		=emEveDB.find(MapSolarSystem.class	, systemID);
		this.type		=emEveDB.find(InvType.class			, (int)typeID);
	}

	public double getPrice() {
		return price;
	}

	public double getVolRem() {
		return volRem;
	}

	public Date getIssued() {
		return issued;
	}

	public int getOrderID() {
		return orderID;
	}

	public int getVolEnter() {
		return volEnter;
	}

	public int getVolMin() {
		return volMin;
	}

	public int getStationID() {
		return stationID;
	}

	public int getRegionID() {
		return regionID;
	}

	public int getSystemID() {
		return systemID;
	}

	public int getJumps() {
		return jumps;
	}

	public short getTypeID() {
		return typeID;
	}

	public short getRange() {
		return range;
	}

	public short getDuration() {
		return duration;
	}

	public byte getBid() {
		return bid;
	}

	
	
	public StaStation getStation() {
		return station;
	}

	public MapRegion getRegion() {
		return region;
	}

	public MapSolarSystem getSystem() {
		return system;
	}

	public InvType getType() {
		return type;
	}

}
