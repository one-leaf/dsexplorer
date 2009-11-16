package luz.dsexplorer.gui;

import java.awt.Font;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import luz.dsexplorer.objects.Process;
import luz.dsexplorer.objects.Result;
import luz.dsexplorer.objects.ResultList;

public class ProcessTree extends JTree {
	private static final long serialVersionUID = 8889377903469038055L;
	private ResultList rl;
	private DefaultTreeModel model;
	private TreeSelectionModel sm = getSelectionModel();
	
	public ProcessTree() {
		this.setModel(null);
		this.setFont(new Font("Lucida Console", Font.PLAIN, 11));
	}
	
	public void setProcess(Process p){
		rl = new ResultList(p);
		model=new DefaultTreeModel(rl);
		this.setModel(model);
		sm.setSelectionPath(new TreePath(model.getRoot()));
	}
	
	public void addPointer(Long pointer){
		addResult(new Result(pointer, null));
	}

	public void addResult(Result result) {
		rl.add(result);
		refresh();
	}

	public void addResults(List<Result> results) {
		for (Result result : results)
			rl.add(result);
		refresh();		
	}
	
	public void refresh(){
		TreePath selection = sm.getSelectionPath();
		model.reload();		//FIXME selection disapears
		sm.setSelectionPath(selection);
	}

}
