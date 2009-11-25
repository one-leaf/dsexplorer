package luz.dsexplorer.gui.listener;

import java.util.EventListener;

import luz.dsexplorer.objects.Result;

public interface DSEditorListener extends EventListener  {
    
	public void AddChildPerformed(Result Result);
	
	public void AddessChanged(Result result);

	public void TypeChanged(Result o);

	public void SizeChanged(Result o);

	public void NameChanged(Result o);



}
