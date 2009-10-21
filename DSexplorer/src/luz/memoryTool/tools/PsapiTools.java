package luz.memoryTool.tools;

import java.util.LinkedList;
import java.util.List;

import luz.memoryTool.interfaces.Kernel32;
import luz.memoryTool.interfaces.Psapi;
import luz.memoryTool.interfaces.Psapi.LPMODULEINFO;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;


public class PsapiTools {
	private static PsapiTools  INSTANCE=null;
	private static Psapi psapi = Psapi.INSTANCE;
	private static Kernel32 k32 = Kernel32.INSTANCE;

	private PsapiTools(){}
	
	public static PsapiTools getInstance(){
		if (INSTANCE==null)
			INSTANCE=new PsapiTools();
		return INSTANCE;
	}	

	
	public List<Integer> enumProcesses() throws Exception{
		List<Integer> list = new LinkedList<Integer>();
		
		int[] pProcessIds = new int[256];
		IntByReference pBytesReturned = new IntByReference(); 
		boolean success = psapi.EnumProcesses(pProcessIds, pProcessIds.length*Integer.SIZE/8, pBytesReturned); 
		if (!success){
    		int err=k32.GetLastError();
            throw new Exception("EnumProcesses failed. Error: "+err);
    	}
    	
		int size = (pBytesReturned.getValue()/(Integer.SIZE/8)); 
		for (int i=0;i<size;i++)
			list.add(pProcessIds[i]);
		
		return list;
	}
	
	public List<Module> EnumProcessModules(Pointer hProcess) throws Exception{
		List<Module> list = new LinkedList<Module>();
		
		Pointer[] lphModule = new Pointer[256];
		IntByReference lpcbNeededs= new IntByReference();
		boolean success = psapi.EnumProcessModules(hProcess, lphModule,lphModule.length, lpcbNeededs);
		if (!success){
    		int err=k32.GetLastError();
            throw new Exception("EnumProcessModules failed. Error: "+err);
    	}
		for (int i = 0; i < lpcbNeededs.getValue()/4; i++) {
			list.add(new Module(hProcess, lphModule[i]));
		}
		
		return list;
	}
	
	public String GetModuleFileNameExA(Pointer hProcess,Pointer hModule){
		byte[] lpImageFileName= new byte[256];
		psapi.GetModuleFileNameExA(hProcess, hModule, lpImageFileName, 256);
		return Native.toString(lpImageFileName);
	}
	
	public LPMODULEINFO GetModuleInformation(Pointer hProcess, Pointer hModule) throws Exception{
		LPMODULEINFO lpmodinfo = new LPMODULEINFO();
		
		boolean success = psapi.GetModuleInformation(hProcess, hModule, lpmodinfo, lpmodinfo.size());
		if (!success){
    		int err=k32.GetLastError();
            throw new Exception("GetModuleInformation failed. Error: "+err);
    	}
		return lpmodinfo;
	}

}
