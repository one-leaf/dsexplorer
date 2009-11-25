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
	private Object value=null;
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
		this.value=value;
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
		log.warn("get size "+size);
		return size;
	}
	
	public Object getValue() {
		return value;
	}
	
	public String getValueString(){
		StringBuilder sb;
		switch (type) {
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
	
	public Object getValueRecent(){
		if (size==0)
			return null;
		
		Memory buffer=new Memory(size);
		try {
			process.ReadProcessMemory(Pointer.createConstant(getPointer()), buffer, (int)buffer.getSize(), null);
		} catch (Exception e) {
			return null;
		}
		switch (type) {
			case Byte1:		value=buffer.getByte     (0);		break;
			case Byte2:		value=buffer.getShort    (0);		break;
			case Byte4:		value=buffer.getInt      (0);		break;
			case Byte8:		value=buffer.getLong     (0);		break;
			case Float:		value=buffer.getFloat    (0);		break;
			case Double:	value=buffer.getDouble   (0);		break;
			case Ascii:		value=buffer.getByteArray(0, size);	break;//Bounds exceeds available space
			case Unicode:	value=buffer.getString   (0);		break;
			case ByteArray:	value=buffer.getByteArray(0, size);	break;
			case Custom:	value=null;							break;
		}
		return value;
	}
	
	public String setName(String name){
		return customName=name;
	}
	
	public void setPointer(Long pointer){
		this.pointer=pointer;
	}
	
	public void setType(Type type) {
		this.type = type;
		this.size=type.getSize();
		
		if (type.equals(Type.Custom)){
			allowsChildren=true;
		}else{
			removeAllChildren();
		}
	}
	
	public void setSize(int size){
		if (size>0 && !type.isFixedSize()){
			log.warn("set size "+size);
			this.size=size;
		}
	}
	
	public void setValue(Object value) {
		this.value=value;
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
	
	////////////////////////////////////////

	
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
		return getPointerString()+"-"+getName()+"-"+getValueRecent();
	}




}
