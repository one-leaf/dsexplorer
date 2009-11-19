package luz.dsexplorer.objects;

import java.lang.reflect.Array;

import javax.swing.tree.DefaultMutableTreeNode;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;

public class Result extends DefaultMutableTreeNode{
	private static final long serialVersionUID = 4163723615095260358L;
	private Long pointer;
	private boolean areChildrenDefined=false;
	private Type type;
	private Object value=null;
	private int size;
	private Process process;
	public enum Type{
		//           fix, 	size, class
		Byte1		(true,	1,	Byte.class), 
		Byte2		(true,	2,	Short.class), 
		Byte4		(true,	4,	Integer.class), 
		Byte8		(true,	8,	Long.class), 
		Float		(true,	4,	Float.class), 
		Double		(true,	8,	Double.class), 
		Ascii		(false,	32,	String.class), 
		Unicode		(false,	32,	String.class), 
		ByteArray	(false,	32,	Array.class), 
		Custom		(false,	32,	Object.class);
		private int size;
		@SuppressWarnings("unchecked")
		private Class clazz;
		private boolean fixedSize;
		@SuppressWarnings("unchecked")
		Type(boolean fixedSize, int size, Class clazz){
			this.fixedSize=fixedSize;
			this.size=size;
			this.clazz=clazz;
		}
		
		public int getSize()	       {return size;}
		@SuppressWarnings("unchecked")
		public Class getClazz()	       {return clazz;}
		public boolean isFixedSize() {return fixedSize;}
	}
	
	public Result(Process process, Long pointer, Object value, Type type){
		this.process=process;
		this.pointer=pointer;
		this.value=value;
		this.type=type;
		this.size=type.getSize();
	}
	
	public long getPointer(){
		return pointer;
	}
	
	public String getPointerString(){
		return pointer==null?null:String.format("%1$08X", pointer);
	}
	
	public Type getType() {
		return type;
	}
	
	public int getSize() {
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
			return value.toString();
		}
	}
	
	public Object getValueRecent(){
		Memory buffer=new Memory(size);
		try {
			process.ReadProcessMemory(Pointer.createConstant(pointer), buffer, (int)buffer.getSize(), null);
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
		case Custom:	value=buffer.getByteArray(0, size);	break;
		}
		return value;
	}
	
	public void setPointer(Long pointer){
		this.pointer=pointer;
		getValueRecent();
	}
	
	public void setType(Type type) {
		this.type = type;
		if (type.isFixedSize())
			this.size=type.getSize();
		getValueRecent();
	}
	
	public void setSize(int size){
		if (size>0 && !type.isFixedSize()){
			this.size=size;
			getValueRecent();
		}
	}
	
	public void setValue(Object value) {
		this.value=value;
		//TODO write mem
	}
	
	////////////////////////////////////////

	@Override
	public boolean isLeaf() {
		return true;
	}
	
	@Override
	public int getChildCount() {
		if (!areChildrenDefined)
			defineChildNodes();
		return super.getChildCount();
	}
	
	
	private void defineChildNodes() {
		areChildrenDefined = true;
		//add(new Result(this.process, null, null, Type.Byte4));
	}
	
	@Override
	public String toString() {
		return getPointerString()+" - "+getValue();
	}




}
