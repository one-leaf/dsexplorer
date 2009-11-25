package luz.dsexplorer.objects;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;

public class Result extends DefaultMutableTreeNode{
	private static final long serialVersionUID = 4163723615095260358L;
	private Long pointer;
	private Type type;
	private Object valueCache=null;
	private boolean valueCacheOK=false;
	
	private int size;
	private Process process;
	private String customName="new custom";
	private static final Log log = LogFactory.getLog(Result.class);
	
	public enum Type{
		//           fix, 	size
		Byte1		(true,	 1), 
		Byte2		(true,	 2), 
		Byte4		(true,	 4), 
		Byte8		(true,	 8), 
		Float		(true,	 4), 
		Double		(true,	 8), 
		Ascii		(false,	32), 
		Unicode		(false,	32), 
		ByteArray	(false,	32), 
		Custom		(true ,	 0);
		private int size;
		private boolean fixedSize;

		Type(boolean fixedSize, int size){
			this.fixedSize=fixedSize;
			this.size=size;
		}
		
		public int getSize()	       {return size;}
		public boolean isFixedSize() {return fixedSize;}
	}
	
	
	public Result(Process process, Long pointer, Object value, Type type){
		this.process=process;
		this.pointer=pointer;
		this.valueCache=value;
		this.valueCacheOK=(value!=null);
		this.type=type;
		this.size=type.getSize();
	}
	
	public String getName(){
		if (isCustom())
			return customName;
		else
			return type.name();
	}
	
	public long getPointer(){
		if (isRelative()){
			Result parent = (Result)getParent();
			long p = parent.getPointer();
			int i=0;
			Result child = (Result)parent.getChildAt(i);
			while(!child.equals(this)){
				p+=child.getSize();
				i++;
				child = (Result)parent.getChildAt(i);
			}
			return p;
		}
		return pointer;
	}
	
	public String getPointerString(){
		Long p=getPointer();
		return p==null?null:String.format("%1$08X", p);
	}
	
	public Type getType() {
		return type;
	}
	
	public int getSize() {
		if (isCustom()){
			size=0;
			for (int i = 0; i < getChildCount(); i++) {
				size+=((Result)getChildAt(i)).getSize();
			}
		}
		return size;
	}
	
	public String getValueString(){
		StringBuilder sb;
		switch (type) {
		case ByteArray:
			sb = new StringBuilder();
			byte[] bytes=(byte[])getValue();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(String.format("%1$02X", bytes[i]));
			}
			return sb.toString();
			
		case Ascii:
			sb = new StringBuilder();
			byte[] chars=(byte[])getValue();
			for (int i = 0; i < chars.length; i++) {
				sb.append((char)chars[i]);
			}
			return sb.toString();			
			
		default:
			return getValue()==null?null:getValue().toString();
		}
	}
	
	public Object getValue(){
		if(valueCacheOK)
			return valueCache;
		
		if (size==0)
			return null;
		
		Memory buffer=new Memory(size);
		try {
			log.trace("Read: "+getPointerString());
			process.ReadProcessMemory(Pointer.createConstant(getPointer()), buffer, (int)buffer.getSize(), null);
		} catch (Exception e) {
			return null;
		}
		switch (type) {
			case Byte1:		valueCache=buffer.getByte     (0);			break;
			case Byte2:		valueCache=buffer.getShort    (0);			break;
			case Byte4:		valueCache=buffer.getInt      (0);			break;
			case Byte8:		valueCache=buffer.getLong     (0);			break;
			case Float:		valueCache=buffer.getFloat    (0);			break;
			case Double:	valueCache=buffer.getDouble   (0);			break;
			case Ascii:		valueCache=buffer.getByteArray(0, size);	break;//Bounds exceeds available space
			case Unicode:	valueCache=buffer.getString   (0);			break;
			case ByteArray:	valueCache=buffer.getByteArray(0, size);	break;
			case Custom:	valueCache=null;							break;
		}
		valueCacheOK=true;
		return valueCache;
	}
	
	public String setName(String name){
		return customName=name;
	}
	
	public void setPointer(Long pointer){
		invalidateCache();
		if (isCustom()){
			for (int i = 0; i < getChildCount(); i++) 
				((Result)getChildAt(i)).invalidateCache();
		}
		this.pointer=pointer;
	}
	
	public void setType(Type type) {
		invalidateCache();
		this.type = type;
		setSize(type.getSize());
		
		if (type.equals(Type.Custom)){
			allowsChildren=true;
		}else{
			removeAllChildren();
		}
	}
	
	public void setSize(int size){
		invalidateCache();
		if(isRelative()){
			Result parent = (Result)getParent();
			for (int i = parent.getIndex(this); i < parent.getChildCount(); i++) 
				((Result)parent.getChildAt(i)).invalidateCache();
		}
		
		if (!type.isFixedSize() || size==type.size){
			this.size=size;
		}
	}
	
	public void setValue(Object value) {
		invalidateCache();
		this.valueCache=value;
		//TODO write mem
	}
	
	public boolean isCustom(){
		return Type.Custom.equals(type);
	}
	
	public boolean isRelative(){
		TreeNode parent = getParent();
		if (parent!=null && parent instanceof Result){
			return ((Result)parent).isCustom();
		}
		return false;
	}
	
	public void invalidateCache(){
		valueCacheOK=false;
	}
	
	////////////////////////////////////////

	@Override
	public boolean isLeaf() {
		return !isCustom();
	}
	
	private boolean areChildrenDefined=false;
	
	@Override
	public int getChildCount() {
		if (!areChildrenDefined)
			defineChildNodes();
		return super.getChildCount();
	}
	
	private void defineChildNodes(){
		areChildrenDefined=true;
	}
	
	
	public Result addCustomEntry(Type type){
//		Long p;
//		int count = getChildCount();
//		if (count>0){
//			Result last = (Result)getChildAt(count-1);
//			p=last.getPointer()+last.getSize();
//		}else{
//			p = this.pointer;
//		}
		Result r = new Result(this.process, null, null, type);
		add(r);
		return r;
		
	}
	
	public void removeCustomEntry(Result result){
		remove(result);
	}
	
	@Override
	public String toString() {
		return getPointerString()+" ["+getName()+"] "+(isCustom()?"":getValueString());
	}




}
