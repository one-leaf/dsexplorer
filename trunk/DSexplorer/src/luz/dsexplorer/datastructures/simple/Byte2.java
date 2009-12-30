package luz.dsexplorer.datastructures.simple;

import luz.dsexplorer.datastructures.DSType;

import com.sun.jna.Memory;


public class Byte2 extends DefaultDatastructure{

	public Byte2(){
		super();
		name=getClass().getSimpleName();
		byteCount=2;
	};
	
	@Override
	public Object eval(Memory buffer) {
		return buffer.getShort(0);
	}
	
	@Override
	public DSType getType(){
		return DSType.Byte2;
	}

}
