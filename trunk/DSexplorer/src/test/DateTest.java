package test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTest {

    public static void main(String[] args) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss");
		
		
		Long value = 129057172789500000L;

		Calendar cal = Calendar.getInstance();
		System.out.println(cal.getTimeInMillis());
		cal.setTimeInMillis((value-116444736000000000L)/10000);	//01.01.1601 00:00:00,000



		System.out.println(sdf.format(cal.getTime()));

		
		
		
		
		
		Long v2 = 116444736000000000L;
		cal.setTimeInMillis(-v2/10000L);
		System.out.println(sdf.format(cal.getTime()));
		
		
		
		
		
    }
	
	
}
