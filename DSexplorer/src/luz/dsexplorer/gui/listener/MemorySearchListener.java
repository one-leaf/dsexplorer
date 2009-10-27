package luz.dsexplorer.gui.listener;

import java.util.EventListener;

public interface MemorySearchListener extends EventListener  {
    
	public void FirstSearchPerformed();
	
	public void NextSearchPerformed();
	
	public void AddPerformed(Long pointer);


}
