package luz.dsexplorer.winapi.api;

import java.io.File;

import javax.swing.tree.MutableTreeNode;

import luz.dsexplorer.exceptions.NoProcessException;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;


public interface ResultList extends MutableTreeNode {

	public void ReadProcessMemory(Pointer pointer, Pointer outputBuffer, int nSize, IntByReference outNumberOfBytesRead) throws NoProcessException, Exception;
    public void add(MutableTreeNode newChild);
	public void setProcess(Process p);
	public void removeAllChildren();
	public void saveToFile(File file) throws Exception;

}
