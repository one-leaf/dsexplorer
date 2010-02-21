package luz.eveMonitor.entities.eveDB.inv;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import luz.eveMonitor.entities.eveDB.EveGraphic;

@Entity(name="InvMarketGroups")
public class InvMarketGroup implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	private int marketGroupID;
	@JoinColumn(name="parentGroupID")
	private InvMarketGroup parentGroupID;
	private String marketGroupName;
	private String description;
	@JoinColumn(name="graphicID")
	private EveGraphic graphicID;
	private boolean hasTypes;
	
	public int getMarketGroupID() {
		return marketGroupID;
	}
	public InvMarketGroup getParentGroupID() {
		return parentGroupID;
	}
	public String getMarketGroupName() {
		return marketGroupName;
	}
	public String getDescription() {
		return description;
	}
	public EveGraphic getGraphicID() {
		return graphicID;
	}
	public boolean isHasTypes() {
		return hasTypes;
	}

	
}
