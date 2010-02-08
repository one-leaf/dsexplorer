package test;


public class Test2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Long offset=-5456L;
		int index=0;
		String r = offset==null?"????????":String.format("%08X", offset+index*16);
		System.out.println(r);

	}

}
