package luz.eveMonitor.threads;

import luz.winapi.api.Process;
import luz.winapi.api.ProcessList;
import luz.winapi.api.WinAPI;
import luz.winapi.api.WinAPIImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public  class ProcessFinder extends Thread{
	private static final Log log = LogFactory.getLog(ProcessFinder.class);
	private Status status=null;
	private boolean run=true;
	private WinAPI winApi= WinAPIImpl.getInstance();	
	
	public ProcessFinder(Status status) {
		this.status=status;
	}
	
	
	@Override
	public void run() {
		begin:
		while(run){
			status.setProcess(findProcess());
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
	
	
	public Process findProcess(){
		ProcessList pl = winApi.getProcessList();
		for (Process p : pl) {
			if (p.getSzExeFile().endsWith("ExeFile.exe")){
				return p;
			}			
		}
		return null;
	}

}