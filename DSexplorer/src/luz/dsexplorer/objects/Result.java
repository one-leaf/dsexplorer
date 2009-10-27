package luz.dsexplorer.objects;

import javax.swing.tree.DefaultMutableTreeNode;


import com.sun.jna.Pointer;

public class Result extends DefaultMutableTreeNode{
	private static final long serialVersionUID = 4163723615095260358L;
	private Pointer pointer;
	private boolean areChildrenDefined=false;
	
	public Result(Pointer pointer){
		this.pointer=pointer;
	}
	
	public Result(long pointer){
		this.pointer=Pointer.createConstant(pointer);
	}
	
	public Pointer getPointer(){
		return pointer;
	}
	
	public void setPointer(Pointer pointer){
		this.pointer=pointer;
	}
	
	public void setPointer(long pointer){
		this.pointer=Pointer.createConstant(pointer);
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
