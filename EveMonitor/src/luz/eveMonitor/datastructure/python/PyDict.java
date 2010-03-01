package luz.eveMonitor.datastructure.python;

import java.util.Iterator;

import luz.winapi.api.Process;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;

public class PyDict extends PyObject {

	public PyDict(PyObject_VAR_HEAD head, Process process) {
		super(head, 20, process);
	}
	
	public int		getMa_Fill    (){return super.getInt   (0);}	//24
	public int		getMa_Used    (){return super.getInt   (4);}
	public int		getMa_Mask    (){return super.getInt   (8);}
	public int		getMa_Table   (){return super.getInt   (12);}
	public int		getMa_Lokup   (){return super.getInt   (16);}
	public PyDictEntry	getDictEntry(int i){
		//TDODO get entry with hash mod ma_mask = slot
		PyDictEntry entry=new PyDictEntry(process);
		try {
			process.ReadProcessMemory(Pointer.createConstant(getMa_Table()+(4*3)*i), entry, (int)entry.getSize(), null);
			return entry;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Iterator<PyDictEntry> getDictEntries(){
		//TODO get all dictentries togeather
		return new Iterator<PyDictEntry>() {
			int index=0;
			int limit=getMa_Mask()+1;

			@Override
			public boolean hasNext() {
				return index<limit;
			}

			@Override
			public PyDictEntry next() {
				return getDictEntry(index++);
			}

			@Override
			public void remove() {}
		};
	}
	
	public class PyDictEntry extends Memory{
		private Process process;
		
		public PyDictEntry(Process process){
			super(3*4);
			this.process=process;
		}
		
		public int getHash()		{ return super.getInt(0);}	
		public int getKeyPtr()		{ return super.getInt(4);}	
		public int getValuePtr()	{ return super.getInt(8);}
		
		public PyObject getValue(){
			if(getValuePtr()!=0)
				return PyObjectFactory.getObject(getValuePtr(), process, false);
			else
				return null;
		}

		public PyObject getKey() {
			if(getKeyPtr()!=0)
				return PyObjectFactory.getObject(getKeyPtr(), process, false);
			else
				return null;
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (o==null)
			return false;
		
		if (!(o instanceof PyDict))
			return false;
		
		if(((PyDict)o).getAddress()!=this.getAddress())
			return false;
		
		return true;
	}

}