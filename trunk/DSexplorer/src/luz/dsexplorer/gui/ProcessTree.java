package luz.dsexplorer.gui;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;

import luz.dsexplorer.objects.Process;
import luz.dsexplorer.objects.ResultList;

public class ProcessTree extends JTree {
	private static final long serialVersionUID = 8889377903469038055L;
	
	public ProcessTree() {
		this.setModel(null);
	}
	
	public void setProcess(Process p){
		this.setModel(new DefaultTreeModel(new ResultList(p)));
	}

}
