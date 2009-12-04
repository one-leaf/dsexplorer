package luz.dsexplorer.objects.datastructure;

import java.util.EventListener;

public interface DSFieldListener extends EventListener {
	public void changed(Object field);

}
