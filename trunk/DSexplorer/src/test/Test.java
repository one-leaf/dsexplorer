package test;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import luz.dsexplorer.interfaces.Shell32;
import luz.dsexplorer.tools.Kernel32Tools;
import luz.dsexplorer.tools.Process;
import luz.dsexplorer.tools.ProcessList;
import luz.dsexplorer.tools.PsapiTools;
import luz.dsexplorer.tools.User32Tools;

import com.sun.jna.Pointer;



 
public class Test {
 
    public static void main(String[] args) throws Exception {
        final Kernel32Tools k32 = Kernel32Tools.getInstance();
        final PsapiTools psapi = PsapiTools.getInstance();
        final User32Tools u32 =User32Tools.getInstance();
        final Shell32 s32 = Shell32.INSTANCE;
        
        
        int pid=4216;
        Process p=null;
        ProcessList list = k32.getProcessList();
        list.refreshWindows();
        for (Process pr : list) {
			if (pr.getPid()==pid){
				p=pr;
				break;
			}
        }
        
        int c=0;
        for (Process pr : list) {
        	c++;
			System.out.println(c+" "+pr.getPid());
        }
        
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
        List<Pointer> hWnds = p.getHwnds();
        
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
