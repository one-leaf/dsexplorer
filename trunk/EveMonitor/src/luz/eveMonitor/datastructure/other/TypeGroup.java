package luz.eveMonitor.datastructure.other;

import java.util.LinkedList;
import java.util.List;

import luz.eveMonitor.entities.eveMon.Order;

public class TypeGroup {
	private OrderSet sells=new OrderSet();
	private OrderSet buys=new OrderSet();
	private List<Transaction> trans;
	private int typeID;
	private int stamp;
	
	

	public List<Transaction> getTrans() {
		return trans;
	}

	public int getTypeID() {
		return typeID;
	}
	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}
	public int getStamp() {
		return stamp;
	}
	public void setStamp(int stamp) {
		this.stamp = stamp;
	}
	
	
	public void createTrans(TransactionSettings settings) {
		trans=new LinkedList<Transaction>();
		for (Order buy : buys) {
			for (Order sell : sells) {
				double win=sell.getPrice()*0.995-buy.getPrice();	//1% tax & Accounting 5 -> 0.5% tax = 99.5% rest
				if(win>0){
					Transaction t=new Transaction(buy, sell);
					t.calcWin(settings);
					trans.add(t);		
				}
			}
		}		
	}
	public void addBuy(Order order) {
		buys.add(order);
	}
	
	public void addSell(Order order) {
		sells.add(order);
	}

	public int getOrderCount() {
		return buys.size()+sells.size();
	}
	
	

}
