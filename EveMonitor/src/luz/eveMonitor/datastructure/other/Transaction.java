package luz.eveMonitor.datastructure.other;

import luz.eveMonitor.entities.eveMon.Order;

public class Transaction implements Comparable<Transaction>{
	public Order buy;
	public Order sell;
	public double win;
	public long items;
	
	public Transaction(Order buy, Order sell) {
		this.buy=buy;
		this.sell=sell;
	}
	
	public long getMaxItemNumber(double volume, double money){
		double maxBuy  = buy.getVolRem();
		double maxSell = sell.getVolRem();		
		double maxVol   = volume / buy.getType().getVolume();
		double maxPrice = money  / buy.getPrice();
		
		double items=Math.min(maxBuy, maxSell);
		items=Math.min(items, maxVol);
		items=Math.min(items, maxPrice);		
		return (long)items;
	}
	
	public double calcWin(TransactionSettings settings) {
		return calcWin(settings.getMaxMoney(), settings.getMaxVolume(), settings.getAccounting(), settings.getSecurity().min);		
	}
	
	public double calcWin(double money, double volume, int accounting, double security){
		if(buy.getSystem().getSecurity()<security || sell.getSystem().getSecurity()<security){
			win=-999999999;
		}else{	
			items = getMaxItemNumber(volume, money);
			win=(items*sell.getPrice())*(1-(10-accounting)/1000d)-items*buy.getPrice();
		}
		return win;
	}
	
	public double getVolume(){
		return buy.getType().getVolume();
	}
	
	@Override
	public int compareTo(Transaction that) {
		double diff=that.win-this.win;
		if(diff>0) 
			return 1;
		else if (diff<0) 
			return -1;
		return 0;
	}

	
	//Object///////////////////////////////////////////////

	@Override
	public int hashCode() {
		return 31*buy.hashCode()+sell.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o==null)
			return false;
		
		if(!(o instanceof Transaction))
			return false;		
		Transaction that=(Transaction)o;		

		return this.buy.equals(that.buy) && this.sell.equals(that.sell);
	}

	
}