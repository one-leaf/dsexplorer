package luz.dsexplorer.tools;

import luz.dsexplorer.interfaces.Kernel32;
import luz.dsexplorer.interfaces.User32;
import luz.dsexplorer.interfaces.User32.WNDENUMPROC;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public class User32Tools {
	private static User32Tools INSTANCE=null;
	private static User32 u32 = User32.INSTANCE;
	private static Kernel32Tools k32 = Kernel32Tools.getInstance();

	
	////////////////////////////////////////////////////////////////////////
	
	private User32Tools(){}
	
	public static User32Tools getInstance(){
		if (INSTANCE==null)
			INSTANCE=new User32Tools();
		return INSTANCE;
	}
	
	private class Container<A>{
		A object=null;
		void setFirst(A object){
			this.object=object;
		}
		A getFirst(){
			return this.object;
		}		
	}
	
	public Pointer getHwnd(Process process){
		final int pidx=process.getPid();
		final Container<Pointer> mutex=new Container<Pointer>();
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				u32.EnumWindows(new WNDENUMPROC() {  
					public boolean callback(Pointer hWnd, Pointer userData) {  
						IntByReference lpdwProcessId=new IntByReference();
						u32.GetWindowThreadProcessId(hWnd,lpdwProcessId);
						int pid=lpdwProcessId.getValue();
						if (pid==pidx){
							synchronized (mutex) {
								mutex.setFirst(hWnd);
								mutex.notifyAll();
							}
							return false;
						}
						return true;
					} 
				}, null);				
			}
		}).start();
		
		try {
			synchronized (mutex) {
				mutex.wait(5000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		return mutex.getFirst();
	}
	
	
	public static final int WM_GETICON=0x7f;
	
	public static final int ICON_SMALL=0;		//wParam
	public static final int ICON_BIG=1;		//wParam
    
	public Pointer SendMessageA(Pointer hWnd,int Msg,int wParam,int lParam){
        return u32.SendMessageA(hWnd, Msg, wParam, lParam);
    }
    
	public static final int GCL_HICON=-14;
	public static final int GCL_HICONSM=-34;
	
    public int GetClassLong(Pointer hWnd,int nIndex) throws Exception{
        int ret = u32.GetClassLongA(hWnd, nIndex);
    	if (ret==0){
    		int err=k32.GetLastError();
    		throw new Exception("GetClassLong failed. Error: "+err);
    	}
    	return ret;
    }
    
}
