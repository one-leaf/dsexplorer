package luz.dsexplorer.datastructures;

import luz.dsexplorer.datastructures.simple.Ascii;
import luz.dsexplorer.datastructures.simple.Byte1;
import luz.dsexplorer.datastructures.simple.Byte2;
import luz.dsexplorer.datastructures.simple.Byte4;
import luz.dsexplorer.datastructures.simple.Byte8;
import luz.dsexplorer.datastructures.simple.ByteArray;
import luz.dsexplorer.datastructures.simple.Double;
import luz.dsexplorer.datastructures.simple.Float;
import luz.dsexplorer.datastructures.simple.TimeUnix;
import luz.dsexplorer.datastructures.simple.TimeW32;
import luz.dsexplorer.datastructures.simple.Unicode;

public enum DSType{
	//           class
	Byte1		(Byte1.class), 
	Byte2		(Byte2.class), 
	Byte4		(Byte4.class), 
	Byte8		(Byte8.class), 
	Float		(Float.class), 
	Double		(Double.class), 
	Ascii		(Ascii.class), 
	Unicode		(Unicode.class), 
	ByteArray	(ByteArray.class),
	TimeW32		(TimeW32.class),
	TimeUnix	(TimeUnix.class),
	Container	(ContainerImpl.class);
	private Class<? extends Datastructure> clazz;

	DSType(Class<? extends Datastructure> clazz){
		this.clazz=clazz;
	}
	
	public Datastructure getInstance(){
		Datastructure instance=null;
		try {
			instance=(Datastructure)clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return instance;
	}
	
 
	

}