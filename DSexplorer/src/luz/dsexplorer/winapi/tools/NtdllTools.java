package luz.dsexplorer.winapi.tools;

import luz.dsexplorer.winapi.constants.ProcessInformationClass;
import luz.dsexplorer.winapi.jna.Ntdll;
import luz.dsexplorer.winapi.jna.Ntdll.PROCESS_BASIC_INFORMATION;

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
	
	public PROCESS_BASIC_INFORMATION NtQueryInformationProcess(Pointer ProcessHandle, 
			ProcessInformationClass pic){
        PROCESS_BASIC_INFORMATION info = new PROCESS_BASIC_INFORMATION(ProcessHandle);
        IntByReference ret = new IntByReference();
        nt.NtQueryInformationProcess(ProcessHandle, pic.getValue(), info, info.size(), ret);
        return info;
	}
    
}
