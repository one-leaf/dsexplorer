package luz.dsexplorer.datastructures;

import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.MutableComboBoxModel;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class DSList  extends AbstractListModel implements MutableComboBoxModel {
	private static final long serialVersionUID = 4670423301077836254L;
	@ElementList(inline=true, required=false)
	List<Container> datastructures = new LinkedList<Container>();
	Container selected=null;
	
	public DSList(){
	}
	
	@Override
	public Container getElementAt(int i) {
		if (datastructures.size()==0)
			datastructures.add(new ContainerImpl());
		return datastructures.get(i);
	}

	@Override
	public int getSize() {
		if (datastructures.size()==0)
			datastructures.add(new ContainerImpl());
		return datastructures.size();
	}
	
	

		@Override
	public Container getSelectedItem() {
		return selected;
	}

	@Override
	public void setSelectedItem(Object obj) {
		selected=(Container)obj;		
	}

	@Override
	public void addElement(Object obj) {
		//System.out.println(obj.hashCode());
		
		if (datastructures.contains(obj))
			return;		//Avoid duplicates
		
		datastructures.add((Container)obj);
		int index=datastructures.indexOf(obj);
		fireIntervalAdded(this, index, index);
	}

	@Override
	public void insertElementAt(Object obj, int index) {
		if (datastructures.contains(obj))
			return;		//Avoid duplicates
		datastructures.add(index, (Container)obj);
		fireIntervalAdded(this, index, index);
	}

	@Override
	public void removeElement(Object obj) {
		int index=datastructures.indexOf(obj);
		datastructures.remove(obj);		
		fireIntervalRemoved(this, index, index);
	}

	@Override
	public void removeElementAt(int index) {
		datastructures.remove(index);
		fireIntervalRemoved(this, index, index);
	}




}
