package luz.eveMonitor.datastructure.python;

import java.util.HashMap;
import java.util.Map;

import luz.winapi.api.Process;

public class PyObjectFactoryCached {
    private static PyObjectFactoryCached instance=new PyObjectFactoryCached();
    private static Map<Long, PyObject> cache=new HashMap<Long, PyObject>();

    private PyObjectFactoryCached() {}

    public synchronized static PyObjectFactoryCached getInstance() {
        return instance;
    }
    
	public static PyObject getObject(long address, Process process, boolean raw){
		Long key=raw?address:address-PyObject_VAR_HEAD.SIZE;
		PyObject obj = cache.get(key);
		if(obj==null){
			obj=PyObjectFactory.getObject(address, process, raw);
			cache.put(key, obj);
		}
		return obj;		
	}
    
    
    
    
}
