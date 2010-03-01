package test;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

		int accounting=4;		
		double buy=24900;
		double sell=29500;

		
		int items = 732;
		double win=(items*sell)*(1-(10-accounting)/1000d)-items*buy;
		
		System.out.println(win);
		System.out.println(items*sell);
		System.out.println(items*buy);
			

		System.out.println(1- (1d/100d * (1-10d*accounting/100d)) );
		
		System.out.println(1- (1d/100d * (100d-10d*accounting)/100d) );
		
		System.out.println(            1 -(10-accounting)/1000d );
		
		System.out.println(  1000d/1000d -(10-accounting)/1000d );
		
		System.out.println(  (1000d-10+accounting)/1000d );
	}

}
