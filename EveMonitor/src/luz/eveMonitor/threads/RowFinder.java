package luz.eveMonitor.threads;

import java.util.List;

import luz.eveMonitor.datastructure.DBRowMarket;
import luz.eveMonitor.gui.MainWindow;
import luz.eveMonitor.utils.Reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public  class RowFinder extends Thread{
	private static final Log log = LogFactory.getLog(RowFinder.class);
	private static Reader reader=new Reader();
	private MainWindow window;
	private Status status=null;
	private boolean run=true;
	
	public RowFinder(Status status, MainWindow window) {
		this.status=status;
		this.window=window;
	}
	
	
	@Override
	public void run() {
		begin:
		while(run){
			//PHASE 1 - wait for dict ///////////////////////////////////////
			while(status.getDict()==null){
				try {
	                synchronized(status){
	                	status.wait();
	                }
	            } catch (InterruptedException e){
	            	continue begin;
	            }				
			}
			log.debug("Eve dict is ready "+status.getDictAddr());
			reader.setProcess(status.getProcess());
			reader.setDict(status.getDict());
			
			//PHASE 2 - find Rows ///////////////////////////////////////
			while(status.getDict()!=null){
				List<DBRowMarket> list = reader.getNewRows();
				this.window.addRows(list);
				
				synchronized (status) {
					try {
						sleep(1*1000);
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