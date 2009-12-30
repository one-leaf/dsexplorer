package luz.dsexplorer.datastructures.simple;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import luz.dsexplorer.datastructures.DSType;

import com.sun.jna.Memory;


public class TimeUnix extends DefaultDatastructure{

	public TimeUnix(){
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
		return DSType.TimeUnix;
	}
	
	@Override
	public String valueToString(Object value) {
		if (value==null)
			return null;

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis((Long)value);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		return sdf.format(cal.getTime());
	}

}
