package luz.dsexplorer.datastructures.simple;

import luz.dsexplorer.datastructures.DSType;

import com.sun.jna.Memory;


public class Double extends DefaultDatastructure{

	public Double(){
		super();
		name=getClass().getSimpleName();
		byteCount=8;
	};
	
	@Override
	public Object eval(Memory buffer) {
		return buffer.getDouble(0);
	}
	
	@Override
	public DSType getType(){
		return DSType.Double;
	}

}
