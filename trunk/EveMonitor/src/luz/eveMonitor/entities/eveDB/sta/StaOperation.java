package luz.eveMonitor.entities.eveDB.sta;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity(name="staOperations")
public class StaOperation implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	private int operationID;
	private int activityID;
	private String operationName;
	private String description;
	private int fringe;
	private int corridor;
	private int hub;
	private int border;
	private int ratio;
	@JoinColumn(name="caldariStationTypeID")
	private StaStationType caldariStationTypeID;
	@JoinColumn(name="minmatarStationTypeID")
	private StaStationType minmatarStationTypeID;
	@JoinColumn(name="amarrStationTypeID")
	private StaStationType amarrStationTypeID;	
	@JoinColumn(name="gallenteStationTypeID")
	private StaStationType gallenteStationTypeID;
	@JoinColumn(name="joveStationTypeID")
	private StaStationType joveStationTypeID;
	
	
	public int getOperationID() {
		return operationID;
	}
	public int getActivityID() {
		return activityID;
	}
	public String getOperationName() {
		return operationName;
	}
	public String getDescription() {
		return description;
	}
	public int getFringe() {
		return fringe;
	}
	public int getCorridor() {
		return corridor;
	}
	public int getHub() {
		return hub;
	}
	public int getBorder() {
		return border;
	}
	public int getRatio() {
		return ratio;
	}
	public StaStationType getCaldariStationTypeID() {
		return caldariStationTypeID;
	}
	public StaStationType getMinmatarStationTypeID() {
		return minmatarStationTypeID;
	}
	public StaStationType getAmarrStationTypeID() {
		return amarrStationTypeID;
	}
	public StaStationType getGallenteStationTypeID() {
		return gallenteStationTypeID;
	}
	public StaStationType getJoveStationTypeID() {
		return joveStationTypeID;
	}



}
