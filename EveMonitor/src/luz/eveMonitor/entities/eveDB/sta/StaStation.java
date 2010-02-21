package luz.eveMonitor.entities.eveDB.sta;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import luz.eveMonitor.entities.eveDB.map.MapConstellation;
import luz.eveMonitor.entities.eveDB.map.MapRegion;
import luz.eveMonitor.entities.eveDB.map.MapSolarSystem;

@Entity(name="StaStations")
public class StaStation implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	private int stationID;
	private int security;
	private double dockingCostPerVolume;
	private double maxShipVolumeDockable;
	private int officeRentalCost;
	@JoinColumn(name="operationID")
	private StaOperation operationID;
	@JoinColumn(name="stationTypeID")
	private StaStationType stationTypeID;
	private int corporationID;
	@JoinColumn(name="solarSystemID")
	private MapSolarSystem solarSystemID;
	@JoinColumn(name="constellationID")
	private MapConstellation constellationID;
	@JoinColumn(name="regionID")
	private MapRegion regionID;
	private String stationName;
	private double x;
	private double y;
	private double z;
	private double reprocessingEfficiency;
	private double reprocessingStationsTake;
	private int reprocessingHangarFlag;
	
	public int getStationID() {
		return stationID;
	}
	public int getSecurity() {
		return security;
	}
	public double getDockingCostPerVolume() {
		return dockingCostPerVolume;
	}
	public double getMaxShipVolumeDockable() {
		return maxShipVolumeDockable;
	}
	public int getOfficeRentalCost() {
		return officeRentalCost;
	}
	public StaOperation getOperationID() {
		return operationID;
	}
	public StaStationType getStationTypeID() {
		return stationTypeID;
	}
	public int getCorporationID() {
		return corporationID;
	}
	public MapSolarSystem getSolarSystemID() {
		return solarSystemID;
	}
	public MapConstellation getConstellationID() {
		return constellationID;
	}
	public MapRegion getRegionID() {
		return regionID;
	}
	public String getStationName() {
		return stationName;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getZ() {
		return z;
	}
	public double getReprocessingEfficiency() {
		return reprocessingEfficiency;
	}
	public double getReprocessingStationsTake() {
		return reprocessingStationsTake;
	}
	public int getReprocessingHangarFlag() {
		return reprocessingHangarFlag;
	}



}
