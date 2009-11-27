package luz.dsexplorer.gui.listener;

import java.util.EventListener;

import luz.dsexplorer.objects.Result;
import luz.dsexplorer.objects.datastructure.Datastructure;

public interface DSEditorListener extends EventListener  {
    
	public void AddFieldPerformed(Datastructure o);
	
	public void AddessChanged(Result result);

	public void TypeChanged(Result o);

	public void SizeChanged(Result o);

	public void NameChanged(Result o);

	public void DSChanged(Result o);

	public void PointerChanged(Result o);



}
