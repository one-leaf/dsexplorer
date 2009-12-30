package luz.dsexplorer.datastructures.simple;

import luz.dsexplorer.datastructures.DSType;

import com.sun.jna.Memory;


public class Byte1 extends DefaultDatastructure{

	public Byte1(){
		super();
		name=getClass().getSimpleName();
		byteCount=1;
	};
	
	@Override
	public Object eval(Memory buffer) {
		return buffer.getByte(0);
	}
	
	@Override
	public DSType getType(){
		return DSType.Byte1;
	}

}
