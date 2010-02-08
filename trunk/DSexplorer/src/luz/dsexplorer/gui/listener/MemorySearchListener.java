package luz.dsexplorer.gui.listener;

import java.util.EventListener;
import java.util.List;

import luz.dsexplorer.datastructures.Result;

public interface MemorySearchListener extends EventListener  {
    
	public void FirstSearchPerformed(int from, int to);
	
	public void NextSearchPerformed();
	
	public void AddPerformed(List<Result> results);


}
