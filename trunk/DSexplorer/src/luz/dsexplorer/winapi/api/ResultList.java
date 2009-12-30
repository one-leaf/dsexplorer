package luz.dsexplorer.winapi.api;

import java.io.File;

import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import luz.dsexplorer.exceptions.NoProcessException;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;


public interface ResultList extends TreeNode, TreeModel {

	public void ReadProcessMemory(Pointer pointer, Pointer outputBuffer, int nSize, IntByReference outNumberOfBytesRead) throws NoProcessException, Exception;
    public void add(Result child);
	public void remove(Result child);
	public void setProcess(Process p);
	public Process getProcess();
	public void removeAllChildren();
	public void saveToFile(File file) throws Exception;
	
    public void reload(TreeNode node);
    public void nodeChanged(TreeNode node);
    public void nodeInserted(TreeNode node);
    public void nodeRemoved(TreeNode node, int index);

}
