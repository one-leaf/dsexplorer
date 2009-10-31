package luz.dsexplorer.winapi.tools;

import luz.dsexplorer.objects.Process;
import luz.dsexplorer.objects.ProcessList;
import luz.dsexplorer.winapi.interfaces.Kernel32;
import luz.dsexplorer.winapi.interfaces.Kernel32.ENUMRESNAMEPROC;
import luz.dsexplorer.winapi.interfaces.Kernel32.LPPROCESSENTRY32;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public class Kernel32Tools {
	private static Kernel32Tools INSTANCE=null;
	private static Kernel32 k32 = Kernel32.INSTANCE;
	
	private Kernel32Tools(){}
	
	public static Kernel32Tools getInstance(){
		if (INSTANCE==null)
			INSTANCE=new Kernel32Tools();
		return INSTANCE;
	}
	
	////////////////////////////////////////////////////////////////////////
	public static final int PROCESS_TERMINATE					=0x00000001;
	public static final int PROCESS_CREATE_THREAD				=0x00000002;
	public static final int PROCESS_VM_OPERATION				=0x00000008;
	public static final int PROCESS_VM_READ					=0x00000010;
	public static final int PROCESS_VM_WRITE					=0x00000020;
	public static final int PROCESS_DUP_HANDLE				=0x00000040;
	public static final int PROCESS_CREATE_PROCESS			=0x00000080;
	public static final int PROCESS_SET_QUOTA					=0x00000100;
	public static final int PROCESS_SET_INFORMATION			=0x00000200;
	public static final int PROCESS_QUERY_INFORMATION			=0x00000400;
	public static final int PROCESS_SUSPEND_RESUME			=0x00000800;
	public static final int PROCESS_QUERY_LIMITED_INFORMATION	=0x00001000;

	public static final int DELETE							=0x00010000;
	public static final int READ_CONTROL						=0x00020000;
	public static final int WRITE_DAC							=0x00040000;
	public static final int WRITE_OWNER						=0x00080000;
	public static final int SYNCHRONIZE						=0x00100000;

	public static final int PROCESS_ALL_ACCESS				=0x001F0FFF;
	////////////////////////////////////////////////////////////////////////

	public static final int TH32CS_SNAPHEAPLIST				=0x00000001;
	public static final int TH32CS_SNAPPROCESS				=0x00000002;
	public static final int TH32CS_SNAPTHREAD					=0x00000004;
	public static final int TH32CS_SNAPMODULE					=0x00000008;	
	public static final int TH32CS_SNAPALL					=0x0000000F;
	
	public static final int TH32CS_SNAPMODULE32				=0x00000010;
	public static final int TH32CS_INHERIT					=0x80000000;
	
	////////////////////////////////////////////////////////////////////////
	

	
	public int GetCurrentProcessId(){
		return k32.GetCurrentProcessId();
	}
	
	public Pointer GetCurrentProcess(){
		return k32.GetCurrentProcess();
	}
	
	
	public int GetLastError(){
		return k32.GetLastError();
	}
	
	public Pointer OpenProcess(int dwDesiredAccess, boolean bInheritHandle, int dwProcessId) throws Exception{
		Pointer process = k32.OpenProcess(dwDesiredAccess, false, dwProcessId);
    	if (process == null){
    		int err=k32.GetLastError();
            throw new Exception("openProcess failed. Error: "+err);
    	}
        return process;
    }
	
	public void ReadProcessMemory(Pointer hProcess, Pointer pointer, Pointer outputBuffer, int nSize, IntByReference outNumberOfBytesRead) throws Exception{
        boolean success = k32.ReadProcessMemory(hProcess, pointer, outputBuffer, nSize, outNumberOfBytesRead);
    	if (!success){
    		int err=k32.GetLastError();
    		throw new Exception("readProcessMemory failed. Error: "+err);
    	}
    }
	
	
	
	public ProcessList getProcessList() throws Exception{
		ProcessList list = new ProcessList();
		
        Pointer hProcessSnap = k32.CreateToolhelp32Snapshot(Kernel32Tools.TH32CS_SNAPPROCESS, 0);
        
        LPPROCESSENTRY32 pe32 = new LPPROCESSENTRY32();
        boolean success = k32.Process32First(hProcessSnap, pe32);
    	if (!success){
    		int err=k32.GetLastError();
    		throw new Exception("Process32First failed. Error: "+err);
    	}
       
        do{
        	if (pe32.th32ProcessID!=0){
	        	Process process = new Process(pe32);
	        	list.add(process);
        	}
        }while(k32.Process32Next(hProcessSnap, pe32));
		return list;		
	}
    
    
    
    public Pointer FindResource(Pointer hModule, String lpName, String lpType){
    	return k32.FindResourceA(hModule, lpName, lpType);
    }
    
    public Pointer LockResource(Pointer hResData){
        return k32.LockResource(hResData);
    }

    public int SizeofResource(Pointer hModule,Pointer hResInfo) throws Exception{
        int size = k32.SizeofResource(hModule,hResInfo);
    	if (size==0){
    		int err=k32.GetLastError();
    		throw new Exception("SizeofResource failed. Error: "+err);
    	}
    	return size;
    }
    
    public boolean EnumResourceNamesA(Pointer hModule,String lpszType, ENUMRESNAMEPROC lpEnumFunc, IntByReference lParam){
        return k32.EnumResourceNamesA(hModule, lpszType, lpEnumFunc, lParam);
    }
    
    
}
