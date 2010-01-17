package luz.eveMonitor.datastructure;

public class PyDictEntry {
	public int hash;
	public int keyPtr;
	public int ValuePtr;
	
	public PyDictEntry(int hash, int keyPtr, int valuePtr) {
		this.hash=hash;
		this.keyPtr=keyPtr;
		this.ValuePtr=valuePtr;
	}
	

}
