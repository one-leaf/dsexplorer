package luz.dsexplorer.datastructures;

import com.sun.jna.Memory;


public interface Datastructure {
	
	public int getByteCount();
	public void setByteCount(int byteCount);
	public boolean isByteCountFix();
	
	public String getName();
	public void setName(String name);
	
	public Object eval(Memory buffer);
	public String valueToString(Object value);
	
	public boolean isContainer();
	public Datastructure getContainer();
	public void setContainer(Datastructure container);
	
	public void addListener(DSListener listener);
	public void removeListener(DSListener listener);
	
	public DSType getType();

}
