package luz.eveMonitor.entities.inv;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import luz.eveMonitor.entities.EveGraphic;

@Entity(name="invGroups")
public class InvGroup implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private int groupID;
	private int categoryID;
	private String groupName;
	private String description;
	@JoinColumn(name="graphicID")
	private EveGraphic graphicID;
	private boolean useBasePrice;
	private boolean allowManufacture;
	private boolean allowRecycler;
	private boolean anchored;
	private boolean anchorable;
	private boolean fittableNonSingleton;
	private boolean published;
	
	
	public int getGroupID() {
		return groupID;
	}
	public int getCategoryID() {
		return categoryID;
	}
	public String getGroupName() {
		return groupName;
	}
	public String getDescription() {
		return description;
	}
	public EveGraphic getGraphicID() {
		return graphicID;
	}
	public boolean isUseBasePrice() {
		return useBasePrice;
	}
	public boolean isAllowManufacture() {
		return allowManufacture;
	}
	public boolean isAllowRecycler() {
		return allowRecycler;
	}
	public boolean isAnchored() {
		return anchored;
	}
	public boolean isAnchorable() {
		return anchorable;
	}
	public boolean isFittableNonSingleton() {
		return fittableNonSingleton;
	}
	public boolean isPublished() {
		return published;
	}



}
