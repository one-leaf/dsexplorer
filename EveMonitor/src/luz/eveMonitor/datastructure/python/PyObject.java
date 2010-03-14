package luz.eveMonitor.datastructure.python;

import luz.winapi.api.Process;
import luz.winapi.api.exception.Kernel32Exception;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;

public class PyObject extends Memory{

	protected PyObject_VAR_HEAD head;
	protected Process process;

	
	public PyObject(PyObject_VAR_HEAD head, int dataSize, Process process){
		super(dataSize);
		this.head=head;
		this.process=process;
	}
	
	public long getAddress(){
		return head.getAddress();
	}
	
	public int getNextPtr(){
		return head.getNextPtr();
	}
	
	public int getPrevPtr(){
		return head.getPrevPtr();
	}
	
	public int getU1() {
		return head.getU1();
	}
	
	public int getU2() {
		return head.getU2();
	}
	
	public int getRefCount(){
		return head.getRefCount();
	}
	
	public PyObject getNext() throws Kernel32Exception{
		return PyObjectFactory.getObject(head.getNextPtr(), process, true);
	}
	
	public PyObject getPrev() throws Kernel32Exception{
		return PyObjectFactory.getObject(head.getPrevPtr(), process, true);
	}
	
	
	
	
	public void read() throws Kernel32Exception{
		process.ReadProcessMemory(Pointer.createConstant(getAddress()+head.getSize()), this, (int)this.getSize(), null);
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName()+"@"+String.format("%08X", getAddress());
	}



}
