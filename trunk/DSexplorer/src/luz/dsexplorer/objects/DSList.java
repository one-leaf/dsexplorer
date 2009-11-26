package luz.dsexplorer.objects;

import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.MutableComboBoxModel;

public class DSList  extends AbstractListModel implements MutableComboBoxModel {
	private static final long serialVersionUID = 4670423301077836254L;
	List<Datastructure> datastructures = new LinkedList<Datastructure>();
	Datastructure selected=null;
	
	public DSList(){
		datastructures.add(new Datastructure("new custom"));
	}
	
	@Override
	public Datastructure getElementAt(int i) {
		return datastructures.get(i);
	}

	@Override
	public int getSize() {
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
		datastructures.add((Datastructure)obj);		
	}

	@Override
	public void insertElementAt(Object obj, int i) {
		datastructures.add(i, (Datastructure)obj);	
	}

	@Override
	public void removeElement(Object obj) {
		datastructures.remove(obj);
	}

	@Override
	public void removeElementAt(int i) {
		datastructures.remove(i);
	}




}
