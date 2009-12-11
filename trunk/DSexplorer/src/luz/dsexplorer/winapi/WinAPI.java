package luz.dsexplorer.winapi;

import java.awt.image.BufferedImage;
import java.util.List;

import luz.dsexplorer.winapi.constants.GAFlags;
import luz.dsexplorer.winapi.constants.ProcessInformationClass;
import luz.dsexplorer.winapi.jna.Kernel32.LPPROCESSENTRY32;
import luz.dsexplorer.winapi.jna.Kernel32.MEMORY_BASIC_INFORMATION;
import luz.dsexplorer.winapi.jna.Ntdll.PROCESS_BASIC_INFORMATION;
import luz.dsexplorer.winapi.jna.Psapi.LPMODULEINFO;
import luz.dsexplorer.winapi.jna.Psapi.PPROCESS_MEMORY_COUNTERS;
import luz.dsexplorer.winapi.tools.Kernel32Tools;
import luz.dsexplorer.winapi.tools.NtdllTools;
import luz.dsexplorer.winapi.tools.PsapiTools;
import luz.dsexplorer.winapi.tools.Shell32Tools;
import luz.dsexplorer.winapi.tools.User32Tools;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public class WinAPI {
	private static WinAPI INSTANCE=null;
	
	private Kernel32Tools k32   = Kernel32Tools.getInstance();
	private PsapiTools    psapi = PsapiTools   .getInstance();
	private Shell32Tools  s32   = Shell32Tools .getInstance();
	private NtdllTools    nt    = NtdllTools   .getInstance();
	private User32Tools   u32   = User32Tools  .getInstance();
	
	private WinAPI(){}
	
	public static WinAPI getInstance(){
		if (INSTANCE==null)
			INSTANCE=new WinAPI();
		return INSTANCE;
	}
	
	/**
	 * Return an new process list, and links the windows (hWnds) to the processes.
	 * @return
	 */
	public ProcessList getProcessList() {
		ProcessList plist = new ProcessList();	
		
		try {
			List<LPPROCESSENTRY32>  processes = k32.getProcessList();
			for (LPPROCESSENTRY32 pe32 : processes)
				plist.add(pe32);
	
			List<Pointer> hWnds = u32.EnumWindows();
			IntByReference lpdwProcessId=new IntByReference();
			int pid=0;
			for (Pointer hWnd : hWnds) {
				u32.GetWindowThreadProcessId(hWnd,lpdwProcessId);
				pid=lpdwProcessId.getValue();
				plist.add(pid, hWnd);
			}
		} catch (Exception e) {
		}
		
		return plist;
	}
	
	public Pointer OpenProcess(int dwDesiredAccess, boolean bInheritHandle, int dwProcessId) throws Exception{
		return k32.OpenProcess(dwDesiredAccess, bInheritHandle, dwProcessId);		
	}
	
	public MEMORY_BASIC_INFORMATION VirtualQueryEx(Pointer hProcess,Pointer lpAddress){
		return k32.VirtualQueryEx(hProcess, lpAddress);
	}
	
	public void ReadProcessMemory(Pointer hProcess, Pointer pointer, Pointer outputBuffer, int nSize, IntByReference outNumberOfBytesRead) throws Exception{
		k32.ReadProcessMemory(hProcess, pointer, outputBuffer, nSize, outNumberOfBytesRead);
	}
	
	
	
	
	public String GetModuleFileNameExA(Pointer hProcess,Pointer hModule){
		return psapi.GetModuleFileNameExA(hProcess, hModule);
	}
	
	public LPMODULEINFO GetModuleInformation(Pointer hProcess, Pointer hModule) throws Exception{
		return psapi.GetModuleInformation(hProcess, hModule);
	}
	
	public String GetProcessImageFileNameA(Pointer hProcess){
		return psapi.GetProcessImageFileNameA(hProcess);
	}
	
	public List<Pointer> EnumProcessModules(Pointer hProcess) throws Exception{
		return psapi.EnumProcessModules(hProcess);
	}
	
	public PPROCESS_MEMORY_COUNTERS GetProcessMemoryInfo(Pointer Process) throws Exception{
		return psapi.GetProcessMemoryInfo(Process);
	}
	
	
	
	
	public Pointer ExtractSmallIcon(String lpszFile, int nIconIndex){
		return s32.ExtractSmallIcon(lpszFile, nIconIndex);
	}
	
	
	
	
	public PROCESS_BASIC_INFORMATION NtQueryInformationProcess(Pointer ProcessHandle, 
			ProcessInformationClass pic){
		return nt.NtQueryInformationProcess(ProcessHandle,  pic);
	}
	
	
	
	public Pointer getHIcon(Pointer hWnd){
		return u32.getHIcon(hWnd);
	}
	
	public BufferedImage getIcon(Pointer hIcon) {
		return u32.getIcon(hIcon);
	}
	
    public Pointer GetAncestor(Pointer hwnd, GAFlags gaFlags){
    	return u32.GetAncestor(hwnd, gaFlags);
    }

}
