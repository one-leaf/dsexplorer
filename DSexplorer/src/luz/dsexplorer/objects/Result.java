package luz.dsexplorer.objects;

import javax.swing.tree.DefaultMutableTreeNode;

public class Result extends DefaultMutableTreeNode{
	private static final long serialVersionUID = 4163723615095260358L;
	private Long pointer;
	private boolean areChildrenDefined=false;
	
	public Result(Long pointer){
		this.pointer=pointer;
	}
	
	public long getPointer(){
		return pointer;
	}
	
	public void setPointer(Long pointer){
		this.pointer=pointer;
	}
	
	////////////////////////////////////////
	
	@Override
	public boolean isLeaf() {
		return true;
	}
	
	@Override
	public int getChildCount() {
		if (!areChildrenDefined)
			defineChildNodes();
		return super.getChildCount();
	}
	
	
	private void defineChildNodes() {
		areChildrenDefined = true;
		add(new Result(null));
	}
	
	@Override
	public String toString() {
		if (pointer==null)
			return null;
		else 
			return pointer.toString();
	}
}
