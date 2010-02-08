package luz.dsexplorer.search;

import java.nio.charset.Charset;

import luz.dsexplorer.datastructures.DSType;
import luz.dsexplorer.datastructures.Datastructure;
import luz.dsexplorer.datastructures.Result;
import luz.dsexplorer.datastructures.ResultListImpl;
import luz.dsexplorer.winapi.api.Process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.jna.Memory;

public class UnicodeListener extends AbstractMemoryListener {
	private static final Log log = LogFactory.getLog(UnicodeListener.class);
	private Charset utf16=Charset.forName("UTF-16");
	private DSType type=DSType.Unicode;
	private int targetSize;
	private String value;
	
	@Override
	public void init(Process process, String value) {
		this.results= new ResultListImpl(process);
		this.overlapping=value.length()*2;	//Assume utf16 = 2 bytes
		this.targetSize=value.length()*2;
		this.value=value.toLowerCase();		
	}
	
	@Override
	public void mem(Memory outputBuffer, long address, long size) {
		String current;
		for (long pos = 0; pos < size-overlapping; pos=pos+1) {
			current=outputBuffer.getString(pos, true).substring(0,this.targetSize/2);
			System.out.println(current);
			//current = new String(outputBuffer.getByteArray(pos, this.targetSize), utf16);
			//if (current.equalsIgnoreCase(this.value)){
			if(current.toLowerCase().startsWith(this.value)){
				Datastructure ds = this.type.getInstance();
				ds.setByteCount(this.targetSize);
				add(new Result(getResultList(), ds, address+pos, current));
				log.debug("Found:\t"+Long.toHexString(address+pos));
			}
		}
	}

}

