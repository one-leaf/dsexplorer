package luz.dsexplorer.datastructures;

import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractListModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.sun.jna.Memory;

@Root
public class ContainerImpl extends AbstractListModel implements Container{
	private static final Log log = LogFactory.getLog(ContainerImpl.class);
	private static final long serialVersionUID = -7295827399382661392L;
	@ElementList(inline=true, required=false)
	private List<Datastructure> fields=new LinkedList<Datastructure>();
	@Attribute
	private String name="composed";
	@Attribute
	private boolean pointer=false;
	private Datastructure parent;	//needs to be reconstructed after loading
	private List<DSListener> listeners=new LinkedList<DSListener>();
	
	public ContainerImpl(){};
	
	//Container///////////////////////////////////////////////
	


	@Override
	public List<Datastructure> getFields(){
		return fields;
	}
	
	@Override
	public void addField(Datastructure field) {
		field.setContainer(this);
		int index=fields.size();
		fields.add(field);
		for (int i = listeners.size()-1; i>=0; i--)
			listeners.get(i).addedField(field, index);
	}
	
	@Override
	public void addField(Datastructure field, int index) {
		field.setContainer(this);
		fields.add(index, field);
		for (int i = listeners.size()-1; i>=0; i--)
			listeners.get(i).addedField(field, index);
	}	
	
	@Override
	public void removeField(int fieldIndex) {
		fields.remove(fieldIndex);
		for (int i = listeners.size()-1; i>=0; i--)
			listeners.get(i).removedField(fieldIndex);
	}
	
	@Override
	public void replaceField(Datastructure oldField, Datastructure newField, int index) {
		newField.setContainer(this);
		fields.set(index, newField);
		for (int i = listeners.size()-1; i>=0; i--)
			listeners.get(i).replacedField(oldField, newField, index);
	}
	
	@Override
	public void setPointer(boolean pointer) {
		this.pointer=pointer;
		for (int i = listeners.size()-1; i>=0; i--)
			listeners.get(i).pointerChanged(pointer);
	}
	
	@Override
	public boolean isPointer() {
		return pointer;
	}

	@Override
	public int getOffset(int fieldIndex){
		if (fieldIndex>=fields.size()){
			fieldIndex=fields.size();
			log.warn("fieldIndex("+fieldIndex+") >= Field list size ("+ fieldIndex+")");
			//FIXME fieldIndex >= Field list size
		}
		
		int bytes=0;
		for (int i = 0; i < fieldIndex; i++)
			bytes+=fields.get(i).getByteCount();

		return bytes;
	}


	
	//Datastructure///////////////////////////////////////////////
	
	@Override
	public boolean isContainer() {
		return true;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name=name;	
		for (int i = listeners.size()-1; i>=0; i--)
			listeners.get(i).hasChanged();
	}
	

	@Override
	public int getByteCount() {
		if (isPointer())
			return 4;
		int sum=0;
		for (Datastructure ds : fields){
			if (ds.isContainer() && !((Container)ds).isPointer() )
				sum+=0;	//FIXME incorrect. but watch out for infinite recursions
			else
				sum+=ds.getByteCount();
		}
		return sum;
	}
	
	@Override
	public void setByteCount(int byteCount) {
		//fix. depends on fields
	}

	@Override
	public Object eval(Memory buffer) {
		return null;	//nothing show
	}
	
	@Override	
	public boolean isByteCountFix(){
		return true;
	}
	
	@Override
	public DSType getType(){
		return DSType.Container;
	}

	@Override
	public String valueToString(Object value) {
		return null;	//has no value
	}

	
	@Override
	public Datastructure getContainer(){
		return parent;
	}
	
	@Override
	public void setContainer(Datastructure parent){
		log.debug("container set");
		this.parent=parent;
	}
	
	@Override
	public void addListener(DSListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(DSListener listener) {
		listeners.remove(listener);
	}
	
	//AbstractListModel///////////////////////////////////////////////
		
	@Override
	public Object getElementAt(int index) {
		return fields.get(index);
	}

	@Override
	public int getSize() {
		return fields.size();
	}



	//Object///////////////////////////////////////////////
	@Override
	public String toString() {
		return getName();
	}




}
