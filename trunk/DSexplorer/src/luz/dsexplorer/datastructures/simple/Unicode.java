package luz.dsexplorer.datastructures.simple;

import luz.dsexplorer.datastructures.DSType;

import com.sun.jna.Memory;


public class Unicode extends DefaultDatastructure{

	public Unicode(){
		super();
		name=getClass().getSimpleName();
		byteCount=32;
		byteCountFix=false;
	};
	
	@Override
	public Object eval(Memory buffer) {
		return buffer.getString(0, true).substring(0,this.byteCount/2);
		//return buffer.getString(0, true);
	}
	
	@Override
	public DSType getType(){
		return DSType.Unicode;
	}

}
