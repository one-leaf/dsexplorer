package luz.dsexplorer.gui;

import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import luz.dsexplorer.winapi.api.Process;
import luz.dsexplorer.winapi.api.Result;
import luz.dsexplorer.winapi.api.ResultListImpl;

public class ProcessTree extends JTree {
	private static final long serialVersionUID = 8889377903469038055L;
	private ResultListImpl rl;
	private DefaultTreeModel model;
	private TreeSelectionModel sm = getSelectionModel();

	
	public ProcessTree() {
		this.setModel(null);
		this.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		

	}
	
	public void setProcess(Process p){
		if (rl==null){
			rl = new ResultListImpl(p);
			model=new DefaultTreeModel(rl);
			this.setModel(model);
			sm.setSelectionPath(new TreePath(rl));		
		}else{
			rl.setProcess(p);
			model.nodeChanged(rl);
		}
	}
	
	public void addResult(Result result) {
		Result r;
		int begin=rl.getChildCount();
		int[] indexes= new int[1];
		{
			r = result.clone();		//create clones to avoid any border effects
			rl.add(r);
			indexes[0]=begin;
		}
		model.nodesWereInserted(rl, indexes);
	}

	public void addResults(List<Result> results) {
		Result r;
		int begin=rl.getChildCount();
		int[] indexes= new int[results.size()];
		
		for (int i = 0; i < indexes.length; i++) {
			r=results.get(i).clone();//create clones to avoid any border effects
			rl.add(r);
			indexes[i]=begin+i;
		}
		model.nodesWereInserted(rl, indexes);
	}
	
	public void refresh(){
		TreePath selection = sm.getSelectionPath();
		model.reload();		//FIXME selection disapears
							//FIXME expansion disapears
		sm.setSelectionPath(selection);
		this.expandPath(selection);	
	}

	public void refresh(Result result) {
		model.nodeStructureChanged(result);	//nodeChanged is not enough
	}

	public void reset() {
		rl.removeAllChildren();
		model.nodeStructureChanged(rl);
	}
	
	public void saveToFile(File file){
		try {
			rl.saveToFile(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void openFromFile(File file){
		try {
			reset();
			rl.openFromFile(file);
			refresh();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
