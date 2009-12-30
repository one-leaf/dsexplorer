package luz.dsexplorer.datastructures.simple;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import luz.dsexplorer.datastructures.DSType;

import com.sun.jna.Memory;


public class TimeW32 extends DefaultDatastructure{

	public TimeW32(){
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
		return DSType.TimeW32;
	}
	
	@Override
	public String valueToString(Object value) {
		if (value==null)
			return null;

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(((Long)value-116444736000000000L)/10000L);	//1601.01.01 00:00:00 GMT+0
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		return sdf.format(cal.getTime());
	}

}
