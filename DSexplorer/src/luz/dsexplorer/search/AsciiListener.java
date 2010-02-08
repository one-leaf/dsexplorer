package luz.dsexplorer.search;

import java.nio.charset.Charset;

import luz.dsexplorer.datastructures.DSType;
import luz.dsexplorer.datastructures.Datastructure;
import luz.dsexplorer.datastructures.Result;
import luz.dsexplorer.datastructures.ResultListImpl;
import luz.winapi.api.Process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.jna.Memory;

public class AsciiListener extends AbstractMemoryListener {
	private static final Log log = LogFactory.getLog(AsciiListener.class);
	private Charset ascii=Charset.forName("US-ASCII");
	private DSType type=DSType.Ascii;
	private int targetSize;
	private String value;
	
	@Override
	public void init(Process process, String value) {
		this.results= new ResultListImpl(process);
		this.overlapping=value.length();
		this.targetSize=value.length();
		this.value=value;		
	}
	
	@Override
	public void mem(Memory outputBuffer, long address, long size) {
		String current;
		for (long pos = 0; pos < size-overlapping; pos=pos+1) {
			current = new String(outputBuffer.getByteArray(pos, this.targetSize), ascii);
			if (current.equalsIgnoreCase(this.value)){
				Datastructure ds = this.type.getInstance();
				ds.setByteCount(this.targetSize);
				add(new Result(getResultList(), ds, address+pos, current));
				log.debug("Found:\t"+Long.toHexString(address+pos));
			}
		}
	}

}

