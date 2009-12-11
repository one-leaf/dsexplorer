package luz.dsexplorer.winapi.objects;

import luz.dsexplorer.objects.datastructure.DSType;

import com.sun.jna.Memory;

public abstract class MemoryListener {
	private ResultList results;
	private String value;
	private DSType type;

	public void add(Result result) {
		results.add(result);
	}
	
	public String getValue() {
		return value;
	}
	
	public DSType getType() {
		return type;
	}
	
	public void init(ResultList results2, String value, DSType type){
		this.results=results2;
		this.value=value;
		this.type=type;
	}
	
	public abstract void mem(Memory outputBuffer, long address, long size);

}
