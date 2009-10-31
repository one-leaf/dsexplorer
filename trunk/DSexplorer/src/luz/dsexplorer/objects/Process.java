package luz.dsexplorer.objects;

import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;

import luz.dsexplorer.winapi.interfaces.Kernel32.LPPROCESSENTRY32;
import luz.dsexplorer.winapi.interfaces.Ntdll.PEB;
import luz.dsexplorer.winapi.interfaces.Ntdll.PROCESS_BASIC_INFORMATION;
import luz.dsexplorer.winapi.tools.Kernel32Tools;
import luz.dsexplorer.winapi.tools.NtdllTools;
import luz.dsexplorer.winapi.tools.PsapiTools;
import luz.dsexplorer.winapi.tools.Shell32Tools;
import luz.dsexplorer.winapi.tools.User32Tools;

import com.sun.jna.Pointer;


public class Process {
	private final int pid;
	private final String szExeFile;
	private final int cntThreads;
	private final int th32ParentProcessID;
	private final int pcPriClassBase;

	private Kernel32Tools k32   = Kernel32Tools.getInstance();
	private PsapiTools    psapi = PsapiTools   .getInstance();
	private Shell32Tools  s32   = Shell32Tools .getInstance();
	private NtdllTools    nt    = NtdllTools   .getInstance();
	private User32Tools   u32   = User32Tools  .getInstance();
	private List<Pointer> hWnds = new LinkedList<Pointer>();
	
	public Process(LPPROCESSENTRY32 pe32) {
		this.pid=pe32.th32ProcessID;
		this.szExeFile=pe32.getSzExeFile();
		this.cntThreads=pe32.cntThreads;
		this.pcPriClassBase=pe32.pcPriClassBase.intValue();
		this.th32ParentProcessID=pe32.th32ParentProcessID;
	}

	private Pointer handleCache =null;
	public Pointer getHandle() throws Exception{
		if (handleCache!=null)
			return handleCache;
		handleCache = k32.OpenProcess(Kernel32Tools.PROCESS_ALL_ACCESS, false, this.pid);
		return handleCache;
	}
	
	
	public void clearHwnds() {
		hWnds.clear();
	}
	
	public void addHwnd(Pointer hWnd) {
		hWnds.add(hWnd);		
	}
	
	public List<Pointer> getHwnds(){
		return hWnds;
	}
	
	

	//Getter
	
	public int getPid() {
		return pid;
	}

	public String getSzExeFile() {
		return szExeFile;
	}

	public int getCntThreads() {
		return cntThreads;
	}
	
	public int getTh32ParentProcessID() {
		return th32ParentProcessID;
	}	

	public int getPcPriClassBase() {
		return pcPriClassBase;
	}
	
	public String getProcessImageFileName(){
		 try {
			return psapi.GetProcessImageFileNameA(getHandle());
		} catch (Exception e) {
			return "";
		}
	}

	public String getModuleFileNameExA(){
		 try {
			return psapi.GetModuleFileNameExA(getHandle(), null);
		} catch (Exception e) {
			return "";
		}
	}

	public List<Module> getModules(){
		try {
			return psapi.EnumProcessModules(getHandle());
		} catch (Exception e) {
			return new LinkedList<Module>();
		}
	}

	private Module moduleCache=null;
	public Module getModule(){
		if (moduleCache!=null)
			return moduleCache;	

		List<Module> modules = getModules();
		if (modules.size()>0)
			moduleCache=modules.get(0);

		return moduleCache;
	}
	
	public Pointer getBase() {
		Module module = getModule();
		if (module!=null)
			return module.getLpBaseOfDll();
		else
			return null;
	}
	
	public int getSize() {
		Module module = getModule();
		if (module!=null)
			return module.getSizeOfImage();
		else
			return 0;
	}
	
	public int getMemUsage() {
		try {
			return psapi.GetProcessMemoryInfo(getHandle()).WorkingSetSize;
		} catch (Exception e) {
			return 0;
		}
	}
	
	private ImageIcon iconCache=null;
	public ImageIcon getIcon(){
		if (iconCache!=null)
			return iconCache;
		
		Pointer hIcon = null;
		
        hIcon=s32.ExtractSmallIcon(this.getModuleFileNameExA(), 1);        
        if (hIcon==null){
        	hIcon=s32.ExtractSmallIcon(this.getSzExeFile(), 1);
        }
        
        if (hIcon==null){      	
        	if(hWnds.size()>0){
        		hIcon = u32.getHIcon(u32.GetAncestor(hWnds.get(0), User32Tools.GA_ROOTOWNER));
        	}
//        	for (Pointer hWnd : hWnds) {
//        		hIcon = u32.getHIcon(hWnd);
//        		if (hIcon!=null)
//        			break;
//			}
        }
        
        if (hIcon!=null)
        	iconCache=new ImageIcon(u32.getIcon(hIcon));
        else
        	iconCache=new ImageIcon();
        return iconCache;        
	}
	
	private PROCESS_BASIC_INFORMATION infoCache=null;
	public PROCESS_BASIC_INFORMATION getPROCESS_BASIC_INFORMATION(){
		if (infoCache!=null)
			return infoCache;
		try {
			infoCache = nt.NtQueryInformationProcess(getHandle(), NtdllTools.ProcessBasicInformation);
		} catch (Exception e) {}
		
		return infoCache;
	}
	
	private PEB pebCache=null;
	public PEB getPEB() throws Exception{
		if (pebCache!=null)
			return pebCache;
		pebCache=getPROCESS_BASIC_INFORMATION().getPEB();
		return pebCache;
	}
	
	
}
