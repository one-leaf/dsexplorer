package luz.eveMonitor.datastructure;

import luz.dsexplorer.winapi.api.Process;

public class RowList extends PyList{

	public RowList(PyObject_VAR_HEAD head, Process process) {
		super(head, process);
	}

}
