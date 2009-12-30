package luz.dsexplorer.datastructures.simple;

import luz.dsexplorer.datastructures.DSType;

import com.sun.jna.Memory;


public class Float extends DefaultDatastructure{

	public Float(){
		super();
		name=getClass().getSimpleName();
		byteCount=4;
	};
	
	@Override
	public Object eval(Memory buffer) {
		return buffer.getFloat(0);
	}
	
	@Override
	public DSType getType(){
		return DSType.Float;
	}

}
