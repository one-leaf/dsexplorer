package luz.dsexplorer.interfaces;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;

public interface Psapi extends StdCallLibrary{
	Psapi INSTANCE = (Psapi) Native.loadLibrary("Psapi", Psapi.class);
 
	/*
	 * http://msdn.microsoft.com/en-us/library/ms682629(VS.85).aspx
	 */
	boolean EnumProcesses(int[] pProcessIds, int cb, IntByReference pBytesReturned);
	
	
	/*
	 * http://msdn.microsoft.com/en-us/library/ms682631(VS.85).aspx
	 */
	boolean EnumProcessModules(Pointer hProcess, Pointer[] lphModule,int cb, IntByReference lpcbNeededs);

	
	/*
	 * http://msdn.microsoft.com/en-us/library/ms683198(VS.85).aspx
	 */
	int GetModuleFileNameExA(Pointer hProcess, Pointer hModule, byte[] lpImageFileName, int nSize);

	
	/*
	 * http://msdn.microsoft.com/en-us/library/ms684229(VS.85).aspx
	 */
    public static class LPMODULEINFO extends Structure {
		public Pointer lpBaseOfDll;
		public int  SizeOfImage;
		public Pointer EntryPoint;
    }
	
	/*
	 * http://msdn.microsoft.com/en-us/library/ms683201(VS.85).aspx
	 */
	boolean GetModuleInformation(Pointer hProcess, Pointer hModule, LPMODULEINFO lpmodinfo, int cb);

	
}