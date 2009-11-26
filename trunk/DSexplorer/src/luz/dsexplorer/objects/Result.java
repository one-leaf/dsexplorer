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
	private Long pointer;
	private DSType type;
	private Object valueCache=null;
	private boolean valueCacheOK=false;
	
	private Datastructure datastructure=null;
	private DSField dsField=null;
	
	private long byteCount;
	private Process process;
	private String name;
	private static final Log log = LogFactory.getLog(Result.class);
	

	
	/*
	 * Three types of Results:
	 * 1)  normal results on the root of the tree	(datastructure =null, dsField =null)
	 * 2)  datastructures with children (fields)	(datastructure!=null, dsField =null)
	 * 3a) Fields (children) of the datastructures	(datastructure!=null, dsField!=null)
	 * 3b) Fields which are datastructures			(datastructure!=null, dsField!=null)
	 * 
	 * 
	 */
	
	

	public Result(Process process, Long pointer, Object value, DSType type){
		this.process=process;
		this.pointer=pointer;
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
		this.datastructure=dsField.getDatastructure();
	}
	
	
	public String getName(){
		if(isCustom())
			return datastructure.getName();
		else if (isRelative())
			return dsField.getName();
		else		
			return name;
	}
	
	public Long getPointer(){
		if (isRelative()){
			long p = ((Result)getParent()).getPointer();
			return p+datastructure.getOffset(dsField);
		}else{
			return pointer;
		}
	}
	
	public String getPointerString(){
		Long p=getPointer();
		return p==null?null:String.format("%1$08X", p);
	}
	
	public DSType getType() {
		if (isRelative())
			return dsField.getType();
		else
			return type;
	}
	
	public long getByteCount() {
		if (isCustom()){
			return datastructure.getByteCount();
		}else if(isRelative()){
			return dsField.getByteCount();
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
			log.trace("Read: "+getPointerString());
			process.ReadProcessMemory(Pointer.createConstant(getPointer()), buffer, (int)buffer.getSize(), null);
		} catch (Exception e) {
			log.warn(e);
			return null;
		}
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
		valueCacheOK=true;
		return valueCache;
	}
	
	public Datastructure getDatastructure(){
		return datastructure;
	}
	
	public void setDatastructure(Datastructure ds){
		System.err.println("ds changed");
		if (this.datastructure!=null) 
			this.datastructure.removeListDataListener(this);
		this.datastructure=ds;
		if (this.datastructure!=null) 
			this.datastructure.addListDataListener(this);
		recreateChilds();			
	}	
	
	public void setName(String name){
		this.name=name;
		if (isCustom())
			datastructure.setName(name);
		else if (isRelative())
			dsField.setName(name);
	}
	
	public void setPointer(Long pointer){
		invalidateCache();
		if (isCustom()){
			for (int i = 0; i < getChildCount(); i++) 	//invalidate all children
				((Result)getChildAt(i)).invalidateCache();
		}
		if (!isRelative())
			this.pointer=pointer;
	}
	
	public void setType(DSType type, Datastructure ds) {
		log.trace("SetType "+type);
		invalidateCache();
		this.type = type;
		this.byteCount=type.getByteCount();
		
		if (isCustom()){
			setDatastructure(ds);
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
			Result parent = (Result)getParent();
			for (int i = parent.getIndex(this); i < parent.getChildCount(); i++) 	//invalidate following children
				((Result)parent.getChildAt(i)).invalidateCache();
		}

	}
	
	public void setValue(Object value) {
		invalidateCache();
		this.valueCache=value;
		//TODO write mem
	}
	
	public boolean isCustom(){
		return DSType.Custom.equals(type);
	}
	
	public boolean isRelative(){
		return dsField!=null;
	}
	
	public void invalidateCache(){
		valueCacheOK=false;
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
			for (DSField field : datastructure.getFields())
				add(new Result(process, field));
		}
	}
	
	

	
	//ListDataListener//////////////////////////////////////
	

	@Override
	public void contentsChanged(ListDataEvent listdataevent) {
		invalidateCache();
		if (isRelative()){
			Result p =(Result)getParent();
			Result c;
			for (int i = 0; i < p.getChildCount(); i++) {
				c = (Result)p.getChildAt(i);
				c.invalidateCache();//TODO refresh only following childs
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
		for (DSField field : datastructure.getFields())
			add(new Result(process, field));
	}

	//Object//////////////////////////////////////
	
	@Override
	public String toString() {
		return getPointerString()+" ["+getName()+"] "+(isCustom()?"":getValueString());
	}

}
