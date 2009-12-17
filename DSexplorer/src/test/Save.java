package test;

import java.io.FileOutputStream;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class Save {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Throwable {
			Xy x= new Xy();
		
		
		
			Serializer serializer = new Persister();	
			FileOutputStream fos = new FileOutputStream("test.xml");
			
			serializer.write(x, fos);
			fos.close();


	}
	
	@Root
	private static class Xy{
		@Element
		private int a = 42;
		
		
	}

}
