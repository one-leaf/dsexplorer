package luz.eveMonitor.datastructure.python;

import luz.winapi.api.Process;
import luz.winapi.api.exception.Kernel32Exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PyObjectFactory {
	private static final Log log = LogFactory.getLog(PyObjectFactory.class);
	
	public static PyObject getObject(long address, Process process, boolean raw) throws Kernel32Exception{
		if (raw==false)
			address=address-4*4;	//next, prev, u1, u2
		PyObject_VAR_HEAD head;
		try {
			head=new PyObject_VAR_HEAD(address, process);
			head.read();
		} catch (Kernel32Exception e) {
			log.warn("cannot read PyObject_VAR_HEAD @ "+String.format("%08X", address));
			return null;
		}
		PyObject obj=null;
		//TODO optimize this string mapping
		String sType=head.getTypeString();
		if("dict".equals(sType)){
			obj = new PyDict(head, process);
		}else if("str".equals(sType)){
			obj = new PyStr(head, process);
		}else if("int".equals(sType)){
			obj = new PyInt(head, process);
		}else if("list".equals(sType)){
			obj = new PyList(head, process);
		}else if("blue.DBRow".equals(sType)){
			obj = new DBRow(head, process);
		}else if("blue.DBRowDescriptor".equals(sType)){
			obj = new RowDescr(head, process);			
		}else if("RowList".equals(sType)){
			obj = new RowList(head, process);
		}
		if(obj==null){
			log.warn("unknown object type "+sType+" @ "+String.format("%08X", address));
		}else{
			obj.read();
		}
		return obj;
	}
	
	public static long pyStringHash(String string){
		int len = string.length();
		long x = string.charAt(0) << 7;
		for (int index=0; index < len; index++)
			x = (1000003*x) ^ string.charAt(index);
		x ^= len;
		x = x&0xFFFFFFFFL;	//unsigned int
		return x;
	}

}
