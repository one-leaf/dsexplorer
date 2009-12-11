package luz.dsexplorer.winapi.objects;

import luz.dsexplorer.objects.datastructure.DSType;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public interface Process {

	public void ReadProcessMemory(Pointer pointer, Pointer outputBuffer, int nSize, IntByReference outNumberOfBytesRead) throws Exception;
	
	public String getSzExeFile();
	public String getModuleFileNameExA();

	public int getPid();

	public void addHwnd(Pointer hWnd);

	public Object getIcon();

	public ResultList search(long from, long to, final String value, final DSType type) throws Exception;
}
