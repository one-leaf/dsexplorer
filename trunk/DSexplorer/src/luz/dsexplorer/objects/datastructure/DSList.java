package luz.dsexplorer.objects.datastructure;

import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.MutableComboBoxModel;

public class DSList  extends AbstractListModel implements MutableComboBoxModel {
	private static final long serialVersionUID = 4670423301077836254L;
	List<Datastructure> datastructures = new LinkedList<Datastructure>();
	Datastructure selected=null;
	
	public DSList(){
	}
	
	@Override
	public Datastructure getElementAt(int i) {
		if (datastructures.size()==0)
			datastructures.add(new Datastructure("new custom"));
		return datastructures.get(i);
	}

	@Override
	public int getSize() {
		if (datastructures.size()==0)
			datastructures.add(new Datastructure("new custom"));
		return datastructures.size();
	}
	
	

	@Override
	public Datastructure getSelectedItem() {
		return selected;
	}

	@Override
	public void setSelectedItem(Object obj) {
		selected=(Datastructure)obj;		
	}

	@Override
	public void addElement(Object obj) {
		System.out.println(obj.hashCode());
		
		if (datastructures.contains(obj))
			return;		//Avoid duplicates
		
		datastructures.add((Datastructure)obj);
		int index=datastructures.indexOf(obj);
		fireIntervalAdded(this, index, index);
	}

	@Override
	public void insertElementAt(Object obj, int index) {
		if (datastructures.contains(obj))
			return;		//Avoid duplicates
		datastructures.add(index, (Datastructure)obj);
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
