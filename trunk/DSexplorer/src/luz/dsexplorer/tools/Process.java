package luz.dsexplorer.tools;

import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;

import luz.dsexplorer.interfaces.Shell32;

import com.sun.jna.Pointer;


public class Process {
	private final int pid;
	private final String szExeFile;
	private int cntThreads;
	private int th32ParentProcessID;
	private int pcPriClassBase;
	private Pointer handle =null;
	private Kernel32Tools k32 = Kernel32Tools.getInstance();
	private PsapiTools psapi = PsapiTools.getInstance();
	private Shell32 s32 = Shell32.INSTANCE;
	private User32Tools u32 =User32Tools.getInstance();
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
	
	public ImageIcon getIcon(){
		Pointer hIcon = null;
		
        Pointer[] hIcons=new Pointer[1];
        s32.ExtractIconExA(this.getModuleFileNameExA(), 0, null, hIcons, 1);
        hIcon = hIcons[0];
        
        if (hIcon==null){
            s32.ExtractIconExA(this.getSzExeFile(), 0, null, hIcons, 1);
            hIcon = hIcons[0];
        }
        
        if (hIcon==null && hWnds.size()>0){
	        Pointer hWnd=hWnds.get(0);
			hIcon = u32.getHIcon(hWnd);
        }
        
        if (hIcon==null)
        	return null;
        else
        	return new ImageIcon(u32.getIcon(hIcon));    
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
