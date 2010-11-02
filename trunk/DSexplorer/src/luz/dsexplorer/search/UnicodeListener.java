package luz.dsexplorer.search;

import java.nio.charset.Charset;

import luz.dsexplorer.datastructures.DSType;
import luz.dsexplorer.datastructures.Datastructure;
import luz.dsexplorer.datastructures.Result;
import luz.winapi.api.Process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.jna.Memory;

public class UnicodeListener extends AbstractMemoryListener {
	private static final Log log = LogFactory.getLog(UnicodeListener.class);
	private Charset utf16=Charset.forName("UTF-16");
	private DSType type=DSType.Unicode;
	private int targetSize;
	private String value;
	
	public UnicodeListener(Process process) {
		super(process);
	}
	
	@Override
	public void init(Object value) {
		this.value=((String)value).toLowerCase();	
		this.overlapping=this.value.length()*2;	//Assume utf16 = 2 bytes
		this.targetSize=this.value.length()*2;	
	}
	
	@Override
	public void mem(Memory outputBuffer, long address, long size) {
		String current;
		for (long pos = 0; pos < size-overlapping; pos=pos+1) {
			current=outputBuffer.getString(pos, true);
			if(current.toLowerCase().startsWith(this.value)){
				Datastructure ds = this.type.getInstance();
				ds.setByteCount(this.targetSize);
				add(new Result(getResultList(), ds, address+pos, current));
				log.debug("Found:\t"+Long.toHexString(address+pos));
			}
		}
	}

}

