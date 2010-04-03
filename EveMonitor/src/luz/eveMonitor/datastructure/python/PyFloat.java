package luz.eveMonitor.datastructure.python;

import luz.winapi.api.Process;

public class PyFloat extends PyObject{

	public PyFloat(PyObject_VAR_HEAD head, Process process) {
		super(head, 8, process);
	}
	
	public double	getob_fval()   {return super.getDouble  ( 0);}	

	
	@Override
	public String toString() {
		return ""+getob_fval();
	}

}
