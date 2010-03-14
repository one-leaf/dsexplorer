package luz.eveMonitor.datastructure.python;

import luz.winapi.api.Process;
import luz.winapi.api.exception.Kernel32Exception;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;

public class PyObject_VAR_HEAD extends Memory{
	private long address;
	private Process process;
	private String typeString=null;

	public PyObject_VAR_HEAD(long address, Process process){
		super(6*4);
		this.address=address;
		this.process=process;
	}
	public long	getAddress	  (){return address;}	
	public int		getNextPtr	  (){return super.getInt   ( 0);}	
	public int		getPrevPtr	  (){return super.getInt   ( 4);}
	public int		getU1         (){return super.getInt   ( 8);}	
	public int		getU2         (){return super.getInt   (12);}	
	public int		getRefCount	  (){return super.getInt   (16);}	
	public int		getTypePtr	  (){return super.getInt   (20);}
	public String	getTypeString (){
		if (typeString!=null)
			return typeString;
		Memory buf2 = new Memory(32);
		int nameAddr=0;
		//Pointer
		try{
			process.ReadProcessMemory(Pointer.createConstant(getTypePtr()), buf2, (int)buf2.getSize(), null);
			nameAddr=buf2.getInt(12);
		}catch (Kernel32Exception e){
			System.out.println("cannot get typePtr "+String.format("%08X", getTypePtr()));
			return typeString;
		}
		//Object
		try{
			process.ReadProcessMemory(Pointer.createConstant(nameAddr), buf2, (int)buf2.getSize(), null);
			typeString=buf2.getString(0);
		}catch (Kernel32Exception e){
			System.out.println("cannot get typeString "+String.format("%08X", nameAddr));
		}
		return typeString;	
	}
	
	public void read() throws Kernel32Exception {
		process.ReadProcessMemory(Pointer.createConstant(getAddress()), this, (int)this.getSize(), null);
	}
}
