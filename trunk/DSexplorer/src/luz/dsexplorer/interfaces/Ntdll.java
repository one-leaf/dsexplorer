package luz.dsexplorer.interfaces;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;

public interface Ntdll extends StdCallLibrary{
	Ntdll INSTANCE = (Ntdll) Native.loadLibrary("ntdll", Ntdll.class);

	public static class PROCESS_BASIC_INFORMATION extends Structure {
		public int ExitStatus;
		public int PebBaseAddress;
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
		public RTL_USER_PROCESS_PARAMETERS ProcessParameters=new RTL_USER_PROCESS_PARAMETERS();
		public byte[] Reserved4=new byte[104];
		public byte[] Reserved5=new byte[52];
		public Pointer PostProcessInitRoutine;
		public byte[] Reserved6=new byte[128];
		public byte[] Reserved7=new byte[1];
		public int SessionId;
	}
	
	/*
	 * http://msdn.microsoft.com/en-us/library/aa813741(VS.85).aspx
	 * http://undocumented.ntinternals.net/UserMode/Structures/RTL_USER_PROCESS_PARAMETERS.html
	 */
	public static class RTL_USER_PROCESS_PARAMETERS extends Structure {
		public byte[] Reserved1=new byte[16];
		public byte[] Reserved2=new byte[10];
		public UNICODE_STRING ImagePathName=new UNICODE_STRING();
		public UNICODE_STRING CommandLine=new UNICODE_STRING();
	}
	
	public static class UNICODE_STRING extends Structure {
		public short Length;
		public short MaximumLength;
		public byte[] Buffer=new byte[256];
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