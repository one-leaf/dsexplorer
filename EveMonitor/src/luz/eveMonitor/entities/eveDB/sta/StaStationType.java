package luz.eveMonitor.entities.eveDB.sta;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import luz.eveMonitor.entities.eveDB.EveGraphic;

@Entity(name="staStationTypes")
public class StaStationType implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	private int stationTypeID;
	@JoinColumn(name="dockingBayGraphicID")
	private EveGraphic dockingBayGraphicID;
	@JoinColumn(name="hangarGraphicID")
	private EveGraphic hangarGraphicID;
	private double dockEntryX;
	private double dockEntryY;
	private double dockEntryZ;
	private double dockOrientationX;
	private double dockOrientationY;
	private double dockOrientationZ;
	@JoinColumn(name="operationID")
	private StaOperation operationID;
	private int officeSlots;
	private double reprocessingEfficiency;
	private boolean conquerable;
	
	
	public int getStationTypeID() {
		return stationTypeID;
	}
	public EveGraphic getDockingBayGraphicID() {
		return dockingBayGraphicID;
	}
	public EveGraphic getHangarGraphicID() {
		return hangarGraphicID;
	}
	public double getDockEntryX() {
		return dockEntryX;
	}
	public double getDockEntryY() {
		return dockEntryY;
	}
	public double getDockEntryZ() {
		return dockEntryZ;
	}
	public double getDockOrientationX() {
		return dockOrientationX;
	}
	public double getDockOrientationY() {
		return dockOrientationY;
	}
	public double getDockOrientationZ() {
		return dockOrientationZ;
	}
	public StaOperation getOperationID() {
		return operationID;
	}
	public int getOfficeSlots() {
		return officeSlots;
	}
	public double getReprocessingEfficiency() {
		return reprocessingEfficiency;
	}
	public boolean isConquerable() {
		return conquerable;
	}


}
