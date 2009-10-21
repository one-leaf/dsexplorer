package luz.memoryTool.tools;

import java.util.LinkedList;
import java.util.List;

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
	
	public Process(int pid, String szExeFile){
		this.pid=pid;
		this.szExeFile=szExeFile;
	}
	
	private void open() throws Exception{
		if (handle==null){
			handle = k32.OpenProcess(Kernel32Tools.PROCESS_ALL_ACCESS, false, this.pid);
		}
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
