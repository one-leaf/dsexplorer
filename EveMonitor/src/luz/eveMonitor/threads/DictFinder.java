package luz.eveMonitor.threads;

import java.util.List;

import luz.eveMonitor.datastructure.python.PyDict;
import luz.eveMonitor.datastructure.python.PyObject;
import luz.eveMonitor.datastructure.python.PyObjectFactory;
import luz.eveMonitor.utils.PointerListener;
import luz.winapi.api.exception.OpenProcessException;
import luz.winapi.api.exception.ReadProcessMemoryException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public class DictFinder extends Thread{
	private static final Log log = LogFactory.getLog(DictFinder.class);
	private Status status=null;
	private boolean run=true;
	
	public DictFinder(Status status) {
		this.status=status;
	}

	@Override
	public void run() {
		begin:
		while(run){
			//PHASE 1 - wait for eve ///////////////////////////////////////
			while(status.getProcess()==null){
				try {
	                synchronized(status){
	                	status.wait();
	                }
	            } catch (InterruptedException e){
	            	continue begin;
	            }				
			}
			log.debug("Eve process is ready "+status.getPid());

			//PHASE 2 - find Dict & wait///////////////////////////////////////
			if(!checkDict()){
				findDict();
			}
			
			synchronized (status) {
				try {
					status.wait(1*1000);
				} catch (InterruptedException e) {
					continue begin;
				}
			}
		}
	}
	
	public void stopNow(){
		run=false;
		this.interrupt();
	}

	
	
	public boolean checkDict(){
		PyDict dict=status.getDict();
		if(dict==null)
			return false;
		
		try {
			PyObject obj=PyObjectFactory.getObject(dict.getAddress(), status.getProcess(), true);
			
			if(!(obj instanceof PyDict)){
				log.warn("invalid dict: instance");
				return false;
			}
			
			if(dict.getRefCount()<1){
				log.warn("invalid dict: getRefCount");
				return false;
			}
			log.debug("renewing dict");
			status.setDict(dict);
			
		} catch (ReadProcessMemoryException e) {
			log.warn("invalid dict: ReadProcessMemoryException");
			return false;
		} catch (OpenProcessException e) {
			log.warn("invalid dict: OpenProcessException");
			return false;
		}

		return true;
	}
	
	
	public void findDict(){
		long beginAddr=0;
		long endAddr=0x23000000L;
		int dictHash=(int)pyStringHash("orderCache");

		try {
			PointerListener listener = new PointerListener();
			status.getProcess().search(beginAddr, endAddr, ""+dictHash, listener);
			List<Long> r = listener.getResults();
			for (int i = 0; i < r.size(); i++){
				long res=r.get(i);
				res=res+2*4;
				IntByReference val=new IntByReference();
				status.getProcess().ReadProcessMemory(Pointer.createConstant(res), val.getPointer(), 4, null);
				PyObject obj=PyObjectFactory.getObject(val.getValue(), status.getProcess(), false);
				if (obj instanceof PyDict){
					log.info(String.format("findDict-result: %08X -> %08X", res, val.getValue()));
					PyDict dict=(PyDict)PyObjectFactory.getObject(val.getValue(),status.getProcess(), false);;
					status.setDict(dict);
					return;				
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
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
