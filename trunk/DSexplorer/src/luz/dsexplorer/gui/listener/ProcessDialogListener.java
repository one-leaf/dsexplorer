package luz.dsexplorer.gui.listener;

import java.util.EventListener;

import luz.winapi.api.Process;

public interface ProcessDialogListener extends EventListener  {
    
	public void okPerformed(Process p);
	
	public void cancelPerformed();

}
