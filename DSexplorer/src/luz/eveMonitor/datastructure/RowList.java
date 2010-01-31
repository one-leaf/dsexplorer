package luz.eveMonitor.datastructure;

import java.util.Iterator;

import luz.dsexplorer.winapi.api.Process;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public class RowList extends PyObject{

	public RowList(PyObject_VAR_HEAD head, Process process) {
		super(head, 24, process);
	}
	
	public int		getElements(){return super.getInt   (0);}	//24
	public int		getListPtr (){return super.getInt   (4);}
	public int		getListSize(){return super.getInt   (8);}
	public int		getU3      (){return super.getInt   (12);}
	public int		getU4      (){return super.getInt   (16);}
	public int		getU5      (){return super.getInt   (20);}
	

	public DBRow getElement(int i) {
		IntByReference val = new IntByReference();
		try {
			process.ReadProcessMemory(Pointer.createConstant(getListPtr()+(4*i)), val.getPointer(), 4, null);
			return	(DBRow)PyObjectFactory.getObject(val.getValue(), process, false);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	public Iterator<DBRow> getIterator() {
		return new Iterator<DBRow>() {
			int index=0;
			int limit=getElements();

			@Override
			public boolean hasNext() {
				return index<limit;
			}

			@Override
			public DBRow next() {
				return getElement(index++);
			}

			@Override
			public void remove() {}
		};
	}
}
