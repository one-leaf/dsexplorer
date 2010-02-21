package luz.eveMonitor.threads;

import luz.eveMonitor.utils.Reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public  class ProcessFinder extends Thread{
	private static final Log log = LogFactory.getLog(ProcessFinder.class);
	private static Reader reader=new Reader();
	private Status status=null;
	private boolean run=true;
	
	public ProcessFinder(Status status) {
		this.status=status;
	}
	
	
	@Override
	public void run() {
		begin:
		while(run){
			status.setProcess(reader.findProcess());
			log.debug("checking eve process: "+status.getPid());
			try {
                sleep(2*1000);
            } catch (InterruptedException e){
            	continue begin;
            }
		}
	}
	
	public void stopNow(){
		run=false;
		this.interrupt();
	}

}