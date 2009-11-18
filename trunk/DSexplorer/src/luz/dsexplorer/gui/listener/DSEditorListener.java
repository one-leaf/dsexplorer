package luz.dsexplorer.gui.listener;

import java.util.EventListener;

import luz.dsexplorer.objects.Result.Type;

public interface DSEditorListener extends EventListener  {
    
	public void AddressChanged(long address);
	
	public void TypeChanged(Type type);



}
