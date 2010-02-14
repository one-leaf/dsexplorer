package luz.eveMonitor.entities.inv;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import luz.eveMonitor.entities.EveGraphic;

@Entity(name="invCategories")
public class InvCategory implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private int categoryID;
	private String categoryName;
	private String description;
	@JoinColumn(name="graphicID")
	private EveGraphic graphicID;
	private boolean published;
	
	
	public int getCategoryID() {
		return categoryID;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public String getDescription() {
		return description;
	}
	public EveGraphic getGraphicID() {
		return graphicID;
	}
	public boolean isPublished() {
		return published;
	}

}
