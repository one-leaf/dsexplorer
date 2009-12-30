package luz.dsexplorer.datastructures.simple;

import luz.dsexplorer.datastructures.DSType;

import com.sun.jna.Memory;


public class Byte4 extends DefaultDatastructure{

	public Byte4(){
		super();
		name=getClass().getSimpleName();
		byteCount=4;
	};
	
	@Override
	public Object eval(Memory buffer) {
		return buffer.getInt(0);
	}
	
	@Override
	public DSType getType(){
		return DSType.Byte4;
	}

}
