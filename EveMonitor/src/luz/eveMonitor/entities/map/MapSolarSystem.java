package luz.eveMonitor.entities.map;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import luz.eveMonitor.entities.inv.InvType;

@Entity(name="MapSolarSystems")
public class MapSolarSystem implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private int solarSystemID;
	@JoinColumn(name="regionID")
	private MapRegion regionID;
	@JoinColumn(name="constellationID")
	private MapConstellation constellationID;
	private String solarSystemName;
	private double x;
	private double y;
	private double z;
	private double xMin;
	private double xMax;
	private double yMin;
	private double yMax;
	private double zMin;
	private double zMax;
	private double luminosity;
	private boolean border;
	private boolean fringe;
	private boolean corridor;
	private boolean hub;
	private boolean international;
	private boolean regional;
	private boolean constellation;
	private double security;
	private int factionID;
	private double radius;
	@JoinColumn(name="sunTypeID")
	private InvType sunTypeID;
	private String securityClass;
	
	public MapConstellation getConstellationID() {
		return constellationID;
	}
	public int getSolarSystemID() {
		return solarSystemID;
	}
	public String getSolarSystemName() {
		return solarSystemName;
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
	public double getxMin() {
		return xMin;
	}
	public double getxMax() {
		return xMax;
	}
	public double getyMin() {
		return yMin;
	}
	public double getyMax() {
		return yMax;
	}
	public double getzMin() {
		return zMin;
	}
	public double getzMax() {
		return zMax;
	}
	public double getLuminosity() {
		return luminosity;
	}
	public boolean isBorder() {
		return border;
	}
	public boolean isFringe() {
		return fringe;
	}
	public boolean isCorridor() {
		return corridor;
	}
	public boolean isHub() {
		return hub;
	}
	public boolean isInternational() {
		return international;
	}
	public boolean isRegional() {
		return regional;
	}
	public boolean isConstellation() {
		return constellation;
	}
	public double getSecurity() {
		return security;
	}
	public int getFactionID() {
		return factionID;
	}
	public double getRadius() {
		return radius;
	}
	public InvType getSunTypeID() {
		return sunTypeID;
	}
	public String getSecurityClass() {
		return securityClass;
	}
	public MapRegion getRegionID() {
		return regionID;
	}
	


}
