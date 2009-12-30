package luz.dsexplorer.datastructures.simple;

import luz.dsexplorer.datastructures.DSType;

import com.sun.jna.Memory;


public class Byte8 extends DefaultDatastructure{

	public Byte8(){
		super();
		name=getClass().getSimpleName();
		byteCount=8;
	};
	
	@Override
	public Object eval(Memory buffer) {
		return buffer.getLong(0);
	}
	
	@Override
	public DSType getType(){
		return DSType.Byte8;
	}

}
