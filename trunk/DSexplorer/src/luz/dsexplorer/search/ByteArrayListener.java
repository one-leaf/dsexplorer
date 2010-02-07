package luz.dsexplorer.search;

import luz.dsexplorer.datastructures.DSType;
import luz.dsexplorer.datastructures.Datastructure;
import luz.dsexplorer.winapi.api.Result;
import luz.dsexplorer.winapi.api.ResultList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.jna.Memory;

public class ByteArrayListener extends AbstractMemoryListener {
	private static final Log log = LogFactory.getLog(ByteArrayListener.class);
	private DSType type=DSType.ByteArray;
	private int targetSize;
	private byte[] target;
	
	@Override
	public void init(ResultList results, String value) {
		this.results=results;
		value=value.trim();
		this.overlapping=(value.length()+1)/2;

		this.target = new byte[value.length()/2];
		for (int i = 0; i < target.length; i++)
		   target[i] = (byte) Integer.parseInt(value.substring(2*i, 2*i+2), 16);
		this.targetSize=target.length;		
	}
	
	@Override
	public void mem(Memory outputBuffer, long address, long size) {
		byte[] current;
		boolean equal;
		for (long pos = 0; pos < size-overlapping; pos=pos+1) {
			current = outputBuffer.getByteArray(pos, this.targetSize);
			equal=true;
			for (int i = 0; i < this.targetSize; i++)
				if (current[i]!=this.target[i]) equal=false;		
			if (equal){
				Datastructure ds = this.type.getInstance();
				ds.setByteCount(targetSize);
				add(new Result(getResultList(), ds, address+pos, current));
				log.debug("Found:\t"+Long.toHexString(address+pos));
			}
		}
	}

}

