package luz.eveMonitor.threads;

import luz.eveMonitor.utils.Reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class DictFinder extends Thread{
	private static final Log log = LogFactory.getLog(DictFinder.class);
	private static Reader reader=new Reader();
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
			reader.setProcess(status.getProcess());

			//PHASE 2 - find Dict ///////////////////////////////////////
			status.getCache();
			if(status.getDict()==null){
				Logger logger = Logger.getRootLogger();
				logger.setLevel(Level.WARN);	
				status.setDict(reader.findDict());
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

}
