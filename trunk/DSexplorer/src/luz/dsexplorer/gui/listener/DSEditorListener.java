package luz.dsexplorer.gui.listener;

import java.util.EventListener;

import luz.dsexplorer.objects.Datastructure;
import luz.dsexplorer.objects.Result;

public interface DSEditorListener extends EventListener  {
    
	public void AddFieldPerformed(Datastructure o);
	
	public void AddessChanged(Result result);

	public void TypeChanged(Result o);

	public void SizeChanged(Result o);

	public void NameChanged(Result o);



}
