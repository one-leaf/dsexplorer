package luz.dsexplorer.objects;

import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractListModel;

public class Datastructure extends AbstractListModel{
	private static final long serialVersionUID = 4479689750597516075L;
	private String name;
	private List<DSField> fields=new LinkedList<DSField>();
	
	public Datastructure(String name){
		this.setName(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
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
	
	public void refresh(DSField field){
		int index=fields.indexOf(field);
		fireContentsChanged(field, index, index);
	}
		
	public void addElement(DSType type, String name) {
		addElement(new DSField(type, name, this));		
	}
	
	

	
	
	private void addElement(Object obj) {
		fields.add((DSField)obj);
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
