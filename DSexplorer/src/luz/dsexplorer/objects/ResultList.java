package luz.dsexplorer.objects;

import javax.swing.tree.DefaultMutableTreeNode;

public class ResultList extends DefaultMutableTreeNode{
	private static final long serialVersionUID = 2132455546694390451L;
	private Process process;
	
	public ResultList(Process process){
		this.process=process;
	}
	
	////////////////////////////////////////
	
	@Override
	public boolean isLeaf() {
		return false;
	}
	
	@Override
	public int getChildCount() {
		return super.getChildCount();
	}

	
	@Override
	public String toString() {
		if (process==null)
			return null;
		return process.getSzExeFile();
	}

}
