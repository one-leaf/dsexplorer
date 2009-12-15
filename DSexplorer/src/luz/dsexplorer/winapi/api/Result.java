package luz.dsexplorer.winapi.api;

import java.nio.charset.Charset;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import luz.dsexplorer.objects.datastructure.DSField;
import luz.dsexplorer.objects.datastructure.DSType;
import luz.dsexplorer.objects.datastructure.Datastructure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;

public class Result extends DefaultMutableTreeNode implements ListDataListener, Cloneable{
	private static final long serialVersionUID = 4163723615095260358L;
	private static final Log log = LogFactory.getLog(Result.class);
	private transient ResultList resultList;
	private transient Charset ascii=Charset.forName("US-ASCII");
	private transient Charset utf16=Charset.forName("UTF-16");
	
	private Datastructure datastructure;
	private DSField dsField;
	
	private String name;
	private DSType type;
	private long byteCount;
	private Long address;	
	private boolean isPointer;
	
	private transient Long pointerCache=null;
	private transient boolean pointerCacheOK=false;
	
	private transient Object valueCache=null;
	private transient boolean valueCacheOK=false;


	/*
	 * Four types of Results:
	 * 1) normal results on the root of the tree		(datastructure =null, dsField =null)
	 * 2) datastructures with children (fields)			(datastructure!=null, dsField =null)
	 * 3) Fields (children) of the datastructures		(datastructure =null, dsField!=null)
	 * 4) Fields (children) which are datastructures	(datastructure!=null, dsField!=null)
	 * 
	 *             |Not Custom|  Custom  |
	 * ------------+----------+----------+
	 * Not Relative|    1     |    2     |
	 * ------------+----------+----------+
	 *     Relative|    3     |    4     |
	 * ------------+----------+----------+
	 * 
	 */
	
	public Result(){
		
	}
	
	/* only for XMLEncoder */
	public Result(Long address, DSType type, String name, boolean isPointer){
		this(null, address, null, type, null, name, isPointer);
	}
	
	public Result(Long address, Object value, DSType type){
		this(null, address, value, type, null, type.name(), false);
	}
	
	public Result(Long address, Object value, DSType type, long byteCount){
		this(null, address, value, type, null, type.name(), false);
		setByteCount(byteCount);
	}
	
	public Result(ResultList resultList, Long address, Object value, DSType type, Datastructure ds, String name, boolean isPointer){
		this.resultList=resultList;
		this.address=address;
		this.valueCache=value;
		this.valueCacheOK=(value!=null);
		this.type=type;
		this.byteCount=type.getByteCount();
		this.name=name;
		this.isPointer=isPointer;	
		this.datastructure=ds;
	}
	
	/**
	 * Create Result from a DataStructure Field. This means this Result depends on the parent Result.
	 * The pointer is calculated from the base of the parent and the offset of the field.
	 * @param process
	 * @param parent
	 * @param field
	 */
	public Result(ResultList resultList, DSField field){
		this(resultList, null, null, field.getType(), null, field.getName(), false);
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
					Long base = p.getPointer();	//WARNING: Indirect recursion(getPointer->getAddress)
					if (base==null) 
						return null;
					else
						return base+p.getDatastructure().getOffset(dsField);	//If it is relative->parent is datastructure			
				}else{
					Long base = p.getAddress();	//WARNING : direct recursion (intentionally)
					if (base==null)
						return null;
					else
						return base+p.getDatastructure().getOffset(dsField);	//If it is relative->parent is datastructure
				}
			}else{
				log.warn("Relative Result without Parent (weird->needs fix)");	//FIXME results without parent
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
		default:
			return value.toString();
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
			getResultList().ReadProcessMemory(Pointer.createConstant(getAddress()), buffer, (int)buffer.getSize(), null);
			switch (getType()) {
				case Byte1:		valueCache=buffer.getByte     (0); break;
				case Byte2:		valueCache=buffer.getShort    (0); break;
				case Byte4:		valueCache=buffer.getInt      (0); break;
				case Byte8:		valueCache=buffer.getLong     (0); break;
				case Float:		valueCache=buffer.getFloat    (0); break;
				case Double:	valueCache=buffer.getDouble   (0); break;
				case Ascii:		
					valueCache=new String(buffer.getByteArray(0, (int)buffer.getSize()), ascii);
					break;
				case Unicode:	
					valueCache=new String(buffer.getByteArray(0, (int)buffer.getSize()), utf16);					
					break;
				case ByteArray:	
					valueCache=buffer.getByteArray(0, (int)buffer.getSize());	
					break;
				case Custom:	
					valueCache=null;											
					break;
			}
		} catch (Exception e) {
			log.warn(e);
			valueCache=null;
		}
		valueCacheOK=true;
		return valueCache;
	}
	
	public boolean getIsPointer(){
		return isPointer;
	}
	
	private Long getPointer(){
		if (!isPointer)
			return null;
		
		if(pointerCacheOK)
			return pointerCache;
		
		Memory buffer=new Memory(4);
		try {
			log.trace("Pointer: "+getAddressString());
			getResultList().ReadProcessMemory(Pointer.createConstant(getAddress()), buffer, (int)buffer.getSize(), null);
			pointerCache=(long)buffer.getInt(0);
		} catch (Exception e) {
			log.warn(e);
			pointerCache=null;
		}
		pointerCacheOK=true;
		return pointerCache;
	}
	
	private ResultList getResultList() throws Exception{
		if (resultList!=null){
			return resultList;
		}else {
			Object root=getRoot();
			if (root instanceof ResultList){
				log.warn("Result not linked to ResultList but has it as root");
				return ((ResultList)root);
			}else{
				Exception e= new Exception("Result not linked to ResultList");
				log.error(e);
				throw(e);
			}
		}
	}
	
	public String getPointerString() {
		Long p=getPointer();
		return p==null?null:String.format("%1$08X", p);
	}

	
	
	public Datastructure getDatastructure(){
		if (isRelative())
			return 	((Result)getParent()).getDatastructure();
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
	
	public void setByteCount(long size){
		log.trace("setByteCount "+size);
		invalidateCache();
		if (type==null || !type.isFixedSize() || size==type.getByteCount()){
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
	
	public void setIsPointer(boolean isPointer) {
		if (isCustom()){	//Only for datastructures? (design question)
			this.isPointer=isPointer;
			invalidateChildCache();
		}
	}
	
	public void setResultList(ResultList resultList) {
		this.resultList=resultList;		
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
			if (datastructure==null){
				return;
			}
			for (DSField field : datastructure.getFields())
				add(new Result(resultList, field));
		}
	}

	
	//ListDataListener (for datastructure)//////////////////////////////////////
	
	@Override
	public void contentsChanged(ListDataEvent listdataevent) {
		//TODO if name changes, no need to refresh the memory value
		
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
				add(new Result(resultList, field));
		}
	}

	//Object//////////////////////////////////////
	
	@Override
	public String toString() {
		return getAddressString()+" ["+getName()+"] "+(isCustom()?"":getValueString());
	}

	@Override
	public Result clone() {
		Result c = (Result)super.clone();
		return c;
	}




}
