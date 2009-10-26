package test;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import luz.dsexplorer.interfaces.Advapi32;
import luz.dsexplorer.interfaces.Ntdll;
import luz.dsexplorer.interfaces.Shell32;
import luz.dsexplorer.interfaces.Advapi32.TOKEN_PRIVILEGES;
import luz.dsexplorer.interfaces.Ntdll.PEB;
import luz.dsexplorer.interfaces.Ntdll.PROCESS_BASIC_INFORMATION;
import luz.dsexplorer.interfaces.Ntdll.RTL_USER_PROCESS_PARAMETERS;
import luz.dsexplorer.interfaces.Ntdll.UNICODE_STRING;
import luz.dsexplorer.tools.Advapi32Tools;
import luz.dsexplorer.tools.Kernel32Tools;
import luz.dsexplorer.tools.Process;
import luz.dsexplorer.tools.ProcessList;
import luz.dsexplorer.tools.PsapiTools;
import luz.dsexplorer.tools.User32Tools;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;



 
public class Test {
 
    public static void main(String[] args) throws Exception {
        final Kernel32Tools k32 = Kernel32Tools.getInstance();
        final PsapiTools psapi = PsapiTools.getInstance();
        final User32Tools u32 =User32Tools.getInstance();
        final Shell32 s32 = Shell32.INSTANCE;
        final Ntdll nt = Ntdll.INSTANCE;
        final Advapi32Tools a32 = Advapi32Tools.getInstance();
        
        a32.enableDebugPrivilege(k32.GetCurrentProcess());
        
        int pid=5384;
        Process p=null;
        ProcessList list = k32.getProcessList();
        list.refreshWindows();
        for (Process pr : list) {
			if (pr.getPid()==pid){
				p=pr;
				break;
			}
        }
        

        
        
        
        
        PROCESS_BASIC_INFORMATION info = new PROCESS_BASIC_INFORMATION();
        IntByReference ret = new IntByReference();
        nt.NtQueryInformationProcess(p.getPointer(), Ntdll.ProcessBasicInformation, info, info.size(), ret);
        System.out.println("ExitStatus\t"+info.ExitStatus);
        System.out.println("PebBaseAddress\t"+info.PebBaseAddress);
        System.out.println("AffinityMask\t"+info.AffinityMask);
        System.out.println("BasePriority\t"+info.BasePriority);
        System.out.println("UniqueProcessId\t"+info.UniqueProcessId);
        System.out.println("ParentProcessId\t"+info.ParentProcessId);
        System.out.println("==============================");
        
        PEB peb = new PEB();
        k32.ReadProcessMemory(p.getPointer(), info.PebBaseAddress, peb.getPointer(), peb.size(), ret);
        peb.read();
		System.out.println("InheritedAddressSpace\t"+peb.InheritedAddressSpace);
		System.out.println("ReadImageFileExecOptions\t"+peb.ReadImageFileExecOptions);
        System.out.println("BeingDebugged\t"+peb.BeingDebugged);
        System.out.println("Spare\t"+peb.Spare);
        System.out.println("Mutant\t"+peb.Mutant);
        System.out.println("ImageBaseAddress\t"+peb.ImageBaseAddress);
        System.out.println("Ldr\t"+peb.Ldr);
        System.out.println("ProcessParameters\t"+peb.ProcessParameters);
        System.out.println("PostProcessInitRoutine\t"+peb.PostProcessInitRoutine);
        System.out.println("SessionId\t"+peb.SessionId);
        System.out.println("------------------------------");
        byte[] buffer1 = new byte[peb.size()];
        peb.getPointer().read(0, buffer1, 0, buffer1.length);
        System.out.println(new String(buffer1));
        System.out.println("==============================");
        
        RTL_USER_PROCESS_PARAMETERS pp = new RTL_USER_PROCESS_PARAMETERS();
        k32.ReadProcessMemory(p.getPointer(), peb.ProcessParameters, pp.getPointer(), pp.size(), ret);
        pp.read();
        System.out.println("ImagePathName\t"+pp.ImagePathName);
        System.out.println("CommandLine\t"+pp.CommandLine);        
        System.out.println("------------------------------");
        byte[] buffer2 = new byte[pp.size()];
        pp.getPointer().read(0, buffer2, 0, buffer2.length);
        System.out.println(new String(buffer2));
        System.out.println("==============================");
        
        UNICODE_STRING img=new UNICODE_STRING();
        k32.ReadProcessMemory(p.getPointer(), pp.ImagePathName, img.getPointer(), img.size(), ret);
        img.read();
        System.out.println("ImagePathName\t"+img.Length);
        System.out.println("ImagePathName\t"+img.MaximumLength);
        System.out.println("ImagePathName\t"+img.Buffer);
        System.out.println("------------------------------");
        byte[] buffer3 = new byte[img.size()];
        img.getPointer().read(0, buffer3, 0, buffer3.length);
        System.out.println(new String(buffer3));
        System.out.println("==============================");
        
        UNICODE_STRING cmd=new UNICODE_STRING();
        k32.ReadProcessMemory(p.getPointer(), pp.CommandLine, cmd.getPointer(), cmd.size(), ret);
        cmd.read();
        System.out.println("CommandLine\t"+cmd.Length);
        System.out.println("CommandLine\t"+cmd.MaximumLength);
        System.out.println("CommandLine\t"+cmd);
        System.out.println("------------------------------");
        byte[] buffer4 = new byte[cmd.size()];
        cmd.getPointer().read(0, buffer4, 0, buffer4.length);
        System.out.println(new String(buffer4));
        System.out.println("==============================");

//        
//
//        int[] p1 = new int[1];
//        peb.getPointer().read(4, p1, 0, 1);
//        System.out.println(p1[0]);
        

        
        


        
//		String filename=p.getProcessImageFileName();
//		System.out.println(filename);
//		Pointer[] icons = new Pointer[1];
//		int count = s32.ExtractIconExA(filename, 0, null, icons, 1);
//		System.out.println("count "+count);
//		Pointer icon=icons[0];
//		System.out.println("icon "+icon);
//		
//		
////		Pointer res = k32.FindResource(p.getPointer(), "#0", "#3");
////		System.out.println("res "+res);
//		
////		int size=k32.SizeofResource(p.getModules().get(0).getPointer(), icon);
////		System.out.println("size "+size);
//		
//		Pointer data = k32.LockResource(icon);
//		System.out.println("data "+data);
//
//		
//		System.out.println(data.getByte(0));

        
        //List<Pointer> hWnds = u32.EnumWindows();
//        List<Pointer> hWnds = p.getHwnds();
        
//        JFrame frame = new JFrame();
//        frame.setLayout(new FlowLayout());
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        for (Pointer hWnd :  hWnds) {	
//			Pointer hIcon = u32.getHIcon(hWnd);
//	        if(hIcon!=null){
//
//	            BufferedImage image=u32.getIcon(hIcon);        
//	            JLabel icon = new JLabel();
//	            icon.setIcon(new ImageIcon(image));
//	            frame.add(icon);
//
//
//	        }
//
//		}
//        System.out.println(hWnds.size());
//        frame.pack();
//        frame.setVisible(true);
        
    

        
//        
        
//        JLabel icon = new JLabel();
//        icon.setIcon(new ImageIcon(p.getIcon()));
//
//        JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setContentPane(icon);
//        frame.pack();
//        frame.setVisible(true);

        
        
        


        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
      
//		String lpszType="14";
//		IntByReference lParam = new IntByReference(0);
//		k32.EnumResourceNamesA(p.getPointer(), lpszType, new ENUMRESNAMEPROC() {
//			public boolean callback(Pointer hModule, String lpszType, String lpszName, IntByReference lParam) {
//				System.out.println(lpszName);
//				return true;
//			}
//		}, lParam);
//        
        
        
        
        
//        int pid=5860;
//        IntByReference lpdwProcessId=new IntByReference();
//        Pointer hwnd = u32.GetTopWindow(null);
//        System.out.println(hwnd);
//        System.out.println(u32.GetWindowThreadProcessId(hwnd, lpdwProcessId));
//        System.out.println(lpdwProcessId.getValue());
//        hwnd = u32.GetNextWindow(hwnd, 2);
//        System.out.println(hwnd);
        
        
//        final int pidx=5860;
//		u32.EnumWindows(new WNDENUMPROC() {  
//			public boolean callback(Pointer hWnd, Pointer userData) {  
//				IntByReference lpdwProcessId=new IntByReference();
//				u32.GetWindowThreadProcessId(hWnd,lpdwProcessId); 				
//				int pid=lpdwProcessId.getValue();
//				if (pid==pidx){
//					System.out.println(lpdwProcessId.getValue());
//					for (int i = 0; i < 5000; i++) {
//						Pointer icon = u32.LoadIconA(hWnd, "#"+i);
//						if (icon!=null){
//							System.out.println(icon);
//							break;					
//						}
//					}
//				}
//
//				
//				return true;  
//			}  
//		}, null); 
        
        
//      int pid = 3420;
//      Pointer process = k32.OpenProcess(Kernel32Tools.PROCESS_ALL_ACCESS, false, pid);
//      Pointer x = null;
//      int i=0;
//      while(x == null && i<65000){
//    	  x=k32.FindResource(process, "#"+i, "#3");
//    	  i=i+1;
//      }
//      System.out.println(x);
        

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
