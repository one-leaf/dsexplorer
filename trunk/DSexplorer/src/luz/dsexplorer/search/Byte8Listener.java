package luz.dsexplorer.search;

import luz.dsexplorer.datastructures.DSType;
import luz.dsexplorer.datastructures.Result;
import luz.winapi.api.Process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.jna.Memory;

public class Byte8Listener extends AbstractMemoryListener {
	private static final Log log = LogFactory.getLog(Byte8Listener.class);
	private int overlapping=7;
	private DSType type=DSType.Byte8;
	private long value;
	
	public Byte8Listener(Process process) {
		super(process);
	}
	
	@Override
	public void init(Object value) {
		this.value=(Long)value;
	}
	
	@Override
	public void mem(Memory outputBuffer, long address, long size) {
		long current;
		for (long pos = 0; pos < size-overlapping; pos=pos+1) {
			current=outputBuffer.getLong(pos);
			if (this.value==current){
				add(new Result(getResultList(), this.type.getInstance(), address+pos, current));
				log.debug("Found:\t"+Long.toHexString(address+pos));
			}
		}
	}

}

