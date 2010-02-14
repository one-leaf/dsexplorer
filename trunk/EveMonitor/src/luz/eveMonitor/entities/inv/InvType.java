package luz.eveMonitor.entities.inv;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import luz.eveMonitor.entities.EveGraphic;

@Entity(name="invTypes")
public class InvType implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private int typeID;
	@JoinColumn(name="groupID")
	private InvGroup groupID;
	private String typeName;
	private String description;
	@JoinColumn(name="graphicID")
	private EveGraphic graphicID;
	private double radius;
	private double mass;
	private double volume;
	private double capacity;
	private int portionSize;
	private int raceID;
	private double basePrice;
	private boolean published;
	@JoinColumn(name="marketGroupID")
	private InvMarketGroup marketGroupID;
	private double chanceOfDuplicating;
	
	
	public InvGroup getGroupID() {
		return groupID;
	}
	public String getTypeName() {
		return typeName;
	}
	public String getDescription() {
		return description;
	}
	public EveGraphic getGraphicID() {
		return graphicID;
	}
	public double getRadius() {
		return radius;
	}
	public double getMass() {
		return mass;
	}
	public double getVolume() {
		return volume;
	}
	public double getCapacity() {
		return capacity;
	}
	public int getPortionSize() {
		return portionSize;
	}
	public int getRaceID() {
		return raceID;
	}
	public double getBasePrice() {
		return basePrice;
	}
	public boolean isPublished() {
		return published;
	}
	public InvMarketGroup getMarketGroupID() {
		return marketGroupID;
	}
	public double getChanceOfDuplicating() {
		return chanceOfDuplicating;
	}
	public int getTypeID() {
		return typeID;
	}


}
