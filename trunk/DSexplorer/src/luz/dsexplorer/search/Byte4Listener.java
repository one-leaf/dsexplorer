package luz.dsexplorer.search;

import luz.dsexplorer.datastructures.DSType;
import luz.dsexplorer.datastructures.Result;
import luz.dsexplorer.datastructures.ResultListImpl;
import luz.winapi.api.Process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.jna.Memory;

public class Byte4Listener extends AbstractMemoryListener {
	private static final Log log = LogFactory.getLog(Byte4Listener.class);
	private int overlapping=3;
	private DSType type=DSType.Byte4;
	private int value;
	
	@Override
	public void init(Process process, String value) {
		this.results= new ResultListImpl(process);
		this.value=Integer.parseInt(value);
	}
	
	@Override
	public void mem(Memory outputBuffer, long address, long size) {
		for (long pos = 0; pos < size-overlapping; pos=pos+1) {
			int current=outputBuffer.getInt(pos);
			
			if (this.value==current){
				add(new Result(getResultList(), this.type.getInstance(), address+pos, current));
				log.debug("Found:\t"+Long.toHexString(address+pos));
			}
		}
	}

}

