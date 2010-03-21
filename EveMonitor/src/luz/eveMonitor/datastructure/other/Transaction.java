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

	public long getMaxItemNumber(TransactionSettings ts){
		double maxBuy  = buy.getVolRem();
		double maxSell = sell.getVolRem();		
		double maxVol   = ts.getMaxVolume() / buy.getType().getVolume();
		double maxPrice = ts.getMaxMoney()  / buy.getPrice();
		
		double items=Math.min(maxBuy, maxSell)*ts.getVolMult();
		items=Math.min(items, maxVol);
		items=Math.min(items, maxPrice);		
		return (long)items;
	}
	
	public long getMaxCargo(TransactionSettings ts) {
		return (long)(ts.getMaxVolume() / buy.getType().getVolume());
	}
	
	public double calcWin(TransactionSettings settings) {
		if(buy.getSystem().getSecurity()<settings.getSecurity().min 
				|| sell.getSystem().getSecurity()<settings.getSecurity().min){
			win=-999999999;
		}else{	
			items = getMaxItemNumber(settings);
			win=(items*sell.getPrice())*(1-(10-settings.getAccounting())/1000d)-items*buy.getPrice();
		}
		return win;
	}
	
	public double calcPercent(TransactionSettings ts){
		int accounting = ts.getAccounting();
		items = getMaxItemNumber(ts);
		double gain=(items*sell.getPrice())*(1-(10-accounting)/1000d);
		double loss=items*buy.getPrice();
		return (gain-loss)/loss;		
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