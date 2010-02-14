package luz.eveMonitor.entities.map;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity(name="mapConstellations")
public class MapConstellation implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private int constellationID;
	@JoinColumn(name="regionID")
	private MapRegion regionID;
	private String constellationName;
	private double x;
	private double y;
	private double z;
	private double xMin;
	private double xMax;
	private double yMin;
	private double yMax;
	private double zMin;
	private double zMax;
	private int factionID;
	private double radius;
	
	
	public MapRegion getRegionID() {
		return regionID;
	}
	public String getConstellationName() {
		return constellationName;
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
	public int getFactionID() {
		return factionID;
	}
	public double getRadius() {
		return radius;
	}
	public int getConstellationID() {
		return constellationID;
	}

}
