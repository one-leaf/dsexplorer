package luz.eveMonitor.threads;

import java.io.File;

import luz.eveMonitor.datastructure.python.PyDict;
import luz.eveMonitor.datastructure.python.PyObjectFactory;
import luz.winapi.api.Process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

@Root
public class Status {
	private static final Log log = LogFactory.getLog(Status.class);
	private static final long serialVersionUID = -7273434703969063635L;
	private static final String FILENAME = "eveCache.xml";
	
	private Process process;
	private PyDict dict;
	
	@Element
	private Integer pid;
	@Element
	private Long dictAddr;
	
	public Process getProcess() {
		return process;
	}
	
	public PyDict getDict() {
		return dict;			
	}
	
	
	public Integer getPid() {
		return pid;
	}
	
	public Long getDictAddr() {
		return dictAddr;
	}
	

	
	
	
	public void setProcess(Process process) {
		if (!areEqual(this.process, process)){
			this.process = process;
			this.dict=null;

			if(process!=null)
				this.pid=process.getPid();
			else
				this.pid=null;

			this.getDictCache();
			
			synchronized (this) {
				this.notifyAll();
			}	
		}
	}
	
	public void setDict(PyDict dict) {
		this.dict = dict;
		if(dict!=null)
			this.dictAddr=dict.getAddress();
		else
			this.dictAddr= null;
		
		try {
			Serializer serializer = new Persister();
			serializer.write(this, new File(FILENAME));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		synchronized (this) {
			this.notifyAll();
		}
	}
	
	static public boolean areEqual(Object aThis, Object aThat){
		return aThis == null ? aThat == null : aThis.equals(aThat);
	}
	
	public void getDictCache(){
		if (this.process!=null){			

			try {
				Serializer serializer = new Persister();
				Status p = serializer.read(Status.class, new File(FILENAME));
				
				if(this.getPid().equals(getPid())){
					log.debug("loading cache");					
					this.dictAddr=p.getDictAddr();
					this.dict=(PyDict)PyObjectFactory.getObject(dictAddr, process, true);
					
					synchronized (this) {
						this.notifyAll();
					}					
				}				
			} catch (Exception e) {
				log.warn(e);
			}
		}
	}


	
	
	



}
