package luz.dsexplorer.interfaces;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;

public interface Ntdll extends StdCallLibrary{
	Ntdll INSTANCE = (Ntdll) Native.loadLibrary("ntdll", Ntdll.class);

	public static class PROCESS_BASIC_INFORMATION extends Structure {
		public int ExitStatus;
		public Pointer PebBaseAddress;
		public int AffinityMask;
		public int BasePriority;
		public int UniqueProcessId;
		public int ParentProcessId;
	}
	
	/*
	 * http://msdn.microsoft.com/en-us/library/aa813706(VS.85).aspx
	 * http://undocumented.ntinternals.net/UserMode/Undocumented Functions/NT Objects/Process/PEB.html
	 */
	public static class PEB extends Structure {
		public byte InheritedAddressSpace;
		public byte ReadImageFileExecOptions;
		public byte BeingDebugged;
		public byte Spare;
		public Pointer Mutant;
		public Pointer ImageBaseAddress;
		public Pointer Ldr;
		public Pointer ProcessParameters;		//RTL_USER_PROCESS_PARAMETERS
		public byte[] Reserved4=new byte[104];
		public int[]  Reserved5=new int[52];
		public Pointer PostProcessInitRoutine;
		public byte[] Reserved6=new byte[128];
		public int[]  Reserved7=new int[1];
		public NativeLong SessionId;
	}
	
	/*
	 * http://msdn.microsoft.com/en-us/library/aa813741(VS.85).aspx
	 * http://undocumented.ntinternals.net/UserMode/Structures/RTL_USER_PROCESS_PARAMETERS.html
	 */
	public static class RTL_USER_PROCESS_PARAMETERS extends Structure {
		public byte[] Reserved1=new byte[16];
		public int[] Reserved2=new int[10];
		public Pointer ImagePathName;
		public Pointer CommandLine;
	}
	
	/*
	 * http://msdn.microsoft.com/en-us/library/aa380518(VS.85).aspx
	 */
	public static class UNICODE_STRING extends Structure {
		public short Length;
		public short MaximumLength;
		public char[] Buffer=new char[256];
		
		public String toString(){
			return new String(Buffer);
		}
	}
	
	
//	public static class NTSTATUS extends Structure {
//		    public Pointer ProcessHandle;
//		    public int ProcessInformationClass;
//		    public Pointer ProcessInformation;
//		    public int ProcessInformationLength;
//		    public IntByReference ReturnLength;
//		   }


	
	
	public static final int ProcessBasicInformation=0;
	public static final int ProcessDebugPort=7;
	public static final int ProcessWow64Information=26;
	public static final int ProcessImageFileName=27;
	
	
	/*
	 * http://msdn.microsoft.com/en-us/library/ms684280(VS.85).aspx
	 */
	Pointer NtQueryInformationProcess( Pointer ProcessHandle, int ProcessInformationClass,
			PROCESS_BASIC_INFORMATION ProcessInformation,
			int ProcessInformationLength, IntByReference ReturnLength);

    
}