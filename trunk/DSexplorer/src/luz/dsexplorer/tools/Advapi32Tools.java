package luz.dsexplorer.tools;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;

import luz.dsexplorer.interfaces.Advapi32;
import luz.dsexplorer.interfaces.Kernel32;
import luz.dsexplorer.interfaces.Advapi32.TOKEN_PRIVILEGES;

public class Advapi32Tools {
	private static Advapi32Tools INSTANCE=null;
	private static Advapi32 a32 = Advapi32.INSTANCE;
	private static Kernel32 k32 = Kernel32.INSTANCE;
	
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
        TOKEN_ADJUST_PRIVILEGES | TOKEN_ADJUST_GROUPS | TOKEN_ADJUST_DEFAULT |
        TOKEN_ADJUST_SESSIONID);

	////////////////////////////////////////////////////////////////////////
	
	private Advapi32Tools(){}
	
	public static Advapi32Tools getInstance(){
		if (INSTANCE==null)
			INSTANCE=new Advapi32Tools();
		return INSTANCE;
	}
	
	public void enableDebugPrivilege(Pointer hProcess) throws Exception{
        IntByReference token = new IntByReference();
        boolean success = a32.OpenProcessToken(hProcess, TOKEN_QUERY|TOKEN_ADJUST_PRIVILEGES, token);
    	if (!success){
    		int err=k32.GetLastError();
            throw new Exception("OpenProcessToken failed. Error: "+err);
    	}
        
        LongByReference luid = new LongByReference();
        success = a32.LookupPrivilegeValueA(null, SE_DEBUG_NAME, luid);
    	if (!success){
    		int err=k32.GetLastError();
            throw new Exception("LookupPrivilegeValueA failed. Error: "+err);
    	}
        
        TOKEN_PRIVILEGES tkp = new TOKEN_PRIVILEGES(1);
        tkp.Privileges[0].Luid=luid.getValue();
        tkp.Privileges[0].Attributes=SE_PRIVILEGE_ENABLED;
        Pointer hToken=Pointer.createConstant(token.getValue());
        success = a32.AdjustTokenPrivileges(hToken, false, tkp, 0, null, null);
    	if (!success){
    		int err=k32.GetLastError();
            throw new Exception("AdjustTokenPrivileges failed. Error: "+err);
    	}
	}
    
}
