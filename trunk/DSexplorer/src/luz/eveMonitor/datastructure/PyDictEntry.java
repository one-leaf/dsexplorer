package luz.eveMonitor.datastructure;

import luz.dsexplorer.winapi.api.Process;

import com.sun.jna.Memory;

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
