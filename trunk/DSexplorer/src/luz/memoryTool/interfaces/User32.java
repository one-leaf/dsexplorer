package luz.memoryTool.interfaces;

import com.sun.jna.Pointer;

public interface User32 {
	
	/*
	 * http://msdn.microsoft.com/en-us/library/ms648072%28VS.85%29.aspx
	 */
	Pointer LoadIcon(Pointer hInstance, String lpIconName);

}
