package luz.eveMonitor.datastructure;

import java.util.Date;

import luz.dsexplorer.winapi.api.Process;
import luz.eveMonitor.datastructure.RowDescr.Column;
import luz.eveMonitor.datastructure.RowDescr.ColumnType;

public class DBRow extends PyObject {
	
	public DBRow(PyObject_VAR_HEAD head, Process process) {
		super(head, 67, process);
	}

	public int		getRowDescrPtr(){return super.getInt   (0);}
	public RowDescr getRowDescr(){
		return (RowDescr)PyObjectFactory.getObject(getRowDescrPtr(), process, false);
	}	
	public int		getU4		  (){return super.getInt   (4);}

	
	public Object getColumnValue(String columnName){
		RowDescr rowDescr=getRowDescr();
		Column col=rowDescr.getColumn(columnName);
		ColumnType type=col.getType();
		switch(type){
		case Boolean:	return super.getByte  (8+col.getOffset()/8);
		case Byte:		return super.getByte  (8+col.getOffset());
		case Short:		return super.getShort (8+col.getOffset());
		case Int:		return super.getInt   (8+col.getOffset());
		case Long:		return super.getLong  (8+col.getOffset());
		case Float:		return super.getFloat (8+col.getOffset());
		case Double:	return super.getDouble(8+col.getOffset());
		case String:	return super.getString(8+col.getOffset());
		case Date:		
			return new Date((super.getLong(8+col.getOffset())-116444736000000000L)/10000L);
		default:		return super.getInt   (8+col.getOffset());
		}
	}

}