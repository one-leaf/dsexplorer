package luz.dsexplorer.objects.listener;

import java.util.List;

import luz.dsexplorer.objects.Result;
import luz.dsexplorer.objects.Result.Type;

import com.sun.jna.Memory;

public abstract class MemoryListener {
	private List<Result> results;
	private String value;
	private Type type;

	public List<Result> getResults() {
		return results;
	}
	
	public String getValue() {
		return value;
	}
	
	public Type getType() {
		return type;
	}
	
	public void init(List<Result> results, String value, Type type){
		this.results=results;
		this.value=value;
		this.type=type;
	}
	
	public abstract void mem(Memory outputBuffer, long address, long size);

}
