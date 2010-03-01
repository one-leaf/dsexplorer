package luz.eveMonitor.datastructure.python;

import luz.winapi.api.Process;

public class PyInt extends PyObject{

	public PyInt(PyObject_VAR_HEAD head, Process process) {
		super(head, 4, process);
	}
	
	public int	getob_ival()   {return super.getInt  ( 0);}	

	
	@Override
	public String toString() {
		return ""+getob_ival();
	}

}
