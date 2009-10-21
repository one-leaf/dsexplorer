package test;

import java.util.List;

import luz.memoryTool.interfaces.Shell32;
import luz.memoryTool.tools.Kernel32Tools;
import luz.memoryTool.tools.Module;
import luz.memoryTool.tools.PsapiTools;

import com.sun.jna.Pointer;


 
public class Test {
 
    public static void main(String[] args) throws Exception {
        Kernel32Tools k32 = Kernel32Tools.getInstance();
        PsapiTools psapi = PsapiTools.getInstance();

      int pid = 3420;
      Pointer process = k32.OpenProcess(Kernel32Tools.PROCESS_ALL_ACCESS, false, pid);
      Pointer x = null;
      int i=0;
      while(x == null && i<65000){
    	  x=k32.FindResource(process, "#"+i, "#3");
    	  i=i+1;
      }
      System.out.println(x);
        

//Processlist
//        List<Process> x = k32.getProcessList();
//        for (Process process : x) {
//			System.out.println(process.getPid()+"\t"+process.getSzExeFile()+"\t"+process.getProcessImageFileName());
//		}
        
        
        
        
        
//Dll List
//        int pid=4504;
//        System.out.println("pid= "+pid);
//        Pointer process = k32.OpenProcess(Kernel32Tools.PROCESS_ALL_ACCESS, false, pid);
//        System.out.println(psapi.GetModuleFileNameExA(process, null));
//
//        List<Module> modules=psapi.EnumProcessModules(process);
//        for (Module module : modules) {
//			System.out.println(module.getLpBaseOfDll()+"\t"+Integer.toHexString(module.getSizeOfImage())+"\t"+module.getFileName());
//		}

        
        
        

        
//        int pid = 4672;
//        Pointer process = k32.openProcess(Kernel32Tools.PROCESS_ALL_ACCESS, false, pid);
        
        
//        int offset = 0x1000000;
//        int bufferSize = 128;
//        Memory outputBuffer = new Memory(bufferSize);
//        k32.readProcessMemory(process, offset, outputBuffer, bufferSize, null);
// 
//        byte[] bufferBytes = outputBuffer.getByteArray(0, bufferSize);
//        System.out.println(new String(bufferBytes));
    }
}
