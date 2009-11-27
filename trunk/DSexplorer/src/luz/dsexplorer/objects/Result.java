package luz.dsexplorer.objects;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;

public class Result extends DefaultMutableTreeNode implements ListDataListener{
	private static final long serialVersionUID = 4163723615095260358L;
	private Process process;
	private Datastructure datastructure=null;
	private DSField dsField=null;
	
	private String name;
	private DSType type;
	private long byteCount;
	private Long address;
	
	private boolean isPointer;
	private Long pointerCache=null;
	private boolean pointerCacheOK=false;
	
	private Object valueCache=null;
	private boolean valueCacheOK=false;

	private static final Log log = LogFactory.getLog(Result.class);
	

	
	/*
	 * Four types of Results:
	 * 1) normal results on the root of the tree		(datastructure =null, dsField =null)
	 * 2) datastructures with children (fields)			(datastructure!=null, dsField =null)
	 * 3) Fields (children) of the datastructures		(datastructure =null, dsField!=null)
	 * 4) Fields (children) which are datastructures	(datastructure!=null, dsField!=null)
	 * 
	 */
	
	public Result(Process process, Long address, Object value, DSType type){
		this.process=process;
		this.address=address;
		this.valueCache=value;
		this.valueCacheOK=(value!=null);
		this.type=type;
		this.byteCount=type.getByteCount();
		this.name=type.name();
	}	
	
	/**
	 * Create Result from a DataStructure Field. This means this Result depends on the parent Result.
	 * The pointer is calculated from the base of the parent and the offset of the field.
	 * @param process
	 * @param parent
	 * @param field
	 */
	public Result(Process process, DSField field){
		this(process, null, null, field.getType());
		this.dsField=field;
	}
	
	
	public String getName(){
		if (isRelative())
			return dsField.getName();
		else if(isCustom())
			return datastructure.getName();
		else		
			return name;
	}
	
	public Long getAddress(){
		if (isRelative()){
			Result p=((Result)getParent());
			if (p!=null){
				if (p.isPointer){
					Long base = p.getPointer();
					if (base==null) 
						return null;
					else
						return base+dsField.getOffset();				
				}else{
					Long base = p.getAddress();	//WARNING : recursion
					if (base==null)
						return null;
					else
						return base+dsField.getOffset();
				}
			}else{
				log.warn("Relative Result without Parent (weird->needs fix)");	//FIMXE results without parent
				return null;
			}
		}else{
			return address;
		}
	}
	
	public String getAddressString(){
		Long p=getAddress();
		return p==null?null:String.format("%1$08X", p);
	}
	
	public DSType getType() {
		if (isRelative())
			return dsField.getType();
		else
			return type;
	}
	
	public long getByteCount() {
		if(isRelative()){
			return dsField.getByteCount();
		}else if (isCustom()){
			return datastructure.getByteCount();
		}else{
			return byteCount;
		}
	}
	
	public String getValueString(){
		Object value = getValue();
		if (value==null) return null;
		
		StringBuilder sb;
		switch (getType()) {
		case ByteArray:
			sb = new StringBuilder();
			byte[] bytes=(byte[])value;
			for (int i = 0; i < bytes.length; i++) {
				sb.append(String.format("%1$02X", bytes[i]));
			}
			return sb.toString();
			
		case Ascii:
			sb = new StringBuilder();
			byte[] chars=(byte[])value;
			for (int i = 0; i < chars.length; i++) {
				sb.append((char)chars[i]);
			}
			return sb.toString();			
			
		default:
			return value==null?null:value.toString();
		}
	}
	
	public Object getValue(){
		if (isCustom())
			return null;
		
		if(valueCacheOK)
			return valueCache;
		
		Memory buffer=new Memory(getByteCount());
		try {
			log.trace("Read: "+getAddressString());
			process.ReadProcessMemory(Pointer.createConstant(getAddress()), buffer, (int)buffer.getSize(), null);
			switch (getType()) {
				case Byte1:		valueCache=buffer.getByte     (0);							break;
				case Byte2:		valueCache=buffer.getShort    (0);							break;
				case Byte4:		valueCache=buffer.getInt      (0);							break;
				case Byte8:		valueCache=buffer.getLong     (0);							break;
				case Float:		valueCache=buffer.getFloat    (0);							break;
				case Double:	valueCache=buffer.getDouble   (0);							break;
				case Ascii:		valueCache=buffer.getByteArray(0, (int)buffer.getSize());	break;
				case Unicode:	valueCache=buffer.getString   (0);							break;
				case ByteArray:	valueCache=buffer.getByteArray(0, (int)buffer.getSize());	break;
				case Custom:	valueCache=null;											break;
			}
		} catch (Exception e) {
			log.warn(e);
			valueCache=null;
		}
		valueCacheOK=true;
		return valueCache;
	}
	
	public boolean isPointer(){
		return isPointer;
	}
	
	public Long getPointer(){
		if (!isPointer)
			return null;
		
		if(pointerCacheOK)
			return pointerCache;
		
		Memory buffer=new Memory(4);
		try {
			log.trace("Pointer: "+getAddressString());
			process.ReadProcessMemory(Pointer.createConstant(getAddress()), buffer, (int)buffer.getSize(), null);
			pointerCache=(long)buffer.getInt(0);
		} catch (Exception e) {
			log.warn(e);
			pointerCache=null;
		}
		pointerCacheOK=true;
		return pointerCache;
	}
	
	public String getPointerString() {
		Long p=getPointer();
		return p==null?null:String.format("%1$08X", p);
	}

	
	
	public Datastructure getDatastructure(){
		if (isRelative())
			return dsField.getDatastructure();
		else
			return datastructure;
	}
	
	public void setDatastructure(Datastructure ds){
		log.trace("Datastructure changed to "+ds);
		if (this.datastructure!=null) 
			this.datastructure.removeListDataListener(this);
		this.datastructure=ds;
		if (ds!=null)
			ds.addListDataListener(this);
		recreateChilds();
	}	
	
	public void setName(String name){
		this.name=name;
		if (isCustom())
			datastructure.setName(name);
		else if (isRelative())
			dsField.setName(name);
	}
	
	public void setAddress(Long address){
		invalidateCache();
		if (isCustom()){
			invalidateChildCache();
		}
		if (!isRelative())
			this.address=address;
	}
	
	public void setType(DSType type, Datastructure ds) {
		log.trace("SetType "+type);
		invalidateCache();
		this.type = type;
		this.byteCount=type.getByteCount();
		
		if (isCustom()){
			setDatastructure(ds);
			isPointer=false;
		}else{
			setDatastructure(null);
		}
		
		if(isRelative()){
			MutableTreeNode p = parent;
			dsField.setType(type);	//FIXME this removes the parent????
			parent=p;
		}
	}
	
	public void setByteCount(int size){
		log.trace("setByteCount "+size);
		invalidateCache();
		if (!type.isFixedSize() || size==type.getByteCount()){
			this.byteCount=size;
		}
		
		if(isRelative()){
			dsField.setByteCount(size);
			((Result)getParent()).invalidateChildCache();
		}

	}
	
	public void setValue(Object value) {
		invalidateCache();
		this.valueCache=value;
		//TODO write mem
	}
	
	public void setPointer(boolean isPointer) {
		if (isCustom()){	//Only for datastructures? (design question)
			this.isPointer=isPointer;
			invalidateChildCache();
		}
	}
	
	
	//Helper//////////////////////////////////////
	
	
	public boolean isCustom(){
		return DSType.Custom.equals(type);
	}
	
	public boolean isRelative(){
		return dsField!=null;
	}
	
	private void invalidateCache(){
		valueCacheOK=false;
		pointerCacheOK=false;
	}
	
	private void invalidateChildCache(){
		for (int i = 0; i < getChildCount(); i++)
			((Result)getChildAt(i)).invalidateCache();
	}
	
	
	//DefaultMutableTreeNode//////////////////////////////////////

	@Override
	public boolean isLeaf() {
		return !isCustom();
	}
	
	private boolean areChildrenDefined=false;
	
	@Override
	public int getChildCount() {
		if (isCustom()){
			defineChildNodes();
			return super.getChildCount();
		}else{
			return 0;
		}
	}
	
	private void defineChildNodes(){
		if (!areChildrenDefined){
			areChildrenDefined=true;
			Datastructure ds = datastructure;
			if (ds==null){
				if (dsField!=null) 
					ds=dsField.getDatastructure();
				else
					return;
			}
			for (DSField field : ds.getFields())
				add(new Result(process, field));
		}
	}

	
	//ListDataListener (for datastructure)//////////////////////////////////////
	
	@Override
	public void contentsChanged(ListDataEvent listdataevent) {
		if(isCustom()){	//only custom Results listen to datastructure changes
			if (isRelative()){
				log.debug("contentsChanged custom+relative");
				//special case, with cutoms Results as Children
				recreateChilds();
			}else{
				log.debug("contentsChanged custom");
				invalidateCache();
				invalidateChildCache();
				//no need to recreate children, because they are only modified
			}
		}
		
		

	}

	@Override
	public void intervalAdded(ListDataEvent listdataevent) {
		if(isCustom())
			recreateChilds();	//TODO refresh only following childs
	}

	@Override
	public void intervalRemoved(ListDataEvent listdataevent) {
		if(isCustom())
			recreateChilds();	//TODO refresh only following childs
	}
	
	private void recreateChilds(){
		try{
			removeAllChildren();
		}catch (ArrayIndexOutOfBoundsException e){
			//Ignore "node has no children"
		}
		if (datastructure!=null){
			for (DSField field : datastructure.getFields())
				add(new Result(process, field));
		}
	}

	//Object//////////////////////////////////////
	
	@Override
	public String toString() {
		return getAddressString()+" ["+getName()+"] "+(isCustom()?"":getValueString());
	}




}
