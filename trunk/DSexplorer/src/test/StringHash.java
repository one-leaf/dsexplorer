package test;

public class StringHash {
	
	public static void main(String[] args) throws Exception {
		
		String x = "orderCache";	//0x8FDE2CCC
		//String x ="http://stackoverflow.com";	//Result: 0x73515C63, 1934711907
	
		long hash=pyStringHash(x);
		System.out.println(hash);
		System.out.println(String.format("0x%08X", hash));
		
		
		
	}
	
	private static int pyStringHashInt(String string){
		int len = string.length();
		long x = string.charAt(0) << 7;
		for (int index=0; index < len; index++)
			x = (1000003*x) ^ string.charAt(index);
		x ^= len;
		return (int)x;
	}
	
	private static long pyStringHash(String string){
		int len = string.length();
		long x = string.charAt(0) << 7;
		for (int index=0; index < len; index++)
			x = (1000003*x) ^ string.charAt(index);
		x ^= len;
		x = x&0xFFFFFFFFL;	//unsinged int
		return x;
	}


}
