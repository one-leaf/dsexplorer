package luz.dsexplorer.winapi.tools;

import luz.dsexplorer.winapi.jna.Advapi32;
import luz.dsexplorer.winapi.jna.Kernel32;
import luz.dsexplorer.winapi.jna.Advapi32.LUID;
import luz.dsexplorer.winapi.jna.Advapi32.TOKEN_PRIVILEGES;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

public class Advapi32Tools {
	private static final Advapi32Tools INSTANCE=new Advapi32Tools();	//Eager Creation
	private static Advapi32 a32 = Advapi32.INSTANCE;
	private static Kernel32 k32 = Kernel32.INSTANCE;
	
	private Advapi32Tools(){}
	
	public static Advapi32Tools getInstance(){
		return INSTANCE;
	}
	
	////////////////////////////////////////////////////////////////////////
	
	public static final String SE_DEBUG_NAME = "SeDebugPrivilege";
	
	////////////////////////////////////////////////////////////////////////
	
	public static final int SE_PRIVILEGE_ENABLED = 2;
	
	////////////////////////////////////////////////////////////////////////
    public static final int TOKEN_ASSIGN_PRIMARY     = 0x00000001;
    public static final int TOKEN_DUPLICATE          = 0x00000002;
    public static final int TOKEN_IMPERSONATE        = 0x00000004;
    public static final int TOKEN_QUERY              = 0x00000008;
    public static final int TOKEN_QUERY_SOURCE       = 0x00000010;
    public static final int TOKEN_ADJUST_PRIVILEGES  = 0x00000020;
    public static final int TOKEN_ADJUST_GROUPS      = 0x00000040;
    public static final int TOKEN_ADJUST_DEFAULT     = 0x00000080;
    public static final int TOKEN_ADJUST_SESSIONID   = 0x00000100;
    public static final int STANDARD_RIGHTS_READ     = 0x00020000;
    public static final int STANDARD_RIGHTS_REQUIRED = 0x000F0000;    
    public static final int TOKEN_READ = (STANDARD_RIGHTS_READ | TOKEN_QUERY);
    public static final int TOKEN_ALL_ACCESS = (STANDARD_RIGHTS_REQUIRED | TOKEN_ASSIGN_PRIMARY |
        TOKEN_DUPLICATE | TOKEN_IMPERSONATE | TOKEN_QUERY | TOKEN_QUERY_SOURCE |
        TOKEN_ADJUST_PRIVILEGES | TOKEN_ADJUST_GROUPS | TOKEN_ADJUST_DEFAULT | TOKEN_ADJUST_SESSIONID);

	////////////////////////////////////////////////////////////////////////
	

	
	public void enableDebugPrivilege(Pointer hProcess) throws Exception{
        PointerByReference hToken = new PointerByReference();
        boolean success = a32.OpenProcessToken(hProcess, TOKEN_QUERY|TOKEN_ADJUST_PRIVILEGES, hToken);
    	if (!success){
    		int err=Native.getLastError();
            throw new Exception("OpenProcessToken failed. Error: "+err);
    	}
        
        LUID luid = new LUID();
        success = a32.LookupPrivilegeValueA(null, SE_DEBUG_NAME, luid);
    	if (!success){
    		int err=Native.getLastError();
            throw new Exception("LookupPrivilegeValueA failed. Error: "+err);
    	
    	}
        
        TOKEN_PRIVILEGES tkp = new TOKEN_PRIVILEGES(1);
        tkp.Privileges[0].Luid=luid;
        tkp.Privileges[0].Attributes=SE_PRIVILEGE_ENABLED;
        success = a32.AdjustTokenPrivileges(hToken.getValue(), false, tkp, 0, null, null);
    	if (!success){
    		int err=Native.getLastError();
            throw new Exception("AdjustTokenPrivileges failed. Error: "+err);
    	}
    	
    	k32.CloseHandle(hToken.getValue());
	}
    
}
