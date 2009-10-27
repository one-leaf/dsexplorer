package luz.dsexplorer.gui;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import luz.dsexplorer.objects.Process;

public class ProcessTree extends JTree {
	private static final long serialVersionUID = 8889377903469038055L;
	
	public ProcessTree() {
		this.setModel(null);
	}
	
	public void setProcess(Process p){
		this.setModel(new DefaultTreeModel(new OutlineNode(p)));
	}
	
	
	private final class OutlineNode extends DefaultMutableTreeNode {
		private static final long serialVersionUID = -6288860782298224755L;
		private boolean areChildrenDefined = false;
		private Object node;
		private String name;
		@SuppressWarnings("unchecked")
		private Class clazz;
		
		public OutlineNode(Object object) {
			this.node = object;
			this.name = "";
			this.clazz = object==null?Object.class:object.getClass();
		}

		@SuppressWarnings("unchecked")
		public OutlineNode(Object objects, Class clazz, String name) {
			this.node = objects;
			this.name = name;
			this.clazz = clazz;
		}
		
		public Object getNode(){
			return node;
		}
		
		public String getNodeInfo(){
			if (node==null)
				return "<null>";
			
			String info="<err>";
			try{
				info=node.toString();
			}catch(Throwable e){}			
			return info;
		}
		
		

		@Override
		public boolean isLeaf() {
			if (node == null)				return true;
			if (node instanceof Character)	return true;
			if (node instanceof String)	return true;
			if (node instanceof Number)	return true;
			if (node instanceof Date)		return true;
			if (node instanceof Boolean)	return true;
			return false;
		}

		@Override
		public int getChildCount() {
			if (!areChildrenDefined)
				defineChildNodes();
			return (super.getChildCount());
		}

		@SuppressWarnings({ "unchecked"})
		private void defineChildNodes() {
			areChildrenDefined = true;
			try {
				if (node instanceof Object[]){
					for (Object object : (Object[]) node)
						add(new OutlineNode(object));
				
				}else if (node instanceof List){
					for (Object object : (List) node)
						add(new OutlineNode(object));
				
				}else if (node instanceof Set){
					for (Object object : (Set) node)
						add(new OutlineNode(object));
				
				}else if (node instanceof Map){
					for (Object object : ((Map)node).entrySet())
						add(new OutlineNode(object));
			
				} else{
					addFields(node);
				} 
			}catch (Throwable e) {}

		}
		
		@SuppressWarnings("unchecked")
		private void addFields(Object obj){
			Class clazz = obj.getClass();
			while (clazz!=Object.class){
				for (Field field : clazz.getDeclaredFields()) {
					field.setAccessible(true);
					try {
						add(new OutlineNode(field.get(obj), field.getType(), field.getName()));
					} catch (Throwable e) {}
				}
				clazz=clazz.getSuperclass();
			}
		}

		@Override
		public String toString() {
			if (getParent()==null) return "root";		
			return "["+clazz.getSimpleName()+"] "+name+"="+getNodeInfo();
		}
	}

}
