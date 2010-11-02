package luz.dsexplorer.search;

import luz.dsexplorer.datastructures.Result;
import luz.dsexplorer.datastructures.ResultList;
import luz.dsexplorer.datastructures.ResultListImpl;
import luz.winapi.api.MemoryListener;
import luz.winapi.api.Process;

import com.sun.jna.Memory;


public abstract class AbstractMemoryListener implements MemoryListener {
	protected ResultList results;	
	protected int overlapping;

	public AbstractMemoryListener(Process process){
		results=new ResultListImpl(process);
	}
	
	public void add(Result result) {
		results.add(result);
	}
	
	public ResultList getResultList() {
		return results;
	}
	
	/**
	 * To optimize memory reading, the reading algorithm divides large memory areas into
	 * smaller ones. This value forces the reading algorithm to overlap these memory areas by
	 * the given value. This is usefull if the seach pattern is longer than one Byte. In this case, 
	 * the MemoryListener has only to worry about one area at each time. Values at the end of an 
	 * area will repeat at the beginning of the next area. In general this value should be 
	 * 'size of the search pattern -1'.
	 * @param overlapping
	 */
	public int getOverlapping(){
		return overlapping;
	}
		
	@Override
	public abstract void init(Object value);
	
	@Override
	public abstract void mem(Memory outputBuffer, long address, long size);
	
	@Override
	public ResultList getResults(){
		return results;
	}

}
