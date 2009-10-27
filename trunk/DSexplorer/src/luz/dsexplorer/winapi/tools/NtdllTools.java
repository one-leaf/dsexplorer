package luz.dsexplorer.winapi.tools;

import luz.dsexplorer.winapi.interfaces.Ntdll;
import luz.dsexplorer.winapi.interfaces.Ntdll.PROCESS_BASIC_INFORMATION;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public class NtdllTools {
	private static NtdllTools INSTANCE=null;
	private static Ntdll nt = Ntdll.INSTANCE;
	
	private NtdllTools(){}
	
	public static NtdllTools getInstance(){
		if (INSTANCE==null)
			INSTANCE=new NtdllTools();
		return INSTANCE;
	}
	
	////////////////////////////////////////////////////////////////////////
	
	public static final int ProcessBasicInformation	=0;
	public static final int ProcessDebugPort			=7;
	public static final int ProcessWow64Information	=26;
	public static final int ProcessImageFileName		=27;
	
	public PROCESS_BASIC_INFORMATION NtQueryInformationProcess(Pointer ProcessHandle, 
			int ProcessInformationClass){
        PROCESS_BASIC_INFORMATION info = new PROCESS_BASIC_INFORMATION(ProcessHandle);
        IntByReference ret = new IntByReference();
        nt.NtQueryInformationProcess(ProcessHandle, ProcessInformationClass, info, info.size(), ret);
        return info;
	}
    
}
