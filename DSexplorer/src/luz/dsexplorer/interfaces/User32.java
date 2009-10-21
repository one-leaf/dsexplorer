package luz.dsexplorer.interfaces;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;

public interface User32 extends StdCallLibrary {
	User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);
	
	/*
	 * http://msdn.microsoft.com/en-us/library/ms648072(VS.85).aspx
	 * 
	 * http://support.microsoft.com/kb/142815
	 */
	Pointer LoadIconA(Pointer hInstance, String lpIconName);
	
    boolean DestroyIcon(Pointer hicon);
    
    /*
     * http://msdn.microsoft.com/en-us/library/ms633522(VS.85).aspx
     */
    int GetWindowThreadProcessId(Pointer hWnd, IntByReference lpdwProcessId);

    /*
     * http://msdn.microsoft.com/en-us/library/ms633514(VS.85).aspx
     */
    Pointer GetTopWindow(Pointer hWnd);
    
    /*
     * http://msdn.microsoft.com/en-us/library/ms633509(VS.85).aspx
     */
    Pointer GetNextWindow(Pointer hWnd, int wCmd);
    
    /*
     * http://msdn.microsoft.com/en-us/library/ms633497(VS.85).aspx
     */
    public static interface WNDENUMPROC extends StdCallCallback {
    	public abstract boolean callback(Pointer hwnd, Pointer lParam);
    }
    public abstract boolean EnumWindows(WNDENUMPROC wndenumproc, Pointer lParam);
    
    
    /*
     * http://msdn.microsoft.com/en-us/library/ms648070%28VS.85%29.aspx
     */
    public static class ICONINFO extends Structure{
        boolean fIcon;
        int xHotspot;
        int yHotspot;
        Pointer hbmMask;
        Pointer hbmColor;
    }
    
    boolean GetIconInfo(Pointer hIcon, ICONINFO piconinfo);
    
    
    /*
     * http://msdn.microsoft.com/en-us/library/ms633499(VS.85).aspx
     */
    Pointer FindWindowA(String lpClassName,String lpWindowName);
    
    
    
    /*
     * http://msdn.microsoft.com/en-us/library/ms644950(VS.85).aspx
     */
    Pointer SendMessageA(Pointer hWnd,int Msg,int wParam,int lParam);
    
	/*
	 * http://msdn.microsoft.com/en-us/library/ms633580(VS.85).aspx
     */
    int GetClassLongA(Pointer hWnd,int nIndex);

}
