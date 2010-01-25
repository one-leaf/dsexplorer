package luz.eveMonitor.datastructure;

import luz.dsexplorer.winapi.api.Process;

public class PyList extends PyObject{

	public PyList(long addr, Process process) {
		super(addr, process);
	}
	
	public int		getElements(){return super.getInt   (24);}
	public int		getListPtr (){return super.getInt   (28);}
	public int		getListSize(){return super.getInt   (32);}
	public int		getU3      (){return super.getInt   (36);}
	public int		getU4      (){return super.getInt   (40);}
	public int		getU5      (){return super.getInt   (44);}

}
