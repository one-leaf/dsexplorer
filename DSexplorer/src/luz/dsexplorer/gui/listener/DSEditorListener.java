package luz.dsexplorer.gui.listener;

import java.util.EventListener;

import luz.dsexplorer.winapi.api.Result;

public interface DSEditorListener extends EventListener  {
    
	public void AddFieldPerformed(Result result);
	
	public void AddessChanged(Result result);

	public void TypeChanged(Result result);

	public void SizeChanged(Result result);

	public void NameChanged(Result result);

	public void DSChanged(Result result);

	public void PointerChanged(Result result);



}
