package luz.eveMonitor.datastructure.python;

import java.util.HashMap;
import java.util.Map;

import luz.winapi.api.Process;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;

public class RowDescr extends PyObject{
	private Map<String, Column> columnsMap=null;
	
	public RowDescr(PyObject_VAR_HEAD head, Process process) {
		super(head, 100, process);
	}
	
	public int	getU1		(){return super.getInt( 0);}
	public int	getColumsPtr(){return super.getInt( 4);}
	public int	getU2		(){return super.getInt( 8);}
	public int	getU3		(){return super.getInt(12);}
	public int	getU4		(){return super.getInt(16);}
	public int	getU5		(){return super.getInt(20);}
	public int	getU6		(){return super.getInt(24);}
	public int	getColums	(){return super.getInt(28);}

	
	public Column getColumn(String columnName){
		return columnsMap.get(columnName);
	}
	
	@Override
	public void read() throws Exception {
		super.read();
		int colSize=Column.ColumnSize;
		Memory buffer=new Memory(getColums()*colSize);
		process.ReadProcessMemory(Pointer.createConstant(getColumsPtr()+4), buffer, (int)buffer.getSize(), null);

		columnsMap=new HashMap<String, Column>();
		for (int i = 0; i < getColums(); i++){				
			Column col = new Column(buffer.getByteArray(i*colSize, colSize));
			columnsMap.put(col.getName(), col);
		}
	}

	public class Column extends Memory{
		public static final int ColumnSize=16+4+4+4+2+1+5;
		public Column(){
			super(ColumnSize);
		}
		public Column(byte array[]){
			super(ColumnSize);
			this.getByteBuffer(0, this.size).put(array);
		}
		
		public String		getName()		{ return super.getString(0);}	
		public int			getLength()		{ return super.getInt  (16);}	
		public int			getMaxLength()	{ return super.getInt  (20);}
		public int			getOffset()		{ return super.getInt  (24);}
		public ColumnType	getType()		{ 
			short type=super.getShort(28);
			switch(type){
				case 2:		return ColumnType.Short;
				case 3:		return ColumnType.Int;
				case 5:		return ColumnType.Double;
				case 6:		return ColumnType.Long;
				case 11:	return ColumnType.Boolean;
				case 64:	return ColumnType.Date;
				default:
					System.err.println("UnknownType "+type+" ("+getName()+")");
					return ColumnType.Unknown;
			}
		}
		public byte		getBytes()		{ return super.getByte (30);}
	}
	
	public enum ColumnType{
		Byte,
		Short,
		Int,
		Long,
		Boolean,
		Float,
		Double,
		Date,
		String,
		Unknown
	}
	
	
	
}
