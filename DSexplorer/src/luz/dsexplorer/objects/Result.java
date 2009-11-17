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
	private Process process;
	public enum Type{
		Byte1		(1, Byte.class), 
		Byte2		(2, Short.class), 
		Byte4		(4, Integer.class), 
		Byte8		(8, Long.class), 
		Float		(4, Float.class), 
		Double		(8, Double.class), 
		Ascii		(256, String.class), 
		Unicode		(256, String.class), 
		ByteArray	(256, Array.class), 
		Custom		(256, Object.class);
		private int size;
		@SuppressWarnings("unchecked")
		private Class clazz;
		@SuppressWarnings("unchecked")
		Type(int size, Class clazz){
			this.size=size;
			this.clazz=clazz;
		}
		
		public int getSize()	{return size;}
		@SuppressWarnings("unchecked")
		public Class getClazz()	{return clazz;}
	}
	
	public Result(Process process, Long pointer, Object value, Type type){
		this.process=process;
		this.pointer=pointer;
		this.value=value;
		this.type=type;
	}
	
	public long getPointer(){
		return pointer;
	}
	
	public String getPointerString(){
		return pointer==null?null:String.format("%1$08X", pointer);
	}
	
	public void setPointer(Long pointer){
		this.pointer=pointer;
		getValueRecent();
	}
	
	public void setType(Type type) {
		this.type = type;
		getValueRecent();
	}

	public Type getType() {
		return type;
	}
	
	public Object getValue() {
		return value;
	}
	
	public Object getValueRecent(){
		Memory buffer=new Memory(type.getSize());
		try {
			process.ReadProcessMemory(Pointer.createConstant(pointer), buffer, (int)buffer.getSize(), null);
		} catch (Exception e) {
			return null;
		}
		switch (type) {
		case Byte1:		value=buffer.getByte(0);								break;
		case Byte2:		value=buffer.getShort(0);								break;
		case Byte4:		value=buffer.getInt(0);									break;
		case Byte8:		value=buffer.getLong(0);								break;
		case Float:		value=buffer.getFloat(0);								break;
		case Double:	value=buffer.getDouble(0);								break;
		case Ascii:		value=buffer.getCharArray(0L, (int)buffer.getSize());	break;
		case Unicode:	value=buffer.getString(0);								break;
		case ByteArray:	value=buffer.getByteArray(0L, (int)buffer.getSize());	break;
		case Custom:	value=buffer.getByteArray(0L, (int)buffer.getSize());	break;
		}
		return value;
	}
	
	public void setValue(Object value) {
		this.value=value;
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
