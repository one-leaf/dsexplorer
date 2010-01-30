package luz.eveMonitor.datastructure;

import luz.dsexplorer.winapi.api.Process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PyObjectFactory {
	private static final Log log = LogFactory.getLog(PyObjectFactory.class);
	
	public static PyObject getObject(long address, Process process, boolean raw){
		if (raw==false)
			address=address-4*4;	//next, prev, u1, u2
		PyObject_VAR_HEAD head;
		try {
			head=new PyObject_VAR_HEAD(address, process);
			head.read();
		} catch (Exception e) {
			log.warn("cannot read PyObject_VAR_HEAD @ "+String.format("%08X", address));
			return null;
		}
		PyObject obj=null;
		try {
			String sType=head.getTypeString();
			if("dict".equals(sType)){
				obj = new PyDict(head, process);
				obj.read();
			}else if("str".equals(sType)){
				obj = new PyStr(head, process);
				obj.read();
			}else if("int".equals(sType)){
				obj = new PyInt(head, process);
				obj.read();
			}else if("list".equals(sType)){
				obj = new PyList(head, process);
				obj.read();
			}else if("blue.DBRow".equals(sType)){
				obj = new DBRow(head, process);
				obj.read();
			}else if("RowList".equals(sType)){
				obj = new RowList(head, process);
				obj.read();
			}else{
				log.warn("unknown object type "+sType+" @ "+String.format("%08X", address));
			}
		} catch (Exception e) {
			log.warn("cannot read PyObject Data @ "+String.format("%08X", address));
		}
		return obj;
	}

}
