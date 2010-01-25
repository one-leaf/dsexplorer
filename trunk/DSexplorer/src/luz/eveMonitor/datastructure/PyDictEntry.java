package luz.eveMonitor.datastructure;

import luz.dsexplorer.winapi.api.Process;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;

public class PyDictEntry {
	private Process process;
	private int hash;
	private int keyPtr;
	private int ValuePtr;
	
	public PyDictEntry(int hash, int keyPtr, int valuePtr, Process process) {
		this.hash=hash;
		this.keyPtr=keyPtr;
		this.ValuePtr=valuePtr;
	}
	
	public int getHash(){
		return hash;
	}
	
	public int getKeyPtr(){
		return keyPtr;
	}
	
	public int getValuePtr(){
		return ValuePtr;
	}
	
	public PyObject getValue(){
		Memory buf2 = new Memory(4);
		try {
			process.ReadProcessMemory(Pointer.createConstant(getValuePtr()), buf2, (int)buf2.getSize(), null);
			int address=buf2.getInt(0);
			return new PyObject(address, process);
		} catch (Exception e) {
			//System.out.println("cannot get Value");
			return null;
		}

	}
	
	
}
