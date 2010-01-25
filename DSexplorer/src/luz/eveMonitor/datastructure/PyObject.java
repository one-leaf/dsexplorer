package luz.eveMonitor.datastructure;

import luz.dsexplorer.winapi.api.Process;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;

public class PyObject extends Memory{

	private long address;
	protected Process process;
	private String typeString=null;
	
	public PyObject(long addr, Process process){
		this(addr, process, 100);
	}
	
	
	
	public PyObject(long addr, Process process, int size){
		super(size);
		this.address=addr;
		this.process=process;
		try {

			process.ReadProcessMemory(Pointer.createConstant(addr), this, (int)this.getSize(), null);
		} catch (Exception e) {
			System.out.println("cannot fill PyObject");
		}
	}
	public long	getAddress    (){return address;}
	
	public int		getNextPtr	  (){return super.getInt   (0);}	
	public int		getPrevPtr	  (){return super.getInt   (4);}
	
	public PyObject getNext(){
		return new PyObject(getNextPtr(), process);
	}
	
	public PyObject getPrev(){
		return new PyObject(getPrevPtr(), process);
	}
	
	public int		getU1         (){return super.getInt   (8);}	
	public int		getU2         (){return super.getInt   (12);}
	
	public int		getRefCount	  (){return super.getInt   (16);}	
	public int		getTypePtr	  (){return super.getInt   (20);}
	public String	getTypeString (){
		if (typeString!=null)
			return typeString;
		Memory buf2 = new Memory(32);
		try{
			//Pointer
			process.ReadProcessMemory(Pointer.createConstant(getTypePtr()), buf2, (int)buf2.getSize(), null);
			int nameAddr=buf2.getInt(12);
			//System.out.println("name "+String.format("%08X", nameAddr));
			//Object
			process.ReadProcessMemory(Pointer.createConstant(nameAddr), buf2, (int)buf2.getSize(), null);
			typeString=buf2.getString(0);
		}catch (Exception e){
			System.out.println("cannot get typeString");
		}
		return typeString;	
	}
}
