package luz.dsexplorer.objects.listener;

import java.util.List;

import luz.dsexplorer.objects.DSType;
import luz.dsexplorer.objects.Result;

import com.sun.jna.Memory;

public abstract class MemoryListener {
	private List<Result> results;
	private String value;
	private DSType type;

	public List<Result> getResults() {
		return results;
	}
	
	public String getValue() {
		return value;
	}
	
	public DSType getType() {
		return type;
	}
	
	public void init(List<Result> results, String value, DSType type){
		this.results=results;
		this.value=value;
		this.type=type;
	}
	
	public abstract void mem(Memory outputBuffer, long address, long size);

}
