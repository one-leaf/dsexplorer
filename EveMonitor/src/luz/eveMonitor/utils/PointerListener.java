package luz.eveMonitor.utils;

import java.util.LinkedList;
import java.util.List;

import luz.winapi.api.MemoryListener;
import luz.winapi.api.Process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.jna.Memory;

public class PointerListener implements MemoryListener {
	private static final Log log = LogFactory.getLog(PointerListener.class);
	private List<Long> results;
	private int overlapping=3;
	private int value;
	
	@Override
	public void init(Process process, String value) {
		this.results= new LinkedList<Long>();
		this.value=Integer.parseInt(value);
	}
	
	@Override
	public void mem(Memory outputBuffer, long address, long size) {
		for (long pos = 0; pos < size-overlapping; pos=pos+1) {
			int current=outputBuffer.getInt(pos);
			
			if (this.value==current){
				this.results.add(address+pos);
				log.debug("Found:\t"+Long.toHexString(address+pos));
			}
		}
	}

	@Override
	public int getOverlapping() {
		return overlapping;
	}

	@Override
	public List<Long> getResults() {
		return results;
	}

}

