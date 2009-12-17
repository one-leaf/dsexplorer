package luz.dsexplorer.objects.datastructure;

import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractListModel;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

public class Datastructure extends AbstractListModel implements DSFieldListener{
	private static final long serialVersionUID = 4479689750597516075L;
	@Attribute
	private String name;
	@ElementList(inline=true, required=false)	//May be empty
	private List<DSField> fields=new LinkedList<DSField>();

	//only for xml deserialization
	public Datastructure(){};
	
	public Datastructure(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<DSField> getFields(){
		return fields;
	}
	
	public int getByteCount(){
		int bytes=0;
		for (DSField field : fields) {
			bytes+=field.getByteCount();
		}
		return bytes;
	}
	
	public int getOffset(DSField f){
		int bytes=0;
		for (DSField field : fields) {
			if (field.equals(f)) break;
			bytes+=field.getByteCount();
		}
		return bytes;
	}	
	
	
	public void addElement(DSType type) {
		addElement(new DSField(type, type.name()));		
	}
	
	private void addElement(Object obj) {
		DSField field = (DSField)obj;
		field.addListener(this);
		fields.add(field);
		int index=fields.size()-1;
		fireIntervalAdded(obj, index, index);
	}
	
	public void insertElementAt(Object obj, int i) {
		fields.add(i, (DSField)obj);	
		fireIntervalAdded(obj, i, i);
	}
	
	public void removeElement(Object obj) {
		int index = fields.indexOf(obj);
		fields.remove(index);
		fireIntervalRemoved(obj, index, index);
	}
	
	public void removeElementAt(int index) {
		Object obj = fields.get(index);
		fields.remove(index);
		fireIntervalRemoved(obj, index, index);
	}
		
	//DSFieldListener//////////////////////////////////////
	
	@Override
	public void changed(Object field) {
		int index=fields.indexOf(field);
		fireContentsChanged(field, index, index);	
	}
	

	//AbstractListModel//////////////////////////////////////
	
	@Override
	public Object getElementAt(int i) {
		return fields.get(i);
	}

	@Override
	public int getSize() {
		return fields.size();
	}
	
	//Object//////////////////////////////////////
	
	@Override
	public String toString() {
		return name;
	}






	

}
