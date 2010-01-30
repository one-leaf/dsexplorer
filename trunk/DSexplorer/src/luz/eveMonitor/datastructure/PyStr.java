package luz.eveMonitor.datastructure;

import luz.dsexplorer.winapi.api.Process;

public class PyStr extends PyObject{

	public PyStr(PyObject_VAR_HEAD head, Process process) {
		super(head, 32, process);
	}
	
	//TODO verify these offsets
	public int		getV()         {return super.getInt   ( 0);}	
	public int		getOb_sstate (){return super.getInt   ( 8);}
	public String	getOb_sval()   {return super.getString(12);}
	
	@Override
	public String toString() {
		return getOb_sval();
	}

}
