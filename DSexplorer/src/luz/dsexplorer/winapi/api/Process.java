package luz.dsexplorer.winapi.api;


import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public interface Process {

	public void ReadProcessMemory(Pointer pointer, Pointer outputBuffer, int nSize, IntByReference outNumberOfBytesRead) throws Exception;
	public String getSzExeFile();
	public String getModuleFileNameExA();
	public int getPid();
	public void addHwnd(Pointer hWnd);
	public Object getIcon();
	public ResultList search(long from, long to, final String value, final MemoryListener listener) throws Exception;
	public String getStatic(Long address);
}
