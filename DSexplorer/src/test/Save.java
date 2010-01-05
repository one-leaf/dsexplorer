package test;

import java.io.FileOutputStream;

import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class Save {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Throwable {
			R r = new R(new B());
			
//			Byte4 b = new Byte4();
//			Result r = new Result(null, b, 42L, 123);
		
			Serializer serializer = new Persister();	
			FileOutputStream fos = new FileOutputStream("test.xml");
			
			serializer.write(r, fos);
			fos.close();


	}
	


	
	@Root
	private static class R{
		private I i;
		
		public R(I i){
			this.i=i;
		}
	}
	
	private static interface I{}
	
	@Root
	private static class D implements I{}	
	
	@Root(name="bb")
	private static class B extends D{}
	

	
	

}
