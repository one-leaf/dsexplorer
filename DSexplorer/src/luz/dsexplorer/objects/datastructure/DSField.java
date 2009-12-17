package luz.dsexplorer.objects.datastructure;

import javax.swing.event.EventListenerList;

import org.simpleframework.xml.Attribute;


public class DSField {
	@Attribute
	private DSType type;
	@Attribute
	private String name;
	@Attribute
	private long byteCount;
	private transient EventListenerList listenerList = new EventListenerList();

	//only for xml deserialization
	public DSField(){}

	public DSField(DSType type, String name){
		this(type, name, type.getByteCount());
	}
	
	public DSField(DSType type, String name, long byteCount){
		this.type=type;
		this.name=name;
		this.byteCount=byteCount;
	}
	
	public DSType getType() {
		return type;
	}

	public String getName() {
		return name;
	}
	
	public long getByteCount() {
		return byteCount;
	}

	public void setType(DSType type) {
		this.type = type;
		this.byteCount=type.getByteCount();
		fireActionPerformed(this);
	}
	
	public void setName(String name) {
		this.name = name;
		fireActionPerformed(this);
	}
	
	public void setByteCount(long byteCount){
		if (type==null || !type.isFixedSize()){
			this.byteCount=byteCount;
		}else{
			this.byteCount=type.getByteCount();
		}
		fireActionPerformed(this);
	}
	
	///////////////////////////////////////////////////////////
	
	public void addListener(DSFieldListener l) {
		listenerList.add(DSFieldListener.class, l);
    }
    
    public void removeListener(DSFieldListener l) {
	    listenerList.remove(DSFieldListener.class, l);
    }
    
    public DSFieldListener[] getListeners() {
        return (DSFieldListener[]) listenerList.getListenerList();
    }
    
    protected void fireActionPerformed(Object o) {
    	 Object[] listeners = listenerList.getListenerList(); // Guaranteed to return a non-null array
         // Process the listeners last to first, notifying those that are interested in this event
         for (int i = listeners.length-1; i>=0; i--) {
             if (listeners[i]==DSFieldListener.class) {
            	 ((DSFieldListener)listeners[i+1]).changed((DSField)o);
             }          
         }
    }
    

	
}
