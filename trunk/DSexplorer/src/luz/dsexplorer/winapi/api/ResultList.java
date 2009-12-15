package luz.dsexplorer.winapi.api;

import javax.swing.tree.MutableTreeNode;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;


public interface ResultList extends MutableTreeNode {

	public void ReadProcessMemory(Pointer pointer, Pointer outputBuffer, int nSize, IntByReference outNumberOfBytesRead) throws Exception;
    public void add(MutableTreeNode newChild);

}
