package luz.dsexplorer.search;

import luz.dsexplorer.datastructures.DSType;
import luz.dsexplorer.datastructures.Result;
import luz.winapi.api.Process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.jna.Memory;

public class DoubleListener extends AbstractMemoryListener {
	private static final Log log = LogFactory.getLog(DoubleListener.class);
	private int overlapping=7;
	private DSType type=DSType.Double;
	private long value;
	
	public DoubleListener(Process process) {
		super(process);
	}
	
	@Override
	public void init(Object value) {
		this.value=Math.round((Double)value);
	}
	
	@Override
	public void mem(Memory outputBuffer, long address, long size) {
		Double current;
		for (long pos = 0; pos < size-overlapping; pos=pos+1) {
			current=outputBuffer.getDouble(pos);
			if (Math.round(current)==this.value){
				add(new Result(getResultList(), this.type.getInstance(), address+pos, current));
				log.debug("Found:\t"+Long.toHexString(address+pos));
			}
		}
	}

}

