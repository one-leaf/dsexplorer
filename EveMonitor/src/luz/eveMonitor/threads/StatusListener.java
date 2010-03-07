package luz.eveMonitor.threads;

import java.util.EventListener;

public interface StatusListener extends EventListener{

	public void statusChanged();
}
