package test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTest {

    public static void main(String[] args) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss");
		
		
		Long value = 129057172789500000L;
		Date d= new Date((value-116444736000000000L)/10000L);
		System.out.println(sdf.format(d));
		
		
		
		
		Long v2 = 116444736000000000L;
		d= new Date(-v2/10000L);
		System.out.println(sdf.format(d));
		
    }	
}
