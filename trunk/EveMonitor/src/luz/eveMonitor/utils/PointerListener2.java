package luz.eveMonitor.utils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import luz.winapi.api.MemoryListener;
import luz.winapi.api.Process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.jna.Memory;

public class PointerListener2 implements MemoryListener {
	private static final Log log = LogFactory.getLog(PointerListener2.class);
	private List<Long> results;
	private int overlapping=8;
	private byte[] target;
	
	@Override
	public void init(Process process, String value) {
		this.results= new LinkedList<Long>();
		Integer i=Integer.parseInt(value);
		target=new byte[8];
		target[0]=(byte)((i>>0) & 0xFF);
		target[1]=(byte)((i>>1) & 0xFF);
		target[2]=(byte)((i>>2) & 0xFF);
		target[3]=(byte)((i>>3) & 0xFF);
		target[4]=(byte)((i>>4) & 0xFF);
		target[5]=(byte)((i>>5) & 0xFF);
		target[6]=(byte)((i>>6) & 0xFF);
		target[7]=(byte)((i>>7) & 0xFF);
	}
	
	@Override
	public void mem(Memory outputBuffer, long address, long size) {
		byte[] current;
		for (long pos = 0; pos < size-overlapping; pos=pos+1) {
			current = outputBuffer.getByteArray(pos, this.overlapping);
			if (Arrays.equals(target, current)){
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

