package luz.winapi.api;


import luz.winapi.api.exception.OpenProcessException;
import luz.winapi.api.exception.ReadProcessMemoryException;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public interface Process {

	public void ReadProcessMemory(Pointer pointer, Pointer outputBuffer, int nSize, IntByReference outNumberOfBytesRead) throws OpenProcessException, ReadProcessMemoryException;
	public String getSzExeFile();
	public String getModuleFileNameExA();
	public int getPid();
	public void addHwnd(Pointer hWnd);
	public Object getIcon();
	public void search(long from, long to, final String value, final MemoryListener listener) throws Exception;
	public String getStatic(Long address);
}
