package luz.dsexplorer.datastructures;

import java.util.List;

public interface Container extends Datastructure {
	public List<Datastructure> getFields();
	public void addField(Datastructure field);
	public void addField (Datastructure field, int index);
	public void removeField(int fieldIndex);
	public void replaceField(Datastructure oldField, Datastructure newField, int index);
	
	public void setPointer(boolean pointer);
	public boolean isPointer();
	
	public int getOffset(int fieldIndex);

}
