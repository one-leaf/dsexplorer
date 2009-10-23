package luz.dsexplorer.interfaces;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;

public interface Kernel32 extends StdCallLibrary {
    Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("kernel32", Kernel32.class);
    static int MAX_PATH=256;
    
    /*
     * http://msdn.microsoft.com/en-us/library/ms683180(VS.85).aspx
     */
    int GetCurrentProcessId();
    
    /*
     * http://msdn.microsoft.com/en-us/library/ms683179(VS.85).aspx
     * NOT tested
     */
    Pointer GetCurrentProcess();

    /*
     * http://msdn.microsoft.com/en-us/library/ms679360(VS.85).aspx
     * NOT tested
     */
    int GetLastError();
    
    /*
     * http://msdn.microsoft.com/en-us/library/ms684320(VS.85).aspx
     */
    Pointer OpenProcess(int dwDesiredAccess, boolean bInheritHandle, int dwProcessId);
    
    /*
     * http://msdn.microsoft.com/en-us/library/ms680553(VS.85).aspx
     */
    boolean ReadProcessMemory(Pointer hProcess, int inBaseAddress, Pointer outputBuffer, int nSize, IntByReference outNumberOfBytesRead);
   
    
    boolean CloseHandle(Pointer hObject);
    
    
    /*
     * http://msdn.microsoft.com/en-us/library/ms682489(VS.85).aspx
     * http://msdn.microsoft.com/en-us/library/ms686701(VS.85).aspx
     */
	Pointer CreateToolhelp32Snapshot(int dwFlags,int th32ProcessID);
        
    /*
     * http://msdn.microsoft.com/en-us/library/ms684839(VS.85).aspx
     */
    public static class LPPROCESSENTRY32 extends Structure {
		//public static class ByValue     extends LPPROCESSENTRY32 implements Structure.ByValue {}
		//public static class ByReference extends LPPROCESSENTRY32 implements Structure.ByReference {}
		public int				dwSize;							//The size of the structure, in bytes. If you do not initialize dwSize, Process32First fails.
		public int				cntUsage;							//This member is no longer used and is always set to zero.
		public int				th32ProcessID;					//The process identifier.
		public IntByReference	th32DefaultHeapID;					//This member is no longer used and is always set to zero.
		public int				th32ModuleID;						//This member is no longer used and is always set to zero.
		public int				cntThreads;						//The number of execution threads started by the process.
		public int				th32ParentProcessID;			//The identifier of the process that created this process (its parent process).
		public NativeLong      pcPriClassBase;					//The base priority of any threads created by this process.
		public int				dwFlags;							//This member is no longer used and is always set to zero.
		public char[]			szExeFile = new char[MAX_PATH];//The name of the executable file for the process.
		
		public LPPROCESSENTRY32(){
			dwSize=size();
		}
		
		/*
		 * LPPROCESSENTRY32 seems to need the char array. However its not the correct structure.
		 * So this needs to be transformed.
		 */
		public String getSzExeFile(){
			StringBuilder sb = new StringBuilder();
			char a, b;
			for (int i = 0; i < szExeFile.length; i++) {
				a=(char)(szExeFile[i]   &0xFF);
				if (a==0) break;
				sb.append(a);
				
				b=(char)(szExeFile[i]>>8&0xFF);
				if (b==0) break;
				sb.append(b);
			}
			return sb.toString();
		}
    }
   
    /*
     * http://msdn.microsoft.com/en-us/library/ms684834(VS.85).aspx
     */
    boolean Process32First(Pointer hSnapshot, LPPROCESSENTRY32 lppe);
    
    /*
     * http://msdn.microsoft.com/en-us/library/ms684836(VS.85).aspx
     */
    boolean Process32Next(Pointer hSnapshot, LPPROCESSENTRY32 lppe);
 

    
    /*
     * http://msdn.microsoft.com/en-us/library/ms648042(VS.85).aspx
     */    
    Pointer FindResourceA(Pointer hModule, String lpName, String lpType);
    
    /*
     * http://msdn.microsoft.com/en-us/library/ms648047(VS.85).aspx
     */
    Pointer LockResource(Pointer hResData);
    
    /*
     * http://msdn.microsoft.com/en-us/library/ms648048(VS.85).aspx
     */    
    int SizeofResource(Pointer hModule,Pointer hResInfo);
   
    
    /*
     * http://msdn.microsoft.com/en-us/library/ms648037%28VS.85%29.aspx
     */
    public static interface ENUMRESNAMEPROC extends StdCallCallback {
    	public abstract boolean callback(Pointer hModule, String lpszType, String lpszName, IntByReference lParam);
    }
    boolean EnumResourceNamesA(Pointer hModule,String lpszType, ENUMRESNAMEPROC lpEnumFunc, IntByReference lParam);
    
    
    

    
}
