package luz.memoryTool.tools;

import luz.memoryTool.interfaces.Psapi.LPMODULEINFO;

import com.sun.jna.Pointer;

public class Module {
	private Pointer hProcess;
	private Pointer hModule;
	private Pointer lpBaseOfDll=null;
	private int  SizeOfImage=0;
	private Pointer EntryPoint=null;
	
	private PsapiTools psapi = PsapiTools.getInstance();
	
	protected Module(){}		
			
	public Module(Pointer hProcess, Pointer hModule){
		this.hProcess=hProcess;
		this.hModule=hModule;
	}

	public Pointer getPointer(){
		return hModule;
	}
	
	public String getFileName(){
		return psapi.GetModuleFileNameExA(hProcess,hModule);
	}
	
	
	private void GetModuleInformation(){
		if(lpBaseOfDll==null){
			try {
				LPMODULEINFO x = psapi.GetModuleInformation(hProcess, hModule);
				lpBaseOfDll=x.lpBaseOfDll;
				SizeOfImage=x.SizeOfImage;
				EntryPoint=x.EntryPoint;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public Pointer getLpBaseOfDll(){
		GetModuleInformation();
		return lpBaseOfDll;
	}
	
	public int getSizeOfImage(){
		GetModuleInformation();
		return SizeOfImage;
	}
	
	public Pointer getEntryPoint(){
		GetModuleInformation();
		return EntryPoint;
	}
	

	
}
