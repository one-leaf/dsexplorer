package luz.eveMonitor.threads;

import java.util.List;

import luz.eveMonitor.datastructure.python.PyDict;
import luz.eveMonitor.datastructure.python.PyObject;
import luz.eveMonitor.datastructure.python.PyObjectFactory;
import luz.eveMonitor.utils.PointerListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

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

			//PHASE 2 - find Dict ///////////////////////////////////////
			status.getCache();
			if(status.getDict()==null){
				Logger logger = Logger.getRootLogger();
				logger.setLevel(Level.WARN);	
				status.setDict(findDict());
				logger.setLevel(Level.ALL);
				log.debug("Found Dict "+status.getDict());
			}
			
			//PHASE 3 - sleep ///////////////////////////////////////
			if(status.getDict()!=null){
				synchronized (status) {
					try {
						status.wait();
					} catch (InterruptedException e) {
						continue begin;
					}
				}
			}
		}
	}
	
	public void stopNow(){
		run=false;
		this.interrupt();
	}

	
	
	
	
	
	
	
	
	public PyDict findDict(){
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
					System.out.println(String.format("findDict-result: %08X -> %08X", res, val.getValue()));
					return (PyDict)PyObjectFactory.getObject(val.getValue(),status.getProcess(), false);					
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return null;		
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
