package luz.dsexplorer.datastructures.simple;

import java.util.LinkedList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import luz.dsexplorer.datastructures.DSListener;
import luz.dsexplorer.datastructures.DSType;
import luz.dsexplorer.datastructures.Datastructure;

import com.sun.jna.Memory;

@Root
public abstract class DefaultDatastructure implements Datastructure{
	@Attribute
	protected String name="";
	@Attribute
	protected int  byteCount=0;
	protected boolean byteCountFix=true;
	private Datastructure parent;	//needs to be reconstructed after loading
	private List<DSListener> listeners=new LinkedList<DSListener>();
	
	protected DefaultDatastructure(){}
	
	@Override
	public boolean isContainer() {
		return false;
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
	public Datastructure getContainer(){
		return parent;
	}

	@Override
	public void setContainer(Datastructure parent){
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
	
	
	@Override
	public int getByteCount(){
		return byteCount;
	}
	
	@Override
	public void setByteCount(int byteCount){
		if (!isByteCountFix()){
			this.byteCount=byteCount;
			for (int i = listeners.size()-1; i>=0; i--)
				listeners.get(i).hasChanged();
		}
	}
	
	@Override
	public boolean isByteCountFix(){
		return byteCountFix;
	}
	
	@Override
	public abstract Object eval(Memory buffer);
	
	@Override
	public abstract DSType getType();
	
	@Override
	public String valueToString(Object value){
		return value==null?null:value.toString();
	}
}
