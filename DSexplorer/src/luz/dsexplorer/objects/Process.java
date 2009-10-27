package luz.dsexplorer.objects;

import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;

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
	private int cntThreads;
	private int th32ParentProcessID;
	private int pcPriClassBase;
	private Pointer handle =null;
	private Kernel32Tools k32   = Kernel32Tools.getInstance();
	private PsapiTools    psapi = PsapiTools   .getInstance();
	private Shell32Tools  s32   = Shell32Tools .getInstance();
	private NtdllTools    nt    = NtdllTools   .getInstance();
	private User32Tools   u32   = User32Tools  .getInstance();
	private List<Pointer> hWnds = new LinkedList<Pointer>();
	
	public Process(int pid, String szExeFile){
		this.pid=pid;
		this.szExeFile=szExeFile;
	}
	
	private void open() throws Exception{
		if (handle==null){
			handle = k32.OpenProcess(Kernel32Tools.PROCESS_ALL_ACCESS, false, this.pid);
		}
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
	
	public Pointer getPointer(){
		 try {
				open();
				return handle;
			} catch (Exception e) {
				//e.printStackTrace();
				return null;
			}
	}
	
	public String getProcessImageFileName(){
		 try {
			open();
			return psapi.GetProcessImageFileNameA(handle);
		} catch (Exception e) {
			//e.printStackTrace();
			return "";
		}
	}

	public String getModuleFileNameExA(){
		 try {
			open();
			return psapi.GetModuleFileNameExA(handle, null);
		} catch (Exception e) {
			//e.printStackTrace();
			return "";
		}
	}

	public List<Module> getModules() throws Exception{
		 try {
				open();
				return psapi.EnumProcessModules(handle);
			} catch (Exception e) {
				//e.printStackTrace();
				return new LinkedList<Module>();
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
		infoCache = nt.NtQueryInformationProcess(getPointer(), NtdllTools.ProcessBasicInformation);
		return infoCache;
	}
	
	private PEB pebCache=null;
	public PEB getPEB() throws Exception{
		if (pebCache!=null)
			return pebCache;
		pebCache=getPROCESS_BASIC_INFORMATION().getPEB();
		return pebCache;
	}
	
	//Setter
	
	public void setCntThreads(int cntThreads) {
		this.cntThreads = cntThreads;
	}

	public void setTh32ParentProcessID(int th32ParentProcessID) {
		this.th32ParentProcessID = th32ParentProcessID;
	}

	public void setPcPriClassBase(int pcPriClassBase) {
		this.pcPriClassBase = pcPriClassBase;
	}
	
	




	
}
