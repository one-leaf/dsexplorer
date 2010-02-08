package luz.dsexplorer.search;

import luz.dsexplorer.datastructures.DSType;
import luz.dsexplorer.datastructures.Result;
import luz.dsexplorer.datastructures.ResultListImpl;
import luz.winapi.api.Process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.jna.Memory;

public class FloatListener extends AbstractMemoryListener {
	private static final Log log = LogFactory.getLog(FloatListener.class);
	private int overlapping=3;
	private DSType type=DSType.Float;
	private int value;
	
	@Override
	public void init(Process process, String value) {
		this.results= new ResultListImpl(process);
		this.value=Math.round(Float.parseFloat(value));
	}
	
	@Override
	public void mem(Memory outputBuffer, long address, long size) {
		Float current;
		for (long pos = 0; pos < size-overlapping; pos=pos+1) {
			current=outputBuffer.getFloat(pos);
			if (Math.round(current)==this.value){
				add(new Result(getResultList(), this.type.getInstance(), address+pos, current));
				log.debug("Found:\t"+Long.toHexString(address+pos));
			}
		}
	}

}

