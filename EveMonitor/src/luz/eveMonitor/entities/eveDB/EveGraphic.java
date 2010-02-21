package luz.eveMonitor.entities.eveDB;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="EveGraphics")
public class EveGraphic implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	private int graphicID;
	private String url3D;
	private String urlWeb;
	private String description;
	private boolean published;
	private boolean obsolete;
	private String icon;
	private String urlSound;
	private int explosionID;
	
	public int getGraphicID() {
		return graphicID;
	}
	public String getUrl3D() {
		return url3D;
	}
	public String getUrlWeb() {
		return urlWeb;
	}
	public String getDescription() {
		return description;
	}
	public boolean isPublished() {
		return published;
	}
	public boolean isObsolete() {
		return obsolete;
	}
	public String getIcon() {
		return icon;
	}
	public String getUrlSound() {
		return urlSound;
	}
	public int getExplosionID() {
		return explosionID;
	}

}
